package com.henghao.parkland.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.benefit.buy.library.views.xlistview.XListView;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
import com.henghao.parkland.R;
import com.henghao.parkland.adapter.SGBWAdapter;
import com.henghao.parkland.model.entity.SGBWEntity;
import com.henghao.parkland.model.protocol.HttpPublic;
import com.lidroid.xutils.ViewUtils;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 晏琦云 on 2017/2/22.
 * 施工备忘展示界面
 */

public class ProjectSGBWActivity extends ActivityFragmentSupport {


    @InjectView(R.id.tv_state_log)
    TextView tvState;

    @InjectView(R.id.lv_sgbw)
    XListView listView;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    private List<SGBWEntity> data;
    private SGBWAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_project_sgbw);
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
                Intent intent = new Intent(ProjectSGBWActivity.this, ProjectSGBWAddActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initData() {
        initWithBar();
        mLeftTextView.setVisibility(View.VISIBLE);
        mLeftTextView.setText("施工备忘");
        tv_title.setText("施工备忘");
        data = new ArrayList<>();
        adapter = new SGBWAdapter(this, data);
    }

    @Override
    public void onResume() {
        super.onResume();
        httpRequest();
    }

    /**
     * 访问网络
     */
    private void httpRequest() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        SharedPreferences preferences = getSharedPreferences(Constant.SHARED_SET, 0);
        String UID = preferences.getString(Constant.USERID, null);
        FormEncodingBuilder requestBodyBuilder = new FormEncodingBuilder();
        requestBodyBuilder.add("uid", UID);
        RequestBody requestBody = requestBodyBuilder.build();
        Request request = builder.url(HttpPublic.QUERYSGMEMOMSG).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        mActivityFragmentView.viewLoading(View.VISIBLE);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mActivityFragmentView.viewLoading(View.GONE);
                        Toast.makeText(ProjectSGBWActivity.this, "网络访问错误！", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String result_str = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(result_str);
                    int error = jsonObject.getInt("error");
                    if (error == 1) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvState.setVisibility(View.GONE);
                            }
                        });
                        data.clear();
                        JSONArray dataArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < dataArray.length(); i++) {
                            SGBWEntity entity = new SGBWEntity();
                            JSONObject dataObject = dataArray.getJSONObject(i);
                            String projectname = dataObject.getString("projectName");
                            String workcontent = dataObject.getString("projectDetail");
                            entity.setProject_name(projectname);
                            entity.setWork_content(workcontent);
                            data.add(entity);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                                listView.setAdapter(adapter);
                                mActivityFragmentView.viewLoading(View.GONE);
                            }
                        });
                    } else if (error == 0) {
                        final String result = jsonObject.getString("result");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ProjectSGBWActivity.this, result, Toast.LENGTH_SHORT).show();
                                tvState.setVisibility(View.VISIBLE);
                                tvState.setText(result);
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
