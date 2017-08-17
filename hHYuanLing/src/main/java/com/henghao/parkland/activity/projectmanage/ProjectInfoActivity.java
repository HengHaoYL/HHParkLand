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
import com.henghao.parkland.adapter.ProjectInfoAdapter;
import com.henghao.parkland.model.entity.BaseEntity;
import com.henghao.parkland.model.entity.ProjectInfoEntity;
import com.henghao.parkland.utils.Requester;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;


/**
 * 项目管理 -- 项目信息
 */
public class ProjectInfoActivity extends ActivityFragmentSupport implements XListView.IXListViewListener {

//    @InjectView(R.id.layout_bottom)
//    LinearLayout layoutBottom;
//    private CheckBox checkBox;//全选/多选
//    private TextView tvEdit;//编辑
//    private List<Integer> itemID;//被选中的item ID集合

    @InjectView(R.id.lv_projectinfo)
    XListView listView;

    private static final String TAG = "ProjectInfoActivity";

    private ProjectInfoAdapter mAdapter;//项目信息适配器
    private List<ProjectInfoEntity> projectInfoEntities;//项目信息数据
    private List<ProjectInfoEntity> initProjectInfoEntities;//初始加载项目信息数据
    private int page = 0;//默认查询页数为0
    private Call findXmxxCall;//查询项目信息请求

    private int indexOfSelect = 0;//选中的板块 0个人信息 1企业信息
    private String[] names = {"个人信息", "企业信息"};//对话框选项

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_project_info);
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
        mCenterTextView.setText("项目信息");
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
                                initProjectInfoEntities.clear();//清空缓存
                                findXmxxCall = Requester.findXmxx(page, getLoginUid(), "", findXmxxCallBack);
                                listView.setAdapter(mAdapter);
                                listView.setPullLoadEnable(true);//设置可上滑加载更多
                                indexOfSelect = 0;
                                break;
                            case 1://企业信息
                                initProjectInfoEntities.clear();//清空缓存
                                findXmxxCall = Requester.findXmxx(page, "", getLoginUser().getDeptId(), findXmxxCallBack);
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
        mRightTextView.setText("添加");
        mRightTextView.setVisibility(View.VISIBLE);
        mRightLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果未登录，请先登录
                if (getLoginUserName() == null) {
                    msg("请先登录！");
                    return;
                }
                Intent intent = new Intent(ProjectInfoActivity.this, ProjectInfoSubmitActivity.class);
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
//        listView.addHeaderView(HeaderView);
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
//                    for (ProjectInfoEntity entity : data) {
//                        entity.setChecked(true);
//                        addId(entity.getPid());
//                    }
//                } else {
//                    itemID.clear();
//                    for (ProjectInfoEntity entity : data) {
//                        entity.setChecked(false);
//                    }
//                }
//                mAdapter.notifyDataSetChanged();
//            }
//        });

        listView.setPullLoadEnable(true);//设置可上滑加载更多
        projectInfoEntities = new ArrayList<>();
        initProjectInfoEntities = new ArrayList<>();
        mAdapter = new ProjectInfoAdapter(this, initProjectInfoEntities);
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
        initProjectInfoEntities.clear();//清空缓存
        //请求网络，查询项目信息，默认查询个人信息
        findXmxxCall = Requester.findXmxx(page, getLoginUid(), "", findXmxxCallBack);
        listView.setAdapter(mAdapter);
        listView.setPullLoadEnable(true);//设置可上滑加载更多
    }

    private DefaultCallback findXmxxCallBack = new DefaultCallback() {
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
                    if (initProjectInfoEntities.size() == 0) {
                        mActivityFragmentView.viewMainGone();
                    }
                    msg(baseEntity.getMsg());
                    listView.setPullLoadEnable(false);
                    return;
                }
                String jsonStr = ToolsJson.toJson(baseEntity.getData());
                Type infoType = new TypeToken<ArrayList<ProjectInfoEntity>>() {
                }.getType();
                //查询结果有数据，做数据展示
                mActivityFragmentView.viewEmptyGone();
                projectInfoEntities.clear();
                projectInfoEntities = ToolsJson.parseObjecta(jsonStr, infoType);
                for (int i = 0; i < projectInfoEntities.size(); i++) {
                    ProjectInfoEntity entity = projectInfoEntities.get(i);
                    initProjectInfoEntities.add(entity);
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
        if (findXmxxCall != null && !findXmxxCall.isCanceled()) {
            findXmxxCall.cancel();
        }
    }

    @Override
    public void onRefresh() {
        page = 0;//重置页数
        initProjectInfoEntities.clear();//清空缓存
        switch (indexOfSelect) {
            case 0://个人信息
                findXmxxCall = Requester.findXmxx(page, getLoginUid(), "", findXmxxCallBack);
                listView.setPullLoadEnable(true);//设置可上滑加载更多
                listView.setAdapter(mAdapter);
                break;
            case 1://企业信息
                findXmxxCall = Requester.findXmxx(page, "", getLoginUser().getDeptId(), findXmxxCallBack);
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
                        findXmxxCall = Requester.findXmxx(page, getLoginUid(), "", findXmxxCallBack);
                    }
                }, 1000);
                break;
            case 1://企业信息
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;//页数加1
                        findXmxxCall = Requester.findXmxx(page, "", getLoginUser().getDeptId(), findXmxxCallBack);
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

//    /**
//     * 删除信息
//     *
//     * @param entity
//     */
//    private void deleteInfo(final List<DeleteEntity> entity) {
//        OkHttpClient okHttpClient = new OkHttpClient();
//        Request.Builder builder = new Request.Builder();
//        Gson gson = new Gson();
//        String parameter = gson.toJson(entity);
//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), parameter);
//        Request request = builder.post(requestBody).url(ProtocolUrl.ROOT_URL + ProtocolUrl.DELETE_PROJECT).build();
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
//                        Toast.makeText(context, "网络访问错误！", Toast.LENGTH_SHORT).show();
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
//                             * 删除成功之后，则项目信息列表减少了一个，所以索引要随之-1
//                             */
//                            if (XiangmuFragment.mInfoEntity != null) {
//                                XiangmuFragment.index -= 1;
//                            }
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

//    @Override
//    public void addId(int id) {
//        itemID.add(new Integer(id));
//    }

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

//    @Override
//    public void setChecked() {
//        int size = itemID.size();
//        if (size == data.size()) {
//            checkBox.setChecked(true);
//        } else {
//            checkBox.setChecked(false);
//        }
//    }

//    /**
//     * 得到选中的ID
//     *
//     * @return
//     */
//    public List<Integer> getIdList() {
//        List<Integer> idList = new ArrayList<Integer>();
//        for (ProjectInfoEntity entity : data) {
//            if (entity.isChecked()) {
//                idList.add(entity.getPid());
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
//            if (data.get(i).getPid() == id) {
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
//        for (ProjectInfoEntity entity : data) {
//            if (entity.isChecked()) {
//                return true;
//            }
//        }
//        return false;
//    }
}