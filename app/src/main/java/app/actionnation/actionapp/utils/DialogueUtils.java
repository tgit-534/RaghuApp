package app.actionnation.actionapp.utils;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;
import app.actionnation.actionapp.R;

public class DialogueUtils {
    private static MaterialDialog dialog;

    public static void stopProgress(Context context) {
        if ((dialog != null && dialog.isShowing())) {
            dialog.dismiss();
            dialog = null;
        }
    }
    public static void showDialogProgress(Context context) {
        try {
            if (dialog == null) {
                dialog = new MaterialDialog.Builder(context)
                        .progress(true, 0)
                        .backgroundColorRes(android.R.color.white)
                        .contentColorRes(R.color.main_color)
                        .content("Please wait..")
                        .progressIndeterminateStyle(false)
                        .cancelable(false)
                        .show();
            } else if (!dialog.isShowing()) {
                try {
                    dialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
