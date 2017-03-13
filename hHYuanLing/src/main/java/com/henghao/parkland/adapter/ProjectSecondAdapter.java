package com.henghao.parkland.adapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
import com.henghao.parkland.R;
import com.henghao.parkland.activity.ProjectMoneyActivity;
import com.henghao.parkland.activity.ProjectSGLogActivity;
import com.henghao.parkland.activity.ProjectSGSafeLogActivity;
import com.henghao.parkland.activity.ProjectSpvLogActivity;
import com.henghao.parkland.model.entity.AppGridEntity;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * 项目管理-我的〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author zhangxianwen
 * @version HDMNV100R001, 2015年12月21日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ProjectSecondAdapter extends ArrayAdapter<AppGridEntity> {

    private final LayoutInflater inflater;

    private final BitmapUtils mBitmapUtils;

    private final ActivityFragmentSupport mActivityFragmentSupport;

    private final List<AppGridEntity> mList;

    public ProjectSecondAdapter(ActivityFragmentSupport activityFragment, List<AppGridEntity> list) {
        super(activityFragment, R.layout.item_gridview_textimage, list);
        this.mActivityFragmentSupport = activityFragment;
        this.mList = list;
        this.inflater = LayoutInflater.from(activityFragment);
        this.mBitmapUtils = new BitmapUtils(activityFragment, Constant.CACHE_DIR_PATH);
        this.mBitmapUtils.configDefaultLoadFailedImage(R.drawable.img_loading_fail_big);
        this.mBitmapUtils.configDefaultLoadingImage(R.drawable.img_loading_default_big);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HodlerView mHodlerView = null;
        if (convertView == null) {
            mHodlerView = new HodlerView();
            convertView = this.inflater.inflate(R.layout.item_gridview_textimage, null);
            mHodlerView.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            mHodlerView.image_title = (ImageView) convertView.findViewById(R.id.image_title);
            convertView.setTag(mHodlerView);
        } else {
            mHodlerView = (HodlerView) convertView.getTag();
        }
    /*    if (position == this.mList.size()) {
            mHodlerView.image_title.setImageBitmap(BitmapFactory.decodeResource(
                    this.mActivityFragmentSupport.getResources(), R.drawable.icon_addpic_unfocused));
            mHodlerView.tv_title.setVisibility(View.GONE);
        }
        else {*/
        mHodlerView.image_title.setImageResource(getItem(position).getImageId());
        mHodlerView.tv_title.setVisibility(View.VISIBLE);
        mHodlerView.tv_title.setText(getItem(position).getName());


        viewOnClick(mHodlerView, convertView, position);
        return convertView;
    }

    /**
     * 点击
     *
     * @param mHodlerView
     * @param convertView
     * @param position
     */
    private void viewOnClick(HodlerView mHodlerView, View convertView, final int position) {
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent();
                switch (position) {
                    case 0:
                        //监理日志
                        intent.setClass(mActivityFragmentSupport, ProjectSpvLogActivity.class);
                        mActivityFragmentSupport.startActivity(intent);
                        break;
//                    case 1:
//                        //施工备忘
//                        intent.setClass(mActivityFragmentSupport, ProjectSGBWActivity.class);
//                        mActivityFragmentSupport.startActivity(intent);
//                        break;
//                    case 1:
//                        //施工资料
//                        intent.setClass(mActivityFragmentSupport, ProjectSGMaterialActivity.class);
//                        mActivityFragmentSupport.startActivity(intent);
//                        break;
                    case 1:
                        //施工日志
                        final AlertDialog.Builder builder = new AlertDialog.Builder(mActivityFragmentSupport);
                        builder.setIcon(R.drawable.icon_select);
                        builder.setTitle("请选择日志类型");
                        String[] data = {"施工日志", "施工安全日志"};
                        builder.setItems(data, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent myIntent = new Intent();
                                switch (which) {
                                    case 0://施工日志
                                        myIntent.setClass(mActivityFragmentSupport, ProjectSGLogActivity.class);
                                        mActivityFragmentSupport.startActivity(myIntent);
                                        break;
                                    case 1://施工安全日志
                                        myIntent.setClass(mActivityFragmentSupport, ProjectSGSafeLogActivity.class);
                                        mActivityFragmentSupport.startActivity(myIntent);
                                        break;
                                }
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        break;
                    case 2:
                        //施工钱包
                        intent.setClass(mActivityFragmentSupport, ProjectMoneyActivity.class);
                        mActivityFragmentSupport.startActivity(intent);
                        break;
                }
            }
        });
    }

    private class HodlerView {

        TextView tv_title;

        ImageView image_title;
    }

    @Override
    public int getCount() {
        return (this.mList.size());
    }
}
