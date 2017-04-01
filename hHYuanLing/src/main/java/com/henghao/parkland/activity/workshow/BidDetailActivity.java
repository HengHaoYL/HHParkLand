package com.henghao.parkland.activity.workshow;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
import com.henghao.parkland.R;
import com.henghao.parkland.model.entity.BidEntity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 晏琦云 on 2017/3/15.
 * 工作台招标信息展示
 */

public class BidDetailActivity extends ActivityFragmentSupport {

    @InjectView(R.id.tv_titleName)
    TextView tvTitleName;
    @InjectView(R.id.tv_contacts)
    TextView tvContacts;
    @InjectView(R.id.tv_dates)
    TextView tvDates;
    @InjectView(R.id.tv_tel)
    TextView tvTel;
    @InjectView(R.id.tv_content)
    TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_biddetail);
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
        mCenterTextView.setText("招标信息");
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        BidEntity mEntity = (BidEntity) bundle.getSerializable(Constant.INTNET_DATA);
        tvContacts.setText(mEntity.getContacts());
        tvContent.setText(mEntity.getContent());
        tvDates.setText(mEntity.getDates());
        tvTel.setText(mEntity.getTel());
        tvTitleName.setText(mEntity.getTitleName());
    }
}
