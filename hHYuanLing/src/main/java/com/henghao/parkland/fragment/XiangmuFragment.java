package com.henghao.parkland.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.henghao.parkland.R;
import com.henghao.parkland.adapter.ProjectFirstAdapter;
import com.henghao.parkland.adapter.ProjectSceAdapter;
import com.henghao.parkland.model.entity.AppGridEntity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目管理〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author zhangxianwen
 * @version HDMNV100R001, 2016年8月15日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class XiangmuFragment extends FragmentSupport {

    @ViewInject(R.id.gridview)
    private GridView gridview;
    private ProjectSceAdapter mMyAdapter;
    private ProjectFirstAdapter mProAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.fragment_xiangmuguanli);
        this.mActivityFragmentView.viewEmpty(R.layout.activity_empty);
        this.mActivityFragmentView.viewEmptyGone();
        this.mActivityFragmentView.viewLoading(View.GONE);
        ViewUtils.inject(this, this.mActivityFragmentView);
        initWidget();
        initData();
        return this.mActivityFragmentView;
    }

    private void initData() {
        projectTop();
        projectMy();

    }

    /**
     * 项目管理
     */
    private void projectMy() {
        List<AppGridEntity> mList2 = new ArrayList<AppGridEntity>();
        //第一个
        AppGridEntity mEntity = new AppGridEntity();
        mEntity.setImageId(R.drawable.icon_projectone);
        mEntity.setName("项目信息");
        mList2.add(mEntity);
        //第二个
        AppGridEntity mEntity2 = new AppGridEntity();
        mEntity2.setImageId(R.drawable.icon_clearing);
        mEntity2.setName("项目结算");
        mList2.add(mEntity2);
        //第三个
        AppGridEntity mEntity3 = new AppGridEntity();
        mEntity3.setImageId(R.drawable.icon_projecttwo);
        mEntity3.setName("会审结果");
        mList2.add(mEntity3);
        //第四个
        AppGridEntity mEntity4 = new AppGridEntity();
        mEntity4.setImageId(R.drawable.icon_projectthree);
        mEntity4.setName("供货方信息");
        mList2.add(mEntity4);
        //第五个
        AppGridEntity mEntity5 = new AppGridEntity();
        mEntity5.setImageId(R.drawable.icon_projectfore);
        mEntity5.setName("施工人员");
        mList2.add(mEntity5);
        //第六个
        AppGridEntity mEntity6 = new AppGridEntity();
        mEntity6.setImageId(R.drawable.icon_projectfive);
        mEntity6.setName("开工报告");
        mList2.add(mEntity6);
        //第七个
        AppGridEntity mEntity7 = new AppGridEntity();
        mEntity7.setImageId(R.drawable.icon_projectsix);
        mEntity7.setName("设备信息");
        mList2.add(mEntity7);
        //第八个
        AppGridEntity mEntity8 = new AppGridEntity();
        mEntity8.setImageId(R.drawable.icon_projectseven);
        mEntity8.setName("工序报验");
        mList2.add(mEntity8);
        //第九个
        AppGridEntity mEntity9 = new AppGridEntity();
        mEntity9.setImageId(R.drawable.icon_eight);
        mEntity9.setName("现场勘查");
        mList2.add(mEntity9);
        //第十个
        AppGridEntity mEntity10 = new AppGridEntity();
        mEntity10.setImageId(R.drawable.icon_biangeng);
        mEntity10.setName("变更管理");
        mList2.add(mEntity10);
        //第十一个
        AppGridEntity mEntity11 = new AppGridEntity();
        mEntity11.setImageId(R.drawable.icon_jungong);
        mEntity11.setName("竣工");
        mList2.add(mEntity11);
        //第十二个
        AppGridEntity mEntity12 = new AppGridEntity();
        mEntity12.setImageId(R.drawable.icon_schedule);
        mEntity12.setName("进度申报");
        mList2.add(mEntity12);
        //第十二个
        AppGridEntity mEntity13 = new AppGridEntity();
        mEntity13.setImageId(R.drawable.icon_technology);
        mEntity13.setName("技术交底");
        mList2.add(mEntity13);
        mProAdapter = new ProjectFirstAdapter(this.mActivity, mList2);
        this.gridview.setAdapter(mProAdapter);
        mProAdapter.notifyDataSetChanged();
    }

    /**
     * 我的
     */
    private void projectTop() {
        List<AppGridEntity> mList = new ArrayList<AppGridEntity>();
        //第一个
        AppGridEntity mEntity = new AppGridEntity();
        mEntity.setImageId(R.drawable.icon_projectsgbw);
        mEntity.setName("工作备忘");
        mList.add(mEntity);
//        //第二个
//        AppGridEntity mEntity2 = new AppGridEntity();
//        mEntity2.setImageId(R.drawable.icon_projectsgjh);
//        mEntity2.setName("施工备忘");
//        mList.add(mEntity2);
        //第二个
        AppGridEntity mEntity2 = new AppGridEntity();
        mEntity2.setImageId(R.drawable.icon_projecsgzl);
        mEntity2.setName("施工资料");
        mList.add(mEntity2);
        //第三个
        AppGridEntity mEntity3 = new AppGridEntity();
        mEntity3.setImageId(R.drawable.icon_projectrzbw);
        mEntity3.setName("施工日志");
        mList.add(mEntity3);
        //第四个
        AppGridEntity mEntity4 = new AppGridEntity();
        mEntity4.setImageId(R.drawable.icon_projectsgzj);
        mEntity4.setName("施工钱包");
        mList.add(mEntity4);
        mMyAdapter = new ProjectSceAdapter(this.mActivity, mList);
    }

    /**
     * 标题操作 〈一句话功能简述〉 〈功能详细描述〉
     *
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    /**
     * 标题操作 〈一句话功能简述〉 〈功能详细描述〉
     *
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    private void initwithContent() {
        // TODO Auto-generated method stub
        initWithCenterBar();
        this.mCenterTextView.setVisibility(View.VISIBLE);
        this.mCenterTextView.setText(getResources().getString(R.string.app_name));
        initWithBar();
        this.mLeftImageView.setVisibility(View.VISIBLE);
        this.mLeftImageView.setImageResource(R.drawable.home_liebiao);

    }

    public void initWidget() {
        initwithContent();
    }

    @OnClick({R.id.xm_my, R.id.xm_project})
    private void vieOnClick(View v) {
        switch (v.getId()) {
            case R.id.xm_project:
                //项目管理
                this.gridview.setAdapter(mProAdapter);
                mProAdapter.notifyDataSetChanged();
                break;
            case R.id.xm_my:
                //我的
                this.gridview.setAdapter(mMyAdapter);
                mMyAdapter.notifyDataSetChanged();
                break;

        }
    }
}
