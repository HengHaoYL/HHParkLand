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
import com.henghao.parkland.activity.projectmanage.ProjectSGSafeLogDesActivity;
import com.henghao.parkland.model.entity.ProjectSGSafeLogEntity;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 施工安全日志〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author zhangxianwen
 * @version HDMNV100R001, 2015年12月21日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ProjectSGSafeLogAdapter extends ArrayAdapter<ProjectSGSafeLogEntity> {

    private final LayoutInflater inflater;

    private final BitmapUtils mBitmapUtils;

    private final ActivityFragmentSupport mActivityFragmentSupport;

    public ProjectSGSafeLogAdapter(ActivityFragmentSupport activityFragment, List<ProjectSGSafeLogEntity> mList) {
        super(activityFragment, R.layout.item_projectsafelog, mList);
        this.mActivityFragmentSupport = activityFragment;
        this.inflater = LayoutInflater.from(activityFragment);
        this.mBitmapUtils = new BitmapUtils(activityFragment, Constant.CACHE_DIR_PATH);
        this.mBitmapUtils.configDefaultLoadFailedImage(R.drawable.img_loading_fail_big);
        this.mBitmapUtils.configDefaultLoadingImage(R.drawable.img_loading_default_big);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHodlerView = null;
        if (convertView == null) {
            convertView = this.inflater.inflate(R.layout.item_projectsafelog, null);
            mHodlerView = new ViewHolder(convertView);
            convertView.setTag(mHodlerView);
        } else {
            mHodlerView = (ViewHolder) convertView.getTag();
        }
        mHodlerView.tvName.setText(getItem(position).getName());
        mHodlerView.tvConstructionUnit.setText(getItem(position).getConstructionUnit());
        mHodlerView.tvStartTime.setText(getItem(position).getStartTime());
        mHodlerView.tvCompletionTime.setText(getItem(position).getCompletionTime());
        viewClick(mHodlerView, convertView, position);
        return convertView;
    }

    private void viewClick(ViewHolder mHodlerView, View convertView, final int position) {
        final ProjectSGSafeLogEntity mentity = getItem(position);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mActivityFragmentSupport, ProjectSGSafeLogDesActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.INTNET_DATA, mentity);
                intent.putExtra("bundle", bundle);
                mActivityFragmentSupport.startActivity(intent);
            }
        });
    }

    static class ViewHolder {
        @InjectView(R.id.tv_name)
        TextView tvName;
        @InjectView(R.id.tv_constructionUnit)
        TextView tvConstructionUnit;
        @InjectView(R.id.tv_startTime)
        TextView tvStartTime;
        @InjectView(R.id.tv_completionTime)
        TextView tvCompletionTime;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
