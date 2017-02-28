/* 
 * 文件名：CasesFragmentPagerAdapter.java
 * 版权：Copyright 2009-2010 companyName MediaNet. Co. Ltd. All Rights Reserved. 
 * 描述： 
 * 修改人：
 * 修改时间：
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */
package com.henghao.parkland.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.fragment.FragmentSupport;
import com.henghao.parkland.fragment.SiftImageFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 显示图片适配器
 * 〈一句话功能简述〉 〈功能详细描述〉
 * @author zhangxianwen
 * @version HDMNV100R001, 2017年2月28日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SiftImagePagerAdapter extends FragmentPagerAdapter {

    private List<FragmentSupport> mFragments = new ArrayList<FragmentSupport>();

    private FragmentManager mFragmentManager;

    private ActivityFragmentSupport mFragmentActivity;

    public SiftImagePagerAdapter(ActivityFragmentSupport activity, List<String> images) {
        super(activity.getSupportFragmentManager());
        mFragmentActivity = activity;
        mFragmentManager = activity.getSupportFragmentManager();
        for (String image: images) {
            initData(mFragmentActivity, image);
        }
    }

    private void initData(FragmentActivity activity, String image) {
        mFragments.add(SiftImageFragment.newInstance(image));
    }

    public List<FragmentSupport> getFragments() {
        return mFragments;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public FragmentSupport getItem(int position) {
        return ((mFragments == null) || (mFragments.size() == 0)) ? null : mFragments.get(position);
    }

    public void setFragments() {
        if (mFragments != null) {
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            for (FragmentSupport f: mFragments) {
                ft.remove(f);
            }
            ft.commit();
            ft = null;
            mFragmentManager.executePendingTransactions();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
