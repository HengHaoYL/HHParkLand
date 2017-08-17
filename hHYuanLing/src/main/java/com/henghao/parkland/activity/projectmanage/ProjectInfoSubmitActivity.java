package com.henghao.parkland.activity.projectmanage;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.benefit.buy.library.http.query.callback.AjaxStatus;
import com.benefit.buy.library.utils.tools.ToolsJson;
import com.benefit.buy.library.utils.tools.ToolsKit;
import com.benefit.buy.library.utils.tools.ToolsRegex;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.BuildConfig;
import com.henghao.parkland.ProtocolUrl;
import com.henghao.parkland.R;
import com.henghao.parkland.fragment.XiangmuFragment;
import com.henghao.parkland.model.entity.BaseEntity;
import com.henghao.parkland.utils.Requester;
import com.henghao.parkland.views.DateChooseWheelViewDialog;

import org.json.JSONException;

import java.lang.reflect.Type;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 项目管理 -- 项目信息提交
 */
public class ProjectInfoSubmitActivity extends ActivityFragmentSupport {

    @InjectView(R.id.et_name)
    EditText etName;
    @InjectView(R.id.et_principal)
    EditText etPrincipal;
    @InjectView(R.id.et_tel)
    EditText etTel;
    @InjectView(R.id.et_personNum)
    EditText etPersonNum;
    @InjectView(R.id.et_address)
    EditText etAddress;
    @InjectView(R.id.et_company)
    EditText etCompany;
    @InjectView(R.id.tv_startTime)
    TextView tvStartTime;
    @InjectView(R.id.tv_endTime)
    TextView tvEndTime;
    @InjectView(R.id.tv_submit)
    TextView tvSubmit;

    private static final String TAG = "ProjectInfoSubmitActivi";
    private String name;//项目名称
    private String principal;//项目负责人
    private String tel;//联系方式
    private String personNum;//项目人数
    private String address;//项目地点
    private String company;//施工单位
    private String startTime;//开工时间
    private String endTime;//竣工时间
    private Call addXmxxCall;//项目信息提交请求

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_project_infosubmit);
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
        mLeftTextView.setVisibility(View.VISIBLE);
        mLeftTextView.setText("返回");
    }

    @Override
    public void initData() {
        super.initData();
    }

    @OnClick({R.id.tv_submit, R.id.tv_startTime, R.id.tv_endTime})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.tv_startTime:
                getDialogTime("请选择开工时间", 0);
                break;
            case R.id.tv_endTime:
                getDialogTime("请选择竣工时间", 1);
                break;
            case R.id.tv_submit:
                name = etName.getText().toString().trim();
                principal = etPrincipal.getText().toString().trim();
                tel = etTel.getText().toString().trim();
                personNum = etPersonNum.getText().toString().trim();
                address = etAddress.getText().toString().trim();
                company = etCompany.getText().toString().trim();
                startTime = tvStartTime.getText().toString().trim();
                endTime = tvEndTime.getText().toString().trim();
                if (checkData()) {
                    // 访问网络，提交项目信息
                    addXmxxCall = Requester.addXmxx(getLoginUid(), getLoginUser().getDeptId(), name, principal, tel, Integer.parseInt(personNum), address, company, startTime, endTime, addXmxxCallBack);
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
                        tvEndTime.setText(time);
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
        if (ToolsKit.isEmpty(name)) {
            msg("项目名称不能为空！");
            etName.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(principal)) {
            msg("项目负责人不能为空！");
            etPrincipal.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(tel)) {
            msg("联系方式不能为空！");
            etTel.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(personNum)) {
            msg("项目人数不能为空！");
            etPersonNum.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(address)) {
            msg("项目地点不能为空！");
            etAddress.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(company)) {
            msg("施工单位不能为空！");
            etCompany.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(startTime)) {
            msg("请选择开工时间！");
            return false;
        }
        if (ToolsKit.isEmpty(endTime)) {
            msg("请选择竣工时间！");
            return false;
        }
        if (!ToolsRegex.isMobileNumber(tel)) {
            msg("联系方式格式不对！");
            etTel.requestFocus();
            return false;
        }
        return true;
    }

    private DefaultCallback addXmxxCallBack = new DefaultCallback() {
        @Override
        public void onFailure(Exception e, int code) {
            if (BuildConfig.DEBUG) Log.e(TAG, "onFailure: code = " + code, e);
            e.printStackTrace();
            Toast.makeText(context, "网络访问错误！", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onSuccess(String response) {
            try {
                if (BuildConfig.DEBUG) Log.d(TAG, "onSuccess: " + response);
                Type baseType = new TypeToken<BaseEntity>() {
                }.getType();
                BaseEntity baseEntity = ToolsJson.parseObjecta(response, baseType);
                int errorCode = baseEntity.getErrorCode();
                if (errorCode > 0) {//提交失败
                    msg(baseEntity.getMsg());
                    etName.requestFocus();
                    return;
                }
                //添加成功之后，则项目信息列表多了一个，所以索引要随之+1
                if (XiangmuFragment.mInfoEntity != null) {
                    XiangmuFragment.index += 1;
                }
                onBackPressed();
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
        }
    };

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
                    etName.requestFocus();
                    return;
                }
                onBackPressed();
                return;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (addXmxxCall != null && !addXmxxCall.isCanceled()) {
            addXmxxCall.cancel();
        }
    }
}
