package com.example.fernanda.trabalho3.ui;

import android.content.Context;
import android.widget.Toast;

class ViewUtils {
    static void showToast(Context context, String text){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

}
