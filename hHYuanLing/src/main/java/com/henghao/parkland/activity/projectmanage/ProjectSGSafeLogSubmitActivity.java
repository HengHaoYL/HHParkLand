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
 * 项目管理 -- 施工安全日志提交
 */
public class ProjectSGSafeLogSubmitActivity extends ActivityFragmentSupport {


    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.tv_dates)
    TextView tvDates;
    @InjectView(R.id.et_constructionSite)
    EditText etConstructionSite;
    @InjectView(R.id.et_constructionDynamic)
    EditText etConstructionDynamic;
    @InjectView(R.id.et_safetySituation)
    EditText etSafetySituation;
    @InjectView(R.id.et_safetyProblems)
    EditText etSafetyProblems;
    @InjectView(R.id.et_fillPeople)
    EditText etFillPeople;
    @InjectView(R.id.btn_submit)
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_project_sgsafelogsubmit);
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
        mLeftTextView.setText("施工安全日志");
        mLeftTextView.setVisibility(View.VISIBLE);
        tvTitle.setText("施工安全日志");
        initWithCenterBar();
        mCenterTextView.setText(XiangmuFragment.mInfoEntity.getXmName());
    }

    @Override
    public void initData() {
        super.initData();
    }

    @OnClick({R.id.tv_dates, R.id.btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_dates:
                getDialogTime("请选择日期");
                break;
            case R.id.btn_submit:
                if (checkData()) {
                    int PID = XiangmuFragment.mInfoEntity.getPid();
                    String dates = tvDates.getText().toString().trim();//日期
                    String constructionSite = etConstructionSite.getText().toString().trim();// 施工部位
                    String constructionDynamic = etConstructionDynamic.getText().toString().trim();// 施工工序动态
                    String safetySituation = etSafetySituation.getText().toString().trim();// 安全状况
                    String safetyProblems = etSafetyProblems.getText().toString().trim();// 安全问题的处理
                    String fillPeople = etFillPeople.getText().toString().trim();// 填写人
                    /**
                     * 访问网络
                     */
                    ProjectProtocol mProtocol = new ProjectProtocol(this);
                    mProtocol.addResponseListener(this);
                    mProtocol.saveSummaryLogMsg(PID, getLoginUid(), dates, constructionSite,
                            constructionDynamic, safetySituation, safetyProblems, fillPeople);
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
        if (ToolsKit.isEmpty(etConstructionSite.getText().toString().trim())) {
            msg("施工部位不能为空！");
            etConstructionSite.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(etConstructionDynamic.getText().toString().trim())) {
            msg("施工工序动态不能为空！");
            etConstructionDynamic.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(etSafetySituation.getText().toString().trim())) {
            msg("安全状况不能为空！");
            etSafetySituation.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(etSafetyProblems.getText().toString().trim())) {
            msg("安全问题的处理不能为空！");
            etSafetyProblems.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(etFillPeople.getText().toString().trim())) {
            msg("填写人不能为空！");
            etFillPeople.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void OnMessageResponse(String url, Object jo, AjaxStatus status) throws JSONException {
        super.OnMessageResponse(url, jo, status);
        if (url.endsWith(ProtocolUrl.PROJECT_SAVESUMMARYLOGMSG)) {
            if (jo instanceof BaseEntity) {
                BaseEntity base = (BaseEntity) jo;
                msg(base.getMsg());
                onBackPressed();
                return;
            }
        }
    }
}
