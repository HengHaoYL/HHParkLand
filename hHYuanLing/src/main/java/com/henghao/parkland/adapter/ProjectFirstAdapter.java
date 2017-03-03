package com.henghao.parkland.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
import com.henghao.parkland.R;
import com.henghao.parkland.activity.ProjectBGManageActivity;
import com.henghao.parkland.activity.ProjectDeclareActivity;
import com.henghao.parkland.activity.ProjectGHFActivity;
import com.henghao.parkland.activity.ProjectGXBYActivity;
import com.henghao.parkland.activity.ProjectHSResultActivity;
import com.henghao.parkland.activity.ProjectInfoActivity;
import com.henghao.parkland.activity.ProjectJunGongActivity;
import com.henghao.parkland.activity.ProjectKGBGActivity;
import com.henghao.parkland.activity.ProjectSBDataActivity;
import com.henghao.parkland.activity.ProjectTeamActivity;
import com.henghao.parkland.activity.ProjectTechnologActivity;
import com.henghao.parkland.activity.ProjectXckcActivity;
import com.henghao.parkland.model.entity.AppGridEntity;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * 项目管理-项目信息〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author zhangxianwen
 * @version HDMNV100R001, 2015年12月21日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ProjectFirstAdapter extends ArrayAdapter<AppGridEntity> {

    private final LayoutInflater inflater;

    private final BitmapUtils mBitmapUtils;

    private final ActivityFragmentSupport mActivityFragmentSupport;

    private final List<AppGridEntity> mList;

    public ProjectFirstAdapter(ActivityFragmentSupport activityFragment, List<AppGridEntity> list) {
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
                Intent intent = new Intent();
                switch (position) {
                    case 0:
                        //项目信息
                        intent.setClass(mActivityFragmentSupport, ProjectInfoActivity.class);
                        mActivityFragmentSupport.startActivity(intent);
                        break;
                    case 1:
                        //项目结算
                        intent.setClass(mActivityFragmentSupport, ProjectInfoActivity.class);
                        mActivityFragmentSupport.startActivity(intent);
                        break;
                    case 2:
                        //会审结果
                        intent.setClass(mActivityFragmentSupport, ProjectHSResultActivity.class);
                        mActivityFragmentSupport.startActivity(intent);
                        break;
                    case 3:
                        //供货方信息
                        intent.setClass(mActivityFragmentSupport, ProjectGHFActivity.class);
                        mActivityFragmentSupport.startActivity(intent);
                        break;
                    case 4:
                        //施工团队
                        intent.setClass(mActivityFragmentSupport, ProjectTeamActivity.class);
                        mActivityFragmentSupport.startActivity(intent);
                        break;
                    case 5:
                        //开工报告
                        intent.setClass(mActivityFragmentSupport, ProjectKGBGActivity.class);
                        mActivityFragmentSupport.startActivity(intent);
                        break;
                    case 6:
                        //设备信息
                        intent.setClass(mActivityFragmentSupport, ProjectSBDataActivity.class);
                        mActivityFragmentSupport.startActivity(intent);
                        break;
                    case 7:
                        //工序报验
                        intent.setClass(mActivityFragmentSupport, ProjectGXBYActivity.class);
                        mActivityFragmentSupport.startActivity(intent);
                        break;
                    case 8:
                        //现场勘察
                        intent.setClass(mActivityFragmentSupport, ProjectXckcActivity.class);
                        mActivityFragmentSupport.startActivity(intent);
                        break;
                    case 9:
                        //变更管理
                        intent.setClass(mActivityFragmentSupport, ProjectBGManageActivity.class);
                        mActivityFragmentSupport.startActivity(intent);
                        break;
                    case 10:
                        //竣工
                        intent.setClass(mActivityFragmentSupport, ProjectJunGongActivity.class);
                        mActivityFragmentSupport.startActivity(intent);
                        break;
                    case 11:
                        //进度申报
                        intent.setClass(mActivityFragmentSupport, ProjectDeclareActivity.class);
                        mActivityFragmentSupport.startActivity(intent);
                        break;
                    case 12:
                        //技术交底
                        intent.setClass(mActivityFragmentSupport, ProjectTechnologActivity.class);
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
