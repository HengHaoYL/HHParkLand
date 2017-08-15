package com.henghao.parkland.activity.user;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RadioGroup;

import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.R;
import com.henghao.parkland.adapter.FragmentTabAdapter;
import com.henghao.parkland.fragment.FragmentSupport;
import com.henghao.parkland.fragment.user.LoginFragment;
import com.henghao.parkland.fragment.user.RegisterFragment;
import com.henghao.parkland.model.entity.HCMenuEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class LoginAndRegActivity extends ActivityFragmentSupport {

    @InjectView(R.id.tabs_rg)
    RadioGroup mTabs;

    public List<FragmentSupport> fragments = new ArrayList<FragmentSupport>();
    private List<HCMenuEntity> menuLists;
    private FragmentTabAdapter tabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (this.tabAdapter != null) {
            this.tabAdapter.remove();
        }
        mActivityFragmentView.viewMain(R.layout.activity_loginandreg);
        mActivityFragmentView.viewEmpty(R.layout.activity_empty);
        mActivityFragmentView.viewEmptyGone();
        mActivityFragmentView.viewLoading(View.GONE);
        mActivityFragmentView.clipToPadding(true);
        setContentView(mActivityFragmentView);
        ButterKnife.inject(this);
        initWidget();
        initData();
    }

    @Override
    public void initWidget() {
        initWithContent();
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
        menuList();
        try {
            // 动态加载tab
            // 动态设置tab item
            for (int i = 0; i < this.menuLists.size(); i++) {
                HCMenuEntity menu = this.menuLists.get(i);
                if (menu.getStatus() == -1) {
                    @SuppressWarnings("unchecked")
                    Class<FragmentSupport> clazz = (Class<FragmentSupport>) Class.forName(menu.getClazz());
                    FragmentSupport fragmentSuper = clazz.newInstance();
                    fragmentSuper.fragmentId = menu.getmId();
                    this.fragments.add(fragmentSuper);
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        this.tabAdapter = new FragmentTabAdapter(this, this.fragments, R.id.fragment_content, this.mTabs);
        this.tabAdapter.setOnRgsExtraCheckedChangedListener(new FragmentTabAdapter.OnRgsExtraCheckedChangedListener() {

            @Override
            public void OnRgsExtraCheckedChanged(RadioGroup radioGroup, int checkedId, int index) {
                System.out.println("Extra---- " + index + " checked!!! ");
            }
        });
    }

    /**
     * 牵扯到的tab items
     */
    public void menuList() {
        this.menuLists = new ArrayList<HCMenuEntity>();
        HCMenuEntity mMenuLogin = new HCMenuEntity(1, getResources().getString(R.string.login),
                R.drawable.selectorgreen_malldetails, LoginFragment.class.getName(), -1);// 登录
        this.menuLists.add(mMenuLogin);
        HCMenuEntity mMenuRegister = new HCMenuEntity(2, getResources().getString(R.string.register_finish),
                R.drawable.selectorgreen_malldetails, RegisterFragment.class.getName(), -1);// 注册
        this.menuLists.add(mMenuRegister);
    }

    public void setLoginFragment() {
        tabAdapter.showTab(0);
        LoginFragment loginFragment = (LoginFragment) fragments.get(0);
        loginFragment.getAuthCode();
    }
}
