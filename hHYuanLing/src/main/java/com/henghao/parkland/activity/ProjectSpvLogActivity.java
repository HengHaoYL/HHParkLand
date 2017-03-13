package com.henghao.parkland.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.benefit.buy.library.http.query.callback.AjaxStatus;
import com.benefit.buy.library.views.xlistview.XListView;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.ProtocolUrl;
import com.henghao.parkland.R;
import com.henghao.parkland.adapter.ProjectSpvLogAdapter;
import com.henghao.parkland.fragment.XiangmuFragment;
import com.henghao.parkland.model.entity.BaseEntity;
import com.henghao.parkland.model.entity.ProjectSpvLogEntity;
import com.henghao.parkland.model.protocol.ProjectProtocol;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 晏琦云 on 2017/2/22.
 * 监理日志展示界面
 */

public class ProjectSpvLogActivity extends ActivityFragmentSupport {


    @InjectView(R.id.tv_state_spvlog)
    TextView tvState;
    @InjectView(R.id.lv_spvlog)
    XListView listView;

    private List<ProjectSpvLogEntity> data;
    private ProjectSpvLogAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_project_spvlog);
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
        mLeftTextView.setText("监理日志");
        mLeftTextView.setVisibility(View.VISIBLE);
        initWithRightBar();
        mRightTextView.setText("添加");
        mRightTextView.setVisibility(View.VISIBLE);
        mRightLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (XiangmuFragment.mInfoEntity == null) {
                    msg("请先添加项目信息！");
                    return;
                }
                Intent intent = new Intent(ProjectSpvLogActivity.this, ProjectSpvLogSubmitActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initData() {
        View HeaderView = LayoutInflater.from(this).inflate(R.layout.include_projecttop, null);
        TextView tv_title = (TextView) HeaderView.findViewById(R.id.tv_title);
        tv_title.setText("监理日志");
        listView.addHeaderView(HeaderView);
        data = new ArrayList<>();
        mAdapter = new ProjectSpvLogAdapter(this, data);
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
        mProtocol.querySupervisionlogMsg(getLoginUid());
        mActivityFragmentView.viewLoading(View.VISIBLE);
    }

    @Override
    public void OnMessageResponse(String url, Object jo, AjaxStatus status) throws JSONException {
        super.OnMessageResponse(url, jo, status);
        if (url.endsWith(ProtocolUrl.PROJECT_QUERYSUPERVISIONLOGMSG)) {
            if (jo instanceof BaseEntity) {
                BaseEntity mData = (BaseEntity) jo;
                // msg(mData.getMsg());
                tvState.setVisibility(View.VISIBLE);
                tvState.setText(mData.getMsg());
                return;
            }
            tvState.setVisibility(View.GONE);
            List<ProjectSpvLogEntity> homedata = (List<ProjectSpvLogEntity>) jo;
            data.clear();
            data.addAll(homedata);
            mAdapter.notifyDataSetChanged();
        }
    }
}
