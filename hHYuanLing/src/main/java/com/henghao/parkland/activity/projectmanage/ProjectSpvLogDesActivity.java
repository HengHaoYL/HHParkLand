package com.henghao.parkland.activity.projectmanage;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
import com.henghao.parkland.R;
import com.henghao.parkland.model.entity.ProjectSpvLogEntity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 项目管理 -- 监理日志详细信息
 */
public class ProjectSpvLogDesActivity extends ActivityFragmentSupport {

    @InjectView(R.id.tv_projectName)
    TextView tvProjectName;
    @InjectView(R.id.tv_supervisionPosition)
    TextView tvSupervisionPosition;
    @InjectView(R.id.tv_progressSituation)
    TextView tvProgressSituation;
    @InjectView(R.id.tv_workingSitustion)
    TextView tvWorkingSitustion;
    @InjectView(R.id.tv_question)
    TextView tvQuestion;
    @InjectView(R.id.tv_other)
    TextView tvOther;
    @InjectView(R.id.tv_dates)
    TextView tvDates;
    @InjectView(R.id.tv_noteTaker)
    TextView tvNoteTaker;
    @InjectView(R.id.tv_engineer)
    TextView tvEngineer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_project_spvlogdes);
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
        initWithCenterBar();
        mCenterTextView.setText("监理日志");
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        ProjectSpvLogEntity mEntity = (ProjectSpvLogEntity) bundle.getSerializable(Constant.INTNET_DATA);
        tvDates.setText(mEntity.getDates());
        tvEngineer.setText(mEntity.getEngineer());
        tvNoteTaker.setText(mEntity.getNoteTaker());
        tvOther.setText(mEntity.getOther());
        tvProgressSituation.setText(mEntity.getProgressSituation());
        tvProjectName.setText(mEntity.getProjectName());
        tvQuestion.setText(mEntity.getQuestion());
        tvSupervisionPosition.setText(mEntity.getSupervisionPosition());
        tvWorkingSitustion.setText(mEntity.getWorkingSitustion());
    }
}
