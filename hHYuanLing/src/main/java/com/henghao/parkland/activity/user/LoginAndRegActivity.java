package com.henghao.parkland.activity.user;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.R;
import com.henghao.parkland.fragment.user.LoginFragment;
import com.henghao.parkland.fragment.user.RegisterFragment;
import com.lidroid.xutils.view.annotation.ViewInject;


public class LoginAndRegActivity extends ActivityFragmentSupport implements OnCheckedChangeListener {

    @ViewInject(R.id.tabs_rg)
    private RadioGroup mTabs;

    private LoginFragment loginFragment;

    private RegisterFragment registerFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mActivityFragmentView.viewMain(R.layout.activity_loginandreg);
        mActivityFragmentView.viewEmpty(R.layout.activity_empty);
        mActivityFragmentView.viewEmptyGone();
        mActivityFragmentView.viewLoading(View.GONE);
        mActivityFragmentView.clipToPadding(true);
        setContentView(mActivityFragmentView);
        com.lidroid.xutils.ViewUtils.inject(this);
        initWidget();
        initData();
    }

    @Override
    public void initWidget() {
        initWithContent();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        loginFragment = (LoginFragment) LoginFragment.newInstance(0);
        registerFragment = (RegisterFragment) RegisterFragment.newInstance(1);
        ft.replace(R.id.fragment_content, loginFragment).commit();
    }

    private void initWithContent() {
        mActivityFragmentView.getNavitionBarView().setBackgroundColor(getResources().getColor(R.color.blue));
        /** 导航栏 */
        initWithCenterBar();
        mCenterTextView.setVisibility(View.VISIBLE);
        mCenterTextView.setText("登录");
        mCenterTextView.setTextColor(getResources().getColor(R.color.white));
        initWithBar();
        mLeftImageView.setVisibility(View.VISIBLE);
        mLeftImageView.setImageResource(R.drawable.btn_blackback);
    }

    @Override
    public void initData() {
        mTabs.setOnCheckedChangeListener(this);
    }

    public void setLoginFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        loginFragment = (LoginFragment) LoginFragment.newInstance(0);
        ft.replace(R.id.fragment_content, loginFragment).commit();
        ((RadioButton) mTabs.getChildAt(0)).setChecked(true);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        // mRadioGroup.check(checkedId);
        // llHeader.check(checkedId);
        for (int i = 0; i < group.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) group.getChildAt(i);
            if (radioButton.isChecked()) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                switch (i) {
                    case 0:
                        loginFragment = (LoginFragment) LoginFragment.newInstance(0);
                        ft.replace(R.id.fragment_content, loginFragment).commit();
                        break;
                    case 1:
                        registerFragment = (RegisterFragment) RegisterFragment.newInstance(1);
                        ft.replace(R.id.fragment_content, registerFragment).commit();
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
