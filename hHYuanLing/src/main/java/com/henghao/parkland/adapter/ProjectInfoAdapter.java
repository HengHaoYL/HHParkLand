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
import com.henghao.parkland.activity.projectmanage.ProjectInfoDesActivity;
import com.henghao.parkland.callback.MyCallBack;
import com.henghao.parkland.model.entity.ProjectInfoEntity;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * 项目信息〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author zhangxianwen
 * @version HDMNV100R001, 2015年12月21日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ProjectInfoAdapter extends ArrayAdapter<ProjectInfoEntity> {

    private final LayoutInflater inflater;

    private final BitmapUtils mBitmapUtils;

    private final ActivityFragmentSupport mActivityFragmentSupport;

    private boolean showCheckBox = false;//true 多选框显示 false 多选框不显示

    private MyCallBack callBack;

    public ProjectInfoAdapter(ActivityFragmentSupport activityFragment, List<ProjectInfoEntity> mList, MyCallBack callBack) {
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
        final ProjectInfoEntity entity = getItem(position);
        HodlerView mHodlerView = null;
        if (convertView == null) {
            mHodlerView = new HodlerView();
            convertView = this.inflater.inflate(R.layout.item_projectmanager, null);
            mHodlerView.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            mHodlerView.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            mHodlerView.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            mHodlerView.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
            convertView.setTag(mHodlerView);
        } else {
            mHodlerView = (HodlerView) convertView.getTag();
        }
        /**
         * 显示多选框
         */
        if (showCheckBox) {
            mHodlerView.checkBox.setVisibility(View.VISIBLE);
        } else {
            mHodlerView.checkBox.setVisibility(View.GONE);
        }
        mHodlerView.checkBox.setChecked(entity.isChecked());
        mHodlerView.tv_title.setText(entity.getConstructionUnit());
        mHodlerView.tv_name.setText(entity.getXmName());
        mHodlerView.tv_time.setText(entity.getXmPerson());
        viewClick(mHodlerView, convertView, position);
        mHodlerView.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (entity.isChecked()) {
                    entity.setChecked(false);
                    callBack.removeId(entity.getPid());
                } else {
                    entity.setChecked(true);
                    callBack.addId(entity.getPid());
                }
                callBack.setChecked();
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    private void viewClick(final HodlerView mHodlerView, View convertView, final int position) {
        final ProjectInfoEntity entity = getItem(position);
        if (showCheckBox) {
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /**
                     * 如果多选框选中，则不选，否则选中
                     */
                    if (entity.isChecked()) {
                        entity.setChecked(false);
                        callBack.removeId(entity.getPid());
                    } else {
                        entity.setChecked(true);
                        callBack.addId(entity.getPid());
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
                    intent.setClass(mActivityFragmentSupport, ProjectInfoDesActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constant.INTNET_DATA, entity);
                    intent.putExtra("bundle", bundle);
                    mActivityFragmentSupport.startActivity(intent);
                }
            });
        }
    }

    private class HodlerView {

        TextView tv_title;

        TextView tv_name;

        TextView tv_time;

        CheckBox checkBox;
    }
}
