package com.henghao.parkland.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.benefit.buy.library.http.query.callback.AjaxStatus;
import com.benefit.buy.library.utils.tools.ToolsJson;
import com.benefit.buy.library.utils.tools.ToolsKit;
import com.benefit.buy.library.views.xlistview.XListView;
import com.google.gson.reflect.TypeToken;
import com.henghao.parkland.ProtocolUrl;
import com.henghao.parkland.R;
import com.henghao.parkland.adapter.BidAdapter;
import com.henghao.parkland.adapter.EquipmentLeasingAdapter;
import com.henghao.parkland.adapter.RecruitAdapter;
import com.henghao.parkland.adapter.SeedlingAdapter;
import com.henghao.parkland.model.entity.BaseEntity;
import com.henghao.parkland.model.entity.BidEntity;
import com.henghao.parkland.model.entity.EquipmentLeasingEntity;
import com.henghao.parkland.model.entity.RecruitEntity;
import com.henghao.parkland.model.entity.SeedlingEntity;
import com.henghao.parkland.model.protocol.WorkShowProtocol;
import com.henghao.parkland.views.dialog.DialogWorkShow;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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

    // 设置一个最大的数据条数，超过即不再加载
    private int MaxDateNum;
    private int indexOfSelect = 2;//选中的板块 1设备租赁 2苗木信息 3招标信息 4人员招聘

    private TextView tv_title;//标题

//    private View dialogView;

    private EquipmentLeasingAdapter equipmentLeasingAdapter;//设备租赁适配器
    private List<EquipmentLeasingEntity> equipmentLeasingEntities;//设备租赁数据
    private List<EquipmentLeasingEntity> initEquipmentLeasingEntities;//初始加载设备租赁数据

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
        equipmentLeasingEntities = new ArrayList<>();
        initEquipmentLeasingEntities = new ArrayList<>();
        equipmentLeasingAdapter = new EquipmentLeasingAdapter(this.mActivity, initEquipmentLeasingEntities);
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
        /**
         * 第一次进入时默认显示苗木信息的数据
         */
        tv_title.setText("苗木信息");
        WorkShowProtocol protocol = new WorkShowProtocol(mActivity);
        protocol.addResponseListener(WorkShowFragment.this);
        protocol.querySeedlingmessage();
        mActivityFragmentView.viewLoading(View.VISIBLE);
    }

    private void initView() {
        mActivityFragmentView.viewMainGone();
        LayoutInflater mInflater = LayoutInflater.from(this.mActivity);
        //添加布局
        View headerView = mInflater.inflate(R.layout.include_homework, this.listview_xuqiu, false);
        //滚动图
//        AutoScrollViewPager viewPager = (AutoScrollViewPager) headerView.findViewById(R.id.view_pager);
//        CirclePageIndicator indicator = (CirclePageIndicator) headerView.findViewById(R.id.indicator);
//        CommonAutoViewpager.viewPageAdapter(this.mActivity, viewPager, indicator);
        //信息展示
//        NoScrollListView mNoScrollListView = (NoScrollListView) headerView.findViewById(R.id.listview_noscroll);
        //信息展示数据
//        List<String> mList = new ArrayList<String>();
//        for (int i = 0; i < 2; i++) {
//            mList.add("8");
//        }
//        WorkListShowAdapter mShowAdapter = new WorkListShowAdapter(this.mActivity, mList);
//        mNoScrollListView.setAdapter(mShowAdapter);
//        mShowAdapter.notifyDataSetChanged();
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
//        LayoutInflater inflater = LayoutInflater.from(mActivity);
//        this.dialogView = inflater.inflate(R.layout.workshow_dialog, null);
//        ListView mListView = (ListView) this.dialogView.findViewById(R.id.dialog_listview);
//        final List<String> mList = new ArrayList<String>();
//        mList.add("设备租赁");
//        mList.add("苗木信息");
//        mList.add("招标信息");
//        mList.add("人员招聘");
//        CommonListStringAdapter mListStringAdapter = new CommonListStringAdapter(mActivity, mList);
//        mListView.setAdapter(mListStringAdapter);
//        mListStringAdapter.notifyDataSetChanged();
//        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
//        builder.setView(dialogView);
//        final AlertDialog dialog = builder.create();
//        dialog.setContentView(dialogView);
        dialog = new DialogWorkShow(mActivity, new DialogWorkShow.DialogWorkShowListener() {
            @Override
            public void onClick(View view) {
                WorkShowProtocol protocol = new WorkShowProtocol(mActivity);
                protocol.addResponseListener(WorkShowFragment.this);
                switch (view.getId()) {
                    case R.id.tv_dialog1://设备租赁
                        tv_title.setText("设备租赁");
                        protocol.queryEquipmentLeasing();
                        mActivityFragmentView.viewLoading(View.VISIBLE);
                        indexOfSelect = 1;
                        break;
                    case R.id.tv_dialog2://苗木信息
                        tv_title.setText("苗木信息");
                        protocol.querySeedlingmessage();
                        mActivityFragmentView.viewLoading(View.VISIBLE);
                        indexOfSelect = 2;
                        break;
                    case R.id.tv_dialog3://招标信息
                        tv_title.setText("招标信息");
                        protocol.queryBid();
                        mActivityFragmentView.viewLoading(View.VISIBLE);
                        indexOfSelect = 3;
                        break;
                    case R.id.tv_dialog4://人员招聘
                        tv_title.setText("人员招聘");
                        protocol.queryRecruit();
                        mActivityFragmentView.viewLoading(View.VISIBLE);
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
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//                String whatSelect = mList.get(arg2);
//                tv_title.setText(whatSelect);
//                WorkShowProtocol protocol = new WorkShowProtocol(mActivity);
//                protocol.addResponseListener(WorkShowFragment.this);
//                switch (arg2) {
//                    case 0://设备租赁
//                        protocol.queryEquipmentLeasing();
//                        mActivityFragmentView.viewLoading(View.VISIBLE);
//                        indexOfSelect = 1;
//                        dialog.dismiss();
//                        break;
//                    case 1://苗木信息
//                        protocol.querySeedlingmessage();
//                        mActivityFragmentView.viewLoading(View.VISIBLE);
//                        indexOfSelect = 2;
//                        dialog.dismiss();
//                        break;
//                    case 2://招标信息
//                        protocol.queryBid();
//                        mActivityFragmentView.viewLoading(View.VISIBLE);
//                        indexOfSelect = 3;
//                        dialog.dismiss();
//                        break;
//                    case 3://人员招聘
//                        protocol.queryRecruit();
//                        mActivityFragmentView.viewLoading(View.VISIBLE);
//                        indexOfSelect = 4;
//                        dialog.dismiss();
//                        break;
//                }
//            }
//        });
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
                        int count = equipmentLeasingAdapter.getCount();
                        if (count + 10 < MaxDateNum) {
                            // 每次加载10条
                            for (int i = count; i < count + 10; i++) {
                                EquipmentLeasingEntity entity = equipmentLeasingEntities.get(i);
                                initEquipmentLeasingEntities.add(entity);
                                equipmentLeasingAdapter.notifyDataSetChanged();
                            }
                            listview_xuqiu.stopLoadMore();
                        } else {
                            // 数据已经不足10条
                            for (int i = count; i < MaxDateNum; i++) {
                                EquipmentLeasingEntity entity = equipmentLeasingEntities.get(i);
                                initEquipmentLeasingEntities.add(entity);
                                equipmentLeasingAdapter.notifyDataSetChanged();
                            }
                            listview_xuqiu.setPullLoadEnable(false);
                        }
                    }
                }, 1000);
                break;
            case 2://苗木信息
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int count = seedlingAdapter.getCount();
                        if (count + 10 < MaxDateNum) {
                            // 每次加载10条
                            for (int i = count; i < count + 10; i++) {
                                SeedlingEntity entity = seedlingEntities.get(i);
                                initSeedlingEntities.add(entity);
                                seedlingAdapter.notifyDataSetChanged();
                            }
                            listview_xuqiu.stopLoadMore();
                        } else {
                            // 数据已经不足10条
                            for (int i = count; i < MaxDateNum; i++) {
                                SeedlingEntity entity = seedlingEntities.get(i);///////////////
                                initSeedlingEntities.add(entity);
                                seedlingAdapter.notifyDataSetChanged();
                            }
                            listview_xuqiu.setPullLoadEnable(false);
                        }
                    }
                }, 1000);
                break;
            case 3://招标信息
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int count = bidAdapter.getCount();
                        if (count + 10 < MaxDateNum) {
                            // 每次加载10条
                            for (int i = count; i < count + 10; i++) {
                                BidEntity entity = bidEntities.get(i);
                                initBidEntities.add(entity);
                                bidAdapter.notifyDataSetChanged();
                            }
                            listview_xuqiu.stopLoadMore();
                        } else {
                            // 数据已经不足10条
                            for (int i = count; i < MaxDateNum; i++) {
                                BidEntity entity = bidEntities.get(i);
                                initBidEntities.add(entity);
                                bidAdapter.notifyDataSetChanged();
                            }
                            listview_xuqiu.setPullLoadEnable(false);
                        }
                    }
                }, 1000);
                break;
            case 4://人员招聘
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int count = recruitAdapter.getCount();
                        if (count + 10 < MaxDateNum) {
                            // 每次加载10条
                            for (int i = count; i < count + 10; i++) {
                                RecruitEntity entity = recruitEntities.get(i);
                                initRecruitEntities.add(entity);
                                recruitAdapter.notifyDataSetChanged();
                            }
                            listview_xuqiu.stopLoadMore();
                        } else {
                            // 数据已经不足10条
                            for (int i = count; i < MaxDateNum; i++) {
                                RecruitEntity entity = recruitEntities.get(i);
                                initRecruitEntities.add(entity);
                                recruitAdapter.notifyDataSetChanged();
                            }
                            listview_xuqiu.setPullLoadEnable(false);
                        }
                    }
                }, 1000);
                break;
        }
    }

    @Override
    public void OnMessageResponse(String url, Object jo, AjaxStatus status) throws JSONException {
        super.OnMessageResponse(url, jo, status);
        listview_xuqiu.setPullLoadEnable(true);
        /**
         * 设备租赁
         */
        if (url.endsWith(ProtocolUrl.RELEASE_QUERYEQUIPMENTLEASING)) {
            if (jo instanceof BaseEntity) {
                BaseEntity mData = (BaseEntity) jo;
                /**
                 * 如果查询结果没有数据，则显示无数据
                 */
                mActivityFragmentView.viewMainGone();
                if (mData.getStatus() > 0) {
                    mActivity.msg(mData.getMsg());
                    return;
                } else {
                    /**
                     * 查询结果有数据，做数据展示
                     */
                    mActivityFragmentView.viewEmptyGone();
                    String jsonStr = ToolsJson.toJson(mData.getData());
                    Type type = new TypeToken<List<EquipmentLeasingEntity>>() {
                    }.getType();
                    equipmentLeasingEntities.clear();
                    initEquipmentLeasingEntities.clear();
                    List<EquipmentLeasingEntity> homeData = ToolsJson.parseObjecta(jsonStr, type);
                    String topPath = mData.getPath();//图片URL头部地址
                    for (EquipmentLeasingEntity entity : homeData) {
                        entity.setFilesId(topPath);
                        equipmentLeasingEntities.add(entity);
                    }
                    MaxDateNum = equipmentLeasingEntities.size(); // 设置最大数据条数
                    /**
                     * 展示设备租赁数据
                     */
                    if (MaxDateNum > 10) {
                        for (int i = 0; i < 10; i++) {
                            EquipmentLeasingEntity entity = equipmentLeasingEntities.get(i);
                            initEquipmentLeasingEntities.add(entity);
                        }
                    } else {
                        for (int i = 0; i < MaxDateNum; i++) {
                            EquipmentLeasingEntity entity = equipmentLeasingEntities.get(i);
                            initEquipmentLeasingEntities.add(entity);
                        }
                        listview_xuqiu.setPullLoadEnable(false);
                    }
                    equipmentLeasingAdapter.notifyDataSetChanged();
                    listview_xuqiu.setAdapter(equipmentLeasingAdapter);
                }
            }
        }
        /**
         * 苗木信息
         */
        if (url.endsWith(ProtocolUrl.RELEASE_QUERYSEEDLINGMESSAGE)) {
            if (jo instanceof BaseEntity) {
                BaseEntity mData = (BaseEntity) jo;
                /**
                 * 如果查询结果没有数据，则显示无数据
                 */
                mActivityFragmentView.viewMainGone();
                if (mData.getStatus() > 0) {
                    mActivity.msg(mData.getMsg());
                    return;
                } else {
                    /**
                     * 查询结果有数据，做数据展示
                     */
                    mActivityFragmentView.viewEmptyGone();
                    String jsonStr = ToolsJson.toJson(mData.getData());
                    Type type = new TypeToken<List<SeedlingEntity>>() {
                    }.getType();
                    seedlingEntities.clear();
                    initSeedlingEntities.clear();
                    List<SeedlingEntity> homeData = ToolsJson.parseObjecta(jsonStr, type);
                    String topPath = mData.getPath();//图片URL头部地址
                    for (SeedlingEntity entity : homeData) {
                        entity.setFilesId(topPath);
                        seedlingEntities.add(entity);
                    }
                    MaxDateNum = seedlingEntities.size(); // 设置最大数据条数
                    /**
                     * 展示苗木信息数据
                     */
                    if (MaxDateNum > 10) {
                        for (int i = 0; i < 10; i++) {
                            SeedlingEntity entity = seedlingEntities.get(i);
                            initSeedlingEntities.add(entity);
                        }
                    } else {
                        for (int i = 0; i < MaxDateNum; i++) {
                            SeedlingEntity entity = seedlingEntities.get(i);
                            initSeedlingEntities.add(entity);
                        }
                        listview_xuqiu.setPullLoadEnable(false);
                    }
                    seedlingAdapter.notifyDataSetChanged();
                    listview_xuqiu.setAdapter(seedlingAdapter);
                }
            }
        }
        /**
         * 招标信息
         */
        if (url.endsWith(ProtocolUrl.RELEASE_QUERYBID)) {
            if (jo instanceof BaseEntity) {
                BaseEntity mData = (BaseEntity) jo;
                /**
                 * 如果查询结果没有数据，则显示无数据
                 */
                mActivityFragmentView.viewMainGone();
                mActivity.msg(mData.getMsg());
            } else if (jo instanceof List) {
                /**
                 * 查询结果有数据，做数据展示
                 */
                mActivityFragmentView.viewEmptyGone();
                bidEntities.clear();
                initBidEntities.clear();
                bidEntities = (List<BidEntity>) jo;
                MaxDateNum = bidEntities.size(); // 设置最大数据条数
                /**
                 * 展示招标信息数据 10条
                 */
                if (MaxDateNum > 10) {
                    for (int i = 0; i < 10; i++) {
                        BidEntity entity = bidEntities.get(i);
                        initBidEntities.add(entity);
                    }
                } else {
                    for (int i = 0; i < MaxDateNum; i++) {
                        BidEntity entity = bidEntities.get(i);
                        initBidEntities.add(entity);
                    }
                    listview_xuqiu.setPullLoadEnable(false);
                }
                bidAdapter.notifyDataSetChanged();
                listview_xuqiu.setAdapter(bidAdapter);
            }
        }
        /**
         * 人员招聘
         */
        if (url.endsWith(ProtocolUrl.RELEASE_QUERYRECRUIT)) {
            if (jo instanceof BaseEntity) {
                BaseEntity mData = (BaseEntity) jo;
                /**
                 * 如果查询结果没有数据，则显示无数据
                 */
                mActivityFragmentView.viewMainGone();
                mActivity.msg(mData.getMsg());
            } else if (jo instanceof List) {
                /**
                 * 查询结果有数据，做数据展示
                 */
                mActivityFragmentView.viewEmptyGone();
                recruitEntities.clear();
                initRecruitEntities.clear();
                recruitEntities = (List<RecruitEntity>) jo;
                MaxDateNum = recruitEntities.size(); // 设置最大数据条数
                /**
                 * 展示人员招聘数据 10条
                 */
                if (MaxDateNum > 10) {
                    for (int i = 0; i < 10; i++) {
                        RecruitEntity entity = recruitEntities.get(i);
                        initRecruitEntities.add(entity);
                    }
                } else {
                    for (int i = 0; i < MaxDateNum; i++) {
                        RecruitEntity entity = recruitEntities.get(i);
                        initRecruitEntities.add(entity);
                    }
                    listview_xuqiu.setPullLoadEnable(false);
                }
                recruitAdapter.notifyDataSetChanged();
                listview_xuqiu.setAdapter(recruitAdapter);
            }
        }
    }

    @Override
    public void onRefresh() {
        WorkShowProtocol protocol = new WorkShowProtocol(mActivity);
        protocol.addResponseListener(WorkShowFragment.this);
        switch (indexOfSelect) {
            case 1://设备租赁
                protocol.queryEquipmentLeasing();
                mActivityFragmentView.viewLoading(View.VISIBLE);
                break;
            case 2://苗木信息
                protocol.querySeedlingmessage();
                mActivityFragmentView.viewLoading(View.VISIBLE);
                break;
            case 3://招标信息
                protocol.queryBid();
                mActivityFragmentView.viewLoading(View.VISIBLE);
                break;
            case 4://人员招聘
                protocol.queryRecruit();
                mActivityFragmentView.viewLoading(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onLoadMore() {
        loadMoreData();
    }
}
