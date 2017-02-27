package com.henghao.parkland.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.benefit.buy.library.http.query.callback.AjaxStatus;
import com.benefit.buy.library.utils.tools.ToolsKit;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.ProtocolUrl;
import com.henghao.parkland.R;
import com.henghao.parkland.model.entity.BaseEntity;
import com.henghao.parkland.model.protocol.ProjectProtocol;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_projectinfosubmit);
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

    @OnClick(R.id.btn_submit_projectinfo)
    public void onClick() {
        String xmName = etXmName.getText().toString().trim();
        String xmAdd = etXmAdd.getText().toString().trim();
        String xmContact = etXmContact.getText().toString().trim();
        String xmPerson = etXmPerson.getText().toString().trim();
        int xmPersonNum = new Integer(etXmPersonNum.getText().toString().trim()).intValue();
        if (checkData()) {
            /**
             * 访问网络
             */
            ProjectProtocol mProtocol = new ProjectProtocol(this);
            mProtocol.addResponseListener(this);
            mProtocol.saveProjectMsg(getLoginUid(), xmAdd, xmContact, xmName, xmPerson, xmPersonNum);
            mActivityFragmentView.viewLoading(View.VISIBLE);
        }
    }

    private boolean checkData() {
        if (ToolsKit.isEmpty(etXmName.getText().toString().trim())) {
            msg("项目名称不能为空！");
            etXmName.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(etXmAdd.getText().toString().trim())) {
            msg("项目地址不能为空！");
            etXmAdd.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(etXmContact.getText().toString().trim())) {
            msg("联系方式不能为空！");
            etXmContact.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(etXmPerson.getText().toString().trim())) {
            msg("负责人不能为空！");
            etXmPerson.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(etXmPersonNum.getText().toString().trim())) {
            msg("项目人数不能为空！");
            etXmPersonNum.requestFocus();
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
                onBackPressed();
                return;
            }
        }
    }
}
