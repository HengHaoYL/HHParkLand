package com.henghao.parkland.activity.projectmanage;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
import com.henghao.parkland.R;
import com.henghao.parkland.model.entity.ProjectSGLogEntity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 项目管理 -- 施工日志详细信息
 */
public class ProjectSGLogDesActivity extends ActivityFragmentSupport {


    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.tv_name)
    TextView tvName;
    @InjectView(R.id.tv_dates)
    TextView tvDates;
    @InjectView(R.id.tv_proactContent)
    TextView tvProactContent;
    @InjectView(R.id.tv_technicalIndex)
    TextView tvTechnicalIndex;
    @InjectView(R.id.tv_workingCondition)
    TextView tvWorkingCondition;
    @InjectView(R.id.tv_principal)
    TextView tvPrincipal;
    @InjectView(R.id.tv_builder)
    TextView tvBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_project_sglogdes);
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
        mLeftTextView.setText("施工日志");
        mLeftTextView.setVisibility(View.VISIBLE);
        tvTitle.setText("施工日志");
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        ProjectSGLogEntity mEntity = (ProjectSGLogEntity) bundle.getSerializable(Constant.INTNET_DATA);
        tvName.setText(mEntity.getName());
        tvDates.setText(mEntity.getDates());
        tvBuilder.setText(mEntity.getBuilder());
        tvPrincipal.setText(mEntity.getPrincipal());
        tvProactContent.setText(mEntity.getProactContent());
        tvTechnicalIndex.setText(mEntity.getTechnicalIndex());
        tvWorkingCondition.setText(mEntity.getWorkingCondition());
    }
}
