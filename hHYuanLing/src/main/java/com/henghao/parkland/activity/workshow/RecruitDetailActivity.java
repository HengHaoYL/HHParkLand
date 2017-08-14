package com.henghao.parkland.activity.workshow;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
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


    @InjectView(R.id.ll_layout1)
    LinearLayout layout1;
    @InjectView(R.id.ll_layout2)
    LinearLayout layout2;
    @InjectView(R.id.ll_layout3)
    LinearLayout layout3;
    @InjectView(R.id.tv_positions)
    TextView tvPositions;
    @InjectView(R.id.tv_money)
    TextView tvMoney;
    @InjectView(R.id.tv_companyName)
    TextView tvCompanyName;
    @InjectView(R.id.tv_companyAdress)
    TextView tvCompanyAdress;
    @InjectView(R.id.tv_workAdress)
    TextView tvWorkAdress;
    @InjectView(R.id.tv_companyIntro)
    TextView tvCompanyIntro;
    @InjectView(R.id.tv_content)
    TextView tvContent;
    @InjectView(R.id.tv_experience)
    TextView tvExperience;
    @InjectView(R.id.tv_evaluate)
    TextView tvEvaluate;
    @InjectView(R.id.tv_contact)
    TextView tvContact;
    @InjectView(R.id.tv_date)
    TextView tvDate;
    @InjectView(R.id.tv_time)
    TextView tvTime;
    @InjectView(R.id.tv_tel)
    TextView tvTel;
    @InjectView(R.id.tv_email)
    TextView tvEmail;

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
        tvPositions.setText(mEntity.getPositions());
        tvMoney.setText(mEntity.getMoney() + "");
        tvContact.setText(mEntity.getContact());
        tvDate.setText(mEntity.getDate());
        tvTime.setText(mEntity.getTime());
        tvTel.setText(mEntity.getTel());
        tvEmail.setText(mEntity.getEmail());
        //根据信息的类型（招聘信息/应聘信息）显示不同的数据
        layout1.setVisibility(View.GONE);
        layout2.setVisibility(View.GONE);
        layout3.setVisibility(View.GONE);
        if (mEntity.getType().equals("应聘")) {
            //显示工作经历与自我评价
            layout3.setVisibility(View.VISIBLE);
            tvExperience.setText(mEntity.getExperience());
            tvEvaluate.setText(mEntity.getEvaluate());
        } else if (mEntity.getType().equals("招聘")) {
            //显示公司名称、公司地址、工作地址、公司简介、工作内容
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.VISIBLE);
            tvCompanyName.setText(mEntity.getCompanyName());
            tvCompanyAdress.setText(mEntity.getCompanyAdress());
            tvWorkAdress.setText(mEntity.getWorkAdress());
            tvCompanyIntro.setText(mEntity.getCompanyIntro());
            tvContent.setText(mEntity.getContent());
        }
    }
}
