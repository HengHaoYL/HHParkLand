package com.henghao.parkland.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
import com.henghao.parkland.R;
import com.henghao.parkland.activity.ProjectSpvLogDesActivity;
import com.henghao.parkland.model.entity.ProjectSpvLogEntity;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * 监理日志〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author zhangxianwen
 * @version HDMNV100R001, 2015年12月21日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ProjectSpvLogAdapter extends ArrayAdapter<ProjectSpvLogEntity> {

    private final LayoutInflater inflater;

    private final BitmapUtils mBitmapUtils;

    private final ActivityFragmentSupport mActivityFragmentSupport;

    public ProjectSpvLogAdapter(ActivityFragmentSupport activityFragment, List<ProjectSpvLogEntity> mList) {
        super(activityFragment, R.layout.item_projectmanager, mList);
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
            convertView = this.inflater.inflate(R.layout.item_projectmanager, null);
            mHodlerView.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            mHodlerView.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            mHodlerView.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(mHodlerView);
        } else {
            mHodlerView = (HodlerView) convertView.getTag();
        }
        mHodlerView.tv_name.setText(getItem(position).getProjectName());
        mHodlerView.tv_title.setText(getItem(position).getSupervisionPosition());
        mHodlerView.tv_time.setText(getItem(position).getDates());
        viewClick(mHodlerView, convertView, position);
        return convertView;
    }

    private void viewClick(ProjectSpvLogAdapter.HodlerView mHodlerView, View convertView, final int position) {
        final ProjectSpvLogEntity mentity = getItem(position);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mActivityFragmentSupport, ProjectSpvLogDesActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.INTNET_DATA, mentity);
                intent.putExtra("bundle", bundle);
                mActivityFragmentSupport.startActivity(intent);
            }
        });
    }

    private class HodlerView {

        TextView tv_name;

        TextView tv_title;

        TextView tv_time;
    }
}
