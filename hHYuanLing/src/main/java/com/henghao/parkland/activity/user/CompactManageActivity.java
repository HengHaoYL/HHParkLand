package com.henghao.parkland.activity.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.benefit.buy.library.http.query.callback.AjaxStatus;
import com.benefit.buy.library.utils.tools.ToolsJson;
import com.benefit.buy.library.views.xlistview.XListView;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.ProtocolUrl;
import com.henghao.parkland.R;
import com.henghao.parkland.adapter.CompactAdapter;
import com.henghao.parkland.callback.MyCallBack;
import com.henghao.parkland.model.entity.BaseEntity;
import com.henghao.parkland.model.entity.CompactEntity;
import com.henghao.parkland.model.entity.DeleteEntity;
import com.henghao.parkland.model.protocol.ProjectSecProtocol;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by 晏琦云 on 2017/4/21.
 * 合同管理展示页面
 */
public class CompactManageActivity extends ActivityFragmentSupport implements MyCallBack {

    @InjectView(R.id.layout_bottom)
    LinearLayout layoutBottom;
    @InjectView(R.id.lv_compact_manage)
    XListView mXlistView;

    private String mType;//被选中的合同类型
    private static int indexOfSelect;//选中的板块 0园林类 1建设类 2园林工程类 3景观类

    private CheckBox checkBox;//全选/多选
    private TextView tvEdit;//编辑
    private List<Integer> itemID;//被选中的item ID集合

    private List<CompactEntity> data;
    private CompactAdapter mAdapter;

    /**
     * 设置选中的index
     *
     * @param index
     */
    public static void setIndexOfSelect(int index) {
        indexOfSelect = index;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_compact_manage);
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
        switch (indexOfSelect) {
            case 0:
                mType = "园林绿化";
                break;
            case 1:
                mType = "施工建设";
                break;
            case 2:
                mType = "园林工程";
                break;
            case 3:
                mType = "景观风景";
                break;
        }
        mActivityFragmentView.viewMainGone();
        initWithBar();
        initWithCenterBar();
        mCenterTextView.setText("合同管理");
        initWithRightBar();
        mRightTextView.setVisibility(View.VISIBLE);
        mRightTextView.setText("添加");
        mRightLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getLoginUser() == null) {
                    msg("请先登录！");
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("Type", mType);
                intent.setClass(CompactManageActivity.this, CompactManageSubmitActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        itemID = new ArrayList<>();
        View HeaderView = LayoutInflater.from(this).inflate(R.layout.include_projecttop, null);
        checkBox = (CheckBox) HeaderView.findViewById(R.id.checkBox);
        tvEdit = (TextView) HeaderView.findViewById(R.id.tv_edit);
        mXlistView.addHeaderView(HeaderView);
        tvEdit.setVisibility(View.VISIBLE);
        data = new ArrayList<>();
        mAdapter = new CompactAdapter(this, data, this);
        mXlistView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String edit = tvEdit.getText().toString().trim();
                if (edit.equals("编辑")) {
                    tvEdit.setText("完成");
                    checkBox.setVisibility(View.VISIBLE);
                    mAdapter.showCheckBox();
                    mAdapter.notifyDataSetChanged();
                    layoutBottom.setVisibility(View.VISIBLE);
                } else {
                    tvEdit.setText("编辑");
                    checkBox.setVisibility(View.GONE);
                    mAdapter.hideCheckBox();
                    mAdapter.notifyDataSetChanged();
                    layoutBottom.setVisibility(View.GONE);
                }
            }
        });
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    itemID.clear();
                    for (CompactEntity entity : data) {
                        entity.setChecked(true);
                        addId(entity.getId());
                    }
                } else {
                    itemID.clear();
                    for (CompactEntity entity : data) {
                        entity.setChecked(false);
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        itemID.clear();//清空
        checkBox.setChecked(false);//默认不选中
        tvEdit.setText("编辑");
        checkBox.setVisibility(View.GONE);
        mAdapter.hideCheckBox();
        mAdapter.notifyDataSetChanged();
        layoutBottom.setVisibility(View.GONE);
        /**
         * 访问网络
         */
        ProjectSecProtocol mProtocol = new ProjectSecProtocol(this);
        mProtocol.addResponseListener(this);
        switch (indexOfSelect) {
            case 0://园林类
                mProtocol.queryGardenCompact(getLoginUid());
                mActivityFragmentView.viewLoading(View.VISIBLE);
                break;
            case 1://建设类合同
                mProtocol.queryBuildCompact(getLoginUid());
                mActivityFragmentView.viewLoading(View.VISIBLE);
                break;
            case 2://园林工程类
                mProtocol.queryEngineeringCompact(getLoginUid());
                mActivityFragmentView.viewLoading(View.VISIBLE);
                break;
            case 3://景观类
                mProtocol.queryLandscapeCompact(getLoginUid());
                mActivityFragmentView.viewLoading(View.VISIBLE);
                break;
        }

    }

    @Override
    public void OnMessageResponse(String url, Object jo, AjaxStatus status) throws JSONException {
        super.OnMessageResponse(url, jo, status);
        //查询园林类合同、建设类合同、园林工程类合同、景观类合同
        if (url.endsWith(ProtocolUrl.COMPACT_QUERYGARDENCOMPACT) ||
                url.endsWith(ProtocolUrl.COMPACT_QUERYBUILDCOMPACT) ||
                url.endsWith(ProtocolUrl.COMPACT_QUERYENGINEERINGCOMPACT) ||
                url.endsWith(ProtocolUrl.COMPACT_QUERYLANDSCAPECOMPACT)) {
            if (jo instanceof BaseEntity) {
                BaseEntity mEntity = (BaseEntity) jo;
                if (mEntity.getStatus() > 0) {
                    mActivityFragmentView.viewMainGone();
                    return;
                } else {
                    mActivityFragmentView.viewEmptyGone();
                    String jsonStr = ToolsJson.toJson(mEntity.getData());
                    Type type = new TypeToken<List<CompactEntity>>() {
                    }.getType();
                    data.clear();
                    List<CompactEntity> homeData = ToolsJson.parseObjecta(jsonStr, type);
                    String topPath = mEntity.getPath();//图片、文件下载URL头部地址
                    for (CompactEntity entity : homeData) {
                        entity.setPictureId(topPath);
                        entity.setCompactId(topPath);
                        data.add(entity);
                    }
                    mAdapter.notifyDataSetChanged();
                    mXlistView.setAdapter(mAdapter);
                    return;
                }
            }
        }
    }

    @OnClick({R.id.tv_delete, R.id.tv_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_delete:
                if (!isItemChecked()) {
                    Toast.makeText(this, "您还没有选择信息哦！", Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setIcon(R.drawable.icon_warn);
                builder.setTitle("警告！");
                builder.setMessage("您真的确认要删除这些信息吗？");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        List<DeleteEntity> deleteArray = new ArrayList<DeleteEntity>();
                        for (Integer id : itemID) {
                            DeleteEntity entity = new DeleteEntity();
                            entity.setUid(getLoginUid());
                            entity.setId(id);
                            deleteArray.add(entity);
                        }
                        deleteInfo(deleteArray, indexOfSelect);//请求网络，删除信息
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case R.id.tv_cancel:
                tvEdit.setText("编辑");
                checkBox.setVisibility(View.GONE);
                mAdapter.hideCheckBox();
                mAdapter.notifyDataSetChanged();
                layoutBottom.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 删除信息
     *
     * @param entity
     */
    private void deleteInfo(List<DeleteEntity> entity, int index) {
        String url = "";
        switch (index) {
            case 0:
                url = ProtocolUrl.ROOT_URL + "/" + ProtocolUrl.DELETECOMPACT_GARDEN;
                break;
            case 1:
                url = ProtocolUrl.ROOT_URL + "/" + ProtocolUrl.DELETECOMPACT_BUILD;
                break;
            case 2:
                url = ProtocolUrl.ROOT_URL + "/" + ProtocolUrl.DELETECOMPACT_ENGINEERING;
                break;
            case 3:
                url = ProtocolUrl.ROOT_URL + "/" + ProtocolUrl.DELETECOMPACT_LANDSCAPE;
                break;
        }
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        Gson gson = new Gson();
        String parameter = gson.toJson(entity);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), parameter);
        Request request = builder.post(requestBody).url(url).build();
        mActivityFragmentView.viewLoading(View.VISIBLE);
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mActivityFragmentView.viewLoading(View.GONE);
                        Toast.makeText(context, "网络访问错误！", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String result_str = response.body().string();
                Type type = new TypeToken<BaseEntity>() {
                }.getType();
                try {
                    final BaseEntity baseEntity = ToolsJson.parseObjecta(result_str, type);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            msg(baseEntity.getMsg());
                            mActivityFragmentView.viewLoading(View.GONE);
                            /**
                             * 刷新界面
                             */
                            itemID.clear();
                            // 如果全选按钮被选中，将全选按钮选中状态取消
                            checkBox.setChecked(false);
                            List<Integer> idList = getIdList();
                            for (int id : idList) {
                                removeList(id);
                            }
                            mAdapter.notifyDataSetChanged();
                            /**
                             * 全部删除之后
                             */
                            if (data.size() == 0) {
                                mActivityFragmentView.viewMainGone();
                            }
                        }
                    });
                } catch (JsonSyntaxException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mActivityFragmentView.viewLoading(View.GONE);
                            Toast.makeText(context, "服务器错误，请稍后重试！", Toast.LENGTH_SHORT).show();
                        }
                    });
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void addId(int id) {
        itemID.add(new Integer(id));
    }

    @Override
    public void removeId(int id) {
        int index = 0;
        /**
         * 在List中找到与id相同的索引
         */
        for (int i = 0; i < itemID.size(); i++) {
            if (itemID.get(i) == id) {
                index = i;
                break;
            }
        }
        itemID.remove(index);
    }

    @Override
    public void setChecked() {
        int size = itemID.size();
        if (size == data.size()) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }
    }

    /**
     * 得到选中的ID
     *
     * @return
     */
    public List<Integer> getIdList() {
        List<Integer> idList = new ArrayList<Integer>();
        for (CompactEntity entity : data) {
            if (entity.isChecked()) {
                idList.add(entity.getId());
            }
        }
        return idList;
    }

    /**
     * 删除List
     */
    public void removeList(int id) {
        int index = 0;
        /**
         * 在List中找到与id相同的索引
         */
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId() == id) {
                index = i;
            }
        }
        data.remove(index);
    }

    /**
     * 判断是否有item被选中
     *
     * @return
     */
    private boolean isItemChecked() {
        for (CompactEntity entity : data) {
            if (entity.isChecked()) {
                return true;
            }
        }
        return false;
    }
}
