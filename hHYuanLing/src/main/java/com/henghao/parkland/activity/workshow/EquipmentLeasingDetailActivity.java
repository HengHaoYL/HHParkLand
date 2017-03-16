package com.henghao.parkland.activity.workshow;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.benefit.buy.library.views.NoScrollGridView;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
import com.henghao.parkland.R;
import com.henghao.parkland.adapter.CommonGridViewAdapter;
import com.henghao.parkland.model.entity.EquipmentLeasingEntity;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 晏琦云 on 2017/3/15.
 * 工作台设备租赁信息展示
 */

public class EquipmentLeasingDetailActivity extends ActivityFragmentSupport {
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
        mLeftTextView.setText("设备租赁");
        mLeftTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        EquipmentLeasingEntity mEntity = (EquipmentLeasingEntity) bundle.getSerializable(Constant.INTNET_DATA);
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
        tvContacts.setText(mEntity.getContacts());
        tvContent.setText(mEntity.getContent());
        tvDates.setText(mEntity.getDates());
        tvTel.setText(mEntity.getTel());
        tvTitleName.setText(mEntity.getTitleName());
    }
}
