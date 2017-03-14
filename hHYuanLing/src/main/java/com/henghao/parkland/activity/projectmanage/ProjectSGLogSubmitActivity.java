package com.henghao.parkland.activity.projectmanage;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.benefit.buy.library.http.query.callback.AjaxStatus;
import com.benefit.buy.library.utils.tools.ToolsKit;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.ProtocolUrl;
import com.henghao.parkland.R;
import com.henghao.parkland.fragment.XiangmuFragment;
import com.henghao.parkland.model.entity.BaseEntity;
import com.henghao.parkland.model.protocol.ProjectProtocol;
import com.henghao.parkland.views.DateChooseWheelViewDialog;

import org.json.JSONException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 项目管理 -- 施工日志提交
 */
public class ProjectSGLogSubmitActivity extends ActivityFragmentSupport {


    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.tv_dates)
    TextView tvDates;
    @InjectView(R.id.et_proactContent)
    EditText etProactContent;
    @InjectView(R.id.et_technicalIndex)
    EditText etTechnicalIndex;
    @InjectView(R.id.et_workingCondition)
    EditText etWorkingCondition;
    @InjectView(R.id.et_principal)
    EditText etPrincipal;
    @InjectView(R.id.et_builder)
    EditText etBuilder;
    @InjectView(R.id.btn_sub)
    Button btnSub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_project_sglogsubmit);
        this.mActivityFragmentView.viewEmpty(R.layout.activity_empty);
        this.mActivityFragmentView.viewEmptyGone();
        this.mActivityFragmentView.viewLoading(View.GONE);
        this.mActivityFragmentView.getNavitionBarView().setVisibility(View.VISIBLE);
        setContentView(this.mActivityFragmentView);
        ButterKnife.inject(this);
        initWidget();
        initData();
    }

    @Override
    public void initWidget() {
        super.initWidget();
        initWithBar();
        mLeftTextView.setText("施工日志");
        mLeftTextView.setVisibility(View.VISIBLE);
        tvTitle.setText("施工日志");
        initWithCenterBar();
        mCenterTextView.setText(XiangmuFragment.mInfoEntity.getXmName());
    }

    @Override
    public void initData() {
        super.initData();
    }

    @OnClick({R.id.tv_dates, R.id.btn_sub})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_dates:
                getDialogTime("请选择日期");
                break;
            case R.id.btn_sub:
                if (checkData()) {
                    int PID = XiangmuFragment.mInfoEntity.getPid();
                    String dates = tvDates.getText().toString().trim();
                    String proactContent = etProactContent.getText().toString().trim();
                    String technicalIndex = etTechnicalIndex.getText().toString().trim();
                    String builder = etBuilder.getText().toString().trim();
                    String principal = etPrincipal.getText().toString().trim();
                    String workingCondition = etWorkingCondition.getText().toString().trim();
                    /**
                     * 访问网络
                     */
                    ProjectProtocol mProtocol = new ProjectProtocol(this);
                    mProtocol.addResponseListener(this);
                    mProtocol.saveConstructionLogMsg(PID, getLoginUid(), dates, proactContent, technicalIndex, builder, principal, workingCondition);
                    mActivityFragmentView.viewLoading(View.VISIBLE);
                }
                break;
        }
    }

    private DateChooseWheelViewDialog getDialogTime(String title) {
        DateChooseWheelViewDialog startDateChooseDialog = new DateChooseWheelViewDialog(this, new DateChooseWheelViewDialog.DateChooseInterface() {
            @Override
            public void getDateTime(String time, boolean longTimeChecked) {
                tvDates.setText(time);
            }
        });
        startDateChooseDialog.setDateDialogTitle(title);
        startDateChooseDialog.showDateChooseDialog();
        startDateChooseDialog.setCanceledOnTouchOutside(true);
        return startDateChooseDialog;
    }

    private boolean checkData() {
        if (ToolsKit.isEmpty(tvDates.getText().toString().trim())) {
            msg("请选择日期");
            return false;
        }
        if (ToolsKit.isEmpty(etProactContent.getText().toString().trim())) {
            msg("生产活动内容不能为空！");
            etProactContent.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(etTechnicalIndex.getText().toString().trim())) {
            msg("质量安全技术指标不能为空！");
            etTechnicalIndex.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(etWorkingCondition.getText().toString().trim())) {
            msg("完成工作情况不能为空！");
            etWorkingCondition.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(etPrincipal.getText().toString().trim())) {
            msg("工程项目负责人不能为空！");
            etPrincipal.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(etBuilder.getText().toString().trim())) {
            msg("施工员不能为空！");
            etBuilder.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void OnMessageResponse(String url, Object jo, AjaxStatus status) throws JSONException {
        super.OnMessageResponse(url, jo, status);
        if (url.endsWith(ProtocolUrl.PROJECT_SAVECONSTRUCTIONLOGMSG)) {
            if (jo instanceof BaseEntity) {
                BaseEntity base = (BaseEntity) jo;
                msg(base.getMsg());
                onBackPressed();
                return;
            }
        }
    }
}
