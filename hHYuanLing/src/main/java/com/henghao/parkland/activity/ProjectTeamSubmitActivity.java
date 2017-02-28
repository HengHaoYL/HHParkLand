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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_projectteamsubmit);
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
            /**
             * 访问网络
             */
            ProjectProtocol mProtocol = new ProjectProtocol(this);
            mProtocol.addResponseListener(this);
            mProtocol.saveSgPersonnelMsg(psIdcard, psName, psTel, getLoginUid());
            mActivityFragmentView.viewLoading(View.VISIBLE);
        }
    }

    private boolean checkData() {
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
