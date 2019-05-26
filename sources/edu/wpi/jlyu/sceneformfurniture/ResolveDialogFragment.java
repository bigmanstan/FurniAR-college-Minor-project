package edu.wpi.jlyu.sceneformfurniture;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class ResolveDialogFragment extends DialogFragment {
    private OkListener okListener;
    private EditText shortCodeField;

    interface OkListener {
        void onOkPressed(String str);
    }

    void setOkListener(OkListener okListener) {
        this.okListener = okListener;
    }

    private LinearLayout getDialogLayout() {
        Context context = getContext();
        LinearLayout layout = new LinearLayout(context);
        this.shortCodeField = new EditText(context);
        this.shortCodeField.setInputType(2);
        this.shortCodeField.setLayoutParams(new LayoutParams(-1, -2));
        this.shortCodeField.setFilters(new InputFilter[]{new LengthFilter(8)});
        layout.addView(this.shortCodeField);
        layout.setLayoutParams(new LayoutParams(-1, -2));
        return layout;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Builder builder = new Builder(getActivity());
        builder.setView(getDialogLayout()).setTitle("Resolve Anchor ").setPositiveButton("OK", new -$$Lambda$ResolveDialogFragment$dkU2rcK6XCLeE7VCGkCDSXKwtcE()).setNegativeButton("Cancel", -$$Lambda$ResolveDialogFragment$mip3OZkpRnTQlJb5aoTk_h1VOQo.INSTANCE);
        return builder.create();
    }

    public static /* synthetic */ void lambda$onCreateDialog$0(ResolveDialogFragment resolveDialogFragment, DialogInterface dialog, int which) {
        Editable shortCodeText = resolveDialogFragment.shortCodeField.getText();
        if (resolveDialogFragment.okListener != null && shortCodeText != null && shortCodeText.length() > 0) {
            resolveDialogFragment.okListener.onOkPressed(shortCodeText.toString());
        }
    }

    static /* synthetic */ void lambda$onCreateDialog$1(DialogInterface dialog, int which) {
    }
}
