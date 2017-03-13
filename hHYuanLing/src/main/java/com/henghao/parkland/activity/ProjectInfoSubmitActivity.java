package com.henghao.parkland.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.benefit.buy.library.http.query.callback.AjaxStatus;
import com.benefit.buy.library.utils.tools.ToolsKit;
import com.benefit.buy.library.utils.tools.ToolsRegex;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.ProtocolUrl;
import com.henghao.parkland.R;
import com.henghao.parkland.fragment.XiangmuFragment;
import com.henghao.parkland.model.entity.BaseEntity;
import com.henghao.parkland.model.protocol.ProjectProtocol;
import com.henghao.parkland.views.DateChooseWheelViewDialog;
import com.lidroid.xutils.ViewUtils;

import org.json.JSONException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 项目管理 -- 项目信息提交
 */
public class ProjectInfoSubmitActivity extends ActivityFragmentSupport {

    @InjectView(R.id.et_xmName)
    EditText etXmName;
    @InjectView(R.id.et_xmPerson)
    EditText etXmPerson;
    @InjectView(R.id.et_xmContact)
    EditText etXmContact;
    @InjectView(R.id.et_xmPersonNum)
    EditText etXmPersonNum;
    @InjectView(R.id.et_xmAdd)
    EditText etXmAdd;
    @InjectView(R.id.btn_submit_projectinfo)
    TextView btnSubmit;
    @InjectView(R.id.et_constructionUnit)
    EditText etConstructionUnit;
    @InjectView(R.id.tv_startTime)
    TextView tvStartTime;
    @InjectView(R.id.tv_completionTime)
    TextView tvCompletionTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_project_infosubmit);
        this.mActivityFragmentView.viewEmpty(R.layout.activity_empty);
        this.mActivityFragmentView.viewEmptyGone();
        this.mActivityFragmentView.viewLoading(View.GONE);
        this.mActivityFragmentView.getNavitionBarView().setVisibility(View.VISIBLE);
        ViewUtils.inject(this, this.mActivityFragmentView);
        setContentView(this.mActivityFragmentView);
        ButterKnife.inject(this);
        initWidget();
        initData();
    }

    @Override
    public void initWidget() {
        super.initWidget();
        initWithBar();
        mLeftTextView.setText("项目信息");
        mLeftTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void initData() {
        super.initData();
    }

    @OnClick({R.id.btn_submit_projectinfo, R.id.tv_startTime, R.id.tv_completionTime})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_startTime:
                getDialogTime("请选择时间", 0);
                break;
            case R.id.tv_completionTime:
                getDialogTime("请选择时间", 1);
                break;
            case R.id.btn_submit_projectinfo:
                String xmName = etXmName.getText().toString().trim();
                String xmAdd = etXmAdd.getText().toString().trim();
                String xmContact = etXmContact.getText().toString().trim();
                String xmPerson = etXmPerson.getText().toString().trim();
                String constructionUnit = etConstructionUnit.getText().toString().trim();
                String startTime = tvStartTime.getText().toString().trim();
                String completionTime = tvCompletionTime.getText().toString().trim();
                if (checkData()) {
                    /**
                     * 访问网络
                     */
                    ProjectProtocol mProtocol = new ProjectProtocol(this);
                    mProtocol.addResponseListener(this);
                    mProtocol.saveProjectMsg(getLoginUid(), xmAdd, xmContact, xmName, xmPerson,
                            new Integer(etXmPersonNum.getText().toString().trim()).intValue(), constructionUnit, startTime, completionTime);
                    mActivityFragmentView.viewLoading(View.VISIBLE);
                }
                break;
        }
    }

    private DateChooseWheelViewDialog getDialogTime(String title, final int pos) {
        DateChooseWheelViewDialog startDateChooseDialog = new DateChooseWheelViewDialog(this, new DateChooseWheelViewDialog.DateChooseInterface() {
            @Override
            public void getDateTime(String time, boolean longTimeChecked) {
                switch (pos) {
                    case 0:
                        tvStartTime.setText(time);
                        break;
                    case 1:
                        tvCompletionTime.setText(time);
                        break;
                }
            }
        });
        startDateChooseDialog.setDateDialogTitle(title);
        startDateChooseDialog.showDateChooseDialog();
        startDateChooseDialog.setCanceledOnTouchOutside(true);
        return startDateChooseDialog;
    }

    private boolean checkData() {
        if (ToolsKit.isEmpty(etXmName.getText().toString().trim())) {
            msg("项目名称不能为空！");
            etXmName.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(etConstructionUnit.getText().toString().trim())) {
            msg("施工单位不能为空！");
            etConstructionUnit.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(tvStartTime.getText().toString().trim())) {
            msg("请选择开工时间！");
            return false;
        }
        if (ToolsKit.isEmpty(tvCompletionTime.getText().toString().trim())) {
            msg("请选择合同竣工时间！");
            return false;
        }
        if (ToolsKit.isEmpty(etXmPerson.getText().toString().trim())) {
            msg("负责人不能为空！");
            etXmPerson.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(etXmContact.getText().toString().trim())) {
            msg("联系方式不能为空！");
            etXmContact.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(etXmPersonNum.getText().toString().trim())) {
            msg("项目人数不能为空！");
            etXmPersonNum.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(etXmAdd.getText().toString().trim())) {
            msg("项目地址不能为空！");
            etXmAdd.requestFocus();
            return false;
        }
        if (!ToolsRegex.isMobileNumber(etXmContact.getText().toString().trim())) {
            msg("联系方式格式不对！");
            etXmContact.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void OnMessageResponse(String url, Object jo, AjaxStatus status) throws JSONException {
        super.OnMessageResponse(url, jo, status);
        if (url.endsWith(ProtocolUrl.PROJECT_SAVEPROJECTMSG)) {
            if (jo instanceof BaseEntity) {
                BaseEntity base = (BaseEntity) jo;
                msg(base.getMsg());
                if (base.getMsg().equals("保存成功")) {
                    /**
                     * 添加成功之后，则项目信息列表多了一个，所以索引要随之+1
                     */
                    if (XiangmuFragment.mInfoEntity != null) {
                        XiangmuFragment.index += 1;
                    }
                } else {
                    etXmName.requestFocus();
                    return;
                }
                onBackPressed();
                return;
            }
        }
    }
}
