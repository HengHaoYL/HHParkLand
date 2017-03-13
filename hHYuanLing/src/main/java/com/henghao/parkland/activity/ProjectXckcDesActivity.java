package com.henghao.parkland.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.benefit.buy.library.views.NoScrollGridView;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
import com.henghao.parkland.R;
import com.henghao.parkland.adapter.CommonGridViewAdapter;
import com.henghao.parkland.model.entity.ProjectXcKcEntity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

import static com.henghao.parkland.R.id.gridView;

/**
 * 项目管理 -- 现场勘查详细信息
 */
public class ProjectXckcDesActivity extends ActivityFragmentSupport {

    @ViewInject(R.id.tv_title)
    TextView tv_title;
    @ViewInject(R.id.tv_name)
    TextView tv_name;
    @ViewInject(R.id.tv_time)
    TextView tv_time;
    @ViewInject(R.id.tv_address)
    TextView tv_address;
    @ViewInject(R.id.tv_person)
    TextView tv_person;
    @ViewInject(gridView)
    NoScrollGridView gridview;

    CommonGridViewAdapter mAdapter;

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
        mLeftTextView.setText("现场勘察");
        mLeftTextView.setVisibility(View.VISIBLE);
        tv_title.setText("现场勘察");
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        ProjectXcKcEntity mEntity = (ProjectXcKcEntity) bundle.getSerializable(Constant.INTNET_DATA);
        /**
         * 拼接图片URL地址
         */
        ArrayList<String> data = new ArrayList<>();
        ArrayList<String> urls = mEntity.getUrl();
        for (String url : urls) {
            data.add(mEntity.getXcSituation() + url);
        }
        mAdapter = new CommonGridViewAdapter(this, data);
        gridview.setAdapter(mAdapter);
        tv_name.setText(mEntity.getName());
        tv_time.setText(mEntity.getXcTime());
        tv_address.setText(mEntity.getXcAdd());
        tv_person.setText(mEntity.getXcPerson());
    }
}
