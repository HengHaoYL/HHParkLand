package com.henghao.parkland.fragment.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.benefit.buy.library.utils.tools.ToolsJson;
import com.benefit.buy.library.utils.tools.ToolsKit;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.henghao.parkland.BuildConfig;
import com.henghao.parkland.Constant;
import com.henghao.parkland.R;
import com.henghao.parkland.activity.MainActivity;
import com.henghao.parkland.fragment.FragmentSupport;
import com.henghao.parkland.model.entity.BaseEntity;
import com.henghao.parkland.model.entity.UserLoginEntity;
import com.henghao.parkland.utils.Requester;
import com.higdata.okhttphelper.callback.BytesCallback;

import java.lang.reflect.Type;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;

import static com.henghao.parkland.R.id.iv_eye_login;

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
    @InjectView(R.id.et_userName_login)
    EditText etUserName;
    @InjectView(R.id.et_passWord_login)
    EditText etPassword;
    @InjectView(iv_eye_login)
    ImageView ivEye;
    @InjectView(R.id.iv_authCode_login)
    ImageView ivAuthCode;
    @InjectView(R.id.et_userCode_login)
    EditText etUserCode;

    private Call loginCall;//登录请求
    private Call authCodeCall;//验证码请求

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
        ButterKnife.inject(this, this.mActivityFragmentView);
        initWidget();
        return this.mActivityFragmentView;
    }

    private void initWidget() {
        mLeftImageView = (ImageView) getActivity().findViewById(R.id.bar_left_img);
        mLeftImageView.setImageDrawable(getResources().getDrawable(R.drawable.btn_back));
        mCenterTextView = (TextView) getActivity().findViewById(R.id.bar_center_title);
        mCenterTextView.setText("登录");
    }

    @OnClick({iv_eye_login, R.id.iv_authCode_login, R.id.login_reset_password, R.id.tv_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case iv_eye_login:
                boolean isSelected = ivEye.isSelected();
                //反转选中状态
                ivEye.setSelected(!isSelected);
                if (isSelected) {
                    //选中时显示密码
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    //未选中时隐藏密码
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                break;
            case R.id.iv_authCode_login:
                authCodeCall = Requester.authCode(authCodeCallBack);//请求服务器更换验证码
                break;
            case R.id.login_reset_password:
                mActivity.msg("未实现");
                break;
            case R.id.tv_login:
                //登录
                if (checkData()) {
                    loginCall = Requester.login(etUserName.getText().toString().trim(), etPassword.getText().toString().trim(), etUserCode.getText().toString().trim(), loginCallback);
                }
                break;
        }
    }

    private boolean checkData() {
        if (ToolsKit.isEmpty(etUserName.getText().toString().trim())) {
            mActivity.msg("用户名不能为空");
            etUserName.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(etPassword.getText().toString().trim())) {
            mActivity.msg("密码不能为空");
            etPassword.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(etUserCode.getText().toString().trim())) {
            mActivity.msg("验证码不能为空");
            etUserCode.requestFocus();
            return false;
        }
        return true;
    }

    private BytesCallback authCodeCallBack = new BytesDefaultCallback() {//验证码回调
        @Override
        public void onFailure(Exception e, int code) {
            if (BuildConfig.DEBUG) Log.e(TAG, "onFailure: code = " + code, e);
            e.printStackTrace();
            Toast.makeText(mActivity, "网络访问错误！", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onSuccess(byte[] response) {
            if (BuildConfig.DEBUG) Log.d(TAG, "onSuccess: ========================验证码");
            Bitmap bitmap = BitmapFactory.decodeByteArray(response, 0, response.length);
            ivAuthCode.setImageBitmap(bitmap);
        }
    };

    private DefaultCallback loginCallback = new DefaultCallback() {//登录回调
        @Override
        public void onFailure(Exception e, int code) {
            if (BuildConfig.DEBUG) Log.e(TAG, "onFailure: code = " + code, e);
            e.printStackTrace();
            Toast.makeText(mActivity, "网络访问错误！", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onSuccess(String response) {
            if (BuildConfig.DEBUG) Log.d(TAG, "onSuccess: " + response);
            try {
                Type baseType = new TypeToken<BaseEntity>() {
                }.getType();
                BaseEntity baseEntity = ToolsJson.parseObjecta(response, baseType);
                int errorCode = baseEntity.getErrorCode();
                if (errorCode > 0) {//登录错误
                    mActivity.msg(baseEntity.getMsg());
                    return;
                }
                //登录成功
                String jsonStr = ToolsJson.toJson(baseEntity.getData());
                Type userType = new TypeToken<UserLoginEntity>() {
                }.getType();
                UserLoginEntity userLogin = ToolsJson.parseObjecta(jsonStr, userType);
                mActivity.getLoginUserSharedPre().edit()
                        .putString(Constant.USER, jsonStr)//保存用户实体
                        .putString(Constant.USERID, userLogin.getUid())//保存用户ID
                        .putString(Constant.USERNAME, userLogin.getUserName())//保存用户名
                        .putString(Constant.USERPHONE, userLogin.getTel())//保存用户联系电话
                        .apply();
                Intent intent = new Intent();
                intent.setClass(mActivity, MainActivity.class);
                startActivity(intent);
                mActivity.onBackPressed();
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                mActivity.msg("登录失败，请稍后重试");
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        //刷新验证码，以防失效
        authCodeCall = Requester.authCode(authCodeCallBack);//请求服务器更换验证码
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (loginCall != null && !loginCall.isCanceled()) {
            loginCall.cancel();
        }
        if (authCodeCall != null && !authCodeCall.isCanceled()) {
            authCodeCall.cancel();
        }
        ButterKnife.reset(this);
    }
}
