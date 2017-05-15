package com.henghao.parkland.activity.projectmanage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.benefit.buy.library.phoneview.MultiImageSelectorActivity;
import com.benefit.buy.library.utils.tools.ToolsKit;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.ProtocolUrl;
import com.henghao.parkland.R;
import com.henghao.parkland.fragment.XiangmuFragment;
import com.henghao.parkland.utils.FileUtils;
import com.henghao.parkland.utils.PopupWindowHelper;
import com.henghao.parkland.views.DateChooseWheelViewDialog;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 项目管理 --工序报验提交
 */
public class ProjectGXBYSubmitActivity extends ActivityFragmentSupport {

    @InjectView(R.id.tv_gxName)
    TextView tvGxName;
    @InjectView(R.id.et_gxProcedure)
    EditText etGxProcedure;
    @InjectView(R.id.et_workPost)
    EditText etWorkPost;
    @InjectView(R.id.tv_gxTime)
    TextView tvGxTime;
    @InjectView(R.id.tv_uploadImage)
    TextView tvUploadImage;
    @InjectView(R.id.tv_submit)
    TextView tvSubmit;
    @InjectView(R.id.rg_personnelType)
    RadioGroup rgPersonnelType;
    @InjectView(R.id.et_personnelType)
    EditText etPersonnelType;

    private View popView;

    private PopupWindowHelper popupWindowHelper;

    private static final int REQUEST_IMAGE = 0x00;
    private String personnelType = "施工员";//默认选中的人员类型
    private ArrayList<String> mSelectPath;
    private ArrayList<File> mFileList = new ArrayList<>();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case FileUtils.COMPRESS_FINISH:
                    mActivityFragmentView.viewLoading(View.GONE);
                    requestData();
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
        this.mActivityFragmentView.viewMain(R.layout.activity_project_gxbysubmit);
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
        tvGxName.setText(XiangmuFragment.mInfoEntity.getXmName());
    }

    @Override
    public void initData() {
        super.initData();
        ((RadioButton) rgPersonnelType.getChildAt(0)).setChecked(true);
        /**
         * 单选框监听事件
         */
        rgPersonnelType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_one://施工员
                        personnelType = "施工员";
                        break;
                    case R.id.rb_two://监理员
                        personnelType = "监理员";
                        break;
                    case R.id.rb_three://管理员
                        personnelType = "管理员";
                        break;
                }
            }
        });
    }

    @OnClick({R.id.tv_gxTime, R.id.tv_uploadImage, R.id.tv_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_gxTime:
                getDialogTime("请选择日期");
                break;
            case R.id.tv_uploadImage:
                addPic();
                break;
            case R.id.tv_submit:
                if (checkData()) {
                    mActivityFragmentView.viewLoading(View.VISIBLE, getString(R.string.compressing));
                    FileUtils.compressImagesFromList(context, handler, mFileList);
                }
                break;
        }
    }

    private boolean checkData() {
        if (ToolsKit.isEmpty(etGxProcedure.getText().toString().trim())) {
            msg("工序名称不能为空！");
            etGxProcedure.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(etPersonnelType.getText().toString().trim())) {
            msg("交接者不能为空！");
            etPersonnelType.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(etWorkPost.getText().toString().trim())) {
            msg("工作岗位不能为空！");
            etWorkPost.requestFocus();
            return false;
        }
        if (tvGxTime.getText().toString().trim().equals("施工日期")) {
            msg("请选择施工时间！");
            return false;
        }
        if (tvUploadImage.getText().toString().trim().equals("影像资料图")) {
            msg("请选择图片！");
            return false;
        }
        return true;
    }

    private DateChooseWheelViewDialog getDialogTime(String title) {
        DateChooseWheelViewDialog startDateChooseDialog = new DateChooseWheelViewDialog(this, new DateChooseWheelViewDialog.DateChooseInterface() {
            @Override
            public void getDateTime(String time, boolean longTimeChecked) {
                tvGxTime.setText(time);
            }
        });
        startDateChooseDialog.setDateDialogTitle(title);
        startDateChooseDialog.showDateChooseDialog();
        startDateChooseDialog.setCanceledOnTouchOutside(true);
        return startDateChooseDialog;
    }

    /**
     * 访问网络
     */
    private void requestData() {
        String gxName = tvGxName.getText().toString().trim();//工程名称
        String gxProcedure = etGxProcedure.getText().toString().trim();//工序名称
        String workPost = etWorkPost.getText().toString().trim();//工作岗位
        String gxTime = tvGxTime.getText().toString().trim();//施工日期
        String receiver = etPersonnelType.getText().toString().trim();//交接者
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        String UID = getLoginUid();
        int PID = XiangmuFragment.mInfoEntity.getPid();//项目信息ID
        MultipartBuilder multipartBuilder = new MultipartBuilder();
        multipartBuilder.type(MultipartBuilder.FORM)//
                .addFormDataPart("uid", UID)//用户ID
                .addFormDataPart("pid", String.valueOf(PID))//项目信息ID
                .addFormDataPart("gxName", gxName)
                .addFormDataPart("gxProcedure", gxProcedure)
                .addFormDataPart("personnelType", personnelType)
                .addFormDataPart("receiver", receiver)
                .addFormDataPart("workPost", workPost)
                .addFormDataPart("gxTime", gxTime);
        for (File file : mFileList) {
            multipartBuilder.addFormDataPart(file.getName(), file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));//图片
        }
        RequestBody requestBody = multipartBuilder.build();
        Request request = builder.post(requestBody).url(ProtocolUrl.ROOT_URL + "/" + ProtocolUrl.PROJECT_SAVECHECKOUTMSG).build();
        mActivityFragmentView.viewLoading(View.VISIBLE);
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mActivityFragmentView.viewLoading(View.GONE);
                    }
                });
                msg("网络请求错误！");
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String content = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(content);
                    final String result = jsonObject.getString("result");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mActivityFragmentView.viewLoading(View.GONE);
                            Toast.makeText(ProjectGXBYSubmitActivity.this, result, Toast.LENGTH_SHORT).show();
                        }
                    });
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
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
                        tvUploadImage.setText("图片名：" + fileNames.toString());
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
