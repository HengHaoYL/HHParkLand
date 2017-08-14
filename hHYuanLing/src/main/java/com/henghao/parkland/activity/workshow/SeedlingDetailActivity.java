package com.henghao.parkland.activity.workshow;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.benefit.buy.library.views.NoScrollGridView;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
import com.henghao.parkland.R;
import com.henghao.parkland.adapter.CommonGridViewAdapter;
import com.henghao.parkland.model.entity.SeedlingEntity;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 晏琦云 on 2017/3/15.
 * 工作台苗木信息展示
 */

public class SeedlingDetailActivity extends ActivityFragmentSupport {

    CommonGridViewAdapter mAdapter;
    @InjectView(R.id.tv_titleName)
    TextView tvTitleName;
    @InjectView(R.id.tv_contact)
    TextView tvContact;
    @InjectView(R.id.tv_date)
    TextView tvDate;
    @InjectView(R.id.tv_time)
    TextView tvTime;
    @InjectView(R.id.tv_tel)
    TextView tvTel;
    @InjectView(R.id.tv_supplier)
    TextView tvSupplier;
    @InjectView(R.id.tv_address)
    TextView tvAddress;
    @InjectView(R.id.tv_breed)
    TextView tvBreed;
    @InjectView(R.id.tv_sub)
    TextView tvSub;
    @InjectView(R.id.tv_content)
    TextView tvContent;
    @InjectView(R.id.gridView)
    NoScrollGridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_seedlingdetail);
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
        mCenterTextView.setText("苗木信息");
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        SeedlingEntity mEntity = (SeedlingEntity) bundle.getSerializable(Constant.INTNET_DATA);
        ArrayList<String> urls = mEntity.getPicture();
        mAdapter = new CommonGridViewAdapter(this, urls);
        if (urls != null) {
            gridView.setAdapter(mAdapter);
        }
        tvAddress.setText(mEntity.getAddress());
        tvContact.setText(mEntity.getContact());
        tvContent.setText(mEntity.getContent());
        tvDate.setText(mEntity.getDate());
        tvTime.setText(mEntity.getTime());
        tvSub.setText(mEntity.getSub());
        tvTel.setText(mEntity.getTel());
        tvSupplier.setText(mEntity.getSupplier());
        tvTitleName.setText(mEntity.getTitleName());
        tvBreed.setText(mEntity.getBreed());
    }
}
