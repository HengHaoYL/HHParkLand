package com.henghao.parkland.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.benefit.buy.library.utils.tools.ToolsKit;
import com.benefit.buy.library.views.CircularImageView;
import com.benefit.buy.library.views.dialog.BaseDialog;
import com.henghao.parkland.BuildConfig;
import com.henghao.parkland.Constant;
import com.henghao.parkland.R;
import com.henghao.parkland.activity.DebugSetupActivity;
import com.henghao.parkland.activity.QiandaoActivity;
import com.henghao.parkland.activity.projectmanage.ProjectInfoActivity;
import com.henghao.parkland.activity.user.LoginAndRegActivity;
import com.henghao.parkland.activity.user.MyWorkerListActivity;
import com.henghao.parkland.activity.user.SettingActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 我的〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author zhangxianwen
 * @version HDMNV100R001, 2016年8月15日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class MyLoginFragment extends FragmentSupport {

    /**
     * 用户头像
     */
    @ViewInject(R.id.user_header)
    private CircularImageView user_header;
    /**
     * 用户名称
     */
    @ViewInject(R.id.tv_userName)
    private TextView tv_userName;
    /**
     * 用户电话
     */
    @ViewInject(R.id.tv_userPhone)
    private TextView tv_userPhone;
    /**
     * 登录
     */
    @ViewInject(R.id.tv_login)
    private TextView tv_login;
    /**
     * 设置
     */
    @ViewInject(R.id.image_setting)
    private ImageView image_setting;
    /**
     * 我的工作
     */
    @ViewInject(R.id.tv_myworker)
    private TextView tv_myworker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.fragment_my);
        this.mActivityFragmentView.viewEmpty(R.layout.activity_empty);
        this.mActivityFragmentView.viewEmptyGone();
        this.mActivityFragmentView.viewLoading(View.GONE);
        mActivityFragmentView.getNavitionBarView().setVisibility(View.GONE);
        ViewUtils.inject(this, this.mActivityFragmentView);
        initWidget();
        initData();
        if (BuildConfig.DEBUG) initButton();
        return this.mActivityFragmentView;
    }

    /**
     * 设置HOST按钮
     */
    private void initButton() {
        Button bt = (Button) mActivityFragmentView.findViewById(R.id.bt_set_host);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), DebugSetupActivity.class));
            }
        });
    }

    private void initData() {

    }


    public void initWidget() {
    }


    public void onResume() {
        super.onResume();
        String Uid = mActivity.getLoginUser();
        if (ToolsKit.isEmpty(Uid)) {
            //未登录
            tv_userName.setText("点击登录更精彩");
            tv_userPhone.setText("暂未登录");
            tv_login.setText("登录");
        } else {
            //已登录
            tv_userName.setText(mActivity.getLoginUser());
            tv_userPhone.setText(mActivity.getLoginUserPhone());
            tv_login.setText("注销");
        }
    }

    @OnClick({R.id.tv_login, R.id.ll_updatename, R.id.image_setting, R.id.tv_myworker, R.id.tv_qiandao, R.id.tv_myproject})
    private void viewClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.tv_login:
                if (isLogin()) {
                    Resources mResources = getResources();
                    String title = mResources.getString(R.string.account_cancel_hint);
                    String ok = mResources.getString(R.string.ok);
                    String cancel = mResources.getString(R.string.cancel);
                    String message = mResources.getString(R.string.account_cancel_affirm);
                    BaseDialog.getDialog(mActivity, title, message, cancel, cancelListener, ok, exitListener).show();
                } else {
                    intent.setClass(mActivity, LoginAndRegActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.ll_updatename:
                if (!isLogin()) {
                    intent.setClass(mActivity, LoginAndRegActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.image_setting:
                //设置
                intent.setClass(mActivity, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_myworker:
                //我的轨迹
                intent.setClass(mActivity, MyWorkerListActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_qiandao:
                //签到
                intent.setClass(mActivity, QiandaoActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_myproject:
                //我的项目
                intent.setClass(mActivity, ProjectInfoActivity.class);
                startActivity(intent);
                break;
        }
    }


    private boolean isLogin() {
        if (!ToolsKit.isEmpty(mActivity.getLoginUser())) {
            return true;
        }
        return false;
    }

    // 注销对话框监听器
    DialogInterface.OnClickListener exitListener = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            SharedPreferences.Editor editor = mActivity.getLoginUserSharedPre().edit();
            // 退出清空用户信息
            editor.remove(Constant.USERNAME).remove(Constant.USERID)
                    .remove(Constant.USERIMG).remove(Constant.USERPHONE);
            editor.putString(Constant.USERID, null);
            editor.putString(Constant.USERNAME, null);
            editor.putString(Constant.USERPHONE, null);
            editor.putString(Constant.USERIMG, null);
            editor.commit();
            dialog.dismiss();

            Intent intent = new Intent();
            intent.setClass(mActivity, LoginAndRegActivity.class);
            startActivity(intent);
        }
    };

    DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    };
}
