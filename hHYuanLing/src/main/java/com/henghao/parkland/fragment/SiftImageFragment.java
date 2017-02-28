/* 
 * 文件名：CasesFragment.java
 * 版权：Copyright 2009-2010 companyName MediaNet. Co. Ltd. All Rights Reserved. 
 * 描述： 
 * 修改人：
 * 修改时间：
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */
package com.henghao.parkland.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.henghao.parkland.Constant;
import com.henghao.parkland.R;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;


/**
 * 查看〈一句话功能简述〉 〈功能详细描述〉
 * @author zhangxianwen
 * @version HDMNV100R001, 2017年2月28日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SiftImageFragment extends FragmentSupport {

    @ViewInject(R.id.case_image)
    private com.benefit.buy.library.photoview.PhotoView mImageView;

    @ViewInject(R.id.case_title)
    private TextView mTilteTextView;

    @ViewInject(R.id.case_des)
    private TextView mDesTextView;

    private String image;

    private BitmapUtils mBitmapUtils;

    public static FragmentSupport newInstance(Object obj) {
        SiftImageFragment fragment = new SiftImageFragment();
        if (fragment.object == null) {
            fragment.object = obj;
            fragment.image = (String) obj;
        }
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.fragment_cases);
        this.mActivityFragmentView.viewEmpty(R.layout.activity_empty);
        this.mActivityFragmentView.viewEmptyGone();
        this.mActivityFragmentView.viewLoading(View.GONE);
        this.mActivityFragmentView.getNavitionBarView().setVisibility(View.GONE);
        ViewUtils.inject(this, this.mActivityFragmentView);
        initWithWidget();
        return this.mActivityFragmentView;
    }

    private void initWithWidget() {
        this.mBitmapUtils = new BitmapUtils(this.mActivity, Constant.CACHE_DIR_PATH);
        this.mBitmapUtils.configDefaultLoadFailedImage(R.drawable.img_loading_fail_big);
        this.mBitmapUtils.configDefaultLoadingImage(R.drawable.img_loading_default_big);
        //        mTilteTextView.setLines(3);
        //        mTilteTextView.setText(mCaseDetailEntity.getDes());
        this.mTilteTextView.setVisibility(View.GONE);
        this.mDesTextView.setVisibility(View.GONE);
        //        mDesTextView.setText(mCaseDetailEntity.getDes());
        this.mBitmapUtils.display(this.mImageView, this.image);
    }

}
