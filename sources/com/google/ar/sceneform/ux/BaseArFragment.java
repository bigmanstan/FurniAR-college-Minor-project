package com.google.ar.sceneform.ux;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;
import com.google.ar.core.ArCoreApk;
import com.google.ar.core.ArCoreApk.InstallStatus;
import com.google.ar.core.Config;
import com.google.ar.core.Config.UpdateMode;
import com.google.ar.core.Frame;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.Session;
import com.google.ar.core.Trackable;
import com.google.ar.core.TrackingState;
import com.google.ar.core.exceptions.CameraNotAvailableException;
import com.google.ar.core.exceptions.UnavailableException;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.Scene.OnPeekTouchListener;
import com.google.ar.sceneform.Scene.OnUpdateListener;
import com.google.ar.sceneform.rendering.ModelRenderable;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseArFragment extends Fragment implements OnPeekTouchListener, OnUpdateListener {
    private static final int RC_PERMISSIONS = 1010;
    private static final String TAG = BaseArFragment.class.getSimpleName();
    private ArSceneView arSceneView;
    private boolean canRequestDangerousPermissions = true;
    private FrameLayout frameLayout;
    private GestureDetector gestureDetector;
    private boolean installRequested;
    private boolean isStarted;
    @Nullable
    private OnTapArPlaneListener onTapArPlaneListener;
    private PlaneDiscoveryController planeDiscoveryController;
    private boolean sessionInitializationFailed = false;
    private TransformationSystem transformationSystem;

    /* renamed from: com.google.ar.sceneform.ux.BaseArFragment$1 */
    class C04171 extends SimpleOnGestureListener {
        C04171() {
        }

        public boolean onSingleTapUp(MotionEvent e) {
            BaseArFragment.this.onSingleTap(e);
            return true;
        }

        public boolean onDown(MotionEvent e) {
            return true;
        }
    }

    /* renamed from: com.google.ar.sceneform.ux.BaseArFragment$2 */
    class C04182 implements OnDismissListener {
        C04182() {
        }

        public void onDismiss(DialogInterface arg0) {
            if (!BaseArFragment.this.getCanRequestDangerousPermissions().booleanValue()) {
                BaseArFragment.this.requireActivity().finish();
            }
        }
    }

    /* renamed from: com.google.ar.sceneform.ux.BaseArFragment$3 */
    class C04193 implements OnClickListener {
        C04193() {
        }

        public void onClick(DialogInterface dialog, int which) {
            Intent intent = new Intent();
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", BaseArFragment.this.requireActivity().getPackageName(), null));
            BaseArFragment.this.requireActivity().startActivity(intent);
            BaseArFragment.this.setCanRequestDangerousPermissions(Boolean.valueOf(true));
        }
    }

    /* renamed from: com.google.ar.sceneform.ux.BaseArFragment$4 */
    static /* synthetic */ class C04204 {
        static final /* synthetic */ int[] $SwitchMap$com$google$ar$core$ArCoreApk$InstallStatus = new int[InstallStatus.values().length];

        static {
            try {
                $SwitchMap$com$google$ar$core$ArCoreApk$InstallStatus[InstallStatus.INSTALL_REQUESTED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$google$ar$core$ArCoreApk$InstallStatus[InstallStatus.INSTALLED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    public interface OnTapArPlaneListener {
        void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent);
    }

    public abstract String[] getAdditionalPermissions();

    protected abstract Config getSessionConfiguration(Session session);

    protected abstract void handleSessionException(UnavailableException unavailableException);

    public abstract boolean isArRequired();

    public ArSceneView getArSceneView() {
        return this.arSceneView;
    }

    public PlaneDiscoveryController getPlaneDiscoveryController() {
        return this.planeDiscoveryController;
    }

    public TransformationSystem getTransformationSystem() {
        return this.transformationSystem;
    }

    public void setOnTapArPlaneListener(@Nullable OnTapArPlaneListener onTapArPlaneListener) {
        this.onTapArPlaneListener = onTapArPlaneListener;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.frameLayout = (FrameLayout) inflater.inflate(C0024R.layout.sceneform_ux_fragment_layout, container, false);
        this.arSceneView = (ArSceneView) this.frameLayout.findViewById(C0024R.id.sceneform_ar_scene_view);
        View instructionsView = loadPlaneDiscoveryView(inflater, container);
        this.frameLayout.addView(instructionsView);
        this.planeDiscoveryController = new PlaneDiscoveryController(instructionsView);
        if (VERSION.SDK_INT < 24) {
            return this.frameLayout;
        }
        this.transformationSystem = makeTransformationSystem();
        this.gestureDetector = new GestureDetector(getContext(), new C04171());
        this.arSceneView.getScene().addOnPeekTouchListener(this);
        this.arSceneView.getScene().addOnUpdateListener(this);
        if (isArRequired()) {
            requestDangerousPermissions();
        }
        this.arSceneView.getViewTreeObserver().addOnWindowFocusChangeListener(new -$$Lambda$BaseArFragment$yBbGc2w8ixTWT9ppRfRP_fypjIU());
        return this.frameLayout;
    }

    protected void requestDangerousPermissions() {
        if (this.canRequestDangerousPermissions) {
            int i = 0;
            this.canRequestDangerousPermissions = false;
            List<String> permissions = new ArrayList();
            String[] additionalPermissions = getAdditionalPermissions();
            int permissionLength = additionalPermissions != null ? additionalPermissions.length : 0;
            while (i < permissionLength) {
                if (ContextCompat.checkSelfPermission(requireActivity(), additionalPermissions[i]) != 0) {
                    permissions.add(additionalPermissions[i]);
                }
                i++;
            }
            if (ContextCompat.checkSelfPermission(requireActivity(), "android.permission.CAMERA") != 0) {
                permissions.add("android.permission.CAMERA");
            }
            if (!permissions.isEmpty()) {
                requestPermissions((String[]) permissions.toArray(new String[permissions.size()]), 1010);
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] results) {
        if (ContextCompat.checkSelfPermission(requireActivity(), "android.permission.CAMERA") != 0) {
            new Builder(requireActivity(), 16974374).setTitle("Camera permission required").setMessage("Add camera permission via Settings?").setPositiveButton(17039370, new C04193()).setNegativeButton(17039360, null).setIcon(17301543).setOnDismissListener(new C04182()).show();
        }
    }

    protected Boolean getCanRequestDangerousPermissions() {
        return Boolean.valueOf(this.canRequestDangerousPermissions);
    }

    protected void setCanRequestDangerousPermissions(Boolean canRequestDangerousPermissions) {
        this.canRequestDangerousPermissions = canRequestDangerousPermissions.booleanValue();
    }

    public void onResume() {
        super.onResume();
        if (isArRequired() && this.arSceneView.getSession() == null) {
            initializeSession();
        }
        start();
    }

    protected final void initializeSession() {
        UnavailableException sessionException;
        if (!this.sessionInitializationFailed) {
            if (ContextCompat.checkSelfPermission(requireActivity(), "android.permission.CAMERA") == 0) {
                try {
                    if (C04204.$SwitchMap$com$google$ar$core$ArCoreApk$InstallStatus[ArCoreApk.getInstance().requestInstall(requireActivity(), this.installRequested ^ true).ordinal()] != 1) {
                        Session session = new Session(requireActivity());
                        Config config = getSessionConfiguration(session);
                        config.setUpdateMode(UpdateMode.LATEST_CAMERA_IMAGE);
                        session.configure(config);
                        getArSceneView().setupSession(session);
                        return;
                    }
                    this.installRequested = true;
                    return;
                } catch (UnavailableException e) {
                    sessionException = e;
                    this.sessionInitializationFailed = true;
                    handleSessionException(sessionException);
                } catch (Exception e2) {
                    sessionException = new UnavailableException();
                    sessionException.initCause(e2);
                    this.sessionInitializationFailed = true;
                    handleSessionException(sessionException);
                }
            }
            requestDangerousPermissions();
        }
    }

    protected TransformationSystem makeTransformationSystem() {
        FootprintSelectionVisualizer selectionVisualizer = new FootprintSelectionVisualizer();
        TransformationSystem transformationSystem = new TransformationSystem(getResources().getDisplayMetrics(), selectionVisualizer);
        ((ModelRenderable.Builder) ModelRenderable.builder().setSource(getActivity(), C0024R.raw.sceneform_footprint)).build().thenAccept(new -$$Lambda$BaseArFragment$t12C-HKHlYG_lNGc9NJc1xqq6Nk(selectionVisualizer)).exceptionally(new -$$Lambda$BaseArFragment$IIi-GxsP4fsaym1onJzBlzECU4Q());
        return transformationSystem;
    }

    static /* synthetic */ void lambda$makeTransformationSystem$1(FootprintSelectionVisualizer selectionVisualizer, ModelRenderable renderable) {
        if (selectionVisualizer.getFootprintRenderable() == null) {
            selectionVisualizer.setFootprintRenderable(renderable);
        }
    }

    public static /* synthetic */ Void lambda$makeTransformationSystem$2(BaseArFragment baseArFragment, Throwable throwable) {
        Toast toast = Toast.makeText(baseArFragment.getContext(), "Unable to load footprint renderable", 1);
        toast.setGravity(17, 0, 0);
        toast.show();
        return null;
    }

    protected void onWindowFocusChanged(boolean hasFocus) {
        FragmentActivity activity = getActivity();
        if (hasFocus && activity != null) {
            activity.getWindow().getDecorView().setSystemUiVisibility(5894);
            activity.getWindow().addFlags(128);
        }
    }

    public void onPause() {
        super.onPause();
        stop();
    }

    public void onDestroy() {
        stop();
        this.arSceneView.destroy();
        super.onDestroy();
    }

    public void onPeekTouch(HitTestResult hitTestResult, MotionEvent motionEvent) {
        this.transformationSystem.onTouch(hitTestResult, motionEvent);
        if (hitTestResult.getNode() == null) {
            this.gestureDetector.onTouchEvent(motionEvent);
        }
    }

    public void onUpdate(FrameTime frameTime) {
        Frame frame = this.arSceneView.getArFrame();
        if (frame != null) {
            for (Plane plane : frame.getUpdatedTrackables(Plane.class)) {
                if (plane.getTrackingState() == TrackingState.TRACKING) {
                    this.planeDiscoveryController.hide();
                }
            }
        }
    }

    private void start() {
        if (!(this.isStarted || getActivity() == null)) {
            this.isStarted = true;
            try {
                this.arSceneView.resume();
            } catch (CameraNotAvailableException e) {
                this.sessionInitializationFailed = true;
            }
            if (!this.sessionInitializationFailed) {
                this.planeDiscoveryController.show();
            }
        }
    }

    private void stop() {
        if (this.isStarted) {
            this.isStarted = false;
            this.planeDiscoveryController.hide();
            this.arSceneView.pause();
        }
    }

    private View loadPlaneDiscoveryView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(C0024R.layout.sceneform_plane_discovery_layout, container, false);
    }

    private void onSingleTap(MotionEvent motionEvent) {
        Frame frame = this.arSceneView.getArFrame();
        this.transformationSystem.selectNode(null);
        OnTapArPlaneListener onTapArPlaneListener = this.onTapArPlaneListener;
        if (frame != null && onTapArPlaneListener != null && motionEvent != null && frame.getCamera().getTrackingState() == TrackingState.TRACKING) {
            for (HitResult hit : frame.hitTest(motionEvent)) {
                Trackable trackable = hit.getTrackable();
                if ((trackable instanceof Plane) && ((Plane) trackable).isPoseInPolygon(hit.getHitPose())) {
                    onTapArPlaneListener.onTapPlane(hit, (Plane) trackable, motionEvent);
                    return;
                }
            }
        }
    }
}
