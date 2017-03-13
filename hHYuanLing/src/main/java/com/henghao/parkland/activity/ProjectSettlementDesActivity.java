package com.henghao.parkland.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.benefit.buy.library.views.NoScrollGridView;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
import com.henghao.parkland.R;
import com.henghao.parkland.adapter.CommonGridViewAdapter;
import com.henghao.parkland.model.entity.ProjectSettlementEntity;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 项目管理 -- 项目结算详细信息
 */
public class ProjectSettlementDesActivity extends ActivityFragmentSupport {


    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.tv_dates)
    TextView tvDates;
    @InjectView(R.id.tv_files)
    TextView tvFiles;
    @InjectView(R.id.gridView)
    NoScrollGridView gridView;

    CommonGridViewAdapter mAdapter;
    @InjectView(R.id.tv_name)
    TextView tvName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_project_settlementdes);
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
        mLeftTextView.setText("项目结算");
        mLeftTextView.setVisibility(View.VISIBLE);
        tvTitle.setText("项目结算");
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        ProjectSettlementEntity mEntity = (ProjectSettlementEntity) bundle.getSerializable(Constant.INTNET_DATA);
        tvDates.setText(mEntity.getDates());
        tvName.setText(mEntity.getName());
        /**
         * 拼接图片URL地址
         */
        ArrayList<String> data = new ArrayList<>();
        ArrayList<String> urls = mEntity.getUrl();
        for (String url : urls) {
            data.add(mEntity.getSettlementBookId() + url);
        }
        mAdapter = new CommonGridViewAdapter(this, data);
        gridView.setAdapter(mAdapter);
    }
}
