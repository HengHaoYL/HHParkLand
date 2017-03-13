package com.henghao.parkland.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.benefit.buy.library.views.NoScrollGridView;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
import com.henghao.parkland.R;
import com.henghao.parkland.adapter.CommonGridViewAdapter;
import com.henghao.parkland.model.entity.ProjectBGManageEntity;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 项目管理 -- 变更管理详细信息
 */
public class ProjectBGManageDesActivity extends ActivityFragmentSupport {


    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.tv_name)
    TextView tvName;
    @InjectView(R.id.tv_confirmingParty)
    TextView tvConfirmingParty;
    @InjectView(R.id.tv_times)
    TextView tvTimes;
    @InjectView(R.id.gridView)
    NoScrollGridView gridView;

    CommonGridViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_project_bgmanagedes);
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
        mLeftTextView.setText("变更管理");
        mLeftTextView.setVisibility(View.VISIBLE);
        tvTitle.setText("变更管理");
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        ProjectBGManageEntity mEntity = (ProjectBGManageEntity) bundle.getSerializable(Constant.INTNET_DATA);
        /**
         * 拼接图片URL地址
         */
        ArrayList<String> data = new ArrayList<>();
        ArrayList<String> urls = mEntity.getUrl();
        for (String url : urls) {
            data.add(mEntity.getFiles() + url);
        }
        mAdapter = new CommonGridViewAdapter(this, data);
        gridView.setAdapter(mAdapter);
        tvName.setText(mEntity.getName());
        tvConfirmingParty.setText(mEntity.getConfirmingParty());
        tvTimes.setText(mEntity.getTimes());
    }
}
