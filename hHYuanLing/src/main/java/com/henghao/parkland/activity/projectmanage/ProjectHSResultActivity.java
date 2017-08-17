package com.henghao.parkland.activity.projectmanage;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.henghao.parkland.adapter.ProjectHSResultAdapter;
import com.henghao.parkland.fragment.XiangmuFragment;
import com.henghao.parkland.model.entity.BaseEntity;
import com.henghao.parkland.model.entity.ProjectHSResultEntity;
import com.henghao.parkland.utils.Requester;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;

/**
 * 项目管理 -- 会审结果
 */
public class ProjectHSResultActivity extends ActivityFragmentSupport implements XListView.IXListViewListener {

//    @InjectView(R.id.layout_bottom)
//    LinearLayout layoutBottom;
//    private CheckBox checkBox;//全选/多选
//    private TextView tvEdit;//编辑
//    private List<Integer> itemID;//被选中的item ID集合

    @InjectView(R.id.lv_projecthsresult)
    XListView listView;

    private static final String TAG = "ProjectHSResultActivity";

    private ProjectHSResultAdapter mAdapter;//会审结果适配器
    private List<ProjectHSResultEntity> projectHSResultEntities;//会审结果数据
    private List<ProjectHSResultEntity> initProjectHSResultEntities;//初始加载会审结果数据
    private int page = 0;//默认查询页数为0
    private Call findHsjgCall;//查询会审结果请求

    private int indexOfSelect = 0;//选中的板块 0个人信息 1企业信息
    private String[] names = {"个人信息", "企业信息"};//对话框选项

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_project_hsresult);
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
        listView.setXListViewListener(this);
        mActivityFragmentView.viewMainGone();
        initWithBar();
        initWithCenterBar();
        mCenterTextView.setText("会审结果");
        this.mCenterLinearLayout.setOnClickListener(new View.OnClickListener() {
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
                                initProjectHSResultEntities.clear();//清空缓存
                                findHsjgCall = Requester.findHsjg(page, getLoginUid(), "", findHsjgCallBack);
                                listView.setAdapter(mAdapter);
                                listView.setPullLoadEnable(true);//设置可上滑加载更多
                                indexOfSelect = 0;
                                break;
                            case 1://企业信息
                                initProjectHSResultEntities.clear();//清空缓存
                                findHsjgCall = Requester.findHsjg(page, "", getLoginUser().getDeptId(), findHsjgCallBack);
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
        initWithRightBar();
        mRightTextView.setVisibility(View.VISIBLE);
        mRightTextView.setText("添加");
        mRightLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getLoginUserName() == null) {
                    msg("请先登录！");
                    return;
                }
                if (XiangmuFragment.mInfoEntity == null) {
                    msg("请先添加项目信息！");
                    return;
                }
                Intent intent = new Intent();
                intent.setClass(ProjectHSResultActivity.this, ProjectHSResultSubmitActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initData() {
//        itemID = new ArrayList<>();
//        View HeaderView = LayoutInflater.from(this).inflate(R.layout.include_projecttop, null);
//        checkBox = (CheckBox) HeaderView.findViewById(checkBox);
//        tvEdit = (TextView) HeaderView.findViewById(R.id.tv_edit);
//        mXlistView.addHeaderView(HeaderView);
//        tvEdit.setVisibility(View.VISIBLE);
//        tvEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String edit = tvEdit.getText().toString().trim();
//                if (edit.equals("编辑")) {
//                    tvEdit.setText("完成");
//                    checkBox.setVisibility(View.VISIBLE);
//                    mAdapter.showCheckBox();
//                    mAdapter.notifyDataSetChanged();
//                    layoutBottom.setVisibility(View.VISIBLE);
//                } else {
//                    tvEdit.setText("编辑");
//                    checkBox.setVisibility(View.GONE);
//                    mAdapter.hideCheckBox();
//                    mAdapter.notifyDataSetChanged();
//                    layoutBottom.setVisibility(View.GONE);
//                }
//            }
//        });
//        checkBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (checkBox.isChecked()) {
//                    itemID.clear();
//                    for (ProjectHSResultEntity entity : data) {
//                        entity.setChecked(true);
//                        addId(entity.getHid());
//                    }
//                } else {
//                    itemID.clear();
//                    for (ProjectHSResultEntity entity : data) {
//                        entity.setChecked(false);
//                    }
//                }
//                mAdapter.notifyDataSetChanged();
//            }
//        });

        listView.setPullLoadEnable(true);//设置可上滑加载更多
        projectHSResultEntities = new ArrayList<>();
        initProjectHSResultEntities = new ArrayList<>();
        mAdapter = new ProjectHSResultAdapter(this, initProjectHSResultEntities);
        listView.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
//        itemID.clear();//清空
//        checkBox.setChecked(false);//默认不选中
//        tvEdit.setText("编辑");
//        checkBox.setVisibility(View.GONE);
//        mAdapter.hideCheckBox();
//        mAdapter.notifyDataSetChanged();
//        layoutBottom.setVisibility(View.GONE);

        page = 0;//重置页数
        initProjectHSResultEntities.clear();//清空缓存
        //请求网络，查询会审结果，默认查询个人信息
        findHsjgCall = Requester.findHsjg(page, getLoginUid(), "", findHsjgCallBack);
        listView.setAdapter(mAdapter);
        listView.setPullLoadEnable(true);//设置可上滑加载更多
    }

    private DefaultCallback findHsjgCallBack = new DefaultCallback() {
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
                    if (initProjectHSResultEntities.size() == 0) {
                        mActivityFragmentView.viewMainGone();
                    }
                    msg(baseEntity.getMsg());
                    listView.setPullLoadEnable(false);
                    return;
                }
                String jsonStr = ToolsJson.toJson(baseEntity.getData());
                Type hsResultType = new TypeToken<ArrayList<ProjectHSResultEntity>>() {
                }.getType();
                //查询结果有数据，做数据展示
                mActivityFragmentView.viewEmptyGone();
                projectHSResultEntities.clear();
                projectHSResultEntities = ToolsJson.parseObjecta(jsonStr, hsResultType);
                for (int i = 0; i < projectHSResultEntities.size(); i++) {
                    ProjectHSResultEntity entity = projectHSResultEntities.get(i);
                    initProjectHSResultEntities.add(entity);
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
        if (findHsjgCall != null && !findHsjgCall.isCanceled()) {
            findHsjgCall.cancel();
        }
    }

    @Override
    public void onRefresh() {
        page = 0;//重置页数
        initProjectHSResultEntities.clear();//清空缓存
        switch (indexOfSelect) {
            case 0://个人信息
                findHsjgCall = Requester.findHsjg(page, getLoginUid(), "", findHsjgCallBack);
                listView.setPullLoadEnable(true);//设置可上滑加载更多
                listView.setAdapter(mAdapter);
                break;
            case 1://企业信息
                findHsjgCall = Requester.findHsjg(page, "", getLoginUser().getDeptId(), findHsjgCallBack);
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
                        findHsjgCall = Requester.findHsjg(page, getLoginUid(), "", findHsjgCallBack);
                    }
                }, 1000);
                break;
            case 1://企业信息
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;//页数加1
                        findHsjgCall = Requester.findHsjg(page, "", getLoginUser().getDeptId(), findHsjgCallBack);
                    }
                }, 1000);
                break;
        }
    }


//    @OnClick({R.id.tv_delete, R.id.tv_cancel})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.tv_delete:
//                if (!isItemChecked()) {
//                    Toast.makeText(this, "您还没有选择信息哦！", Toast.LENGTH_SHORT)
//                            .show();
//                    return;
//                }
//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setIcon(R.drawable.icon_warn);
//                builder.setTitle("警告！");
//                builder.setMessage("您真的确认要删除这些信息吗？");
//                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        List<DeleteEntity> deleteArray = new ArrayList<DeleteEntity>();
//                        for (Integer id : itemID) {
//                            DeleteEntity entity = new DeleteEntity();
//                            entity.setUid(getLoginUid());
//                            entity.setId(id);
//                            deleteArray.add(entity);
//                        }
//                        deleteInfo(deleteArray);//请求网络，删除信息
//                    }
//                });
//                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//                AlertDialog dialog = builder.create();
//                dialog.show();
//                break;
//            case R.id.tv_cancel:
//                tvEdit.setText("编辑");
//                checkBox.setVisibility(View.GONE);
//                mAdapter.hideCheckBox();
//                mAdapter.notifyDataSetChanged();
//                layoutBottom.setVisibility(View.GONE);
//                break;
//        }
//    }
//
//    /**
//     * 删除信息
//     *
//     * @param entity
//     */
//    private void deleteInfo(List<DeleteEntity> entity) {
//        OkHttpClient okHttpClient = new OkHttpClient();
//        Request.Builder builder = new Request.Builder();
//        Gson gson = new Gson();
//        String parameter = gson.toJson(entity);
//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), parameter);
//        Request request = builder.post(requestBody).url(ProtocolUrl.ROOT_URL + ProtocolUrl.DELETE_HSRESULT).build();
//        mActivityFragmentView.viewLoading(View.VISIBLE);
//        Call call = okHttpClient.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//                e.printStackTrace();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        mActivityFragmentView.viewLoading(View.GONE);
//                        Toast.makeText(ProjectHSResultActivity.this, "网络访问错误！", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//
//            @Override
//            public void onResponse(Response response) throws IOException {
//                String result_str = response.body().string();
//                Type type = new TypeToken<BaseEntity>() {
//                }.getType();
//                try {
//                    final BaseEntity baseEntity = ToolsJson.parseObjecta(result_str, type);
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(context, baseEntity.getMsg(), Toast.LENGTH_SHORT).show();
//                            mActivityFragmentView.viewLoading(View.GONE);
//                            /**
//                             * 刷新界面
//                             */
//                            itemID.clear();
//                            // 如果全选按钮被选中，将全选按钮选中状态取消
//                            checkBox.setChecked(false);
//                            List<Integer> idList = getIdList();
//                            for (int id : idList) {
//                                removeList(id);
//                            }
//                            mAdapter.notifyDataSetChanged();
//                            /**
//                             * 全部删除之后
//                             */
//                            if (data.size() == 0) {
//                                mActivityFragmentView.viewMainGone();
//                            }
//                        }
//                    });
//                } catch (JsonSyntaxException e) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            mActivityFragmentView.viewLoading(View.GONE);
//                            Toast.makeText(context, "服务器错误，请稍后重试！", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//
//    @Override
//    public void addId(int id) {
//        itemID.add(new Integer(id));
//    }
//
//    @Override
//    public void removeId(int id) {
//        int index = 0;
//        /**
//         * 在List中找到与id相同的索引
//         */
//        for (int i = 0; i < itemID.size(); i++) {
//            if (itemID.get(i) == id) {
//                index = i;
//                break;
//            }
//        }
//        itemID.remove(index);
//    }
//
//    @Override
//    public void setChecked() {
//        int size = itemID.size();
//        if (size == data.size()) {
//            checkBox.setChecked(true);
//        } else {
//            checkBox.setChecked(false);
//        }
//    }
//
//    /**
//     * 得到选中的ID
//     *
//     * @return
//     */
//    public List<Integer> getIdList() {
//        List<Integer> idList = new ArrayList<Integer>();
//        for (ProjectHSResultEntity entity : data) {
//            if (entity.isChecked()) {
//                idList.add(entity.getHid());
//            }
//        }
//        return idList;
//    }
//
//    /**
//     * 删除List
//     */
//    public void removeList(int id) {
//        int index = 0;
//        /**
//         * 在List中找到与id相同的索引
//         */
//        for (int i = 0; i < data.size(); i++) {
//            if (data.get(i).getHid() == id) {
//                index = i;
//            }
//        }
//        data.remove(index);
//    }
//
//    /**
//     * 判断是否有item被选中
//     *
//     * @return
//     */
//    private boolean isItemChecked() {
//        for (ProjectHSResultEntity entity : data) {
//            if (entity.isChecked()) {
//                return true;
//            }
//        }
//        return false;
//    }
}
