package com.henghao.parkland.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.benefit.buy.library.http.query.callback.AjaxStatus;
import com.benefit.buy.library.utils.tools.ToolsJson;
import com.benefit.buy.library.views.xlistview.XListView;
import com.google.gson.reflect.TypeToken;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.ProtocolUrl;
import com.henghao.parkland.R;
import com.henghao.parkland.adapter.MyWorkerListAdapter;
import com.henghao.parkland.model.entity.BaseEntity;
import com.henghao.parkland.model.entity.MyWorkerEntity;
import com.henghao.parkland.model.protocol.ProjectSecProtocol;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 *我的轨迹展示列表
 */
public class MyWorkerListActivity extends ActivityFragmentSupport {

    @ViewInject(R.id.listview)
    private XListView mXlistView;

    private List<MyWorkerEntity> mData;

    private MyWorkerListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.common_xlistview);
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
        mData = new ArrayList<>();
        mActivityFragmentView.viewLoading(View.VISIBLE);
        initWithBar();
        mLeftTextView.setText("我的轨迹");
        mLeftTextView.setVisibility(View.VISIBLE);
        initWithRightBar();
        mRightTextView.setVisibility(View.VISIBLE);
        mRightTextView.setText("添加");
        mRightLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MyWorkerListActivity.this, MyWorkerActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ProjectSecProtocol mProtocol = new ProjectSecProtocol(this);
        mProtocol.addResponseListener(this);
        mProtocol.queryMylocusMsg(getLoginUid());
        mActivityFragmentView.viewLoading(View.VISIBLE);
    }

    @Override
    public void initData() {
        super.initData();
        mAdapter = new MyWorkerListAdapter(this, mData);
        mXlistView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void OnMessageResponse(String url, Object jo, AjaxStatus status) throws JSONException {
        super.OnMessageResponse(url, jo, status);
        if(jo == null){
            mActivityFragmentView.viewMainGone();
            return;
        }
        if (url.endsWith(ProtocolUrl.PROJECT_QUERY_MYLOCUSMSG)) {
            if (jo instanceof BaseEntity) {
                BaseEntity mEntity = (BaseEntity) jo;
                if (mEntity.getData() == null) {
                    mActivityFragmentView.viewMainGone();
                    return;
                }
                mActivityFragmentView.viewEmptyGone();
                String data = ToolsJson.toJson(mEntity.getData());
                Type type = new TypeToken<List<MyWorkerEntity>>() {
                }.getType();
                List<MyWorkerEntity> homeData = ToolsJson.parseObjecta(data, type);
                mData.clear();
                mData.addAll(homeData);
                mAdapter.notifyDataSetChanged();
                return;
            }
        }
    }
}
