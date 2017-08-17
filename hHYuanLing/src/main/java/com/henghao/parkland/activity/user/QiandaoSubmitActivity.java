package com.henghao.parkland.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.benefit.buy.library.utils.tools.ToolsJson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.BuildConfig;
import com.henghao.parkland.R;
import com.henghao.parkland.model.entity.BaseEntity;
import com.henghao.parkland.utils.Requester;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.lang.reflect.Type;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;


/**
 * 签到提交界面 〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author yanqiyun
 * @version HDMNV100R001, 2016-12-01
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class QiandaoSubmitActivity extends ActivityFragmentSupport {
    /**
     * 签到时间
     */
    @InjectView(R.id.tv_time_qiandaosubmit)
    TextView tvTime;
    /**
     * 当前企业
     */
    @InjectView(R.id.tv_company_qiandaosubmit)
    TextView tvCompany;
    /**
     * 签到地点
     */
    @InjectView(R.id.tv_address_qiandaosubmit)
    TextView tvAddress;
    /**
     * 签到备注
     */
    @InjectView(R.id.et_comments_qiandaosubmit)
    EditText etComments;

    private static final String TAG = "QiandaoSubmitActivity";

    private String name;//姓名
    private String address;//签到地址
    private double latitude;//纬度
    private double longitude;//经度
    private String company;//当前企业
    private Call qiandaoCall;//签到请求

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_qiandao_submit);
        this.mActivityFragmentView.viewEmpty(R.layout.activity_empty);
        this.mActivityFragmentView.viewEmptyGone();
        this.mActivityFragmentView.viewLoading(View.GONE);
        this.mActivityFragmentView.clipToPadding(true);
        setContentView(this.mActivityFragmentView);
        ButterKnife.inject(this);
        ViewUtils.inject(this);
        initWidget();
        initData();
    }

    @Override
    public void initWidget() {
        initWithBar();
        mLeftTextView.setVisibility(View.VISIBLE);
        mLeftTextView.setText("返回");
        initWithCenterBar();
        this.mCenterTextView.setVisibility(View.VISIBLE);
        this.mCenterTextView.setText("签到");
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        String time = intent.getStringExtra("time");
        name = intent.getStringExtra("name");
        address = intent.getStringExtra("address");
        latitude = intent.getDoubleExtra("latitude", 0);
        longitude = intent.getDoubleExtra("longitude", 0);
        company = intent.getStringExtra("company");
        tvTime.setText(time);
        tvAddress.setText(address);
        tvCompany.setText(company);
    }

    @OnClick({R.id.tv_submit_qiandaosubmit})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.tv_submit_qiandaosubmit:
                String comments = etComments.getText().toString().trim();
                String compId = getLoginUser().getDeptId();
                String uid = getLoginUid();
                // 提交
                qiandaoCall = Requester.signIn(address, comments, compId, uid, name, latitude, longitude, company, qiandaoCallBack);
                break;
        }
    }

    private DefaultCallback qiandaoCallBack = new DefaultCallback() {
        @Override
        public void onFailure(Exception e, int code) {
            if (BuildConfig.DEBUG) Log.e(TAG, "onFailure: code = " + code, e);
            e.printStackTrace();
            Toast.makeText(context, "网络访问错误！", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onSuccess(String response) {
            if (BuildConfig.DEBUG) Log.d(TAG, "onSuccess: " + response);
            try {
                Type baseType = new TypeToken<BaseEntity>() {
                }.getType();
                BaseEntity baseEntity = ToolsJson.parseObjecta(response, baseType);
                int errorCode = baseEntity.getErrorCode();
                if (errorCode > 0) {//签到错误
                    msg(baseEntity.getMsg());
                    return;
                } else {//签到成功
                    Toast.makeText(context, baseEntity.getMsg(), Toast.LENGTH_SHORT).show();
                }
                onBackPressed();
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                msg("签到失败，请稍后重试");
            }
        }
    };
}
