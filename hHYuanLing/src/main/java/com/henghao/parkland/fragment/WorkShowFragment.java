package com.henghao.parkland.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.benefit.buy.library.utils.tools.ToolsJson;
import com.benefit.buy.library.utils.tools.ToolsKit;
import com.benefit.buy.library.views.xlistview.XListView;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.henghao.parkland.BuildConfig;
import com.henghao.parkland.R;
import com.henghao.parkland.adapter.BidAdapter;
import com.henghao.parkland.adapter.EquipmentAdapter;
import com.henghao.parkland.adapter.RecruitAdapter;
import com.henghao.parkland.adapter.SeedlingAdapter;
import com.henghao.parkland.model.entity.BaseEntity;
import com.henghao.parkland.model.entity.BidEntity;
import com.henghao.parkland.model.entity.EquipmentEntity;
import com.henghao.parkland.model.entity.RecruitEntity;
import com.henghao.parkland.model.entity.SeedlingEntity;
import com.henghao.parkland.model.protocol.WorkShowProtocol;
import com.henghao.parkland.utils.Requester;
import com.henghao.parkland.views.dialog.DialogWorkShow;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 工作台 〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author zhangxianwen
 * @version HDMNV100R001, 2016年8月15日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class WorkShowFragment extends FragmentSupport implements XListView.IXListViewListener {

    @ViewInject(R.id.listview_xuqiu)
    private XListView listview_xuqiu;
    private static final String TAG = "WorkShowFragment";

    // 设置一个最大的数据条数，超过即不再加载
    private int MaxDateNum;
    private int indexOfSelect = 2;//选中的板块 1设备租赁 2苗木信息 3招标信息 4人员招聘

    private TextView tv_title;//标题
    private int page = 0;//默认查询页数为0
    private Call seedlingCall;//苗木信息查询请求
    private Call equipmentCall;//设备租赁查询请求
    private Call recruitCall;//人员招聘查询请求
    private Call bidCall;//招标信息查询请求

    private EquipmentAdapter equipmentAdapter;//设备租赁适配器
    private List<EquipmentEntity> equipmentEntities;//设备租赁数据
    private List<EquipmentEntity> initEquipmentEntities;//初始加载设备租赁数据

    private SeedlingAdapter seedlingAdapter;//苗木信息适配器
    private List<SeedlingEntity> seedlingEntities;//苗木信息数据
    private List<SeedlingEntity> initSeedlingEntities;//初始加载苗木信息数据

    private BidAdapter bidAdapter;//招标信息适配器
    private List<BidEntity> bidEntities;//招标信息数据
    private List<BidEntity> initBidEntities;//初始加载招标信息数据

    private RecruitAdapter recruitAdapter;//人员招聘适配器
    private List<RecruitEntity> recruitEntities;//人员招聘数据
    private List<RecruitEntity> initRecruitEntities;//初始加载人员招聘数据
    private DialogWorkShow dialog;//选择对话框

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.fragment_work);
        this.mActivityFragmentView.viewEmpty(R.layout.activity_empty);
        this.mActivityFragmentView.viewEmptyGone();
        this.mActivityFragmentView.viewLoading(View.GONE);
        ViewUtils.inject(this, this.mActivityFragmentView);
        initWidget();
        initData();
        return this.mActivityFragmentView;
    }

    /**
     * 初始化数据
     */
    private void initData() {
        initView();
        //设备租赁
        equipmentEntities = new ArrayList<>();
        initEquipmentEntities = new ArrayList<>();
        equipmentAdapter = new EquipmentAdapter(this.mActivity, initEquipmentEntities);
        //苗木信息
        seedlingEntities = new ArrayList<>();
        initSeedlingEntities = new ArrayList<>();
        seedlingAdapter = new SeedlingAdapter(this.mActivity, initSeedlingEntities);
        //招标信息
        bidEntities = new ArrayList<>();
        initBidEntities = new ArrayList<>();
        bidAdapter = new BidAdapter(this.mActivity, initBidEntities);
        //人员招聘
        recruitEntities = new ArrayList<>();
        initRecruitEntities = new ArrayList<>();
        recruitAdapter = new RecruitAdapter(this.mActivity, initRecruitEntities);
        //第一次进入时默认显示苗木信息的数据
        tv_title.setText("苗木信息");
        listview_xuqiu.setPullLoadEnable(true);
        seedlingCall = Requester.findSeedling(page, seedlingCallBack);
        listview_xuqiu.setAdapter(seedlingAdapter);
    }

    private void initView() {
        mActivityFragmentView.viewMainGone();
        LayoutInflater mInflater = LayoutInflater.from(this.mActivity);
        //添加布局
        View headerView = mInflater.inflate(R.layout.include_homework, this.listview_xuqiu, false);
        tv_title = (TextView) headerView.findViewById(R.id.tv_title);
        this.listview_xuqiu.addHeaderView(headerView);
    }

    /**
     * 标题操作 〈一句话功能简述〉 〈功能详细描述〉
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    /**
     * 标题操作 〈一句话功能简述〉 〈功能详细描述〉
     *
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    private void initwithContent() {
        initWithCenterBar();
        this.mCenterTextView.setVisibility(View.VISIBLE);
        this.mCenterTextView.setText(R.string.workshow);
        initWithBar();
        this.mLeftImageView.setVisibility(View.VISIBLE);
        this.mLeftImageView.setImageResource(R.drawable.home_liebiao);
        dialog = new DialogWorkShow(mActivity, new DialogWorkShow.DialogWorkShowListener() {
            @Override
            public void onClick(View view) {
                page = 0;//重置页数
                WorkShowProtocol protocol = new WorkShowProtocol(mActivity);
                protocol.addResponseListener(WorkShowFragment.this);
                switch (view.getId()) {
                    case R.id.tv_dialog1://设备租赁
                        tv_title.setText("设备租赁");
                        initEquipmentEntities.clear();//清空缓存
                        equipmentCall = Requester.findEquipment(page, equipmentCallBack);
                        listview_xuqiu.setAdapter(equipmentAdapter);
                        listview_xuqiu.setPullLoadEnable(true);//设置可上滑加载更多
                        indexOfSelect = 1;
                        break;
                    case R.id.tv_dialog2://苗木信息
                        tv_title.setText("苗木信息");
                        initSeedlingEntities.clear();//清空缓存
                        seedlingCall = Requester.findSeedling(page, seedlingCallBack);
                        listview_xuqiu.setAdapter(seedlingAdapter);
                        listview_xuqiu.setPullLoadEnable(true);//设置可上滑加载更多
                        indexOfSelect = 2;
                        break;
                    case R.id.tv_dialog3://招标信息
                        tv_title.setText("招标信息");
                        initBidEntities.clear();//清空缓存
                        bidCall = Requester.findBid(page, bidCallBack);
                        listview_xuqiu.setAdapter(bidAdapter);
                        listview_xuqiu.setPullLoadEnable(true);//设置可上滑加载更多
                        indexOfSelect = 3;
                        break;
                    case R.id.tv_dialog4://人员招聘
                        tv_title.setText("人员招聘");
                        initRecruitEntities.clear();//清空缓存
                        recruitCall = Requester.findRecruit(page, recruitCallBack);
                        listview_xuqiu.setAdapter(recruitAdapter);
                        listview_xuqiu.setPullLoadEnable(true);//设置可上滑加载更多
                        indexOfSelect = 4;
                        break;
                }
                dialog.dismiss();
            }
        });
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);
        lp.x = ToolsKit.dip2px(mActivity, 7); // 新位置X坐标
        lp.y = ToolsKit.dip2px(mActivity, 57); // 新位置Y坐标
        dialogWindow.setAttributes(lp);
        mLeftImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
    }

    public void initWidget() {
        listview_xuqiu.setXListViewListener(this);
        initwithContent();
    }

    private void loadMoreData() {
        Handler handler = new Handler();
        switch (indexOfSelect) {
            case 1://设备租赁
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        equipmentCall = Requester.findEquipment(page, equipmentCallBack);
                    }
                }, 1000);
                break;
            case 2://苗木信息
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        seedlingCall = Requester.findSeedling(page, seedlingCallBack);
                    }
                }, 1000);
                break;
            case 3://招标信息
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        bidCall = Requester.findBid(page, bidCallBack);
                    }
                }, 1000);
                break;
            case 4://人员招聘
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        recruitCall = Requester.findRecruit(page, recruitCallBack);
                    }
                }, 1000);
                break;
        }
    }

    @Override
    public void onRefresh() {
        page = 0;//重置页数
        switch (indexOfSelect) {
            case 1://设备租赁
                initEquipmentEntities.clear();//清空缓存
                equipmentCall = Requester.findEquipment(page, equipmentCallBack);
                listview_xuqiu.setPullLoadEnable(true);//设置可上滑加载更多
                listview_xuqiu.setAdapter(equipmentAdapter);
                break;
            case 2://苗木信息
                initSeedlingEntities.clear();//清空缓存
                seedlingCall = Requester.findSeedling(page, seedlingCallBack);
                listview_xuqiu.setPullLoadEnable(true);//设置可上滑加载更多
                listview_xuqiu.setAdapter(seedlingAdapter);
                break;
            case 3://招标信息
                initBidEntities.clear();//清空缓存
                bidCall = Requester.findBid(page, bidCallBack);
                listview_xuqiu.setPullLoadEnable(true);//设置可上滑加载更多
                listview_xuqiu.setAdapter(bidAdapter);
                break;
            case 4://人员招聘
                initRecruitEntities.clear();//清空缓存
                recruitCall = Requester.findRecruit(page, recruitCallBack);
                listview_xuqiu.setPullLoadEnable(true);//设置可上滑加载更多
                listview_xuqiu.setAdapter(recruitAdapter);
                break;
        }
    }

    @Override
    public void onLoadMore() {
        loadMoreData();
    }

    /**
     * 苗木信息查询回调
     */
    private DefaultCallback seedlingCallBack = new DefaultCallback() {
        @Override
        public void onFailure(Exception e, int code) {
            if (BuildConfig.DEBUG) Log.e(TAG, "onFailure: code = " + code, e);
            e.printStackTrace();
            mActivityFragmentView.viewMainGone();
            Toast.makeText(mActivity, "网络访问错误！", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onSuccess(String response) {
            if (BuildConfig.DEBUG) Log.d(TAG, "onSuccess: ");
            try {
                Type baseType = new TypeToken<BaseEntity>() {
                }.getType();
                BaseEntity baseEntity = ToolsJson.parseObjecta(response, baseType);
                int errorCode = baseEntity.getErrorCode();
                if (errorCode > 0) {//无数据
                    if (initSeedlingEntities.size() == 0) {
                        mActivityFragmentView.viewMainGone();
                    }
                    mActivity.msg(baseEntity.getMsg());
                    listview_xuqiu.setPullLoadEnable(false);
                    return;
                }
                String jsonStr = ToolsJson.toJson(baseEntity.getData());
                Type seedlingType = new TypeToken<ArrayList<SeedlingEntity>>() {
                }.getType();
                //查询结果有数据，做数据展示
                mActivityFragmentView.viewEmptyGone();
                seedlingEntities.clear();
                seedlingEntities = ToolsJson.parseObjecta(jsonStr, seedlingType);
                for (int i = 0; i < seedlingEntities.size(); i++) {
                    SeedlingEntity entity = seedlingEntities.get(i);
                    initSeedlingEntities.add(entity);
                }
                seedlingAdapter.notifyDataSetChanged();
                listview_xuqiu.stopLoadMore();
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
        }
    };
    /**
     * 设备租赁查询回调
     */
    private DefaultCallback equipmentCallBack = new DefaultCallback() {
        @Override
        public void onFailure(Exception e, int code) {
            if (BuildConfig.DEBUG) Log.e(TAG, "onFailure: code = " + code, e);
            e.printStackTrace();
            mActivityFragmentView.viewMainGone();
            Toast.makeText(mActivity, "网络访问错误！", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onSuccess(String response) {
            if (BuildConfig.DEBUG) Log.d(TAG, "onSuccess: ");
            try {
                Type baseType = new TypeToken<BaseEntity>() {
                }.getType();
                BaseEntity baseEntity = ToolsJson.parseObjecta(response, baseType);
                int errorCode = baseEntity.getErrorCode();
                if (errorCode > 0) {//无数据
                    if (initEquipmentEntities.size() == 0) {
                        mActivityFragmentView.viewMainGone();
                    }
                    mActivity.msg(baseEntity.getMsg());
                    listview_xuqiu.setPullLoadEnable(false);
                    return;
                }
                String jsonStr = ToolsJson.toJson(baseEntity.getData());
                Type equipmentType = new TypeToken<ArrayList<EquipmentEntity>>() {
                }.getType();
                //查询结果有数据，做数据展示
                mActivityFragmentView.viewEmptyGone();
                equipmentEntities.clear();
                equipmentEntities = ToolsJson.parseObjecta(jsonStr, equipmentType);
                for (int i = 0; i < equipmentEntities.size(); i++) {
                    EquipmentEntity entity = equipmentEntities.get(i);
                    initEquipmentEntities.add(entity);
                }
                equipmentAdapter.notifyDataSetChanged();
                listview_xuqiu.stopLoadMore();
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
        }
    };
    /**
     * 人员招聘查询回调
     */
    private DefaultCallback recruitCallBack = new DefaultCallback() {
        @Override
        public void onFailure(Exception e, int code) {
            if (BuildConfig.DEBUG) Log.e(TAG, "onFailure: code = " + code, e);
            e.printStackTrace();
            mActivityFragmentView.viewMainGone();
            Toast.makeText(mActivity, "网络访问错误！", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onSuccess(String response) {
            if (BuildConfig.DEBUG) Log.d(TAG, "onSuccess: ");
            try {
                Type baseType = new TypeToken<BaseEntity>() {
                }.getType();
                BaseEntity baseEntity = ToolsJson.parseObjecta(response, baseType);
                int errorCode = baseEntity.getErrorCode();
                if (errorCode > 0) {//无数据
                    if (initRecruitEntities.size() == 0) {
                        mActivityFragmentView.viewMainGone();
                    }
                    mActivity.msg(baseEntity.getMsg());
                    listview_xuqiu.setPullLoadEnable(false);
                    return;
                }
                String jsonStr = ToolsJson.toJson(baseEntity.getData());
                Type recruitType = new TypeToken<ArrayList<RecruitEntity>>() {
                }.getType();
                //查询结果有数据，做数据展示
                mActivityFragmentView.viewEmptyGone();
                recruitEntities.clear();
                recruitEntities = ToolsJson.parseObjecta(jsonStr, recruitType);
                for (int i = 0; i < recruitEntities.size(); i++) {
                    RecruitEntity entity = recruitEntities.get(i);
                    initRecruitEntities.add(entity);
                }
                recruitAdapter.notifyDataSetChanged();
                listview_xuqiu.stopLoadMore();
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
        }
    };
    /**
     * 招标信息查询回调
     */
    private DefaultCallback bidCallBack = new DefaultCallback() {
        @Override
        public void onFailure(Exception e, int code) {
            if (BuildConfig.DEBUG) Log.e(TAG, "onFailure: code = " + code, e);
            e.printStackTrace();
            mActivityFragmentView.viewMainGone();
            Toast.makeText(mActivity, "网络访问错误！", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onSuccess(String response) {
            if (BuildConfig.DEBUG) Log.d(TAG, "onSuccess: ");
            try {
                Type baseType = new TypeToken<BaseEntity>() {
                }.getType();
                BaseEntity baseEntity = ToolsJson.parseObjecta(response, baseType);
                int errorCode = baseEntity.getErrorCode();
                if (errorCode > 0) {//无数据
                    if (initBidEntities.size() == 0) {
                        mActivityFragmentView.viewMainGone();
                    }
                    mActivity.msg(baseEntity.getMsg());
                    listview_xuqiu.setPullLoadEnable(false);
                    return;
                }
                String jsonStr = ToolsJson.toJson(baseEntity.getData());
                Type bidType = new TypeToken<ArrayList<BidEntity>>() {
                }.getType();
                //查询结果有数据，做数据展示
                mActivityFragmentView.viewEmptyGone();
                bidEntities.clear();
                bidEntities = ToolsJson.parseObjecta(jsonStr, bidType);
                for (int i = 0; i < bidEntities.size(); i++) {
                    BidEntity entity = bidEntities.get(i);
                    initBidEntities.add(entity);
                }
                bidAdapter.notifyDataSetChanged();
                listview_xuqiu.stopLoadMore();
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (seedlingCall != null && !seedlingCall.isCanceled()) {
            seedlingCall.cancel();
        }
        if (equipmentCall != null && !equipmentCall.isCanceled()) {
            equipmentCall.cancel();
        }
        if (recruitCall != null && !recruitCall.isCanceled()) {
            recruitCall.cancel();
        }
        if (bidCall != null && !bidCall.isCanceled()) {
            bidCall.cancel();
        }
    }
}
