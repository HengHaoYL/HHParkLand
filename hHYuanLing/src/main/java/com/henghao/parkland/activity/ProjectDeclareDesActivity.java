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
import com.henghao.parkland.model.entity.ProjectDeclareEntity;
import com.henghao.parkland.utils.ScanImageUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * 项目管理 -- 进度申报
 */
public class ProjectDeclareDesActivity extends ActivityFragmentSupport {

    @ViewInject(R.id.tv_time)
    TextView tv_time;

    @ViewInject(R.id.gridview)
    GridView gridview;
    private ArrayList<String> mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_decaler_submit);
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
        mLeftTextView.setText("进度申报");
        mLeftTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        ProjectDeclareEntity mEntity = (ProjectDeclareEntity) bundle.getSerializable(Constant.INTNET_DATA);
        final String path = bundle.getString(Constant.INTNET_URL);
        tv_time.setText(mEntity.getDates());
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
                ScanImageUtils.ScanImage(ProjectDeclareDesActivity.this, mUrl, position);
            }
        });
    }
}
