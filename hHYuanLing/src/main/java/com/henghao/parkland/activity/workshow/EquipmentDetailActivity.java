package com.henghao.parkland.activity.workshow;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.benefit.buy.library.views.NoScrollGridView;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
import com.henghao.parkland.R;
import com.henghao.parkland.adapter.CommonGridViewAdapter;
import com.henghao.parkland.model.entity.EquipmentEntity;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 晏琦云 on 2017/3/15.
 * 工作台设备租赁信息展示
 */

public class EquipmentDetailActivity extends ActivityFragmentSupport {
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
    @InjectView(R.id.tv_content)
    TextView tvContent;
    @InjectView(R.id.gridView)
    NoScrollGridView gridView;

    CommonGridViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_equipmentleasingdetail);
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
        mCenterTextView.setText("设备租赁");
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        EquipmentEntity mEntity = (EquipmentEntity) bundle.getSerializable(Constant.INTNET_DATA);
        ArrayList<String> urls = mEntity.getPicture();
        mAdapter = new CommonGridViewAdapter(this, urls);
        if (urls != null) {
            gridView.setAdapter(mAdapter);
        }
        tvContact.setText(mEntity.getContact());
        tvContent.setText(mEntity.getContent());
        tvDate.setText(mEntity.getDate());
        tvTime.setText(mEntity.getTime());
        tvTel.setText(mEntity.getTel());
        tvTitleName.setText(mEntity.getTitleName());
    }
}
