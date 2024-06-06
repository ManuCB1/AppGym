package com.example.appgym.utils;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

public class Utils {

    public static void showDialogDelete(Context context, String title, String message, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Eliminar", positiveListener)
                .setNegativeButton("Cancelar", negativeListener)
                .create()
                .show();
    }
}
