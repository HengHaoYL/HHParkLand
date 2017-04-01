package com.henghao.parkland.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.benefit.buy.library.utils.tools.ToolsKit;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.R;
import com.lidroid.xutils.ViewUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 应用 -- 招投标提交
 */
public class BidSubmitActivity extends ActivityFragmentSupport {

    @InjectView(R.id.et_companyName)
    EditText etCompanyName;
    @InjectView(R.id.et_companyAdd)
    EditText etCompanyAdd;
    @InjectView(R.id.et_contacts)
    EditText etContacts;
    @InjectView(R.id.et_tel)
    EditText etTel;
    @InjectView(R.id.tv_dates)
    TextView tvDates;
    @InjectView(R.id.et_xmAdd)
    EditText etXmAdd;
    @InjectView(R.id.btn_submit_projectinfo)
    TextView btnSubmitProjectinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_bid_submit);
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
        initWithCenterBar();
        mCenterTextView.setText("招投标");
    }

    @Override
    public void initData() {
        super.initData();
    }

    @OnClick(R.id.btn_submit_projectinfo)
    public void onClick() {
        String companyName = etCompanyName.getText().toString().trim();
        String companyAdd = etCompanyAdd.getText().toString().trim();
        String contacts = etContacts.getText().toString().trim();
        String tel = etTel.getText().toString().trim();
        String dates = tvDates.getText().toString().trim();
        String uid = getLoginUid();
        if (checkData()) {

        }
    }

    private boolean checkData() {
        if (ToolsKit.isEmpty(etCompanyName.getText().toString().trim())) {
            msg("企业名称不能为空！");
            etCompanyName.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(etCompanyAdd.getText().toString().trim())) {
            msg("企业地址不能为空！");
            etCompanyAdd.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(etContacts.getText().toString().trim())) {
            msg("联系人不能为空！");
            etContacts.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(etTel.getText().toString().trim())) {
            msg("联系电话不能为空！");
            etTel.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(tvDates.getText().toString().trim())) {
            msg("请选择日期！");
            return false;
        }
        if (ToolsKit.isEmpty(etCompanyName.getText().toString().trim())) {
            msg("企业名称不能为空！");
            etCompanyName.requestFocus();
            return false;
        }
        if (etTel.getText().toString().trim().length() > 11) {
            msg("联系电话格式不正确");
            etTel.requestFocus();
            return false;
        }
        return true;
    }
}
