package oob.instaminder.Util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class DialogUtil {
    public static void showDialog(Context context,
                                  String title,
                                  String message,
                                  String positiveActionText,
                                  DialogInterface.OnClickListener positiveCallback,
                                  String negativeActionText,
                                  DialogInterface.OnClickListener negativeCallback) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveActionText, positiveCallback)
                .setNegativeButton(negativeActionText, negativeCallback)
                .show();
    }

    public static void showAlertDialog(Context context,
                                       String title,
                                       String message,
                                       String positiveActionText) {
        showAlertDialog(context, title, message, positiveActionText, null, true);
    }

    public static void showAlertDialog(Context context,
                                       String title,
                                       String message,
                                       String positiveActionText,
                                       DialogInterface.OnClickListener positiveCallback,
                                       boolean cancellable) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveActionText, positiveCallback)
                .setCancelable(cancellable)
                .show();
    }
}
