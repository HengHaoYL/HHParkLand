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
import com.henghao.parkland.activity.workshow.SeedlingDetailActivity;
import com.henghao.parkland.model.entity.SeedlingEntity;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 工作台苗木信息展示〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author zhangxianwen
 * @version HDMNV100R001, 2015年12月21日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SeedlingAdapter extends ArrayAdapter<SeedlingEntity> {

    private final LayoutInflater inflater;

    private final BitmapUtils mBitmapUtils;

    private final ActivityFragmentSupport mActivityFragmentSupport;

    public SeedlingAdapter(ActivityFragmentSupport activityFragment, List<SeedlingEntity> mList) {
        super(activityFragment, R.layout.item_seedling, mList);
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
            convertView = this.inflater.inflate(R.layout.item_seedling, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvAddress.setText(getItem(position).getAddress());
        holder.tvContent.setText(getItem(position).getContent());
        holder.tvDates.setText(getItem(position).getDates());
        holder.tvSupplier.setText(getItem(position).getSupplier());
        holder.tvTitleName.setText(getItem(position).getTitleName());
        mBitmapUtils.display(holder.imageview, getItem(position).getFilesId() + getItem(position).getUrl().get(0));
        viewClick(holder, convertView, position);
        return convertView;
    }

    private void viewClick(ViewHolder mHodlerView, View convertView, final int position) {
        final SeedlingEntity mentity = getItem(position);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mActivityFragmentSupport, SeedlingDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.INTNET_DATA, mentity);
                intent.putExtra("bundle", bundle);
                mActivityFragmentSupport.startActivity(intent);
            }
        });
    }

    static class ViewHolder {
        @InjectView(R.id.imageview)
        ImageView imageview;
        @InjectView(R.id.tv_titleName)
        TextView tvTitleName;
        @InjectView(R.id.tv_content)
        TextView tvContent;
        @InjectView(R.id.tv_address)
        TextView tvAddress;
        @InjectView(R.id.tv_supplier)
        TextView tvSupplier;
        @InjectView(R.id.tv_dates)
        TextView tvDates;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
