package com.henghao.parkland.activity.projectmanage;

import android.content.Intent;
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

import org.json.JSONException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 项目管理 -- 施工人员
 */
public class ProjectTeamSubmitActivity extends ActivityFragmentSupport {

    @InjectView(R.id.et_psName)
    EditText etPsName;
    @InjectView(R.id.et_psIdcard)
    EditText etPsIdcard;
    @InjectView(R.id.et_psTel)
    EditText etPsTel;
    @InjectView(R.id.tv_submit)
    TextView tvSubmit;
    @InjectView(R.id.et_workPost)
    TextView etWorkPost;
    @InjectView(R.id.tv_personnelType)
    TextView tvPersonnelType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_project_teamsubmit);
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
        mLeftTextView.setText("施工人员");
        mLeftTextView.setVisibility(View.VISIBLE);
        initWithCenterBar();
        mCenterTextView.setText(XiangmuFragment.mInfoEntity.getXmName());
        /**
         * 获取人员类型
         */
        Intent intent = getIntent();
        String personnelType = intent.getStringExtra("personnelType");
        tvPersonnelType.setText(personnelType);
    }

    @Override
    public void initData() {
        super.initData();
    }

    @OnClick(R.id.tv_submit)
    public void onClick() {
        if (checkData()) {
            String psName = etPsName.getText().toString().trim();
            String psIdcard = etPsIdcard.getText().toString().trim();
            String psTel = etPsTel.getText().toString().trim();
            String workPost = etWorkPost.getText().toString().trim();
            String personnelType = tvPersonnelType.getText().toString();
            int PID = XiangmuFragment.mInfoEntity.getPid();
            /**
             * 访问网络
             */
            ProjectProtocol mProtocol = new ProjectProtocol(this);
            mProtocol.addResponseListener(this);
            mProtocol.saveSgPersonnelMsg(PID, personnelType, workPost, psIdcard, psName, psTel, getLoginUid());
            mActivityFragmentView.viewLoading(View.VISIBLE);
        }
    }

    private boolean checkData() {
        if (ToolsKit.isEmpty(etWorkPost.getText().toString().trim())) {
            msg("工作岗位不能为空！");
            etWorkPost.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(etPsName.getText().toString().trim())) {
            msg("姓名不能为空！");
            etPsName.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(etPsIdcard.getText().toString().trim())) {
            msg("身份证号不能为空！");
            etPsIdcard.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(etPsTel.getText().toString().trim())) {
            msg("联系电话不能为空！");
            etPsTel.requestFocus();
            return false;
        }
        /**
         * 验证身份证号格式是否正确
         */
        if (!(etPsIdcard.getText().toString().trim()).matches("(^\\d{15}$)|(^\\d{17}([0-9]|X)$)")) {
            msg("身份证号格式不正确！");
            etPsIdcard.requestFocus();
            return false;
        }
        /**
         * 验证联系电话格式是否正确
         */
        if (!ToolsRegex.isMobileNumber(etPsTel.getText().toString().trim())) {
            msg("联系电话格式不正确");
            etPsTel.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void OnMessageResponse(String url, Object jo, AjaxStatus status) throws JSONException {
        super.OnMessageResponse(url, jo, status);
        if (url.endsWith(ProtocolUrl.PROJECT_SAVESGPERSONNELMSG)) {
            if (jo instanceof BaseEntity) {

                BaseEntity base = (BaseEntity) jo;
                msg(base.getMsg());
                onBackPressed();
                return;
            }
        }
    }
}
