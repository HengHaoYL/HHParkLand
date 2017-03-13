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
import com.henghao.parkland.adapter.ProjectSBDataAdapter;
import com.henghao.parkland.fragment.XiangmuFragment;
import com.henghao.parkland.model.entity.BaseEntity;
import com.henghao.parkland.model.entity.ProjectSBDataEntity;
import com.henghao.parkland.model.protocol.ProjectProtocol;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目管理 -- 设备信息
 */
public class ProjectSBDataActivity extends ActivityFragmentSupport {

    @ViewInject(R.id.lv_projectsbdata)
    private XListView mXlistView;

    @ViewInject(R.id.tv_state_projectsbdata)
    private TextView tvState;

    private List<ProjectSBDataEntity> data;
    private ProjectSBDataAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_project_sbdata);
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
        mLeftTextView.setText("设备信息");
        mLeftTextView.setVisibility(View.VISIBLE);
        initWithRightBar();
        mRightTextView.setVisibility(View.VISIBLE);
        mRightTextView.setText("添加");
        mRightLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (XiangmuFragment.mInfoEntity == null) {
                    msg("请先添加项目信息！");
                    return;
                }
                Intent intent = new Intent();
                intent.setClass(ProjectSBDataActivity.this, ProjectSBDataSubmitActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        View HeaderView = LayoutInflater.from(this).inflate(R.layout.include_projecttop, null);
        TextView tv_title = (TextView) HeaderView.findViewById(R.id.tv_title);
        tv_title.setText("设备信息");
        mXlistView.addHeaderView(HeaderView);
        data = new ArrayList<>();
        mAdapter = new ProjectSBDataAdapter(this, data);
        mXlistView.setAdapter(mAdapter);
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
        mProtocol.queryEquipmentMsg(getLoginUid());
        mActivityFragmentView.viewLoading(View.VISIBLE);
    }

    @Override
    public void OnMessageResponse(String url, Object jo, AjaxStatus status) throws JSONException {
        super.OnMessageResponse(url, jo, status);
        if (url.endsWith(ProtocolUrl.PROJECT_QUERYEQUIPMENTMSG)) {
            if (jo instanceof BaseEntity) {
                BaseEntity mData = (BaseEntity) jo;
                //msg(mData.getMsg());
                tvState.setVisibility(View.VISIBLE);
                tvState.setText(mData.getMsg());
                return;
            }
            tvState.setVisibility(View.GONE);
            List<ProjectSBDataEntity> homedata = (List<ProjectSBDataEntity>) jo;
            data.clear();
            data.addAll(homedata);
            mAdapter.notifyDataSetChanged();
        }
    }
}
