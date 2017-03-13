package com.henghao.parkland.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.benefit.buy.library.views.NoScrollGridView;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
import com.henghao.parkland.R;
import com.henghao.parkland.adapter.CommonGridViewAdapter;
import com.henghao.parkland.model.entity.ProjectJunGongEntity;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 项目管理 -- 竣工验收详细信息
 */
public class ProjectJunGongDesActivity extends ActivityFragmentSupport {

    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.tv_projectName)
    TextView tvProjectName;
    @InjectView(R.id.tv_dates)
    TextView tvDates;
    @InjectView(R.id.tv_inspectionPersonnel)
    TextView tvInspectionPersonnel;
    @InjectView(R.id.gridView)
    NoScrollGridView gridView;

    CommonGridViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_project_jungongdes);
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
        mLeftTextView.setText("竣工验收");
        mLeftTextView.setVisibility(View.VISIBLE);
        tvTitle.setText("竣工验收");
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        ProjectJunGongEntity mEntity = (ProjectJunGongEntity) bundle.getSerializable(Constant.INTNET_DATA);
        tvProjectName.setText(mEntity.getProjectName());
        tvDates.setText(mEntity.getDates());
        tvInspectionPersonnel.setText(mEntity.getInspectionPersonnel());
        /**
         * 拼接图片URL地址
         */
        ArrayList<String> data = new ArrayList<>();
        ArrayList<String> urls = mEntity.getUrl();
        for (String url : urls) {
            data.add(mEntity.getCompletionDrawingId() + url);
        }
        mAdapter = new CommonGridViewAdapter(this, data);
        gridView.setAdapter(mAdapter);
    }
}
