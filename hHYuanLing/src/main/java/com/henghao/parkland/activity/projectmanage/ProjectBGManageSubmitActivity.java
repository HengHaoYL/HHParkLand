package com.henghao.parkland.activity.projectmanage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.benefit.buy.library.phoneview.MultiImageSelectorActivity;
import com.benefit.buy.library.utils.tools.ToolsKit;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.R;
import com.henghao.parkland.fragment.XiangmuFragment;
import com.henghao.parkland.utils.FileUtils;
import com.henghao.parkland.utils.Requester;
import com.henghao.parkland.views.DateChooseWheelViewDialog;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 项目管理 -- 变更管理提交
 */
public class ProjectBGManageSubmitActivity extends ActivityFragmentSupport {

    @InjectView(R.id.sp_confirmingParty)
    Spinner spConfirmingParty;
    @InjectView(R.id.tv_times)
    TextView tvTimes;
    @InjectView(R.id.tv_files)
    TextView tvFiles;
    @InjectView(R.id.tv_submit)
    TextView tvSubmit;
    private String[] data = {"监理员", "管理员"};//下拉框选项内容

    private static final int REQUEST_IMAGE = 0x00;
    private String confirmingParty;//确认方
    private ArrayList<String> mSelectPath;
    private ArrayList<File> mFileList = new ArrayList<>();
    private static final String TAG = "ProjectBGManageSubmitAc";
    private Call submitCall;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case FileUtils.COMPRESS_FINISH://压缩完成
                    mActivityFragmentView.viewLoading(View.GONE);
                    submit();
                    break;
                case FileUtils.COMPRESS_PROGRESS://压缩进度
                    mActivityFragmentView.setLoadingText(getString(R.string.compressing) + " " + msg.arg1 + "/" + msg.arg2);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_project_bgmanagesubmit);
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
        mLeftTextView.setText("");
        mLeftTextView.setVisibility(View.VISIBLE);
        initWithCenterBar();
        mCenterTextView.setText(XiangmuFragment.mInfoEntity.getXmName());
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spConfirmingParty.setAdapter(mAdapter);
        spConfirmingParty.setSelection(0);
    }

    @Override
    public void initData() {
        super.initData();
        spConfirmingParty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                confirmingParty = data[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @OnClick({R.id.tv_times, R.id.tv_files, R.id.tv_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_times:
                getDialogTime("请选择日期");
                break;
            case R.id.tv_files:
                addPic();
                break;
            case R.id.tv_submit:
                if (checkData()) {
                    mActivityFragmentView.viewLoading(View.VISIBLE, getString(R.string.compressing));
                    FileUtils.compressImagesFromList(context, handler, mFileList);
                    break;
                }
        }
    }

    /**
     * 提交信息
     */
    private void submit() {
        int PID = XiangmuFragment.mInfoEntity.getPid();//项目信息ID
        String times = tvTimes.getText().toString().trim();//变更时间
        submitCall = Requester.changeManageSubmit(getLoginUid(), String.valueOf(PID), confirmingParty, times, mFileList, new DefaultCallback() {

            @Override
            public void onFailure(Request request, Exception e, int code) {
                Log.e(TAG, "onFailure: " + e.getMessage());
                e.printStackTrace();
                mActivityFragmentView.viewLoading(View.GONE);
                msg("网络请求错误！");
            }

            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    final String result = jsonObject.getString("result");
                    mActivityFragmentView.viewLoading(View.GONE);
                    Toast.makeText(ProjectBGManageSubmitActivity.this, result, Toast.LENGTH_SHORT).show();
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 判断数据是否正确
     *
     * @return
     */
    private boolean checkData() {
        if (ToolsKit.isEmpty(tvTimes.getText().toString().trim())) {
            msg("请选择变更时间！");
            return false;
        }
        if (ToolsKit.isEmpty(tvFiles.getText().toString().trim())) {
            msg("请选择图片！");
            return false;
        }
        return true;
    }

    private DateChooseWheelViewDialog getDialogTime(String title) {
        DateChooseWheelViewDialog startDateChooseDialog = new DateChooseWheelViewDialog(this, new DateChooseWheelViewDialog.DateChooseInterface() {
            @Override
            public void getDateTime(String time, boolean longTimeChecked) {
                tvTimes.setText(time);
            }
        });
        startDateChooseDialog.setDateDialogTitle(title);
        startDateChooseDialog.showDateChooseDialog();
        startDateChooseDialog.setCanceledOnTouchOutside(true);
        return startDateChooseDialog;
    }

    /**
     * 添加图片
     */
    private void addPic() {
        // 查看session是否过期
        // int selectedMode = MultiImageSelectorActivity.MODE_SINGLE;
        int selectedMode = MultiImageSelectorActivity.MODE_MULTI;
        int maxNum = 9;
        Intent picIntent = new Intent(this, MultiImageSelectorActivity.class);
        // 是否显示拍摄图片
        picIntent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        // 最大可选择图片数量
        picIntent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, maxNum);
        // 选择模式
        picIntent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, selectedMode);
        // 默认选择
        if ((this.mSelectPath != null) && (this.mSelectPath.size() > 0)) {
            picIntent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, this.mSelectPath);
        }
        startActivityForResult(picIntent, REQUEST_IMAGE);
    }

    @SuppressWarnings("static-access")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == REQUEST_IMAGE) {
                if ((resultCode == Activity.RESULT_OK) || (resultCode == Activity.RESULT_CANCELED)) {
                    this.mSelectPath = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    if (!ToolsKit.isEmpty(this.mSelectPath)) {
                        List<String> fileNames = new ArrayList<>();
                        mFileList.clear();
                        for (String filePath : mSelectPath) {
                            String imageName = getImageName(filePath);
                            fileNames.add(imageName);
                            File file = new File(filePath);
                            mFileList.add(file);
                        }
                        Log.i("mFileList", String.valueOf(mFileList.size()));
                        tvFiles.setText("图片名：" + fileNames.toString());
                        //                        this.mBitmapUtils.display(this.mUserHeaderImageView, headerImg);
                    }
                }
            }
        }
    }

    private String getImageName(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (submitCall != null && !submitCall.isCanceled()) {
            submitCall.cancel();
        }
    }
}
