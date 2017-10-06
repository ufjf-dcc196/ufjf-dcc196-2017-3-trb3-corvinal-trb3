package com.example.fernanda.trabalho1.ui;

import android.content.Context;
import android.widget.Toast;

public class ViewUtils {
    static void showToast(Context context, String text){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

}
