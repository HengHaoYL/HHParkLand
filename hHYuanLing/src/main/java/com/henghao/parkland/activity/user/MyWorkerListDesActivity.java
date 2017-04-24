package com.henghao.parkland.activity.user;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
import com.henghao.parkland.R;
import com.henghao.parkland.model.entity.MyWorkerEntity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 我的轨迹详情页面
 */
public class MyWorkerListDesActivity extends ActivityFragmentSupport {

    @InjectView(R.id.tv_workType)
    TextView tvWorkType;
    @InjectView(R.id.tv_details)
    TextView tvDetails;
    @InjectView(R.id.tv_dates)
    TextView tvDates;
    @InjectView(R.id.tv_personnel)
    TextView tvPersonnel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_myworkerlist_des);
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
        mCenterTextView.setText("我的轨迹");
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        MyWorkerEntity mEntity = (MyWorkerEntity) bundle.getSerializable(Constant.INTNET_DATA);
        tvWorkType.setText(mEntity.getWorkType());
        tvDetails.setText(mEntity.getDetails());
        tvDates.setText(mEntity.getDates());
        tvPersonnel.setText(mEntity.getPersonnel());
    }
}
