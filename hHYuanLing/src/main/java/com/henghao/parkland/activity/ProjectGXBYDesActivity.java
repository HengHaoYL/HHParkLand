package com.henghao.parkland.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
import com.henghao.parkland.R;
import com.henghao.parkland.adapter.CommonGridViewAdapter;
import com.henghao.parkland.model.entity.ProjectGXBYEntity;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 项目管理 -- 工序报验详细信息
 */
public class ProjectGXBYDesActivity extends ActivityFragmentSupport {

    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.tv_gxName)
    TextView tvGxName;
    @InjectView(R.id.tv_gxProcedure)
    TextView tvGxProcedure;
    @InjectView(R.id.tv_personnelType)
    TextView tvPersonnelType;
    @InjectView(R.id.tv_workPost)
    TextView tvWorkPost;
    @InjectView(R.id.tv_gxTime)
    TextView tvGxTime;
    @InjectView(R.id.gridView)
    GridView gridView;

    CommonGridViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_project_gxbydes);
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
        mLeftTextView.setText("工序报验");
        mLeftTextView.setVisibility(View.VISIBLE);
        tvTitle.setText("工序报验");
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        ProjectGXBYEntity mEntity = (ProjectGXBYEntity) bundle.getSerializable(Constant.INTNET_DATA);
        /**
         * 拼接图片URL地址
         */
        ArrayList<String> data = new ArrayList<>();
        ArrayList<String> urls = mEntity.getUrl();
        for (String url : urls) {
            data.add(mEntity.getGxImgId() + url);
        }
        mAdapter = new CommonGridViewAdapter(this, data);
        gridView.setAdapter(mAdapter);
        tvGxName.setText(mEntity.getGxName());
        tvGxProcedure.setText(mEntity.getGxProcedure());
        tvGxTime.setText(mEntity.getGxTime());
        tvPersonnelType.setText(mEntity.getPersonnelType());
        tvWorkPost.setText(mEntity.getWorkPost());
    }
}
