package com.henghao.parkland.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.benefit.buy.library.http.query.callback.AjaxStatus;
import com.benefit.buy.library.utils.tools.ToolsJson;
import com.benefit.buy.library.views.xlistview.XListView;
import com.google.gson.reflect.TypeToken;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.ProtocolUrl;
import com.henghao.parkland.R;
import com.henghao.parkland.adapter.ProjectXckcAdapter;
import com.henghao.parkland.model.entity.BaseEntity;
import com.henghao.parkland.model.entity.ProjectXcKcEntity;
import com.henghao.parkland.model.protocol.ProjectSecProtocol;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目管理 -- 现场勘查
 */
public class ProjectXckcActivity extends ActivityFragmentSupport {

    @ViewInject(R.id.lv_projectxckc)
    private XListView mXlistView;
    @ViewInject(R.id.tv_state_projectxckc)
    private TextView tvState;

    private List<ProjectXcKcEntity> data;
    private ProjectXckcAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_project_xckc);
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
        data = new ArrayList<>();
        mActivityFragmentView.viewLoading(View.VISIBLE);
        initWithBar();
        mLeftTextView.setText("现场勘查");
        mLeftTextView.setVisibility(View.VISIBLE);
        initWithRightBar();
        mRightTextView.setVisibility(View.VISIBLE);
        mRightTextView.setText("添加");
        mRightLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ProjectXckcActivity.this, ProjectXckcSubmitActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ProjectSecProtocol mProtocol = new ProjectSecProtocol(this);
        mProtocol.addResponseListener(this);
        mProtocol.queryXCKC(getLoginUid());
    }

    @Override
    public void initData() {
        super.initData();
        View HeaderView = LayoutInflater.from(this).inflate(R.layout.include_projecttop, null);
        TextView tv_title = (TextView) HeaderView.findViewById(R.id.tv_title);
        tv_title.setText("现场勘察");
        mXlistView.addHeaderView(HeaderView);
        mAdapter = new ProjectXckcAdapter(this, data);
        mXlistView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void OnMessageResponse(String url, Object jo, AjaxStatus status) throws JSONException {
        super.OnMessageResponse(url, jo, status);
        if (url.endsWith(ProtocolUrl.PROJECT_QUERYXCKC)) {
            if (jo instanceof BaseEntity) {
                BaseEntity mData = (BaseEntity) jo;
                if (mData.getError() == 0) {
                    //msg(mData.getMsg());
                    tvState.setVisibility(View.VISIBLE);
                    tvState.setText(mData.getMsg());
                    return;
                } else {
                    tvState.setVisibility(View.GONE);
                    String jsonStr = ToolsJson.toJson(mData.getData());
                    Type type = new TypeToken<List<ProjectXcKcEntity>>() {
                    }.getType();
                    data.clear();
                    List<ProjectXcKcEntity> homeData = ToolsJson.parseObjecta(jsonStr, type);
                    String topPath = mData.getPath();//图片URL头部地址
                    for (ProjectXcKcEntity entity : homeData) {
                        entity.setXcSituation(topPath);
                        data.add(entity);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.notifyDataSetChanged();
                            mXlistView.setAdapter(mAdapter);
                        }
                    });
                }
            }
        }
    }
}
