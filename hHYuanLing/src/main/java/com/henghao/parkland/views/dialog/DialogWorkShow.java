package com.henghao.parkland.views.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.henghao.parkland.R;

/**
 * Created by 晏琦云 on 2017/3/22.
 */


public class DialogWorkShow extends Dialog implements View.OnClickListener {

    private DialogWorkShowListener listener;
    private TextView tv_dialog1, tv_dialog2, tv_dialog3, tv_dialog4;

    @Override
    public void onClick(View v) {
        listener.onClick(v);
    }

    public interface DialogWorkShowListener {
        public void onClick(View view);
    }

    public DialogWorkShow(Context context, DialogWorkShowListener listener) {
        super(context, R.style.dialog_alert);
        this.listener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workshow_dialog);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        tv_dialog1 = (TextView) findViewById(R.id.tv_dialog1);
        tv_dialog2 = (TextView) findViewById(R.id.tv_dialog2);
        tv_dialog3 = (TextView) findViewById(R.id.tv_dialog3);
        tv_dialog4 = (TextView) findViewById(R.id.tv_dialog4);
        tv_dialog1.setOnClickListener(this);
        tv_dialog2.setOnClickListener(this);
        tv_dialog3.setOnClickListener(this);
        tv_dialog4.setOnClickListener(this);
    }
}

