package com.henghao.parkland.activity.projectmanage;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.benefit.buy.library.views.NoScrollGridView;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
import com.henghao.parkland.R;
import com.henghao.parkland.adapter.CommonGridViewAdapter;
import com.henghao.parkland.model.entity.ProjectGHFEntity;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 项目管理 -- 供货方信息详细信息
 */
public class ProjectGHFDesActivity extends ActivityFragmentSupport {


    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.tv_name)
    TextView tvName;
    @InjectView(R.id.tv_epName)
    TextView tvEpName;
    @InjectView(R.id.tv_epAdd)
    TextView tvEpAdd;
    @InjectView(R.id.tv_epDate)
    TextView tvEpDate;
    @InjectView(R.id.tv_epTel)
    TextView tvEpTel;
    @InjectView(R.id.gridView)
    NoScrollGridView gridView;

    CommonGridViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_project_ghfdes);
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
        mLeftTextView.setText("供货方信息");
        mLeftTextView.setVisibility(View.VISIBLE);
        tvTitle.setText("供货方信息");
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        ProjectGHFEntity mEntity = (ProjectGHFEntity) bundle.getSerializable(Constant.INTNET_DATA);
        /**
         * 拼接图片URL地址
         */
        ArrayList<String> data = new ArrayList<>();
        ArrayList<String> urls = mEntity.getUrl();
        for (String url : urls) {
            data.add(mEntity.getEpCompactId() + url);
        }
        mAdapter = new CommonGridViewAdapter(this, data);
        gridView.setAdapter(mAdapter);
        tvName.setText(mEntity.getName());
        tvEpAdd.setText(mEntity.getEpAdd());
        tvEpDate.setText(mEntity.getEpDate());
        tvEpName.setText(mEntity.getEpName());
        tvEpTel.setText(mEntity.getEpTel());
    }
}
