package com.TMAProject.EzzSteel.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.TMAProject.EzzSteel.R;

/**
 * Created by TarekkMA on 4/2/16.
 */
public class DialogHelper {
    public static void errorHappendDialog(final Context context, final boolean exit,String title,String msg){
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        if(exit) ((Activity) context).finish();
                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if(exit) ((Activity) context).finish();
                    }
                })
                .setIcon(android.R.drawable.stat_notify_error)
                .show();
    }
    public static void infoHappendDialog(final Context context, final boolean exit,String title,String msg){
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        if (exit) ((Activity) context).finish();
                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if (exit) ((Activity) context).finish();
                    }
                })
                .show();
    }
}
