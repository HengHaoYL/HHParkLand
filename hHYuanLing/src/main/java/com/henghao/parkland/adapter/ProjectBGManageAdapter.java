package com.henghao.parkland.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
import com.henghao.parkland.R;
import com.henghao.parkland.activity.projectmanage.ProjectBGManageDesActivity;
import com.henghao.parkland.callback.MyCallBack;
import com.henghao.parkland.model.entity.ProjectBGManageEntity;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 项目信息〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author zhangxianwen
 * @version HDMNV100R001, 2015年12月21日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ProjectBGManageAdapter extends ArrayAdapter<ProjectBGManageEntity> {

    private final LayoutInflater inflater;

    private final BitmapUtils mBitmapUtils;

    private final ActivityFragmentSupport mActivityFragmentSupport;

    private boolean showCheckBox = false;//true 多选框显示 false 多选框不显示

    private MyCallBack callBack;

    public ProjectBGManageAdapter(ActivityFragmentSupport activityFragment, List<ProjectBGManageEntity> mList, MyCallBack callBack) {
        super(activityFragment, R.layout.item_projectmanager, mList);
        this.mActivityFragmentSupport = activityFragment;
        this.inflater = LayoutInflater.from(activityFragment);
        this.mBitmapUtils = new BitmapUtils(activityFragment, Constant.CACHE_DIR_PATH);
        this.mBitmapUtils.configDefaultLoadFailedImage(R.drawable.img_loading_fail_big);
        this.mBitmapUtils.configDefaultLoadingImage(R.drawable.img_loading_default_big);
        this.callBack = callBack;
    }

    /**
     * 显示多选框
     */
    public void showCheckBox() {
        showCheckBox = true;
    }

    /**
     * 取消显示多选框
     */
    public void hideCheckBox() {
        showCheckBox = false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ProjectBGManageEntity entity = getItem(position);
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = this.inflater.inflate(R.layout.item_projectmanager, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        /**
         * 显示多选框
         */
        if (showCheckBox) {
            holder.checkBox.setVisibility(View.VISIBLE);
        } else {
            holder.checkBox.setVisibility(View.GONE);
        }
        holder.checkBox.setChecked(entity.isChecked());
        holder.tvTime.setText(entity.getTimes());
        holder.tvName.setText(entity.getName());
        holder.tvTitle.setText(entity.getConfirmingParty());
        viewClick(holder, convertView, position);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (entity.isChecked()) {
                    entity.setChecked(false);
                    callBack.removeId(entity.getAid());
                } else {
                    entity.setChecked(true);
                    callBack.addId(entity.getAid());
                }
                callBack.setChecked();
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    private void viewClick(ViewHolder mHodlerView, View convertView, final int position) {
        final ProjectBGManageEntity entity = getItem(position);
        if (showCheckBox) {
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /**
                     * 如果多选框选中，则不选，否则选中
                     */
                    if (entity.isChecked()) {
                        entity.setChecked(false);
                        callBack.removeId(entity.getAid());
                    } else {
                        entity.setChecked(true);
                        callBack.addId(entity.getAid());
                    }
                    callBack.setChecked();
                    notifyDataSetChanged();
                }
            });
        } else {
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(mActivityFragmentSupport, ProjectBGManageDesActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constant.INTNET_DATA, entity);
                    intent.putExtra("bundle", bundle);
                    mActivityFragmentSupport.startActivity(intent);
                }
            });
        }
    }

    static class ViewHolder {
        @InjectView(R.id.checkBox)
        CheckBox checkBox;
        @InjectView(R.id.tv_name)
        TextView tvName;
        @InjectView(R.id.tv_title)
        TextView tvTitle;
        @InjectView(R.id.tv_time)
        TextView tvTime;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
