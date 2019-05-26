package edu.wpi.jlyu.sceneformfurniture;

import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.internal.NavigationMenu;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.google.ar.core.Anchor;
import com.google.ar.core.Anchor.CloudAnchorState;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.Plane.Type;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ModelRenderable.Builder;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;
import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.FabSpeedDial.MenuListener;

public class MainActivity extends AppCompatActivity {
    private AppAnchorState appAnchorState = AppAnchorState.NONE;
    private Anchor cloudAnchor;
    FabSpeedDial fabSpeedDial;
    private CustomArFragment fragment;
    private Uri selectedObject;
    private SnackbarHelper snackbarHelper = new SnackbarHelper();
    private StorageManager storageManager;

    /* renamed from: edu.wpi.jlyu.sceneformfurniture.MainActivity$1 */
    class C00291 implements MenuListener {
        C00291() {
        }

        public boolean onPrepareMenu(NavigationMenu navigationMenu) {
            return true;
        }

        public boolean onMenuItemSelected(MenuItem menuItem) {
            Toast.makeText(MainActivity.this.getApplicationContext(), menuItem.getTitle(), 0).show();
            return false;
        }

        public void onMenuClosed() {
        }
    }

    /* renamed from: edu.wpi.jlyu.sceneformfurniture.MainActivity$2 */
    class C00302 implements OnClickListener {
        C00302() {
        }

        public void onClick(View v) {
            MainActivity.this.setCloudAnchor(null);
        }
    }

    /* renamed from: edu.wpi.jlyu.sceneformfurniture.MainActivity$3 */
    class C00313 implements OnClickListener {
        C00313() {
        }

        public void onClick(View v) {
            if (MainActivity.this.cloudAnchor != null) {
                MainActivity.this.snackbarHelper.showMessageWithDismiss(MainActivity.this.getParent(), "Please clear anchor.");
                return;
            }
            ResolveDialogFragment dialog = new ResolveDialogFragment();
            dialog.setOkListener(new -$$Lambda$MainActivity$3$t1wwlr8A3SGDE8FGvhyHVofpuU4(MainActivity.this));
            dialog.show(MainActivity.this.getSupportFragmentManager(), "Resolve");
        }
    }

    private enum AppAnchorState {
        NONE,
        HOSTING,
        HOSTED,
        RESOLVING,
        RESOLVED
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0032R.layout.activity_main);
        this.fabSpeedDial = (FabSpeedDial) findViewById(C0032R.id.fabID);
        this.fabSpeedDial.setMenuListener(new C00291());
        this.fragment = (CustomArFragment) getSupportFragmentManager().findFragmentById(C0032R.id.sceneform_fragment);
        this.fragment.getPlaneDiscoveryController().hide();
        this.fragment.getArSceneView().getScene().setOnUpdateListener(new -$$Lambda$MainActivity$7IRhXW2hfeVbUzxKRXw2QSCH9xA());
        InitializeGallery();
        ((Button) findViewById(C0032R.id.clear_button)).setOnClickListener(new C00302());
        ((Button) findViewById(C0032R.id.resolve_button)).setOnClickListener(new C00313());
        this.fragment.setOnTapArPlaneListener(new -$$Lambda$MainActivity$2COOJD9VFYJTiaFnLKjjG9uK5fo());
        this.storageManager = new StorageManager(this);
    }

    public static /* synthetic */ void lambda$onCreate$0(MainActivity mainActivity, HitResult hitResult, Plane plane, MotionEvent motionEvent) {
        if (plane.getType() == Type.HORIZONTAL_UPWARD_FACING) {
            if (mainActivity.appAnchorState == AppAnchorState.NONE) {
                mainActivity.setCloudAnchor(mainActivity.fragment.getArSceneView().getSession().hostCloudAnchor(hitResult.createAnchor()));
                mainActivity.appAnchorState = AppAnchorState.HOSTING;
                mainActivity.snackbarHelper.showMessage(mainActivity, "Hosting Anchor...");
                mainActivity.placeObject(mainActivity.fragment, mainActivity.cloudAnchor, mainActivity.selectedObject);
            }
        }
    }

    private void onResolveOKPressed(String dialogValue) {
        this.storageManager.getCloudAnchorID(Integer.parseInt(dialogValue), new -$$Lambda$MainActivity$cTpXfy8AN7amFtjOfG-MaTiFvjo());
    }

    public static /* synthetic */ void lambda$onResolveOKPressed$1(MainActivity mainActivity, String cloudAnchorId) {
        mainActivity.setCloudAnchor(mainActivity.fragment.getArSceneView().getSession().resolveCloudAnchor(cloudAnchorId));
        mainActivity.placeObject(mainActivity.fragment, mainActivity.cloudAnchor, mainActivity.selectedObject);
        mainActivity.snackbarHelper.showMessage(mainActivity, "Resolving Anchor...");
        mainActivity.appAnchorState = AppAnchorState.RESOLVING;
    }

    private void setCloudAnchor(Anchor newAnchor) {
        if (this.cloudAnchor != null) {
            this.cloudAnchor.detach();
        }
        this.cloudAnchor = newAnchor;
        this.appAnchorState = AppAnchorState.NONE;
        this.snackbarHelper.hide(this);
    }

    private void onUpdateFrame(FrameTime frameTime) {
        checkUpdatedAnchor();
    }

    private void checkUpdatedAnchor() {
        if (this.appAnchorState == AppAnchorState.HOSTING || this.appAnchorState == AppAnchorState.RESOLVING) {
            CloudAnchorState cloudState = this.cloudAnchor.getCloudAnchorState();
            SnackbarHelper snackbarHelper;
            StringBuilder stringBuilder;
            if (this.appAnchorState == AppAnchorState.HOSTING) {
                if (cloudState.isError()) {
                    snackbarHelper = this.snackbarHelper;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Error hosting anchor: ");
                    stringBuilder.append(cloudState);
                    snackbarHelper.showMessageWithDismiss(this, stringBuilder.toString());
                    this.appAnchorState = AppAnchorState.NONE;
                } else if (cloudState == CloudAnchorState.SUCCESS) {
                    this.storageManager.nextShortCode(new -$$Lambda$MainActivity$I2NQUQole8iObP_meIBUUs_3DzU());
                    this.appAnchorState = AppAnchorState.HOSTED;
                }
            } else if (this.appAnchorState == AppAnchorState.RESOLVING) {
                if (cloudState.isError()) {
                    snackbarHelper = this.snackbarHelper;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Anchor Resolved ");
                    stringBuilder.append(cloudState);
                    snackbarHelper.showMessageWithDismiss(this, stringBuilder.toString());
                    this.appAnchorState = AppAnchorState.NONE;
                } else if (cloudState == CloudAnchorState.SUCCESS) {
                    this.snackbarHelper.showMessageWithDismiss(this, "Anchor resolved!");
                    this.appAnchorState = AppAnchorState.RESOLVED;
                }
            }
        }
    }

    public static /* synthetic */ void lambda$checkUpdatedAnchor$2(MainActivity mainActivity, Integer shortCode) {
        if (shortCode == null) {
            mainActivity.snackbarHelper.showMessageWithDismiss(mainActivity, "Anchor has been resolved-Place Furniture");
            return;
        }
        mainActivity.storageManager.storeUsingShortCode(shortCode.intValue(), mainActivity.cloudAnchor.getCloudAnchorId());
        SnackbarHelper snackbarHelper = mainActivity.snackbarHelper;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Anchor hosted! Cloud Short Code: ");
        stringBuilder.append(shortCode);
        snackbarHelper.showMessageWithDismiss(mainActivity, stringBuilder.toString());
    }

    private void InitializeGallery() {
        LinearLayout gallery = (LinearLayout) findViewById(C0032R.id.gallery_layout);
        ImageView chair = new ImageView(this);
        chair.setImageResource(C0032R.drawable.chair_thumb);
        chair.setContentDescription("chair");
        chair.setOnClickListener(new -$$Lambda$MainActivity$gkvEON0om2cPSB4DFQEHdAumDE4());
        gallery.addView(chair);
        ImageView goat = new ImageView(this);
        goat.setImageResource(C0032R.drawable.goat_thumb);
        goat.setContentDescription("goat");
        goat.setOnClickListener(new -$$Lambda$MainActivity$oKnb8mhIZwLZgAcU3LESmmgU4dM());
        gallery.addView(goat);
        ImageView lamp = new ImageView(this);
        lamp.setImageResource(C0032R.drawable.lamp_thumb);
        lamp.setContentDescription("lamp");
        lamp.setOnClickListener(new -$$Lambda$MainActivity$CyCeR1MnktSPuDzDWoyjzdJ8Mc4());
        gallery.addView(lamp);
        ImageView sofa = new ImageView(this);
        sofa.setImageResource(C0032R.drawable.sofa_thumb);
        sofa.setContentDescription("sofa");
        sofa.setOnClickListener(new -$$Lambda$MainActivity$tKIJdiKn1c_N3WVITzufthU5Qys());
        gallery.addView(sofa);
        ImageView table = new ImageView(this);
        table.setImageResource(C0032R.drawable.table_thumb);
        table.setContentDescription("table");
        table.setOnClickListener(new -$$Lambda$MainActivity$0pm0x4FRqPSz2_0kWRL-7EGjss4());
        gallery.addView(table);
        ImageView crib = new ImageView(this);
        crib.setImageResource(C0032R.drawable.crib_thumb);
        crib.setContentDescription("crib");
        crib.setOnClickListener(new -$$Lambda$MainActivity$qOTuel58l1zxxwHJDiQJkR5kfUI());
        gallery.addView(crib);
        ImageView armoire = new ImageView(this);
        armoire.setImageResource(C0032R.drawable.armoire_thumb);
        armoire.setContentDescription("armoire");
        armoire.setOnClickListener(new -$$Lambda$MainActivity$_AR9XPyVFBz7miPT6LYQztFki_g());
        gallery.addView(armoire);
    }

    private void placeObject(ArFragment fragment, Anchor anchor, Uri model) {
        ((Builder) ModelRenderable.builder().setSource(fragment.getContext(), model)).build().thenAccept(new -$$Lambda$MainActivity$KnH7wS8qJH4wVX8XmpqoQ1l3Pms(this, fragment, anchor)).exceptionally(new -$$Lambda$MainActivity$Q16Oxc3v1rAGQy0PSp5_LKEP4W4());
    }

    public static /* synthetic */ Void lambda$placeObject$11(MainActivity mainActivity, Throwable throwable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setMessage(throwable.getMessage()).setTitle("Error!");
        builder.create().show();
        return null;
    }

    private void addNodeToScene(ArFragment fragment, Anchor anchor, Renderable renderable) {
        AnchorNode anchorNode = new AnchorNode(anchor);
        TransformableNode node = new TransformableNode(fragment.getTransformationSystem());
        node.setRenderable(renderable);
        node.setParent(anchorNode);
        fragment.getArSceneView().getScene().addChild(anchorNode);
        node.select();
    }
}
