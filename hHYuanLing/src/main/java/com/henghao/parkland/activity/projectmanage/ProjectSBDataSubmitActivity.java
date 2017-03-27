package com.henghao.parkland.activity.projectmanage;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.benefit.buy.library.http.query.callback.AjaxStatus;
import com.benefit.buy.library.utils.tools.ToolsKit;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.ProtocolUrl;
import com.henghao.parkland.R;
import com.henghao.parkland.fragment.XiangmuFragment;
import com.henghao.parkland.model.entity.BaseEntity;
import com.henghao.parkland.model.protocol.ProjectProtocol;
import com.lidroid.xutils.ViewUtils;

import org.json.JSONException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 项目管理 --设备信息提交
 */
public class ProjectSBDataSubmitActivity extends ActivityFragmentSupport {

    @InjectView(R.id.et_sbName)
    EditText etSbName;
    @InjectView(R.id.et_sbSpec)
    EditText etSbSpec;
    @InjectView(R.id.et_sbNum)
    EditText etSbNum;
    @InjectView(R.id.et_sbPurpose)
    EditText etSbPurpose;
    @InjectView(R.id.tv_submit)
    TextView tvSubmit;
    @InjectView(R.id.rg_sbSource)
    RadioGroup rgSbSource;
    private String sbSource = "自有";//默认选中设备来源

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_project_sbdatasubmit);
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
        mLeftTextView.setText("");
        mLeftTextView.setVisibility(View.VISIBLE);
        ((RadioButton) rgSbSource.getChildAt(0)).setChecked(true);
        initWithCenterBar();
        mCenterTextView.setText(XiangmuFragment.mInfoEntity.getXmName());
    }

    @Override
    public void initData() {
        super.initData();
        rgSbSource.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_ziyou:
                        sbSource = "自有";
                        break;
                    case R.id.rb_zulin:
                        sbSource = "租赁";
                        break;
                }
            }
        });
    }

    @OnClick(R.id.tv_submit)
    public void onClick() {
        if (checkData()) {
            String sbName = etSbName.getText().toString().trim();
            String sbSpec = etSbSpec.getText().toString().trim();
            int sbNum = new Integer(etSbNum.getText().toString().trim()).intValue();
            String sbPurpose = etSbPurpose.getText().toString().trim();
            int PID = XiangmuFragment.mInfoEntity.getPid();//项目信息ID
            /**
             * 访问网络
             */
            ProjectProtocol mProtocol = new ProjectProtocol(this);
            mProtocol.addResponseListener(this);
            mProtocol.saveEquipmentMsg(sbName, sbSpec, sbNum, sbPurpose, sbSource, getLoginUid(), PID);
            mActivityFragmentView.viewLoading(View.VISIBLE);
        }
    }

    /**
     * 判断提交内容是否为空
     *
     * @return
     */
    private boolean checkData() {
        if (ToolsKit.isEmpty(etSbName.getText().toString().trim())) {
            msg("设备名称不能为空！");
            etSbName.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(etSbSpec.getText().toString().trim())) {
            msg("设备型号不能为空！");
            etSbSpec.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(etSbNum.getText().toString().trim())) {
            msg("设备数量不能为空！");
            etSbNum.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(etSbPurpose.getText().toString().trim())) {
            msg("设备用途不能为空！");
            etSbPurpose.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void OnMessageResponse(String url, Object jo, AjaxStatus status) throws JSONException {
        super.OnMessageResponse(url, jo, status);
        if (url.endsWith(ProtocolUrl.PROJECT_SAVEEQUIPMENTMSG)) {
            if (jo instanceof BaseEntity) {
                BaseEntity base = (BaseEntity) jo;
                msg(base.getMsg());
                onBackPressed();
                return;
            }
        }
    }
}
