package com.henghao.parkland.activity;

import android.os.Bundle;
import android.view.View;

import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.R;
import com.lidroid.xutils.ViewUtils;

/**
 * 项目管理 -- 资质变更
 */
public class ProjectZiZhiActivity extends ActivityFragmentSupport {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_projectzizhisubmit);
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
        mLeftTextView.setText("资质变更");
        mLeftTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void initData() {
        super.initData();
    }
}
