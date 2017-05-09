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
    @InjectView(R.id.tv_contacts)
    TextView tvContacts;
    @InjectView(R.id.tv_dates)
    TextView tvDates;
    @InjectView(R.id.tv_tel)
    TextView tvTel;
    @InjectView(R.id.tv_supplier)
    TextView tvSupplier;
    @InjectView(R.id.tv_address)
    TextView tvAddress;
    @InjectView(R.id.tv_type)
    TextView tvType;
    @InjectView(R.id.tv_subclass)
    TextView tvSubclass;
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
        /**
         * 拼接图片URL地址
         */
        ArrayList<String> data = new ArrayList<>();
        ArrayList<String> urls = mEntity.getUrl();
        for (String url : urls) {
            data.add(mEntity.getFilesId() + url);
        }
        mAdapter = new CommonGridViewAdapter(this, data);
        gridView.setAdapter(mAdapter);
        tvAddress.setText(mEntity.getAddress());
        tvContacts.setText(mEntity.getContacts());
        tvContent.setText(mEntity.getContent());
        tvDates.setText(mEntity.getDates());
        tvSubclass.setText(mEntity.getSubclass());
        tvTel.setText(mEntity.getTel());
        tvSupplier.setText(mEntity.getSupplier());
        tvTitleName.setText(mEntity.getTitleName());
        tvType.setText(mEntity.getType());
    }
}
