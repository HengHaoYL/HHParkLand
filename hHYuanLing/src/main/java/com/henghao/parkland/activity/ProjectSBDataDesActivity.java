package com.henghao.parkland.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
import com.henghao.parkland.R;
import com.henghao.parkland.model.entity.ProjectSBDataEntity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 项目管理 -- 设备信息详细信息
 */
public class ProjectSBDataDesActivity extends ActivityFragmentSupport {


    @InjectView(R.id.tv_sbName)
    TextView tvSbName;
    @InjectView(R.id.tv_sbSpec)
    TextView tvSbSpec;
    @InjectView(R.id.tv_sbNum)
    TextView tvSbNum;
    @InjectView(R.id.tv_sbPurpose)
    TextView tvSbPurpose;
    @InjectView(R.id.tv_sbSource)
    TextView tvSbSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_project_sbdatades);
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
        mLeftTextView.setText("设备信息");
        mLeftTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        ProjectSBDataEntity mEntity = (ProjectSBDataEntity) bundle.getSerializable(Constant.INTNET_DATA);
        tvSbName.setText(mEntity.getSbName());
        tvSbNum.setText(mEntity.getSbNum() + "");
        tvSbPurpose.setText(mEntity.getSbPurpose());
        tvSbSource.setText(mEntity.getSbSource());
        tvSbSpec.setText(mEntity.getSbSpec());
    }
}
