package com.henghao.parkland.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.benefit.buy.library.http.query.callback.AjaxStatus;
import com.benefit.buy.library.utils.tools.ToolsJson;
import com.benefit.buy.library.views.xlistview.XListView;
import com.google.gson.reflect.TypeToken;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.ProtocolUrl;
import com.henghao.parkland.R;
import com.henghao.parkland.adapter.SGMaterialAdapter;
import com.henghao.parkland.model.entity.BaseEntity;
import com.henghao.parkland.model.entity.SGMaterialEntity;
import com.henghao.parkland.model.protocol.ProjectProtocol;
import com.lidroid.xutils.ViewUtils;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 晏琦云 on 2017/2/24.
 * 施工资料展示界面
 */

public class ProjectSGMaterialActivity extends ActivityFragmentSupport {


    @InjectView(R.id.tv_state_sgmaterial)
    TextView tvState;
    @InjectView(R.id.lv_sgmaterial)
    XListView listView;
    @InjectView(R.id.tv_title)
    TextView tv_title;

    private List<SGMaterialEntity> data;
    private SGMaterialAdapter adapter;
    private static final String TAG = "ProjectSGMaterialActivi";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_project_sgmaterial);
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
        initWithRightBar();
        mRightTextView.setText("添加");
        mRightTextView.setVisibility(View.VISIBLE);
        mRightLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProjectSGMaterialActivity.this, ProjectSGMaterialAddActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void initData() {
        initWithBar();
        mLeftTextView.setVisibility(View.VISIBLE);
        mLeftTextView.setText("施工资料");
        tv_title.setText("施工资料");
        data = new ArrayList<>();
        adapter = new SGMaterialAdapter(this, data);
    }

    @Override
    public void onResume() {
        super.onResume();
        //httpRequest();
        ProjectProtocol mProtocol = new ProjectProtocol(this);
        mProtocol.addResponseListener(this);
        mProtocol.querySGZL(getLoginUid());
        mActivityFragmentView.viewLoading(View.VISIBLE);
    }


    @Override
    public void OnMessageResponse(String url, Object jo, AjaxStatus status) throws JSONException {
        super.OnMessageResponse(url, jo, status);
        if (url.endsWith(ProtocolUrl.PROJECT_QUARYSGZL)) {
            BaseEntity mData = (BaseEntity) jo;
            if (mData.getError() == 0) {
                msg(mData.getMsg());
                tvState.setVisibility(View.VISIBLE);
                tvState.setText(mData.getMsg());
                return;
            } else {
                tvState.setVisibility(View.GONE);
                String jsonStr = ToolsJson.toJson(mData.getData());
                Type type = new TypeToken<List<SGMaterialEntity>>() {
                }.getType();
                data.clear();
                List<SGMaterialEntity> homeData = ToolsJson.parseObjecta(jsonStr, type);
                String topPath = mData.getPath();//图片URL头部地址
                for (SGMaterialEntity entity : homeData) {
                    String work_content = entity.getContent();
                    List<String> urls = entity.getUrl();
                    String work_img_url = topPath + urls.get(0);
                    entity.setContent(work_content);
                    entity.setWorkImg(work_img_url);
                    data.add(entity);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        listView.setAdapter(adapter);
                    }
                });
            }
        }
    }
}
