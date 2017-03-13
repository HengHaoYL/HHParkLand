package com.henghao.parkland.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.henghao.parkland.adapter.ProjectJunGongAdapter;
import com.henghao.parkland.fragment.XiangmuFragment;
import com.henghao.parkland.model.entity.BaseEntity;
import com.henghao.parkland.model.entity.ProjectJunGongEntity;
import com.henghao.parkland.model.protocol.ProjectProtocol;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 晏琦云 on 2017/2/22.
 * 竣工验收展示界面
 */

public class ProjectJunGongActivity extends ActivityFragmentSupport {


    @InjectView(R.id.tv_state_jungong)
    TextView tvState;
    @InjectView(R.id.lv_jungong)
    XListView listView;

    private List<ProjectJunGongEntity> data;
    private ProjectJunGongAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_project_jungong);
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
        mLeftTextView.setText("竣工验收");
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
                Intent intent = new Intent(ProjectJunGongActivity.this, ProjectJunGongSubmitActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initData() {
        View HeaderView = LayoutInflater.from(this).inflate(R.layout.include_projecttop, null);
        TextView tv_title = (TextView) HeaderView.findViewById(R.id.tv_title);
        tv_title.setText("竣工验收");
        listView.addHeaderView(HeaderView);
        data = new ArrayList<>();
        mAdapter = new ProjectJunGongAdapter(this, data);
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
        mProtocol.queryFinalacceptanceMsg(getLoginUid());
        mActivityFragmentView.viewLoading(View.VISIBLE);
    }

    @Override
    public void OnMessageResponse(String url, Object jo, AjaxStatus status) throws JSONException {
        super.OnMessageResponse(url, jo, status);
        if (url.endsWith(ProtocolUrl.PROJECT_QUERYFINALACCEPTANCEMSG)) {
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
                    Type type = new TypeToken<List<ProjectJunGongEntity>>() {
                    }.getType();
                    data.clear();
                    List<ProjectJunGongEntity> homeData = ToolsJson.parseObjecta(jsonStr, type);
                    String topPath = mData.getPath();//图片URL头部地址
                    for (ProjectJunGongEntity entity : homeData) {
                        entity.setCompletionDrawingId(topPath);
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
