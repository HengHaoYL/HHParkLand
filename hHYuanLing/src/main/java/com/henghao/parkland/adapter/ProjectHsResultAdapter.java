package com.henghao.parkland.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
import com.henghao.parkland.R;
import com.henghao.parkland.model.entity.ProjectHSResultEntity;
import com.henghao.parkland.utils.ScanImageUtils;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 项目图纸〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author zhangxianwen
 * @version HDMNV100R001, 2015年12月21日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ProjectHsResultAdapter extends ArrayAdapter<ProjectHSResultEntity> {

    private final LayoutInflater inflater;

    private final BitmapUtils mBitmapUtils;

    private final ActivityFragmentSupport mActivityFragmentSupport;

    public ProjectHsResultAdapter(ActivityFragmentSupport activityFragment, List<ProjectHSResultEntity> mList) {
        super(activityFragment, R.layout.list_item_hsresult, mList);
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
            convertView = inflater.inflate(R.layout.list_item_hsresult, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvHsDepartment.setText(getItem(position).getHsDeparment());
        mBitmapUtils.display(holder.ivHsImg, getItem(position).getHsImgId());
        final ArrayList<String> imgUrls = new ArrayList<>();
        imgUrls.add(getItem(position).getHsImgId());
        holder.ivHsImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScanImageUtils.ScanImage(mActivityFragmentSupport, imgUrls, 0);
            }
        });
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.tv_hsDepartment)
        TextView tvHsDepartment;
        @InjectView(R.id.iv_hsImg)
        ImageView ivHsImg;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
