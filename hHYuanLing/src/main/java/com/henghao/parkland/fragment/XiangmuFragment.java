package com.henghao.parkland.fragment;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.benefit.buy.library.utils.tools.ToolsJson;
import com.benefit.buy.library.utils.tools.ToolsKit;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.henghao.parkland.Constant;
import com.henghao.parkland.ProtocolUrl;
import com.henghao.parkland.R;
import com.henghao.parkland.adapter.ProjectFirstAdapter;
import com.henghao.parkland.adapter.ProjectSecondAdapter;
import com.henghao.parkland.model.entity.AppGridEntity;
import com.henghao.parkland.model.entity.BaseEntity;
import com.henghao.parkland.model.entity.ProjectInfoEntity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目管理〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author zhangxianwen
 * @version HDMNV100R001, 2016年8月15日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class XiangmuFragment extends FragmentSupport {

    @ViewInject(R.id.gridview)
    private GridView gridview;
    private ProjectSecondAdapter mMyAdapter;
    private ProjectFirstAdapter mProAdapter;
    public static ProjectInfoEntity mInfoEntity;//通用的项目信息
    private List<ProjectInfoEntity> mList;//项目信息集合
    private String[] projectNames;//项目名称列表
    public static int index;//项目名称索引

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.fragment_xiangmuguanli);
        this.mActivityFragmentView.viewEmpty(R.layout.activity_empty);
        this.mActivityFragmentView.viewEmptyGone();
        this.mActivityFragmentView.viewLoading(View.GONE);
        ViewUtils.inject(this, this.mActivityFragmentView);
        initWidget();
        initData();
        initClick();
        return this.mActivityFragmentView;
    }

    /**
     * 标题点击事件
     */
    private void initClick() {
        mRightTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 如果还未添加项目信息，则提示用户，否则弹出选项对话框
                 */
                if (projectNames == null) {
                    Toast.makeText(getActivity(), mCenterTextView.getText() + "！", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setIcon(R.drawable.icon_select);
                    builder.setTitle("请选择项目名称");
                    builder.setItems(projectNames, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            /**
                             * 记录当前项目信息
                             */
                            index = which;
                            mInfoEntity = mList.get(index);
                            mCenterTextView.setText(mInfoEntity.getXmName());
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }

    private void initData() {
        projectTop();
        projectMy();
    }

    /**
     * 项目管理
     */
    private void projectMy() {
        List<AppGridEntity> mList2 = new ArrayList<AppGridEntity>();
        //第一个
        AppGridEntity mEntity = new AppGridEntity();
        mEntity.setImageId(R.drawable.icon_projectone);
        mEntity.setName("项目信息");
        mList2.add(mEntity);
        //第二个
        AppGridEntity mEntity2 = new AppGridEntity();
        mEntity2.setImageId(R.drawable.icon_projecttwo);
        mEntity2.setName("会审结果");
        mList2.add(mEntity2);
        //第三个
        AppGridEntity mEntity3 = new AppGridEntity();
        mEntity3.setImageId(R.drawable.icon_projectthree);
        mEntity3.setName("供货方信息");
        mList2.add(mEntity3);
        //第四个
        AppGridEntity mEntity4 = new AppGridEntity();
        mEntity4.setImageId(R.drawable.icon_projectfore);
        mEntity4.setName("施工人员");
        mList2.add(mEntity4);
        //第五个
        AppGridEntity mEntity5 = new AppGridEntity();
        mEntity5.setImageId(R.drawable.icon_projectfive);
        mEntity5.setName("开工报告");
        mList2.add(mEntity5);
        //第六个
        AppGridEntity mEntity6 = new AppGridEntity();
        mEntity6.setImageId(R.drawable.icon_projectsix);
        mEntity6.setName("设备信息");
        mList2.add(mEntity6);
        //第七个
        AppGridEntity mEntity7 = new AppGridEntity();
        mEntity7.setImageId(R.drawable.icon_projectseven);
        mEntity7.setName("工序报验");
        mList2.add(mEntity7);
        //第八个
        AppGridEntity mEntity8 = new AppGridEntity();
        mEntity8.setImageId(R.drawable.icon_eight);
        mEntity8.setName("现场勘察");
        mList2.add(mEntity8);
        //第九个
        AppGridEntity mEntity9 = new AppGridEntity();
        mEntity9.setImageId(R.drawable.icon_biangeng);
        mEntity9.setName("变更管理");
        mList2.add(mEntity9);
        //第十个
        AppGridEntity mEntity10 = new AppGridEntity();
        mEntity10.setImageId(R.drawable.icon_jungong);
        mEntity10.setName("竣工验收");
        mList2.add(mEntity10);
        //第十一个
        AppGridEntity mEntity11 = new AppGridEntity();
        mEntity11.setImageId(R.drawable.icon_schedule);
        mEntity11.setName("进度申报");
        mList2.add(mEntity11);
        //第十二个
        AppGridEntity mEntity12 = new AppGridEntity();
        mEntity12.setImageId(R.drawable.icon_technology);
        mEntity12.setName("技术交底");
        mList2.add(mEntity12);
        //第十三个
        AppGridEntity mEntity13 = new AppGridEntity();
        mEntity13.setImageId(R.drawable.icon_clearing);
        mEntity13.setName("项目结算");
        mList2.add(mEntity13);
        mProAdapter = new ProjectFirstAdapter(this.mActivity, mList2);
        this.gridview.setAdapter(mProAdapter);
        mProAdapter.notifyDataSetChanged();
    }


    /**
     * 我的
     */
    private void projectTop() {
        List<AppGridEntity> mList = new ArrayList<AppGridEntity>();
        //第一个
        AppGridEntity mEntity = new AppGridEntity();
        mEntity.setImageId(R.drawable.icon_projectsgbw);
        mEntity.setName("监理日志");
        mList.add(mEntity);
//        //第二个
//        AppGridEntity mEntity2 = new AppGridEntity();
//        mEntity2.setImageId(R.drawable.icon_projectsgjh);
//        mEntity2.setName("施工备忘");
//        mList.add(mEntity2);
        //第二个
//        AppGridEntity mEntity2 = new AppGridEntity();
//        mEntity2.setImageId(R.drawable.icon_projecsgzl);
//        mEntity2.setName("施工资料");
//        mList.add(mEntity2);
        //第二个
        AppGridEntity mEntity2 = new AppGridEntity();
        mEntity2.setImageId(R.drawable.icon_projectrzbw);
        mEntity2.setName("施工日志");
        mList.add(mEntity2);
        //第三个
        AppGridEntity mEntity3 = new AppGridEntity();
        mEntity3.setImageId(R.drawable.icon_projectsgzj);
        mEntity3.setName("施工钱包");
        mList.add(mEntity3);
        mMyAdapter = new ProjectSecondAdapter(this.mActivity, mList);
    }

    /**
     * 标题操作 〈一句话功能简述〉 〈功能详细描述〉
     *
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    private void initwithContent() {
        initWithCenterBar();
        this.mCenterTextView.setVisibility(View.VISIBLE);
        mCenterTextView.setText("项目管理");
//        requestInternet();
        initWithBar();
        this.mLeftImageView.setVisibility(View.VISIBLE);
        this.mLeftImageView.setImageResource(R.drawable.home_liebiao);
        initWithRightBar();
        mRightTextView.setVisibility(View.VISIBLE);
        mRightTextView.setText("切换");
    }

    @Override
    public void onResume() {
        super.onResume();
        mActivityFragmentView.viewLoading(View.VISIBLE);
        requestInternet();
    }

    /**
     * 访问服务器，请求项目信息
     */
    private void requestInternet() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        FormEncodingBuilder requestBodyBuilder = new FormEncodingBuilder();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constant.SHARED_SET, 0);
        String UID = sharedPreferences.getString(Constant.USERID, "");
        if (UID != null) requestBodyBuilder.add("uid", UID);
        RequestBody requestBody = requestBodyBuilder.build();
        Request request = builder.post(requestBody).url(ProtocolUrl.ROOT_URL + "/" + ProtocolUrl.PROJECT_QUERYPROJECTMSG).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("fail", "请求失败", e);
                        if (e.toString().indexOf("java.net.SocketTimeoutException") != -1) {
                            Toast.makeText(mActivity, "网络访问错误！请稍后重试！", Toast.LENGTH_SHORT).show();
                        } else if (e.toString().indexOf("") != 1) {
                            Toast.makeText(mActivity, "网络不给力，请检查网络设置。", Toast.LENGTH_SHORT).show();
                        }
                        mActivityFragmentView.viewLoading(View.GONE);
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String result_str = response.body().string();
                Type baseEntityTye = new TypeToken<BaseEntity>() {
                }.getType();
                try {
                    /**
                     * 解析Json
                     */
                    final BaseEntity baseEntity = ToolsJson.parseObjecta(result_str, baseEntityTye);
                    String data = ToolsJson.toJson(baseEntity.getData());
                    if (ToolsKit.isEmpty(data)) {//如果返回数据为空
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mCenterTextView.setText(baseEntity.getMsg());
                                mActivityFragmentView.viewLoading(View.GONE);
                                /**
                                 * 还原初始化数据
                                 */
                                if (mList != null) {
                                    mInfoEntity = null;
                                    index = 0;
                                    mList.clear();
                                    projectNames = null;
                                }
                            }
                        });
                    } else {
                        Type type = new TypeToken<List<ProjectInfoEntity>>() {
                        }.getType();
                        mList = ToolsJson.parseObjecta(data, type);
                        /**
                         * 取得返回的项目信息集合名称列表
                         */
                        if (mList != null) {
                            projectNames = new String[mList.size()];
                            for (int i = 0; i < mList.size(); i++) {
                                projectNames[i] = mList.get(i).getXmName();
                            }
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mActivityFragmentView.viewLoading(View.GONE);
                                    /**
                                     * 默认显示第一个项目信息的名称，如果已选则其他，则显示其他项目信息名称
                                     */
                                    mCenterTextView.setText(mList.get(index).getXmName());
                                    mInfoEntity = mList.get(index);
                                }
                            });
                        }
                    }
                } catch (JsonSyntaxException e) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mActivityFragmentView.viewLoading(View.GONE);
                            Toast.makeText(mActivity, "服务器错误，请稍后重试！", Toast.LENGTH_SHORT).show();
                        }
                    });
                    e.printStackTrace();
                }
            }
        });
    }

    public void initWidget() {
        initwithContent();
    }

    @OnClick({R.id.xm_my, R.id.xm_project})
    private void viewOnClick(View v) {
        switch (v.getId()) {
            case R.id.xm_project:
                //项目管理
                this.gridview.setAdapter(mProAdapter);
                mProAdapter.notifyDataSetChanged();
                break;
            case R.id.xm_my:
                //我的
                this.gridview.setAdapter(mMyAdapter);
                mMyAdapter.notifyDataSetChanged();
                break;
        }
    }
}
