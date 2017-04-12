package com.henghao.parkland.views.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.henghao.parkland.R;

/**
 * Created by 晏琦云 on 2017/3/22.
 */


public class DialogLoading extends Dialog {
    private TextView tvText;
    private View view;

    public DialogLoading(Context context) {
        super(context, R.style.dialog_alert);
        view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
        tvText = (TextView) view.findViewById(R.id.tv_text);
    }

    public DialogLoading(Context context, String hint) {
        this(context);
        setText(hint);
    }

    public void setText(String text) {
        tvText.setText(text == null ? "请稍等……" : text);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(view);
        setCanceledOnTouchOutside(false);
        setCancelable(true);
    }
}

