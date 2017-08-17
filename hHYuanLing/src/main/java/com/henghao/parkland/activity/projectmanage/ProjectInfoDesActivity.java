package com.henghao.parkland.activity.projectmanage;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
import com.henghao.parkland.R;
import com.henghao.parkland.model.entity.ProjectInfoEntity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 项目管理 -- 项目信息详细信息
 */
public class ProjectInfoDesActivity extends ActivityFragmentSupport {

    @InjectView(R.id.tv_name)
    TextView tvName;
    @InjectView(R.id.tv_principal)
    TextView tvPrincipal;
    @InjectView(R.id.tv_tel)
    TextView tvTel;
    @InjectView(R.id.tv_personNum)
    TextView tvPersonNum;
    @InjectView(R.id.tv_address)
    TextView tvAddress;
    @InjectView(R.id.tv_company)
    TextView tvCompany;
    @InjectView(R.id.tv_startTime)
    TextView tvStartTime;
    @InjectView(R.id.tv_endTime)
    TextView tvEndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_project_infodesc);
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
        mLeftTextView.setVisibility(View.VISIBLE);
        mLeftTextView.setText("返回");
        initWithCenterBar();
        mCenterTextView.setText("项目信息");
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        ProjectInfoEntity mEntity = (ProjectInfoEntity) bundle.getSerializable(Constant.INTNET_DATA);
        tvStartTime.setText(mEntity.getStartTime());
        tvEndTime.setText(mEntity.getEndTime());
        tvName.setText(mEntity.getName());
        tvAddress.setText(mEntity.getAddress());
        tvCompany.setText(mEntity.getCompany());
        tvPersonNum.setText(mEntity.getPersonNum() + "");
        tvPrincipal.setText(mEntity.getPrincipal() + "");
        tvTel.setText(mEntity.getTel());
    }
}
