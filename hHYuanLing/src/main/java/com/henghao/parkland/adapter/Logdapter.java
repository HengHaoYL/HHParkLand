package com.henghao.parkland.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.henghao.parkland.R;
import com.henghao.parkland.model.entity.SGBWEntity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 晏琦云 on 2017/2/22.
 */

public class Logdapter extends BaseAdapter {
    private Context context;
    private List<SGBWEntity> list;
    private LayoutInflater mInflater;

    public Logdapter(Context context, List<SGBWEntity> list) {
        this.context = context;
        this.list = list;
        mInflater = LayoutInflater.from(context);
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
            convertView = mInflater.inflate(R.layout.list_item_sgbw, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        SGBWEntity entity = list.get(position);
        holder.tvProjectname.setText(entity.getProject_name());
        holder.tvWorkcontent.setText(entity.getWork_content());
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.tv_projectname)
        TextView tvProjectname;
        @InjectView(R.id.tv_workcontent)
        TextView tvWorkcontent;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
