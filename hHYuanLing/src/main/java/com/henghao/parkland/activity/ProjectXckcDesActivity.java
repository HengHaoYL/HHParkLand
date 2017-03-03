package com.henghao.parkland.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
import com.henghao.parkland.R;
import com.henghao.parkland.adapter.CommonGridViewAdapter;
import com.henghao.parkland.model.entity.ProjectXcKcEntity;
import com.henghao.parkland.utils.ScanImageUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * 项目管理 -- 现场勘查详细信息
 */
public class ProjectXckcDesActivity extends ActivityFragmentSupport {

    @ViewInject(R.id.tv_time)
    TextView tv_time;
    @ViewInject(R.id.tv_address)
    TextView tv_address;
    @ViewInject(R.id.tv_person)
    TextView tv_person;

    @ViewInject(R.id.gridview)
    GridView gridview;
    private ArrayList<String> mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_project_xckcdesc);
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
        ProjectXcKcEntity mEntity = (ProjectXcKcEntity) bundle.getSerializable(Constant.INTNET_DATA);
        final String path = bundle.getString(Constant.INTNET_URL);
        tv_time.setText(mEntity.getXcTime());
        tv_address.setText(mEntity.getXcAdd());
        tv_person.setText(mEntity.getXcPerson());
        CommonGridViewAdapter mAdapter = new CommonGridViewAdapter(this, mEntity.getUrl());
        gridview.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        //处理图片地址
        mUrl = new ArrayList<>();
        for (String url : mEntity.getUrl()) {
            mUrl.add(path + url);
        }
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ScanImageUtils.ScanImage(ProjectXckcDesActivity.this, mUrl, position);
            }
        });
    }
}
