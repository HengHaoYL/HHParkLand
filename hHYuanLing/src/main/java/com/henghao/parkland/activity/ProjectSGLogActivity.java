package com.henghao.parkland.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.benefit.buy.library.http.query.callback.AjaxStatus;
import com.benefit.buy.library.views.xlistview.XListView;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.ProtocolUrl;
import com.henghao.parkland.R;
import com.henghao.parkland.adapter.ProjectSGLogAdapter;
import com.henghao.parkland.model.entity.BaseEntity;
import com.henghao.parkland.model.entity.ProjectSGLogEntity;
import com.henghao.parkland.model.protocol.ProjectProtocol;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 晏琦云 on 2017/2/22.
 * 项目管理 -- 施工日志
 */

public class ProjectSGLogActivity extends ActivityFragmentSupport {

    @InjectView(R.id.tv_state_log)
    TextView tvState;
    @InjectView(R.id.lv_log)
    XListView listView;

    private List<ProjectSGLogEntity> data;
    private ProjectSGLogAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_project_log);
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
        mLeftTextView.setText("施工日志");
        mLeftTextView.setVisibility(View.VISIBLE);
        initWithRightBar();
        mRightTextView.setText("添加");
        mRightTextView.setVisibility(View.VISIBLE);
        mRightLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProjectSGLogActivity.this, ProjectSGLogSubmitActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initData() {
        View HeaderView = LayoutInflater.from(this).inflate(R.layout.include_projecttop, null);
        TextView tv_title = (TextView) HeaderView.findViewById(R.id.tv_title);
        tv_title.setText("施工日志");
        listView.addHeaderView(HeaderView);
        data = new ArrayList<>();
        mAdapter = new ProjectSGLogAdapter(this, data);
        listView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        /**
         * 访问网络
         */
        ProjectProtocol mProtocol = new ProjectProtocol(this);
        mProtocol.addResponseListener(this);
        mProtocol.queryBuildersdiaryMsg(getLoginUid());
        mActivityFragmentView.viewLoading(View.VISIBLE);
    }

    @Override
    public void OnMessageResponse(String url, Object jo, AjaxStatus status) throws JSONException {
        super.OnMessageResponse(url, jo, status);
        if (url.endsWith(ProtocolUrl.PROJECT_QUERYBUILDERSDIARYMSG)) {
            if (jo instanceof BaseEntity) {
                BaseEntity mData = (BaseEntity) jo;
                // msg(mData.getMsg());
                tvState.setVisibility(View.VISIBLE);
                tvState.setText(mData.getMsg());
                return;
            }
            tvState.setVisibility(View.GONE);
            List<ProjectSGLogEntity> homedata = (List<ProjectSGLogEntity>) jo;
            data.clear();
            data.addAll(homedata);
            mAdapter.notifyDataSetChanged();
        }
    }
}
