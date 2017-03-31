package com.henghao.parkland.fragment.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.benefit.buy.library.http.query.callback.AjaxStatus;
import com.benefit.buy.library.utils.tools.ToolsKit;
import com.henghao.parkland.Constant;
import com.henghao.parkland.ProtocolUrl;
import com.henghao.parkland.R;
import com.henghao.parkland.activity.MainActivity;
import com.henghao.parkland.fragment.FragmentSupport;
import com.henghao.parkland.model.entity.BaseEntity;
import com.henghao.parkland.model.entity.UserLoginEntity;
import com.henghao.parkland.model.protocol.LoginProtocol;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONException;

/**
 * 我的登录〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author zhangxianwen
 * @version HDMNV100R001, 2016年8月15日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class LoginFragment extends FragmentSupport {

    @ViewInject(R.id.login_pass_quick)
    private TextView login_pass_quick;

    @ViewInject(R.id.login_user)
    private EditText login_user;

    @ViewInject(R.id.login_pass)
    private EditText login_pass;

    @ViewInject(R.id.iv_eye_login)
    private ImageView iv_eye_login;

    private boolean passwordFlag = true; // 隐藏密码的标志 true 隐藏 false 显示

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
        mLeftImageView.setImageDrawable(getResources().getDrawable(R.drawable.btn_blackback));
        mCenterTextView = (TextView) getActivity().findViewById(R.id.bar_center_title);
        mCenterTextView.setText("登录");
    }

    public void initWidget() {
    }


    @OnClick({R.id.tv_login, R.id.iv_eye_login})
    public void viewClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.tv_login:
                //登录
                if (checkData()) {
                    LoginProtocol mLoginProtocol = new LoginProtocol(mActivity);
                    mLoginProtocol.addResponseListener(this);
                    mLoginProtocol.login(login_user.getText().toString().trim(), login_pass.getText().toString().trim(), String.valueOf(1));
                    mActivityFragmentView.viewLoading(View.VISIBLE);
                }
                break;
            case R.id.iv_eye_login:
                if (passwordFlag) {
                    login_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    passwordFlag = false;
                } else {
                    login_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    passwordFlag = true;
                }
                break;
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

    @Override
    public void OnMessageResponse(String url, Object jo, AjaxStatus status) throws JSONException {
        super.OnMessageResponse(url, jo, status);
        if (url.endsWith(ProtocolUrl.APP_LOGIN)) {
            if (jo instanceof BaseEntity) {
                BaseEntity base = (BaseEntity) jo;
                mActivity.msg(base.getMsg());
                return;
            }
            UserLoginEntity userLogin = (UserLoginEntity) jo;
            mActivity.getLoginUserSharedPre().edit().putString(Constant.USERID, userLogin.getUid()).putString(Constant.USERNAME, userLogin.getUsername()).putString(Constant.USERPHONE, userLogin.getTel()).commit();
            Intent intent = new Intent();
            intent.setClass(mActivity, MainActivity.class);
            startActivity(intent);
            mActivity.onBackPressed();
        }
    }

}
