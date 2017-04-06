package com.henghao.parkland.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.TextView;

import com.benefit.buy.library.utils.tools.ToolsKit;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.R;
import com.henghao.parkland.adapter.SiftImagePagerAdapter;
import com.henghao.parkland.views.ViewPagerFixed;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;


/**
 * 显示图片
 *
 * @author zhangxianwen
 * @version HDMNV100R001, 2017年102月28日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SiftImagesActivity extends ActivityFragmentSupport {

    @ViewInject(R.id.image_pager)
    private ViewPagerFixed mViewpager;//Viewpager

    @ViewInject(R.id.page_tv)
    private TextView pageTv; //当前页

    @ViewInject(R.id.page_tv_all)
    private TextView pageTvAll;//总页数

    private int pageindex;

    private int page = 0;

    private ArrayList<String> imagesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.common_view_pager);
        this.mActivityFragmentView.viewEmpty(R.layout.activity_empty);
        this.mActivityFragmentView.viewEmptyGone();
        this.mActivityFragmentView.viewLoading(View.GONE);
        this.mActivityFragmentView.clipToPadding(true);
        setContentView(this.mActivityFragmentView);
        com.lidroid.xutils.ViewUtils.inject(this);
        initWidget();
        initData();
    }

    @Override
    public void initData() {
        super.initData();
        if (this.imagesList != null) {
            SiftImagePagerAdapter madapter = new SiftImagePagerAdapter(this, this.imagesList);
            this.mViewpager.setAdapter(madapter);
        }
        this.pageTvAll.setText("" + this.imagesList.size());
        this.mViewpager.setCurrentItem(this.page - 1);
        if (this.page != 0) {
            this.pageTv.setText(this.page + "");
        }
        this.mViewpager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                SiftImagesActivity.this.pageindex = SiftImagesActivity.this.mViewpager.getCurrentItem() + 1;
                SiftImagesActivity.this.pageTv.setText(SiftImagesActivity.this.pageindex + "");
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    @Override
    public void initWidget() {
        this.imagesList = new ArrayList<String>();
        this.imagesList.clear();
        Intent intent = getIntent();
        this.page = intent.getIntExtra("PAGE", 1);
        List<String> list = intent.getStringArrayListExtra("path");
        if (!ToolsKit.isEmpty(list)) {
            this.imagesList.addAll(list);
        }
        /**
         * 导航栏
         */
        this.mActivityFragmentView.getNavitionBarView().setBackgroundColor(getResources().getColor(R.color.black));
        initWithBar();
        //        initWithCenterBar();
        this.mLeftTextView.setVisibility(View.VISIBLE);
        this.mLeftTextView.setText("");
    }
}
