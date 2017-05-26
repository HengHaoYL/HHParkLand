package com.henghao.parkland.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.R;
import com.henghao.parkland.utils.Requester;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * Created by 晏琦云 on 2017/2/10.
 * 植物养护信息界面
 */

public class MaintenanceActivity extends ActivityFragmentSupport {

    private static final String TAG = "MaintenanceActivity";
    @InjectView(R.id.tv_treeid_maintenance)
    TextView tvTreeid;
    @InjectView(R.id.tv_state_maintenance)
    TextView tvState;
    @InjectView(R.id.tv_time_maintenance)
    TextView tvTime;
    @InjectView(R.id.tv_place_maintenance)
    TextView tvPlace;
    @InjectView(R.id.btn_confirm_maintenance)
    Button btnConfirm;
    @InjectView(R.id.btn_cancel_maintenance)
    Button btnCancel;
    /**
     * 网络访问相关
     */
    private String treeId;//二维码扫描内容
    private String yhStatusname;//养护状态
    private String yhStatustime;//养护时间
    private String yhStatussite;//养护地点
    private Call call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_maintenace);
        this.mActivityFragmentView.viewEmpty(R.layout.activity_empty);
        this.mActivityFragmentView.viewEmptyGone();
        this.mActivityFragmentView.viewLoading(View.GONE);
        this.mActivityFragmentView.getNavitionBarView().setVisibility(View.VISIBLE);
        setContentView(this.mActivityFragmentView);
        ButterKnife.inject(this);
        initWidget();
        initData();
    }

    @Override
    public void initWidget() {
        super.initWidget();
        initWithBar();
        initWithCenterBar();
        mCenterTextView.setText("养护信息");
    }

    public void initData() {
        Intent intent = getIntent();
        treeId = intent.getStringExtra("treeId");
        yhStatusname = intent.getStringExtra("yhStatusname");
        yhStatustime = intent.getStringExtra("yhStatustime");
        yhStatussite = intent.getStringExtra("yhStatussite");
        tvTreeid.setText(treeId);
        tvState.setText(yhStatusname);
        tvTime.setText(yhStatustime);
        tvPlace.setText(yhStatussite);
    }


    @OnClick({R.id.btn_confirm_maintenance, R.id.btn_cancel_maintenance})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm_maintenance:
                // 访问网络
                call = Requester.maintenanceSubmit(treeId, yhStatusname, yhStatustime, yhStatussite, getLoginUid(), callback);
                break;
            case R.id.btn_cancel_maintenance:
                finish();
                break;
        }
    }

    private DefaultCallback callback = new DefaultCallback() {
        @Override
        public void onFailure(Request request, Exception e, int code) {
            Toast.makeText(MaintenanceActivity.this, "网络访问错误！", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onSuccess(String response) {
            Log.i(TAG, "onResponse: " + response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                final String result_str = jsonObject.getString("result");
                Log.i(TAG, "onResponse: " + result_str);
                Toast.makeText(MaintenanceActivity.this, result_str, Toast.LENGTH_SHORT).show();
                finish();
            } catch (JSONException e) {
                Toast.makeText(MaintenanceActivity.this, "服务器错误，请稍后重试！", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (call != null && !call.isCanceled()) {
            call.cancel();
        }
    }
}
