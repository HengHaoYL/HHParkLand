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
 * 项目管理 -- 监理日志提交
 */
public class ProjectSpvLogSubmitActivity extends ActivityFragmentSupport {

    @InjectView(R.id.tv_name)
    TextView tvName;
    @InjectView(R.id.tv_dates)
    TextView tvDates;
    @InjectView(R.id.et_supervisionPosition)
    EditText etSupervisionPosition;
    @InjectView(R.id.et_progressSituation)
    EditText etProgressSituation;
    @InjectView(R.id.et_workingSitustion)
    EditText etWorkingSitustion;
    @InjectView(R.id.et_question)
    EditText etQuestion;
    @InjectView(R.id.et_other)
    EditText etOther;
    @InjectView(R.id.et_noteTaker)
    EditText etNoteTaker;
    @InjectView(R.id.et_engineer)
    EditText etEngineer;
    @InjectView(R.id.btn_sub)
    Button btnSub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_project_spvlogsubmit);
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
        tvName.setText(XiangmuFragment.mInfoEntity.getName());
        initWithCenterBar();
        mCenterTextView.setText(XiangmuFragment.mInfoEntity.getName());
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
                    String dates = tvDates.getText().toString().trim();//日期
                    String projectName = tvName.getText().toString().trim();//项目名称
                    String supervisionPosition = etSupervisionPosition.getText().toString().trim();//监理部位
                    String progressSituation = etProgressSituation.getText().toString().trim();//工程施工进度情况
                    String workingSitustion = etWorkingSitustion.getText().toString().trim();//监理工作情况
                    String question = etQuestion.getText().toString().trim();//存在的问题及处理情况
                    String other = etOther.getText().toString().trim();//其他有关事项
                    String noteTaker = etNoteTaker.getText().toString().trim();//记录人
                    String engineer = etEngineer.getText().toString().trim();//总监理工工程师
                    String PID = XiangmuFragment.mInfoEntity.getId();//项目信息ID
                    /**
                     * 访问网络
                     */
                    ProjectProtocol mProtocol = new ProjectProtocol(this);
                    mProtocol.addResponseListener(this);
                    mProtocol.saveSupervisionlogMsg(PID, getLoginUid(), projectName, supervisionPosition, progressSituation,
                            workingSitustion, question, other, dates, noteTaker, engineer);
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
        if (ToolsKit.isEmpty(etSupervisionPosition.getText().toString().trim())) {
            msg("监理部位不能为空！");
            etSupervisionPosition.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(etProgressSituation.getText().toString().trim())) {
            msg("工程施工进度情况不能为空！");
            etProgressSituation.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(etNoteTaker.getText().toString().trim())) {
            msg("记录人不能为空！");
            etNoteTaker.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(etEngineer.getText().toString().trim())) {
            msg("总监理工工程师不能为空！");
            etEngineer.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void OnMessageResponse(String url, Object jo, AjaxStatus status) throws JSONException {
        super.OnMessageResponse(url, jo, status);
        if (url.endsWith(ProtocolUrl.PROJECT_SAVESUPERVISIONLOGMSG)) {
            if (jo instanceof BaseEntity) {
                BaseEntity base = (BaseEntity) jo;
                msg(base.getMsg());
                onBackPressed();
                return;
            }
        }
    }
}
