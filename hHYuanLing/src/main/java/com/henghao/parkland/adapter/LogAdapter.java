package com.henghao.parkland.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.henghao.parkland.R;
import com.henghao.parkland.model.entity.LogEntity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 晏琦云 on 2017/2/22.
 */

public class LogAdapter extends BaseAdapter {
    private Context context;
    private List<LogEntity> list;
    private LayoutInflater mInflater;

    public LogAdapter(Context context, List<LogEntity> list) {
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
            convertView = mInflater.inflate(R.layout.list_item_log, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        LogEntity entity = list.get(position);
        holder.tvComments.setText(entity.getComments());
        holder.tvProjectDetail.setText(entity.getProjectDetail());
        holder.tvPredictfinishTime.setText(entity.getPredictfinishTime());
        holder.tvProjectName.setText(entity.getProjectName());
        holder.tvProjectPlan.setText(entity.getProjectPlan());
        holder.tvProjectTime.setText(entity.getProjrectTime());
        holder.tvReason.setText(entity.getReason());
        holder.tvRequests.setText(entity.getRequests());
        holder.tvSend.setText(entity.getSend());
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.tv_projectName)
        TextView tvProjectName;
        @InjectView(R.id.tv_projectDetail)
        TextView tvProjectDetail;
        @InjectView(R.id.tv_projectTime)
        TextView tvProjectTime;
        @InjectView(R.id.tv_projectPlan)
        TextView tvProjectPlan;
        @InjectView(R.id.tv_reason)
        TextView tvReason;
        @InjectView(R.id.tv_requests)
        TextView tvRequests;
        @InjectView(R.id.tv_predictfinishTime)
        TextView tvPredictfinishTime;
        @InjectView(R.id.tv_send)
        TextView tvSend;
        @InjectView(R.id.tv_comments)
        TextView tvComments;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
