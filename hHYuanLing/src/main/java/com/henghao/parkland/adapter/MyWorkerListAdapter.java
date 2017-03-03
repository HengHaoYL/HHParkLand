package com.henghao.parkland.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
import com.henghao.parkland.R;
import com.henghao.parkland.model.entity.MyWorkerEntity;
import com.henghao.parkland.views.ExpandableTextView;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * 我的工作轨迹〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author zhangxianwen
 * @version HDMNV100R001, 2015年12月21日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class MyWorkerListAdapter extends ArrayAdapter<MyWorkerEntity> {

    private final LayoutInflater inflater;

    private final BitmapUtils mBitmapUtils;

    private final ActivityFragmentSupport mActivityFragmentSupport;


    public MyWorkerListAdapter(ActivityFragmentSupport activityFragment, List<MyWorkerEntity> mList) {
        super(activityFragment, R.layout.item_my_worker, mList);
        this.mActivityFragmentSupport = activityFragment;
        this.inflater = LayoutInflater.from(activityFragment);
        this.mBitmapUtils = new BitmapUtils(activityFragment, Constant.CACHE_DIR_PATH);
        this.mBitmapUtils.configDefaultLoadFailedImage(R.drawable.img_loading_fail_big);
        this.mBitmapUtils.configDefaultLoadingImage(R.drawable.img_loading_default_big);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HodlerView mHodlerView = null;
        if (convertView == null) {
            mHodlerView = new HodlerView();
            convertView = this.inflater.inflate(R.layout.item_my_worker, null);
            mHodlerView.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            mHodlerView.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            mHodlerView.tv_userName = (TextView) convertView.findViewById(R.id.tv_userName);
            mHodlerView.tv_data = (ExpandableTextView) convertView.findViewById(R.id.expandableTextView);
            convertView.setTag(mHodlerView);
        } else {
            mHodlerView = (HodlerView) convertView.getTag();
        }
        final MyWorkerEntity mEntity = getItem(position);
        mHodlerView.tv_title.setText(mEntity.getWorkType());
        mHodlerView.tv_time.setText(mEntity.getDates());
        mHodlerView.tv_data.setText(mEntity.getDetails());
        mHodlerView.tv_userName.setText(mEntity.getPersonnel());
       if( mHodlerView.tv_data.getLineCount()<=2){
           mHodlerView.tv_data.setCompoundDrawables(null,null,null,null);
       }else {
           mHodlerView.tv_data.setCompoundDrawables(null,null,mActivityFragmentSupport.getResources().getDrawable(R.drawable.arrow_down_),null);
       }
       viewOnClick(mHodlerView,convertView,position);
        return convertView;
    }

    private void viewOnClick(final HodlerView mHodlerView, View convertView, int position) {
        mHodlerView.tv_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mHodlerView.tv_data.getExpandableStatus()){
                    mHodlerView.tv_data.setExpandable(false);
                }else {
                    mHodlerView.tv_data.setExpandable(true);
                }
                notifyDataSetChanged();
            }
        });
    }


    private class HodlerView {

        TextView tv_title;

        TextView tv_time;

        TextView tv_userName;

        ExpandableTextView tv_data;
    }
}
