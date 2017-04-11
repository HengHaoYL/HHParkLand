package com.henghao.parkland.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.henghao.parkland.ProtocolUrl;
import com.henghao.parkland.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Beenlong<yyy66663@foxmail.com>
 * 2017.04.11
 */
public class DebugSetupActivity extends Activity {

    @InjectView(R.id.et_host)
    EditText etHost;
    @InjectView(R.id.bt_apply)
    Button btApply;

    private int changes = 0;            //00000000
    private final int HOST = 1;         //00000001

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug_setup);
        ButterKnife.inject(this);
        etHost.setText(ProtocolUrl.ROOT_URL);
        etHost.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void onChanged() {
                changes |= HOST;
            }
        });
    }

    private void applyChanges() {
        if ((changes & HOST) != 0) {
            ProtocolUrl.ROOT_URL = etHost.getText().toString().trim();
        }

        Toast.makeText(this, "修改完成", Toast.LENGTH_SHORT).show();
        changes = 0;
        btApply.setVisibility(View.GONE);
    }

    @OnClick({R.id.bt_apply, R.id.bt_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_apply:
                applyChanges();
                break;
            case R.id.bt_cancel:
                finish();
                break;
        }
    }

    abstract class TextWatcherAdapter implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            onChanged();
            showApplyButton();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }

        public abstract void onChanged();

    }

    private void showApplyButton() {
        if (btApply.getVisibility() != View.VISIBLE) {
            btApply.setVisibility(View.VISIBLE);
        }
    }
}
