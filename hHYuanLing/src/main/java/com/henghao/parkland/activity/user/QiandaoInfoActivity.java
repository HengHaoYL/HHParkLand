package com.henghao.parkland.activity.user;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.benefit.buy.library.utils.tools.ToolsJson;
import com.benefit.buy.library.views.xlistview.XListView;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.BuildConfig;
import com.henghao.parkland.R;
import com.henghao.parkland.adapter.QiandaoInfoAdapter;
import com.henghao.parkland.model.entity.BaseEntity;
import com.henghao.parkland.model.entity.QiandaoInfoEntity;
import com.henghao.parkland.utils.Requester;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;

/**
 * Created by ASUS on 2017/8/16.
 * 签到情况展示界面
 */

public class QiandaoInfoActivity extends ActivityFragmentSupport implements XListView.IXListViewListener {
    @InjectView(R.id.listView_qiandao)
    XListView listView;

    private static final String TAG = "QiandaoInfoActivity";

    private QiandaoInfoAdapter mAdapter;//签到情况适配器
    private List<QiandaoInfoEntity> qiandaoInfoEntities;//签到情况数据
    private List<QiandaoInfoEntity> initQiandaoInfoEntities;//初始加载签到情况数据
    private int page = 0;//默认查询页数为0
    private Call findSignInCall;//查询签到情况请求

    private int indexOfSelect = 0;//选中的板块 0个人信息 1企业信息
    private String[] names = {"个人信息", "企业信息"};//对话框选项

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_qiandao_info);
        this.mActivityFragmentView.viewEmpty(R.layout.activity_empty);
        this.mActivityFragmentView.viewEmptyGone();
        this.mActivityFragmentView.viewLoading(View.GONE);
        this.mActivityFragmentView.clipToPadding(true);
        setContentView(this.mActivityFragmentView);
        ButterKnife.inject(this);
        initWidget();
        initData();
    }

    @Override
    public void initWidget() {
        listView.setXListViewListener(this);
        mActivityFragmentView.viewMainGone();
        initWithBar();
        this.mLeftTextView.setVisibility(View.VISIBLE);
        this.mLeftTextView.setText("返回");
        initWithCenterBar();
        this.mCenterTextView.setVisibility(View.VISIBLE);
        this.mCenterTextView.setText("签到情况");
        initWithRightBar();
        this.mRightTextView.setVisibility(View.VISIBLE);
        this.mRightTextView.setText("更多");
        this.mRightLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setIcon(R.drawable.icon_select);
                builder.setTitle("请选择");
                builder.setItems(names, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        page = 0;//重置页数
                        switch (which) {
                            case 0://个人信息
                                initQiandaoInfoEntities.clear();//清空缓存
                                findSignInCall = Requester.findSignIn(page, getLoginUid(), "", findSignInCallBaclk);
                                listView.setAdapter(mAdapter);
                                listView.setPullLoadEnable(true);//设置可上滑加载更多
                                indexOfSelect = 0;
                                break;
                            case 1://企业信息
                                initQiandaoInfoEntities.clear();//清空缓存
                                findSignInCall = Requester.findSignIn(page, "", getLoginUser().getDeptId(), findSignInCallBaclk);
                                listView.setAdapter(mAdapter);
                                listView.setPullLoadEnable(true);//设置可上滑加载更多
                                indexOfSelect = 0;
                                break;
                        }
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public void initData() {
        listView.setPullLoadEnable(true);//设置可上滑加载更多
        qiandaoInfoEntities = new ArrayList<>();
        initQiandaoInfoEntities = new ArrayList<>();
        mAdapter = new QiandaoInfoAdapter(this, initQiandaoInfoEntities);
        //请求网络，查询签到情况，默认查询个人信息
        findSignInCall = Requester.findSignIn(page, getLoginUid(), "", findSignInCallBaclk);
        listView.setAdapter(mAdapter);
    }

    private DefaultCallback findSignInCallBaclk = new DefaultCallback() {
        @Override
        public void onFailure(Exception e, int code) {
            if (BuildConfig.DEBUG) Log.e(TAG, "onFailure: code = " + code, e);
            e.printStackTrace();
            mActivityFragmentView.viewMainGone();
            Toast.makeText(context, "网络访问错误！", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onSuccess(String response) {
            if (BuildConfig.DEBUG) Log.d(TAG, "onSuccess: " + response);
            try {
                Type baseType = new TypeToken<BaseEntity>() {
                }.getType();
                BaseEntity baseEntity = ToolsJson.parseObjecta(response, baseType);
                int errorCode = baseEntity.getErrorCode();
                if (errorCode > 0) {//无数据
                    if (initQiandaoInfoEntities.size() == 0) {
                        mActivityFragmentView.viewMainGone();
                    }
                    msg(baseEntity.getMsg());
                    listView.setPullLoadEnable(false);
                    return;
                }
                String jsonStr = ToolsJson.toJson(baseEntity.getData());
                Type infoType = new TypeToken<ArrayList<QiandaoInfoEntity>>() {
                }.getType();
                //查询结果有数据，做数据展示
                mActivityFragmentView.viewEmptyGone();
                qiandaoInfoEntities.clear();
                qiandaoInfoEntities = ToolsJson.parseObjecta(jsonStr, infoType);
                for (int i = 0; i < qiandaoInfoEntities.size(); i++) {
                    QiandaoInfoEntity entity = qiandaoInfoEntities.get(i);
                    initQiandaoInfoEntities.add(entity);
                }
                mAdapter.notifyDataSetChanged();
                listView.stopLoadMore();
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (findSignInCall != null && !findSignInCall.isCanceled()) {
            findSignInCall.cancel();
        }
    }

    @Override
    public void onRefresh() {
        page = 0;//重置页数
        switch (indexOfSelect) {
            case 0://个人信息
                initQiandaoInfoEntities.clear();//清空缓存
                findSignInCall = Requester.findSignIn(page, getLoginUid(), "", findSignInCallBaclk);
                listView.setPullLoadEnable(true);//设置可上滑加载更多
                listView.setAdapter(mAdapter);
                break;
            case 1://企业信息
                initQiandaoInfoEntities.clear();//清空缓存
                findSignInCall = Requester.findSignIn(page, "", getLoginUser().getDeptId(), findSignInCallBaclk);
                listView.setPullLoadEnable(true);//设置可上滑加载更多
                listView.setAdapter(mAdapter);
                break;
        }
    }

    @Override
    public void onLoadMore() {
        Handler handler = new Handler();
        switch (indexOfSelect) {
            case 0://个人信息
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;//页数加1
                        findSignInCall = Requester.findSignIn(page, getLoginUid(), "", findSignInCallBaclk);
                    }
                }, 1000);
                break;
            case 1://企业信息
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;//页数加1
                        findSignInCall = Requester.findSignIn(page, "", getLoginUser().getDeptId(), findSignInCallBaclk);
                    }
                }, 1000);
                break;
        }
    }
}
