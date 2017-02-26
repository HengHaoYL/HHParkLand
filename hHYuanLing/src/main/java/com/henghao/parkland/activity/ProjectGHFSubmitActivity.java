package com.henghao.parkland.activity;

import android.os.Bundle;
import android.view.View;

import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.R;
import com.lidroid.xutils.ViewUtils;

/**
 * 项目管理 -- 项目图纸提交
 */
public class ProjectGHFSubmitActivity extends ActivityFragmentSupport {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_projectghfsubmit);
        this.mActivityFragmentView.viewEmpty(R.layout.activity_empty);
        this.mActivityFragmentView.viewEmptyGone();
        this.mActivityFragmentView.viewLoading(View.GONE);
        this.mActivityFragmentView.getNavitionBarView().setVisibility(View.VISIBLE);
        ViewUtils.inject(this, this.mActivityFragmentView);
        setContentView(this.mActivityFragmentView);
        initWidget();
        initData();
    }

    @Override
    public void initWidget() {
        super.initWidget();
        initWithBar();
        mLeftTextView.setText("供货方信息");
        mLeftTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void initData() {
        super.initData();
    }
}
