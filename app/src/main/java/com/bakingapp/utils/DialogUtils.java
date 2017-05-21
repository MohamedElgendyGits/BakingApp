package com.bakingapp.utils;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by Mohamed Elgendy on 17/5/2017.
 */

public class DialogUtils {

    public static ProgressDialog getProgressDialog(
            Context context, String message, boolean canCancelable,
            boolean canceledOnTouchOutside) {

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(canCancelable);
        progressDialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
        progressDialog.setMessage(message);
        return progressDialog;
    }


    public static Snackbar getSnackBar(View view, CharSequence textMsg,
                                       View.OnClickListener onClickListener, String textOfOnClick) {

        Snackbar snackbar = Snackbar.make(view, textMsg, Snackbar.LENGTH_LONG);
        if (onClickListener != null) {
            snackbar.setAction(textOfOnClick, onClickListener);
            snackbar.setActionTextColor(Color.RED);
        }
        return snackbar;
    }




}
