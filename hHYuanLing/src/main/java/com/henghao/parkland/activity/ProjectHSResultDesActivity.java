package com.henghao.parkland.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.benefit.buy.library.views.NoScrollGridView;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
import com.henghao.parkland.R;
import com.henghao.parkland.adapter.CommonGridViewAdapter;
import com.henghao.parkland.model.entity.ProjectHSResultEntity;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 项目管理 -- 会审结果详细信息
 */
public class ProjectHSResultDesActivity extends ActivityFragmentSupport {


    CommonGridViewAdapter mAdapter;
    @InjectView(R.id.tv_hsDeparment)
    TextView tvHsDeparment;
    @InjectView(R.id.gridView)
    NoScrollGridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_project_hsresultdes);
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
        mLeftTextView.setText("会审结果");
        mLeftTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        ProjectHSResultEntity mEntity = (ProjectHSResultEntity) bundle.getSerializable(Constant.INTNET_DATA);
        /**
         * 拼接图片URL地址
         */
        ArrayList<String> data = new ArrayList<>();
        ArrayList<String> urls = mEntity.getUrl();
        for (String url : urls) {
            data.add(mEntity.getHsImgId() + url);
        }
        mAdapter = new CommonGridViewAdapter(this, data);
        gridView.setAdapter(mAdapter);
        tvHsDeparment.setText(mEntity.getHsDeparment());
    }
}
