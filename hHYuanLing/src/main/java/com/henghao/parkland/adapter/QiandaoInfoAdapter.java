package com.henghao.parkland.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
import com.henghao.parkland.R;
import com.henghao.parkland.activity.user.QiandaoDetailActivity;
import com.henghao.parkland.model.entity.QiandaoInfoEntity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by ASUS on 2017/8/16.
 * 签到情况
 */

public class QiandaoInfoAdapter extends BaseAdapter {

    private ActivityFragmentSupport mActivityFragmentSupport;
    private LayoutInflater inflater;
    private List<QiandaoInfoEntity> mList;

    public QiandaoInfoAdapter(ActivityFragmentSupport fragmentSupport, List<QiandaoInfoEntity> list) {
        this.mActivityFragmentSupport = fragmentSupport;
        this.mList = list;
        inflater = LayoutInflater.from(mActivityFragmentSupport);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_qiandao_info, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        QiandaoInfoEntity entity = mList.get(position);
        holder.tvAddress.setText(entity.getAddress());
        holder.tvName.setText(entity.getName());
        holder.tvTime.setText(entity.getTime());
        viewClick(holder, convertView, position);
        return convertView;
    }

    private void viewClick(ViewHolder mHodlerView, View convertView, final int position) {
        final QiandaoInfoEntity mentity = mList.get(position);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mActivityFragmentSupport, QiandaoDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.INTNET_DATA, mentity);
                intent.putExtra("bundle", bundle);
                mActivityFragmentSupport.startActivity(intent);
            }
        });
    }

    static class ViewHolder {
        @InjectView(R.id.tv_address)
        TextView tvAddress;
        @InjectView(R.id.tv_name)
        TextView tvName;
        @InjectView(R.id.tv_time)
        TextView tvTime;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
