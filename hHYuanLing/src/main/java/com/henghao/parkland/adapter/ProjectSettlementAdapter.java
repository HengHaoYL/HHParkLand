package com.henghao.parkland.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
import com.henghao.parkland.R;
import com.henghao.parkland.activity.projectmanage.ProjectSettlementDesActivity;
import com.henghao.parkland.callback.MyCallBack;
import com.henghao.parkland.model.entity.ProjectSettlementEntity;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 项目结算〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author zhangxianwen
 * @version HDMNV100R001, 2015年12月21日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ProjectSettlementAdapter extends ArrayAdapter<ProjectSettlementEntity> {

    private final LayoutInflater inflater;

    private final BitmapUtils mBitmapUtils;

    private final ActivityFragmentSupport mActivityFragmentSupport;

    private boolean showCheckBox = false;//true 多选框显示 false 多选框不显示

    private MyCallBack callBack;

    public ProjectSettlementAdapter(ActivityFragmentSupport activityFragment, List<ProjectSettlementEntity> mList, MyCallBack callBack) {
        super(activityFragment, R.layout.list_item_hsresult, mList);
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
        ViewHolder mHodlerView = null;
        final ProjectSettlementEntity entity = getItem(position);
        if (convertView == null) {
            convertView = this.inflater.inflate(R.layout.list_item_hsresult, null);
            mHodlerView = new ViewHolder(convertView);
            convertView.setTag(mHodlerView);
        } else {
            mHodlerView = (ViewHolder) convertView.getTag();
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
        mHodlerView.tvName.setText(entity.getName());
        mHodlerView.tvDates.setText(entity.getDates());
        mBitmapUtils.display(mHodlerView.ivImg, entity.getSettlementBookId() + getItem(position).getUrl().get(0));
        viewClick(mHodlerView, convertView, position);
        mHodlerView.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (entity.isChecked()) {
                    entity.setChecked(false);
                    callBack.removeId(entity.getSid());
                } else {
                    entity.setChecked(true);
                    callBack.addId(entity.getSid());
                }
                callBack.setChecked();
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    private void viewClick(ViewHolder mHodlerView, View convertView, final int position) {
        final ProjectSettlementEntity entity = getItem(position);
        if (showCheckBox) {
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /**
                     * 如果多选框选中，则不选，否则选中
                     */
                    if (entity.isChecked()) {
                        entity.setChecked(false);
                        callBack.removeId(entity.getSid());
                    } else {
                        entity.setChecked(true);
                        callBack.addId(entity.getSid());
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
                    intent.setClass(mActivityFragmentSupport, ProjectSettlementDesActivity.class);
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
        @InjectView(R.id.tv_dates)
        TextView tvDates;
        @InjectView(R.id.iv_Img)
        ImageView ivImg;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
