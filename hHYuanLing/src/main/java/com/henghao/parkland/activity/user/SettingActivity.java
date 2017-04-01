package com.henghao.parkland.activity.user;

import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.R;


public class SettingActivity extends ActivityFragmentSupport   {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mActivityFragmentView.viewMain(R.layout.activity_setting);
        mActivityFragmentView.viewEmpty(R.layout.activity_empty);
        mActivityFragmentView.viewEmptyGone();
        mActivityFragmentView.viewLoading(View.GONE);
        mActivityFragmentView.clipToPadding(true);
        setContentView(mActivityFragmentView);
        com.lidroid.xutils.ViewUtils.inject(this);
        initWidget();
        initData();
    }

    @Override
    public void initWidget() {
        // TODO Auto-generated method stub
        initWithContent();
    }

    private void initWithContent() {
        // TODO Auto-generated method stub
        initWithBar();
        initWithCenterBar();
        mCenterTextView.setText("设置");
    }

    @Override
    public void initData() {
        // TODO Auto-generated method stub
    }

}
