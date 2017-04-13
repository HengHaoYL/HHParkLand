package com.henghao.parkland.activity.projectmanage;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.benefit.buy.library.phoneview.MultiImageSelectorActivity;
import com.benefit.buy.library.utils.tools.ToolsJson;
import com.benefit.buy.library.utils.tools.ToolsKit;
import com.google.gson.reflect.TypeToken;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.ProtocolUrl;
import com.henghao.parkland.R;
import com.henghao.parkland.fragment.XiangmuFragment;
import com.henghao.parkland.model.entity.BaseEntity;
import com.henghao.parkland.utils.FileUtils;
import com.henghao.parkland.views.DateChooseWheelViewDialog;
import com.lidroid.xutils.ViewUtils;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import id.zelory.compressor.Compressor;

/**
 * 项目管理 -- 竣工验收
 */
public class ProjectJunGongSubmitActivity extends ActivityFragmentSupport {

    @InjectView(R.id.tv_projectName)
    TextView tvProjectName;
    @InjectView(R.id.tv_dates)
    TextView tvDates;
    @InjectView(R.id.et_inspectionPersonnel)
    EditText etInspectionPersonnel;
    @InjectView(R.id.tv_inspectionSituation)
    TextView tvInspectionSituation;
    @InjectView(R.id.tv_completionDrawing)
    TextView tvCompletionDrawing;
    @InjectView(R.id.tv_completionReport)
    TextView tvCompletionReport;
    @InjectView(R.id.tv_submit)
    TextView tvSubmit;

    private static final String TAG = "ProjectJunGongSubmitAct";
    private static final int REQUEST_YANSHOU = 0x00;//验收现场情况请求
    private static final int REQUEST_JUNGONGTU = 0x01;//竣工图请求
    private static final int REQUEST_JUNGONGBG = 0x02;//竣工报告请求
    private ArrayList<String> mSelectPath1;//被选中的验收现场情况图片地址集合
    private ArrayList<String> mSelectPath2;//被选中的竣工图图片地址集合
    private ArrayList<String> mSelectPath3;//被选中的竣工报告图片地址集合
    private ArrayList<File> mFileList1 = new ArrayList<>();//被选中的验收现场情况图片文件
    private ArrayList<File> mFileList2 = new ArrayList<>();//被选中的竣工图图片文件
    private ArrayList<File> mFileList3 = new ArrayList<>();//被选中的竣工报告图片文件

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case FileUtils.COMPRESS_FINISH:
                    mActivityFragmentView.viewLoading(View.GONE);
                    requestNetwork();
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
        this.mActivityFragmentView.viewMain(R.layout.activity_project_jungongsubmit);
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
        initWithCenterBar();
        mCenterTextView.setText(XiangmuFragment.mInfoEntity.getXmName());
        tvProjectName.setText(XiangmuFragment.mInfoEntity.getXmName());
    }

    @Override
    public void initData() {
        super.initData();
    }

    @OnClick({R.id.tv_dates, R.id.tv_inspectionSituation, R.id.tv_completionDrawing, R.id.tv_completionReport, R.id.tv_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_dates:
                getDialogTime("请选择日期");
                break;
            case R.id.tv_inspectionSituation:
                addPic(REQUEST_YANSHOU);
                break;
            case R.id.tv_completionDrawing:
                addPic(REQUEST_JUNGONGTU);
                break;
            case R.id.tv_completionReport:
                addPic(REQUEST_JUNGONGBG);
                break;
            case R.id.tv_submit:
                if (checkData()) {
                    mActivityFragmentView.viewLoading(View.VISIBLE, getString(R.string.compressing));
                    FileUtils.compressImagesFromList(context, handler, mFileList1, mFileList2, mFileList3);
                }
                break;
        }
    }

    /**
     * /**
     * 访问网络
     */
    private void requestNetwork() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        MultipartBuilder multipartBuilder = new MultipartBuilder();
        int PID = XiangmuFragment.mInfoEntity.getPid();//项目信息ID
        String projectName = tvProjectName.getText().toString().trim();//工程名称
        String dates = tvDates.getText().toString().trim();//竣工时间
        String inspectionPersonnel = etInspectionPersonnel.getText().toString().trim();//验收人员
        multipartBuilder.type(MultipartBuilder.FORM)//
                .addFormDataPart("pid", String.valueOf(PID))//项目信息ID
                .addFormDataPart("uid", getLoginUid())//用户ID
                .addFormDataPart("projectName", projectName)//工程名称
                .addFormDataPart("dates", dates)//竣工时间
                .addFormDataPart("inspectionPersonnel", inspectionPersonnel);//验收人员
        for (File file : mFileList1) {
            multipartBuilder.addFormDataPart(file.getName(), file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));//验收现场情况图片
        }
        for (File file : mFileList2) {
            multipartBuilder.addFormDataPart(file.getName(), file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));//竣工图图片
        }
        for (File file : mFileList3) {
            multipartBuilder.addFormDataPart(file.getName(), file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));//竣工报告图片
        }
        RequestBody requestBody = multipartBuilder.build();
        Request request = builder.post(requestBody).url(ProtocolUrl.ROOT_URL + "/" + ProtocolUrl.PROJECT_SAVEFINALACCEPTANCEMSG).build();
        mActivityFragmentView.viewLoading(View.VISIBLE);
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e(TAG, "onFailure: " + e.getMessage());
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mActivityFragmentView.viewLoading(View.GONE);
                        msg("网络请求错误！");
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String content = response.body().string();
                Log.i(TAG, "onResponse: " + content);
                Type type = new TypeToken<BaseEntity>() {
                }.getType();
                final BaseEntity baseEntity = ToolsJson.parseObjecta(content, type);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mActivityFragmentView.viewLoading(View.GONE);
                        msg(baseEntity.getMsg());
                        finish();
                    }
                });
            }
        });
    }

    /**
     * 判断数据是否正确
     *
     * @return
     */
    private boolean checkData() {
        if (ToolsKit.isEmpty(tvDates.getText().toString().trim())) {
            msg("请选择日期！");
            return false;
        }
        if (ToolsKit.isEmpty(etInspectionPersonnel.getText().toString().trim())) {
            msg("验收人员不能为空！");
            etInspectionPersonnel.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(tvInspectionSituation.getText().toString().trim())) {
            msg("请选择验收现场情况图！");
            return false;
        }
        if (ToolsKit.isEmpty(tvCompletionDrawing.getText().toString().trim())) {
            msg("请选择竣工图！");
            return false;
        }
        if (ToolsKit.isEmpty(tvCompletionReport.getText().toString().trim())) {
            msg("请选择竣工报告图！");
            return false;
        }
        return true;
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

    /**
     * 添加图片
     */
    private void addPic(int request) {
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
        if (request == REQUEST_YANSHOU) {
            if ((this.mSelectPath1 != null) && (this.mSelectPath1.size() > 0)) {
                picIntent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, this.mSelectPath1);
            }
            startActivityForResult(picIntent, REQUEST_YANSHOU);
        } else if (request == REQUEST_JUNGONGTU) {
            if ((this.mSelectPath2 != null) && (this.mSelectPath2.size() > 0)) {
                picIntent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, this.mSelectPath2);
            }
            startActivityForResult(picIntent, REQUEST_JUNGONGTU);
        } else if (request == REQUEST_JUNGONGBG) {
            if ((this.mSelectPath3 != null) && (this.mSelectPath3.size() > 0)) {
                picIntent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, this.mSelectPath3);
            }
            startActivityForResult(picIntent, REQUEST_JUNGONGBG);
        }
    }

    @SuppressWarnings("static-access")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == REQUEST_YANSHOU) {
                if ((resultCode == Activity.RESULT_OK) || (resultCode == Activity.RESULT_CANCELED)) {
                    this.mSelectPath1 = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    if (!ToolsKit.isEmpty(this.mSelectPath1)) {
                        List<String> fileNames = new ArrayList<>();
                        mFileList1.clear();
                        for (String filePath : mSelectPath1) {
                            String imageName = getImageName(filePath);
                            fileNames.add(imageName);
                            File file = new File(filePath);
                            mFileList1.add(file);
                        }
                        tvInspectionSituation.setText("图片名：" + fileNames.toString());
                        //                        this.mBitmapUtils.display(this.mUserHeaderImageView, headerImg);
                    }
                }
            } else if (requestCode == REQUEST_JUNGONGTU) {
                if ((resultCode == Activity.RESULT_OK) || (resultCode == Activity.RESULT_CANCELED)) {
                    this.mSelectPath2 = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    if (!ToolsKit.isEmpty(this.mSelectPath2)) {
                        List<String> fileNames = new ArrayList<>();
                        mFileList2.clear();
                        for (String filePath : mSelectPath2) {
                            String imageName = getImageName(filePath);
                            fileNames.add(imageName);
                            File file = new File(filePath);
                            mFileList2.add(file);
                        }
                        tvCompletionDrawing.setText("图片名：" + fileNames.toString());
                        //                        this.mBitmapUtils.display(this.mUserHeaderImageView, headerImg);
                    }
                }
            } else if (requestCode == REQUEST_JUNGONGBG) {
                if ((resultCode == Activity.RESULT_OK) || (resultCode == Activity.RESULT_CANCELED)) {
                    this.mSelectPath3 = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    if (!ToolsKit.isEmpty(this.mSelectPath3)) {
                        List<String> fileNames = new ArrayList<>();
                        mFileList3.clear();
                        for (String filePath : mSelectPath3) {
                            String imageName = getImageName(filePath);
                            fileNames.add(imageName);
                            File file = new File(filePath);
                            mFileList3.add(file);
                        }
                        tvCompletionReport.setText("图片名：" + fileNames.toString());
                        //                        this.mBitmapUtils.display(this.mUserHeaderImageView, headerImg);
                    }
                }
            }
        }
    }

    private String getImageName(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }
}
