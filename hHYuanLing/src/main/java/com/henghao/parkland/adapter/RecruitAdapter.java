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
import com.henghao.parkland.activity.workshow.RecruitDetailActivity;
import com.henghao.parkland.model.entity.RecruitEntity;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 工作台人员招聘展示〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author zhangxianwen
 * @version HDMNV100R001, 2015年12月21日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class RecruitAdapter extends ArrayAdapter<RecruitEntity> {

    private final LayoutInflater inflater;

    private final BitmapUtils mBitmapUtils;

    private final ActivityFragmentSupport mActivityFragmentSupport;

    public RecruitAdapter(ActivityFragmentSupport activityFragment, List<RecruitEntity> mList) {
        super(activityFragment, R.layout.item_recruit, mList);
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
            convertView = this.inflater.inflate(R.layout.item_recruit, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvPosition.setText(getItem(position).getPosition());
        holder.tvWorkingAdress.setText(getItem(position).getWorkingAdress());
        holder.tvDates.setText(getItem(position).getDates());
        holder.tvTel.setText(getItem(position).getTel());
        holder.tvContact.setText(getItem(position).getContact());
        holder.tvCompanyName.setText(getItem(position).getCompanyName());
        viewClick(holder, convertView, position);
        return convertView;
    }

    private void viewClick(ViewHolder mHodlerView, View convertView, final int position) {
        final RecruitEntity mentity = getItem(position);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mActivityFragmentSupport, RecruitDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.INTNET_DATA, mentity);
                intent.putExtra("bundle", bundle);
                mActivityFragmentSupport.startActivity(intent);
            }
        });
    }

    static class ViewHolder {
        @InjectView(R.id.tv_position)
        TextView tvPosition;
        @InjectView(R.id.tv_companyName)
        TextView tvCompanyName;
        @InjectView(R.id.tv_workingAdress)
        TextView tvWorkingAdress;
        @InjectView(R.id.tv_contact)
        TextView tvContact;
        @InjectView(R.id.tv_tel)
        TextView tvTel;
        @InjectView(R.id.tv_dates)
        TextView tvDates;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
