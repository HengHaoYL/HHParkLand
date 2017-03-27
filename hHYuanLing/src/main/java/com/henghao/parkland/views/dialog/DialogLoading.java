package com.henghao.parkland.views.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.henghao.parkland.R;

/**
 * Created by 晏琦云 on 2017/3/22.
 */


public class DialogLoading extends Dialog {

    public DialogLoading(Context context) {
        super(context, R.style.dialog_alert);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_loading);
        setCanceledOnTouchOutside(false);
        setCancelable(true);
    }
}

