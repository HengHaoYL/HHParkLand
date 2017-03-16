package com.henghao.parkland.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.benefit.buy.library.http.query.callback.AjaxStatus;
import com.benefit.buy.library.utils.tools.ToolsJson;
import com.benefit.buy.library.views.NoScrollListView;
import com.google.gson.reflect.TypeToken;
import com.henghao.parkland.ProtocolUrl;
import com.henghao.parkland.R;
import com.henghao.parkland.adapter.BidAdapter;
import com.henghao.parkland.adapter.CommonListStringAdapter;
import com.henghao.parkland.adapter.EquipmentLeasingAdapter;
import com.henghao.parkland.adapter.RecruitAdapter;
import com.henghao.parkland.adapter.SeedlingAdapter;
import com.henghao.parkland.model.entity.BaseEntity;
import com.henghao.parkland.model.entity.BidEntity;
import com.henghao.parkland.model.entity.EquipmentLeasingEntity;
import com.henghao.parkland.model.entity.RecruitEntity;
import com.henghao.parkland.model.entity.SeedlingEntity;
import com.henghao.parkland.model.protocol.WorkShowProtocol;
import com.henghao.parkland.utils.PopupWindowHelper;
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
public class WorkShowFragment extends FragmentSupport {

    @ViewInject(R.id.listview_xuqiu)
    private NoScrollListView listview_xuqiu;

    private TextView tv_title;//标题

    private View popView;
    private PopupWindowHelper popupWindowHelper;

    private EquipmentLeasingAdapter equipmentLeasingAdapter;//设备租赁适配器
    private List<EquipmentLeasingEntity> equipmentLeasingEntities;//设备租赁数据

    private SeedlingAdapter seedlingAdapter;//苗木信息适配器
    private List<SeedlingEntity> seedlingEntities;//苗木信息数据

    private BidAdapter bidAdapter;//招标信息适配器
    private List<BidEntity> bidEntities;//招标信息数据

    private RecruitAdapter recruitAdapter;//人员招聘适配器
    private List<RecruitEntity> recruitEntities;//人员招聘数据

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
        equipmentLeasingAdapter = new EquipmentLeasingAdapter(this.mActivity, equipmentLeasingEntities);
        //苗木信息
        seedlingEntities = new ArrayList<>();
        seedlingAdapter = new SeedlingAdapter(this.mActivity, seedlingEntities);
        //招标信息
        bidEntities = new ArrayList<>();
        bidAdapter = new BidAdapter(this.mActivity, bidEntities);
        //人员招聘
        recruitEntities = new ArrayList<>();
        recruitAdapter = new RecruitAdapter(this.mActivity, recruitEntities);
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
        // TODO Auto-generated method stub
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
        // TODO Auto-generated method stub
        initWithCenterBar();
        this.mCenterTextView.setVisibility(View.VISIBLE);
        this.mCenterTextView.setText(R.string.workshow);
        initWithBar();
        this.mLeftImageView.setVisibility(View.VISIBLE);
        this.mLeftImageView.setImageResource(R.drawable.home_liebiao);
        LayoutInflater inflater = LayoutInflater.from(mActivity);
        this.popView = inflater.inflate(R.layout.common_android_listview, null);
        ListView mListView = (ListView) this.popView.findViewById(R.id.mlistview);
        final List<String> mList = new ArrayList<String>();
        mList.add("设备租赁");
        mList.add("苗木信息");
        mList.add("招标信息");
        mList.add("人员招聘");
        CommonListStringAdapter mListStringAdapter = new CommonListStringAdapter(mActivity, mList);
        mListView.setAdapter(mListStringAdapter);
        mListStringAdapter.notifyDataSetChanged();
        this.popupWindowHelper = new PopupWindowHelper(this.popView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                String whatSelect = mList.get(arg2);
                tv_title.setText(whatSelect);
                popupWindowHelper.dismiss();
                WorkShowProtocol protocol = new WorkShowProtocol(mActivity);
                protocol.addResponseListener(WorkShowFragment.this);
                switch (arg2) {
                    case 0://设备租赁
                        protocol.queryEquipmentLeasing();
                        mActivityFragmentView.viewLoading(View.VISIBLE);
                        break;
                    case 1://苗木信息
                        protocol.querySeedlingmessage();
                        mActivityFragmentView.viewLoading(View.VISIBLE);
                        break;
                    case 2://招标信息
                        protocol.queryBid();
                        mActivityFragmentView.viewLoading(View.VISIBLE);
                        break;
                    case 3://人员招聘
                        protocol.queryRecruit();
                        mActivityFragmentView.viewLoading(View.VISIBLE);
                        break;
                }
            }
        });
        mLeftImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindowHelper.showAsDropDown(v);
            }
        });
    }

    public void initWidget() {
        initwithContent();
    }

    @Override
    public void OnMessageResponse(String url, Object jo, AjaxStatus status) throws JSONException {
        super.OnMessageResponse(url, jo, status);
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
                    List<EquipmentLeasingEntity> homeData = ToolsJson.parseObjecta(jsonStr, type);
                    String topPath = mData.getPath();//图片URL头部地址
                    for (EquipmentLeasingEntity entity : homeData) {
                        entity.setFilesId(topPath);
                        equipmentLeasingEntities.add(entity);
                    }
                    /**
                     * 展示设备租赁数据
                     */
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
                    List<SeedlingEntity> homeData = ToolsJson.parseObjecta(jsonStr, type);
                    String topPath = mData.getPath();//图片URL头部地址
                    for (SeedlingEntity entity : homeData) {
                        entity.setFilesId(topPath);
                        seedlingEntities.add(entity);
                    }
                    /**
                     * 展示苗木信息数据
                     */
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
            }
            /**
             * 查询结果有数据，做数据展示
             */
            mActivityFragmentView.viewEmptyGone();
            bidEntities.clear();
            List<BidEntity> homeData = (List<BidEntity>) jo;
            /**
             * 展示苗木信息数据
             */
            bidEntities.addAll(homeData);
            bidAdapter.notifyDataSetChanged();
            listview_xuqiu.setAdapter(bidAdapter);
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
            }
            /**
             * 查询结果有数据，做数据展示
             */
            mActivityFragmentView.viewEmptyGone();
            recruitEntities.clear();
            List<RecruitEntity> homeData = (List<RecruitEntity>) jo;
            /**
             * 展示人员招聘数据
             */
            recruitAdapter.addAll(homeData);
            recruitAdapter.notifyDataSetChanged();
            listview_xuqiu.setAdapter(recruitAdapter);
        }
    }
}
