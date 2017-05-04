package com.henghao.parkland.activity.workshow;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
import com.henghao.parkland.R;
import com.henghao.parkland.model.entity.RecruitEntity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 晏琦云 on 2017/3/16.
 * 工作台人员招聘展示
 */
public class RecruitDetailActivity extends ActivityFragmentSupport {

    @InjectView(R.id.tv_position)
    TextView tvPosition;
    @InjectView(R.id.tv_companyName)
    TextView tvCompanyName;
    @InjectView(R.id.tv_workingAdress)
    TextView tvWorkingAdress;
    @InjectView(R.id.tv_contact)
    TextView tvContact;
    @InjectView(R.id.tv_dates)
    TextView tvDates;
    @InjectView(R.id.tv_tel)
    TextView tvTel;
    @InjectView(R.id.tv_detail)
    TextView tvDetail;
    @InjectView(R.id.tv_companyAdress)
    TextView tvCompanyAdress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_recruitdetail);
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
        mCenterTextView.setText("人员招聘");
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        RecruitEntity mEntity = (RecruitEntity) bundle.getSerializable(Constant.INTNET_DATA);
        tvCompanyName.setText(mEntity.getCompanyName());
        tvCompanyAdress.setText(mEntity.getCompanyAdress());
        tvContact.setText(mEntity.getContact());
        tvDates.setText(mEntity.getDates());
        tvDetail.setText(mEntity.getDetail());
        tvPosition.setText(mEntity.getPosition());
        tvTel.setText(mEntity.getTel());
        tvWorkingAdress.setText(mEntity.getWorkingAdress());
    }
}
