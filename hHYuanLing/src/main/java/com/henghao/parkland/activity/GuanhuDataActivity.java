package com.henghao.parkland.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.benefit.buy.library.http.query.callback.AjaxStatus;
import com.benefit.buy.library.utils.tools.ToolsJson;
import com.google.gson.reflect.TypeToken;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
import com.henghao.parkland.ProtocolUrl;
import com.henghao.parkland.R;
import com.henghao.parkland.model.entity.BaseEntity;
import com.henghao.parkland.model.entity.YHDataEntity;
import com.henghao.parkland.model.protocol.YHDataProtocol;
import com.henghao.parkland.utils.ScanImageUtils;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * 管护详情界面
 */
public class GuanhuDataActivity extends ActivityFragmentSupport implements View.OnClickListener {

    /**
     * 植物编号
     */
    @ViewInject(R.id.tv_treeid_maintenance)
    private TextView tv_treeid_maintenance;

    /**
     * 养护地址
     */
    @ViewInject(R.id.tv_address)
    private TextView tv_address;
    /**
     * 养护时间
     */
    @ViewInject(R.id.tv_time)
    private TextView tv_time;
    /**
     * 养护人员
     */
    @ViewInject(R.id.tv_worker)
    private TextView tv_worker;
    /**
     * 养护内容
     */
    @ViewInject(R.id.tv_details)
    private TextView tv_details;
    /**
     * 问题发现
     */
    @ViewInject(R.id.tv_question)
    private TextView tv_question;
    /**
     * 植物长势
     */
    @ViewInject(R.id.tv_growup)
    private TextView tv_growup;
    /**
     * 备注
     */
    @ViewInject(R.id.tv_data)
    private TextView tv_data;
    /**
     * 保洁情况
     */
    @ViewInject(R.id.tv_clen)
    private TextView tv_clen;
    /**
     * 养护前图片
     */
    @ViewInject(R.id.iv_before)
    private ImageView iv_before;
    /**
     * 养护后图片
     */
    @ViewInject(R.id.iv_after)
    private ImageView iv_after;

    private BitmapUtils mBitmapUtils;
    private ArrayList<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_yhdata);
        this.mActivityFragmentView.viewEmpty(R.layout.activity_empty);
        this.mActivityFragmentView.viewEmptyGone();
        this.mActivityFragmentView.viewLoading(View.GONE);
        this.mActivityFragmentView.getNavitionBarView().setVisibility(View.VISIBLE);
        ViewUtils.inject(this, this.mActivityFragmentView);
        setContentView(this.mActivityFragmentView);
        mBitmapUtils = new BitmapUtils(context, Constant.CACHE_DIR_PATH);
        mBitmapUtils.configDefaultLoadFailedImage(R.drawable.img_loading_fail_big);
        mBitmapUtils.configDefaultLoadingImage(R.drawable.img_loading_default_big);
        initWidget();
        initData();
    }

    @Override
    public void initWidget() {
        int yId = getIntent().getIntExtra("yid", 0);
        String treeId = getIntent().getStringExtra("treeId");
        String uid = getLoginUid();
        YHDataProtocol mmProtocol = new YHDataProtocol(this);
        mmProtocol.addResponseListener(this);
        mmProtocol.getYHData(yId, treeId, uid);
        mActivityFragmentView.viewLoading(View.VISIBLE);
    }

    @Override
    public void initData() {
        super.initData();
        initWithBar();
        initWithCenterBar();
        mCenterTextView.setText("管护信息");
    }

    @Override
    public void OnMessageResponse(String url, Object jo, AjaxStatus status) throws JSONException {
        super.OnMessageResponse(url, jo, status);
//        if (url.endsWith(ProtocolUrl.APP_GHMANAGEMSGBYID)) {
//            if (jo instanceof BaseEntity) {
//                return;
//            }
//            YHDataEntity mdata = (YHDataEntity) jo;
//            setData(mdata);
//        }
        if (url.endsWith(ProtocolUrl.APP_GHMANAGEMSGBYID)) {
            if (jo instanceof BaseEntity) {
                BaseEntity mData = (BaseEntity) jo;
                if (mData.getStatus() > 0) {
                    msg(mData.getMsg());
                    return;
                } else {
                    mActivityFragmentView.viewEmptyGone();
                    String jsonStr = ToolsJson.toJson(mData.getData());
                    Type type = new TypeToken<YHDataEntity>() {
                    }.getType();
                    YHDataEntity mdata = ToolsJson.parseObjecta(jsonStr, type);
                    String topPath = mData.getPath();//图片URL头部地址
                    mdata.setNowfile(topPath + mdata.getNowfile());
                    mdata.setOldfile(topPath + mdata.getOldfile());
                    setData(mdata);
                }
            }
        }
    }

    private void setData(YHDataEntity mdata) {
        tv_treeid_maintenance.setText(mdata.getTreeId());
        tv_address.setText(mdata.getYhSite());
        tv_time.setText(mdata.getYhTime());
        tv_worker.setText(mdata.getYhWorker());
        tv_details.setText(mdata.getYhDetails());
        tv_question.setText(mdata.getYhQuestion());
        tv_growup.setText(mdata.getTreeGrowup());
        tv_data.setText(mdata.getYhComment());
        tv_clen.setText(mdata.getYhClean());
        mBitmapUtils.display(iv_before, mdata.getOldfile());
        mBitmapUtils.display(iv_after, mdata.getNowfile());
        data = new ArrayList<>();
        data.add(mdata.getOldfile());
        data.add(mdata.getNowfile());
        iv_before.setOnClickListener(this);
        iv_after.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_before:
                ScanImageUtils.ScanImage(context, data, 0);
                break;
            case R.id.iv_after:
                ScanImageUtils.ScanImage(context, data, 1);
                break;
        }
    }
}
