package com.henghao.parkland.activity.projectmanage;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
import com.henghao.parkland.R;
import com.henghao.parkland.model.entity.ProjectTeamEntity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 项目管理 -- 施工人员详细信息
 */
public class ProjectTeamDesActivity extends ActivityFragmentSupport {


    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tv_name)
    TextView tv_name;
    @InjectView(R.id.tv_personnelType)
    TextView tvPersonnelType;
    @InjectView(R.id.tv_workPost)
    TextView tvWorkPost;
    @InjectView(R.id.tv_psName)
    TextView tvPsName;
    @InjectView(R.id.tv_psIdcard)
    TextView tvPsIdcard;
    @InjectView(R.id.tv_psTel)
    TextView tvPsTel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_project_teamdesc);
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
        mLeftTextView.setText("施工人员");
        mLeftTextView.setVisibility(View.VISIBLE);
        tv_title.setText("施工人员");
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        ProjectTeamEntity mEntity = (ProjectTeamEntity) bundle.getSerializable(Constant.INTNET_DATA);
        tv_name.setText(mEntity.getName());
        tvPersonnelType.setText(mEntity.getPersonnelType());
        tvWorkPost.setText(mEntity.getWorkPost());
        tvPsIdcard.setText(mEntity.getPsIdcard());
        tvPsName.setText(mEntity.getPsName());
        tvPsTel.setText(mEntity.getPsTel());
    }
}
