package com.henghao.parkland.fragment.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.benefit.buy.library.utils.tools.ToolsKit;
import com.henghao.parkland.BuildConfig;
import com.henghao.parkland.Constant;
import com.henghao.parkland.R;
import com.henghao.parkland.activity.MainActivity;
import com.henghao.parkland.fragment.FragmentSupport;
import com.henghao.parkland.model.entity.UserLoginEntity;
import com.henghao.parkland.utils.Requester;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.squareup.okhttp.Request;

/**
 * 我的登录〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author zhangxianwen
 * @version HDMNV100R001, 2016年8月15日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class LoginFragment extends FragmentSupport {

    private static final String TAG = "LoginFragment";
    @ViewInject(R.id.login_pass_quick)
    private TextView login_pass_quick;

    @ViewInject(R.id.login_user)
    private EditText login_user;

    @ViewInject(R.id.login_pass)
    private EditText login_pass;

    @ViewInject(R.id.login_reset_password)
    private TextView tv_reset_password;

    @ViewInject(R.id.iv_eye_login)
    private ImageView iv_eye_login;

    public static FragmentSupport newInstance(Object obj) {
        LoginFragment fragment = new LoginFragment();
        if (fragment.object == null) {
            fragment.object = obj;
        }
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_login);
        this.mActivityFragmentView.viewEmpty(R.layout.activity_empty);
        this.mActivityFragmentView.viewEmptyGone();
        this.mActivityFragmentView.viewLoading(View.GONE);
        mActivityFragmentView.getNavitionBarView().setVisibility(View.GONE);
        ViewUtils.inject(this, this.mActivityFragmentView);
        initWidget();
        initData();
        return this.mActivityFragmentView;
    }

    private void initData() {
        mLeftImageView = (ImageView) getActivity().findViewById(R.id.bar_left_img);
        mLeftImageView.setImageDrawable(getResources().getDrawable(R.drawable.btn_back));
        mCenterTextView = (TextView) getActivity().findViewById(R.id.bar_center_title);
        mCenterTextView.setText("登录");
    }

    public void initWidget() {
    }


    @OnClick({R.id.tv_login, R.id.iv_eye_login, R.id.login_reset_password})
    public void viewClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                //登录
                if (checkData())
                    Requester.login(login_user.getText().toString().trim(), login_pass.getText().toString().trim(), String.valueOf(1), loginCallback);
                break;
            case R.id.iv_eye_login:
                boolean isSelected = iv_eye_login.isSelected();
                //反转选中状态
                iv_eye_login.setSelected(!isSelected);
                if (isSelected) {
                    //选中时显示密码
                    login_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    //未选中时隐藏密码
                    login_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                break;
            case R.id.login_reset_password:
                mActivity.msg("未实现");
        }
    }

    private boolean checkData() {
        if (ToolsKit.isEmpty(login_user.getText().toString().trim())) {
            mActivity.msg("用户名不能为空");
            return false;
        }
        if (ToolsKit.isEmpty(login_pass.getText().toString().trim())) {
            mActivity.msg("密码不能为空");
            return false;
        }
        return true;
    }

    private DefaultCallback loginCallback = new DefaultCallback() {
        @Override
        public void onFailure(Request request, Exception e, int code) {

        }

        @Override
        public void onSuccess(String response) {
            if (BuildConfig.DEBUG) Log.d("LoginFragment", response);
            JSONObject jo = JSON.parseObject(response);
            String data = jo.getString("data");
            if (TextUtils.isEmpty(data)) {
                mActivity.msg(jo.getString("result"));
                return;
            }
            try {
                UserLoginEntity userLogin = JSON.parseObject(data, UserLoginEntity.class);
                mActivity.getLoginUserSharedPre().edit().putString(Constant.USERID, userLogin.getUid()).putString(Constant.USERNAME, userLogin.getUsername()).putString(Constant.USERPHONE, userLogin.getTel()).apply();
                Intent intent = new Intent();
                intent.setClass(mActivity, MainActivity.class);
                startActivity(intent);
                mActivity.onBackPressed();
            } catch (Exception e) {
                e.printStackTrace();
                mActivity.msg("登录失败，请稍后重试");
            }
        }
    };
}
