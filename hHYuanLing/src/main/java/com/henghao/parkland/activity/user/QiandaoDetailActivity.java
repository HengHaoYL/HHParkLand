package com.henghao.parkland.activity.user;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
import com.henghao.parkland.R;
import com.henghao.parkland.model.entity.QiandaoInfoEntity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by ASUS on 2017/8/16.
 * 签到详情界面
 */

public class QiandaoDetailActivity extends ActivityFragmentSupport {

    @InjectView(R.id.tv_name_qiandao_detail)
    TextView tvName;
    @InjectView(R.id.tv_time_qiandao_detail)
    TextView tvTime;
    @InjectView(R.id.tv_company_qiandao_detail)
    TextView tvCompany;
    @InjectView(R.id.tv_address_qiandao_detail)
    TextView tvAddress;
    @InjectView(R.id.tv_latitude_qiandao_detail)
    TextView tvLatitude;
    @InjectView(R.id.tv_longitude_qiandao_detail)
    TextView tvLongitude;
    @InjectView(R.id.tv_comments_qiandao_detail)
    TextView tvComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_qiandao_detail);
        this.mActivityFragmentView.viewEmpty(R.layout.activity_empty);
        this.mActivityFragmentView.viewEmptyGone();
        this.mActivityFragmentView.viewLoading(View.GONE);
        this.mActivityFragmentView.clipToPadding(true);
        setContentView(this.mActivityFragmentView);
        ButterKnife.inject(this);
        initWidget();
        initData();
    }

    @Override
    public void initWidget() {
        initWithBar();
        this.mLeftTextView.setVisibility(View.VISIBLE);
        this.mLeftTextView.setText("返回");
        initWithCenterBar();
        this.mCenterTextView.setVisibility(View.VISIBLE);
        this.mCenterTextView.setText("签到详情");
    }

    @Override
    public void initData() {
        Bundle bundle = getIntent().getBundleExtra("bundle");
        QiandaoInfoEntity mEntity = (QiandaoInfoEntity) bundle.getSerializable(Constant.INTNET_DATA);
        tvName.setText(mEntity.getName());
        tvAddress.setText(mEntity.getAddress());
        tvComments.setText(mEntity.getComments());
        tvCompany.setText(mEntity.getCompany());
        tvLatitude.setText(mEntity.getLatitude() + "");
        tvLongitude.setText(mEntity.getLongitude() + "");
        tvTime.setText(mEntity.getTime());
    }
}
