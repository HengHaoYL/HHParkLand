package com.henghao.parkland.activity.projectmanage;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
import com.henghao.parkland.R;
import com.henghao.parkland.model.entity.ProjectSGSafeLogEntity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 项目管理 -- 施工日志详细信息
 */
public class ProjectSGSafeLogDesActivity extends ActivityFragmentSupport {


    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.tv_name)
    TextView tvName;
    @InjectView(R.id.tv_constructionUnit)
    TextView tvConstructionUnit;
    @InjectView(R.id.tv_startTime)
    TextView tvStartTime;
    @InjectView(R.id.tv_completionTime)
    TextView tvCompletionTime;
    @InjectView(R.id.tv_dates)
    TextView tvDates;
    @InjectView(R.id.tv_constructionSite)
    TextView tvConstructionSite;
    @InjectView(R.id.tv_constructionDynamic)
    TextView tvConstructionDynamic;
    @InjectView(R.id.tv_safetySituation)
    TextView tvSafetySituation;
    @InjectView(R.id.tv_safetyProblems)
    TextView tvSafetyProblems;
    @InjectView(R.id.tv_fillPeople)
    TextView tvFillPeople;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_project_sgsafelogdes);
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
        mLeftTextView.setText("施工安全日志");
        mLeftTextView.setVisibility(View.VISIBLE);
        tvTitle.setText("施工安全日志");
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        ProjectSGSafeLogEntity mEntity = (ProjectSGSafeLogEntity) bundle.getSerializable(Constant.INTNET_DATA);
        tvName.setText(mEntity.getName());
        tvConstructionUnit.setText(mEntity.getConstructionUnit());
        tvStartTime.setText(mEntity.getStartTime());
        tvCompletionTime.setText(mEntity.getCompletionTime());
        tvDates.setText(mEntity.getDates());
        tvConstructionSite.setText(mEntity.getConstructionSite());
        tvConstructionDynamic.setText(mEntity.getConstructionDynamic());
        tvSafetySituation.setText(mEntity.getSafetySituation());
        tvSafetyProblems.setText(mEntity.getSafetyProblems());
        tvFillPeople.setText(mEntity.getFillPeople());
    }
}
