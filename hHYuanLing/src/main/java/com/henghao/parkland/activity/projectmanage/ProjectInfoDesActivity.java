package com.henghao.parkland.activity.projectmanage;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
import com.henghao.parkland.R;
import com.henghao.parkland.model.entity.ProjectInfoEntity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 项目管理 -- 项目信息详细信息
 */
public class ProjectInfoDesActivity extends ActivityFragmentSupport {

    @ViewInject(R.id.et_xmName)
    TextView etXmName;

    @ViewInject(R.id.et_constructionUnit)
    TextView etConstructionUnit;

    @ViewInject(R.id.tv_startTime)
    TextView tvStartTime;

    @ViewInject(R.id.tv_completionTime)
    TextView tvCompletionTime;

    @ViewInject(R.id.et_xmPerson)
    TextView etXmPerson;

    @ViewInject(R.id.et_xmContact)
    TextView etXmContact;

    @ViewInject(R.id.et_xmPersonNum)
    TextView etXmPersonNum;

    @ViewInject(R.id.et_xmAdd)
    TextView etXmAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_project_infodesc);
        this.mActivityFragmentView.viewEmpty(R.layout.activity_empty);
        this.mActivityFragmentView.viewEmptyGone();
        this.mActivityFragmentView.viewLoading(View.GONE);
        this.mActivityFragmentView.getNavitionBarView().setVisibility(View.VISIBLE);
        ViewUtils.inject(this, this.mActivityFragmentView);
        setContentView(this.mActivityFragmentView);
        initWidget();
        initData();
    }

    @Override
    public void initWidget() {
        super.initWidget();
        initWithBar();
        mLeftTextView.setText("项目信息");
        mLeftTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        ProjectInfoEntity mEntity = (ProjectInfoEntity) bundle.getSerializable(Constant.INTNET_DATA);
        tvStartTime.setText(mEntity.getStartTime());
        tvCompletionTime.setText(mEntity.getCompletionTime());
        etXmName.setText(mEntity.getXmName());
        etXmAdd.setText(mEntity.getXmAdd());
        etXmContact.setText(mEntity.getXmContact());
        etXmPerson.setText(mEntity.getXmPerson());
        etXmPersonNum.setText(mEntity.getXmPersonNum() + "");
        etConstructionUnit.setText(mEntity.getConstructionUnit());
    }
}
