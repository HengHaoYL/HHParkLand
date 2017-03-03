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
import com.henghao.parkland.adapter.ProjectBGManageAdapter;
import com.henghao.parkland.model.entity.BaseEntity;
import com.henghao.parkland.model.entity.ProjectBGManageEntity;
import com.henghao.parkland.model.protocol.ProjectProtocol;
import com.lidroid.xutils.ViewUtils;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 项目管理 -- 变更管理
 */
public class ProjectBGManageActivity extends ActivityFragmentSupport {

    @InjectView(R.id.tv_state_projectbgmanage)
    TextView tvState;
    @InjectView(R.id.lv_projectbgmanage)
    XListView listView;
    private ProjectBGManageAdapter mAdapter;

    private List<ProjectBGManageEntity> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_project_bgmanage);
        this.mActivityFragmentView.viewEmpty(R.layout.activity_empty);
        this.mActivityFragmentView.viewEmptyGone();
        this.mActivityFragmentView.viewLoading(View.GONE);
        this.mActivityFragmentView.getNavitionBarView().setVisibility(View.VISIBLE);
        setContentView(this.mActivityFragmentView);
        ViewUtils.inject(this, this.mActivityFragmentView);
        ButterKnife.inject(this);
        initWidget();
        initData();
    }

    @Override
    public void initWidget() {
        super.initWidget();
        initWithBar();
        mLeftTextView.setText("变更管理");
        mLeftTextView.setVisibility(View.VISIBLE);
        initWithRightBar();
        mRightTextView.setText("添加");
        mRightTextView.setVisibility(View.VISIBLE);
        mRightLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProjectBGManageActivity.this, ProjectBGManageSubmitActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        View HeaderView = LayoutInflater.from(this).inflate(R.layout.include_projecttop, null);
        TextView tv_title = (TextView) HeaderView.findViewById(R.id.tv_title);
        tv_title.setText("变更管理");
        listView.addHeaderView(HeaderView);
        data = new ArrayList<>();
        mAdapter = new ProjectBGManageAdapter(this, data);
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
        mProtocol.queryAlterationMsg(getLoginUid());
        mActivityFragmentView.viewLoading(View.VISIBLE);
    }

    @Override
    public void OnMessageResponse(String url, Object jo, AjaxStatus status) throws JSONException {
        super.OnMessageResponse(url, jo, status);
        if (url.endsWith(ProtocolUrl.PROJECT_QUERYALTERATIONMSG)) {
            if (jo instanceof BaseEntity) {
                BaseEntity mData = (BaseEntity) jo;
                if (mData.getError() == 0) {
                    msg(mData.getMsg());
                    tvState.setVisibility(View.VISIBLE);
                    tvState.setText(mData.getMsg());
                    return;
                } else {
                    tvState.setVisibility(View.GONE);
                    String jsonStr = ToolsJson.toJson(mData.getData());
                    Type type = new TypeToken<List<ProjectBGManageEntity>>() {
                    }.getType();
                    data.clear();
                    List<ProjectBGManageEntity> homeData = ToolsJson.parseObjecta(jsonStr, type);
                    String topPath = mData.getPath();//图片URL头部地址
                    for (ProjectBGManageEntity entity : homeData) {
                        entity.setFile(topPath);
                        data.add(entity);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.notifyDataSetChanged();
                            listView.setAdapter(mAdapter);
                        }
                    });
                }
            }
        }
    }
}
