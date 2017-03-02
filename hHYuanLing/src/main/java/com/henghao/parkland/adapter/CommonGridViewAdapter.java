package com.henghao.parkland.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
import com.henghao.parkland.R;
import com.henghao.parkland.utils.ScanImageUtils;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * GridView显示图片〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author zhangxianwen
 * @version HDMNV100R001, 2015年12月21日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class CommonGridViewAdapter extends ArrayAdapter<String> {

    private final LayoutInflater inflater;

    private final BitmapUtils mBitmapUtils;

    private final ActivityFragmentSupport mActivityFragmentSupport;

    private String urlPath;

    public CommonGridViewAdapter(ActivityFragmentSupport activityFragment, List<String> mList, String path) {
        super(activityFragment, R.layout.common_imageview, mList);
        this.mActivityFragmentSupport = activityFragment;
        urlPath = path;
        this.inflater = LayoutInflater.from(activityFragment);
        this.mBitmapUtils = new BitmapUtils(activityFragment, Constant.CACHE_DIR_PATH);
        this.mBitmapUtils.configDefaultLoadFailedImage(R.drawable.img_loading_fail_big);
        this.mBitmapUtils.configDefaultLoadingImage(R.drawable.img_loading_default_big);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        HodlerView mHodlerView = null;
        if (convertView == null) {
            mHodlerView = new HodlerView();
            convertView = this.inflater.inflate(R.layout.common_imageview, null);
            mHodlerView.imghorzen = (ImageView) convertView.findViewById(R.id.imghorzen);
            convertView.setTag(mHodlerView);
        } else {
            mHodlerView = (HodlerView) convertView.getTag();
        }
        mBitmapUtils.display(mHodlerView.imghorzen, urlPath + getItem(position));
        final ArrayList<String> imgUrls = new ArrayList<>();
        imgUrls.add(urlPath + getItem(position));
        mHodlerView.imghorzen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScanImageUtils.ScanImage(mActivityFragmentSupport, imgUrls, position);
            }
        });
        return convertView;
    }

    private class HodlerView {

        ImageView imghorzen;
    }
}
