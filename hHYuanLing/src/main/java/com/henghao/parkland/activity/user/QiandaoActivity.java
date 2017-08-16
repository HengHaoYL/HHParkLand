package com.henghao.parkland.activity.user;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.benefit.buy.library.utils.tools.ToolsKit;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.henghao.parkland.R.id.et_company_qiandao;


/**
 * 签到界面 〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author yanqiyun
 * @version HDMNV100R001, 2016-12-01
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class QiandaoActivity extends ActivityFragmentSupport {
    /**
     * 签到的时间（年月日）
     */
    @InjectView(R.id.tv_time_qiandao)
    TextView tvTime;
    /**
     * 当前企业
     */
    @InjectView(et_company_qiandao)
    EditText etCompany;
    /**
     * 签到的地点
     */
    @InjectView(R.id.tv_address_qiandao)
    TextView tvAddress;
    /**
     * 签到按钮图片
     */
    @InjectView(R.id.iv_qiandao)
    ImageView ivQiandao;
    /**
     * 签到的具体时间（时分）
     */
    @InjectView(R.id.tv_hourminute_qiandao)
    TextView tvHourminute;
    //    /**
//     * 签到的状态（当前已签到或未签到）
//     */
//    @ViewInject(R.id.tv_state_qiandao)
//    private TextView tv_state_qiandao;
    //    /**
//     * 签到打勾
//     */
//    @ViewInject(R.id.img_confirm_qiandao)
//    private ImageView img_confirm_qiandao;
//    private int count = 0;// 签到次数
//    private Call call;

    private static final String TAG = "QiandaoActivity";

    // 定位相关声明
    public LocationClient locationClient = null;
    private double latitude;//纬度
    private double longitude;//经度
    private String addrStr;//当前地理位置

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 在使用SDK各组件之前初始化context信息，传入ApplicationContext
        // 注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        this.mActivityFragmentView.viewMain(R.layout.activity_qiandao);
        this.mActivityFragmentView.viewEmpty(R.layout.activity_empty);
        this.mActivityFragmentView.viewEmptyGone();
        this.mActivityFragmentView.viewLoading(View.GONE);
        this.mActivityFragmentView.clipToPadding(true);
        setContentView(this.mActivityFragmentView);
        ButterKnife.inject(this);
        initWidget();
        initData();
    }

    @OnClick({R.id.iv_qiandao})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            // 点击签到
            case R.id.iv_qiandao:
                String uid = getLoginUid();
                if (ToolsKit.isEmpty(uid)) {
                    Toast.makeText(QiandaoActivity.this, "当前没有登录，请先登录！！", Toast.LENGTH_SHORT).show();
                    return;
                }
                String time = this.tvHourminute.getText().toString();// 签到时间
                String address = this.tvAddress.getText().toString(); // 签到地址
                String company = this.etCompany.getText().toString();// 当前企业
                if (ToolsKit.isEmpty(company)) {
                    msg("请输入当前企业！");
                    etCompany.requestFocus();
                    return;
                }
                if (ToolsKit.isEmpty(addrStr)) {
                    Toast.makeText(QiandaoActivity.this, "当前没有定位，请定位后再签到！", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(QiandaoActivity.this, QiandaoSubmitActivity.class);
                intent.putExtra("time", time);
                intent.putExtra("address", address);
                intent.putExtra("longitude", longitude);
                intent.putExtra("latitude", latitude);
                intent.putExtra("company", company);
                startActivity(intent);
                break;
        }
    }

    /**
     * 判断GPS是否开启
     *
     * @param context
     * @return true 表示开启
     */
    private boolean isOPenGPS(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return gps;
    }

    /**
     * 检测网络是否连接
     *
     * @return
     */
    private boolean checkNetworkState() {
        boolean flag = false;
        // 得到网络连接信息
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        // 去进行判断网络是否连接
        if (manager.getActiveNetworkInfo() != null) {
            flag = manager.getActiveNetworkInfo().isAvailable();
        }
        return flag;
    }

    public BDLocationListener myListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null) {
                return;
            }
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            addrStr = location.getAddrStr();
            System.out.println("经度：" + longitude + "，纬度：" + latitude);
            /**
             * 如果GPS未打开且无网络
             */
            if (!checkNetworkState() && !isOPenGPS(getContext())) {
                tvAddress.setText("没有定位信息！");
                addrStr = null;
            }
            if (ToolsKit.isEmpty(addrStr)) {
                tvAddress.setText("没有定位信息！");
                ivQiandao.setImageResource(R.drawable.icon_grayciecle);
                ivQiandao.setClickable(false);
                return;
            }
            ivQiandao.setClickable(true);
            ivQiandao.setImageResource(R.drawable.icon_orangecircle);
            tvAddress.setText(addrStr);

        }
    };

    @Override
    public void initWidget() {
        initWithBar();
        this.mLeftTextView.setVisibility(View.VISIBLE);
        this.mLeftTextView.setText("返回");
        initWithCenterBar();
        this.mCenterTextView.setVisibility(View.VISIBLE);
        this.mCenterTextView.setText("签到");
        initWithRightBar();
        this.mRightTextView.setVisibility(View.VISIBLE);
        this.mRightTextView.setText("签到情况");
        mRightLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, QiandaoInfoActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 设置定位参数
     */
    private void setLocationOption() {
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开GPS
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式
        option.setCoorType("bd09ll"); // 返回的定位结果是百度经纬度,默认值gcj02
        option.setScanSpan(5000); // 设置发起定位请求的间隔时间为5000ms
        option.setIsNeedAddress(true); // 返回的定位结果包含地址信息
        option.setNeedDeviceDirect(true); // 返回的定位结果包含手机机头的方向
        this.locationClient.setLocOption(option);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.locationClient.start(); // 开始定位
        //请求服务器，查询当天签到次数---------------------暂时还未实现
        //queryNumber();
    }

//    /**
//     * 查询当天签到次数
//     */
//    private void queryNumber() {
//        call = Requester.qiandaoQuery(getLoginUid(), callback);
//    }
//
//    /**
//     * 查询签到次数回调
//     */
//    private DefaultCallback callback = new DefaultCallback() {
//        @Override
//        public void onFailure(Exception e, int code) {
//            if (BuildConfig.DEBUG) Log.e(TAG, "onFailure: code = " + code, e);
//            e.printStackTrace();
//            Toast.makeText(context, "网络访问错误！", Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onSuccess(String response) {
//            try {
//                JSONObject jsonObject = new JSONObject(response);
//                count = jsonObject.getInt("data");
//                if (count == 0) {
//                    img_confirm_qiandao.setVisibility(View.GONE);
//                    tv_state_qiandao.setText("今日你还未签到");
//                } else {
//                    img_confirm_qiandao.setVisibility(View.VISIBLE);
//                    tv_state_qiandao.setText("今日你已签到" + count + "次");
//                }
//            } catch (JSONException e) {
//                if (BuildConfig.DEBUG) Log.e("onSuccess", "签到查询失败", e);
//                Toast.makeText(context, "服务器错误，请稍后重试！", Toast.LENGTH_SHORT).show();
//            }
//        }
//    };

    @Override
    public void initData() {
        // 定位
        this.locationClient = new LocationClient(getApplicationContext()); // 实例化LocationClient类
        this.locationClient.registerLocationListener(this.myListener); // 注册监听函数
        this.setLocationOption(); // 设置定位参数
        this.locationClient.start(); // 开始定位
        // 设置签到时间（年、月、日）
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        String time = dateFormat.format(date);
        this.tvAddress.setText(time);
        // 设置签到具体时间（时、分）
        dateFormat = new SimpleDateFormat("HH:mm");
        time = dateFormat.format(date);
        this.tvHourminute.setText(time);
    }

    // 三个状态实现地图生命周期管理
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 退出时销毁定位
        this.locationClient.stop();
//        if (call != null && !call.isCanceled()) {
//            call.cancel();
//        }
    }

    @Override
    public void onPause() {
        super.onPause();
        this.locationClient.stop();
    }
}
