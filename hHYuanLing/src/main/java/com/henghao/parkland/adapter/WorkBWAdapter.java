package com.henghao.parkland.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.henghao.parkland.R;
import com.henghao.parkland.model.entity.WorkBWEntity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 晏琦云 on 2017/2/22.
 */

public class WorkBWAdapter extends BaseAdapter {
    private Context context;
    private List<WorkBWEntity> list;
    private LayoutInflater mInflater;

    public WorkBWAdapter(Context context, List<WorkBWEntity> list) {
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
            convertView = mInflater.inflate(R.layout.list_item_workbw, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        WorkBWEntity entity = list.get(position);
        holder.tvStartTime.setText(entity.getStart_time());
        holder.tvEndTime.setText(entity.getEnd_time());
        holder.tvWorkContent.setText(entity.getWork_content());
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.tv_starttime)
        TextView tvStartTime;
        @InjectView(R.id.tv_endtime)
        TextView tvEndTime;
        @InjectView(R.id.tv_workcontent)
        TextView tvWorkContent;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
