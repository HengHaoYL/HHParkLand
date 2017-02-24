package com.henghao.parkland.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.henghao.parkland.Constant;
import com.henghao.parkland.R;
import com.henghao.parkland.model.entity.SGMaterialEntity;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 晏琦云 on 2017/2/22.
 */

public class SGMaterialAdapter extends BaseAdapter {
    private Context context;
    private List<SGMaterialEntity> list;
    private LayoutInflater mInflater;
    private final BitmapUtils mBitmapUtils;

    public SGMaterialAdapter(Context context, List<SGMaterialEntity> list) {
        this.context = context;
        this.list = list;
        mInflater = LayoutInflater.from(context);
        this.mBitmapUtils = new BitmapUtils(context, Constant.CACHE_DIR_PATH);
        this.mBitmapUtils.configDefaultLoadFailedImage(R.drawable.img_loading_fail_big);
        this.mBitmapUtils.configDefaultLoadingImage(R.drawable.img_loading_default_big);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_sgmaterial, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        SGMaterialEntity entity = list.get(position);
        holder.tvWorkContent.setText(entity.getContent());
        mBitmapUtils.display(holder.ivWorkImg, entity.getWorkImg());
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.iv_workimg)
        ImageView ivWorkImg;
        @InjectView(R.id.tv_workcontent)
        TextView tvWorkContent;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
