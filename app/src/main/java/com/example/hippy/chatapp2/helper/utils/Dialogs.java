package com.example.hippy.chatapp2.helper.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by HIPPY on 9/11/2015.
 */
public class Dialogs {

    public static ProgressDialog getProgressDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("please_wait...");
        return progressDialog;
    }
}
