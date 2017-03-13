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
import com.henghao.parkland.adapter.ProjectGXBYAdapter;
import com.henghao.parkland.fragment.XiangmuFragment;
import com.henghao.parkland.model.entity.BaseEntity;
import com.henghao.parkland.model.entity.ProjectGXBYEntity;
import com.henghao.parkland.model.protocol.ProjectProtocol;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目管理 -- 工序报验
 */
public class ProjectGXBYActivity extends ActivityFragmentSupport {

    @ViewInject(R.id.lv_projectgxby)
    private XListView mXlistView;
    @ViewInject(R.id.tv_state_projectgxby)
    private TextView tvState;

    private List<ProjectGXBYEntity> data;
    private ProjectGXBYAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_project_gxby);
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
        mLeftTextView.setText("工序报验");
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
                intent.setClass(ProjectGXBYActivity.this, ProjectGXBYSubmitActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        View HeaderView = LayoutInflater.from(this).inflate(R.layout.include_projecttop, null);
        TextView tv_title = (TextView) HeaderView.findViewById(R.id.tv_title);
        tv_title.setText("工序报验");
        mXlistView.addHeaderView(HeaderView);
        data = new ArrayList<>();
        mAdapter = new ProjectGXBYAdapter(this, data);
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
        mProtocol.queryCheckoutMsg(getLoginUid());
        mActivityFragmentView.viewLoading(View.VISIBLE);
    }

    @Override
    public void OnMessageResponse(String url, Object jo, AjaxStatus status) throws JSONException {
        super.OnMessageResponse(url, jo, status);
        if (url.endsWith(ProtocolUrl.PROJECT_QUERYCHECKOUTMSG)) {
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
                    Type type = new TypeToken<List<ProjectGXBYEntity>>() {
                    }.getType();
                    data.clear();
                    List<ProjectGXBYEntity> homeData = ToolsJson.parseObjecta(jsonStr, type);
                    String topPath = mData.getPath();//图片URL头部地址
                    for (ProjectGXBYEntity entity : homeData) {
                        entity.setGxImgId(topPath);
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
