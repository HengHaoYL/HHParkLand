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
import com.henghao.parkland.model.entity.ProjectTechnologEntity;
import com.henghao.parkland.utils.ScanImageUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * 项目管理 -- 技术交底
 */
public class ProjectTechnologDesActivity extends ActivityFragmentSupport {

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
        mLeftTextView.setText("技术交底");
        mLeftTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        ProjectTechnologEntity mEntity = (ProjectTechnologEntity) bundle.getSerializable(Constant.INTNET_DATA);
        final String path = bundle.getString(Constant.INTNET_URL);
        tv_time.setText(mEntity.getDates());
        tv_address.setText(mEntity.getSites());
        tv_person.setText(mEntity.getContent());
        //处理图片地址
        mUrl = new ArrayList<>();
        for (String url : mEntity.getUrl()) {
            mUrl.add(path + url);
        }
        CommonGridViewAdapter mAdapter = new CommonGridViewAdapter(this, mUrl);
        gridview.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ScanImageUtils.ScanImage(ProjectTechnologDesActivity.this, mUrl, position);
            }
        });
    }
}
