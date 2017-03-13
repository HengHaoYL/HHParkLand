package com.henghao.parkland.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
import com.henghao.parkland.R;
import com.henghao.parkland.activity.ProjectHSResultDesActivity;
import com.henghao.parkland.model.entity.ProjectHSResultEntity;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 会审结果〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author zhangxianwen
 * @version HDMNV100R001, 2015年12月21日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ProjectHSResultAdapter extends ArrayAdapter<ProjectHSResultEntity> {

    private final LayoutInflater inflater;

    private final BitmapUtils mBitmapUtils;

    private final ActivityFragmentSupport mActivityFragmentSupport;

    public ProjectHSResultAdapter(ActivityFragmentSupport activityFragment, List<ProjectHSResultEntity> mList) {
        super(activityFragment, R.layout.list_item_hsresult, mList);
        this.mActivityFragmentSupport = activityFragment;
        this.inflater = LayoutInflater.from(activityFragment);
        this.mBitmapUtils = new BitmapUtils(activityFragment, Constant.CACHE_DIR_PATH);
        this.mBitmapUtils.configDefaultLoadFailedImage(R.drawable.img_loading_fail_big);
        this.mBitmapUtils.configDefaultLoadingImage(R.drawable.img_loading_default_big);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_hsresult, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvDates.setText(getItem(position).getHsDeparment());
        holder.tvName.setText(getItem(position).getName());
        mBitmapUtils.display(holder.imageView, getItem(position).getHsImgId() + getItem(position).getUrl().get(0));
        viewClick(holder, convertView, position);
        return convertView;
    }

    private void viewClick(ProjectHSResultAdapter.ViewHolder mHodlerView, View convertView, final int position) {
        final ProjectHSResultEntity mentity = getItem(position);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mActivityFragmentSupport, ProjectHSResultDesActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.INTNET_DATA, mentity);
                intent.putExtra("bundle", bundle);
                mActivityFragmentSupport.startActivity(intent);
            }
        });
    }

    static class ViewHolder {
        @InjectView(R.id.tv_dates)
        TextView tvDates;
        @InjectView(R.id.tv_name)
        TextView tvName;
        @InjectView(R.id.iv_Img)
        ImageView imageView;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
