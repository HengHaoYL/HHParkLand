package com.henghao.parkland.activity.projectmanage;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.benefit.buy.library.phoneview.MultiImageSelectorActivity;
import com.benefit.buy.library.utils.tools.ToolsKit;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.ProtocolUrl;
import com.henghao.parkland.R;
import com.henghao.parkland.fragment.XiangmuFragment;
import com.henghao.parkland.utils.FileUtils;
import com.henghao.parkland.views.DateChooseWheelViewDialog;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
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


/**
 * 项目管理 -- 现场勘查
 */
public class ProjectXckcSubmitActivity extends ActivityFragmentSupport {

    @ViewInject(R.id.et_time)
    private TextView et_time;

    @ViewInject(R.id.tv_photo)
    private TextView tv_photo;

    @ViewInject(R.id.et_address)
    private EditText et_address;

    @ViewInject(R.id.et_username)
    private EditText et_username;


    private ArrayList<String> mSelectPath;
    private ArrayList<File> mFileList = new ArrayList<>();
    private static final int REQUEST_IMAGE = 0x00;

    private String mData;
    private String name;
    private String address;
    private String data;

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
        this.mActivityFragmentView.viewMain(R.layout.activity_project_xckcsubmit);
        this.mActivityFragmentView.viewEmpty(R.layout.activity_empty);
        this.mActivityFragmentView.viewEmptyGone();
        this.mActivityFragmentView.viewLoading(View.GONE);
        this.mActivityFragmentView.getNavitionBarView().setVisibility(View.VISIBLE);
        ViewUtils.inject(this, this.mActivityFragmentView);
        setContentView(this.mActivityFragmentView);
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
        mCenterTextView.setText(XiangmuFragment.mInfoEntity.getName());
    }

    @Override
    public void initData() {
        super.initData();
    }

    @OnClick({R.id.tv_submit, R.id.et_time, R.id.tv_photo})
    private void viewOnclick(View v) {
        switch (v.getId()) {
            case R.id.tv_submit:
                if (checkData()) {
                    mActivityFragmentView.viewLoading(View.VISIBLE, getString(R.string.compressing));
                    FileUtils.compressImagesFromList(context, handler, mFileList);
                }
                break;
            case R.id.et_time:
                getDialogTime("请选择日期");
                break;
            case R.id.tv_photo:
                addPic();
                break;
        }
    }

    private boolean checkData() {
        name = et_username.getText().toString().trim();
        address = et_address.getText().toString().trim();
        if (ToolsKit.isEmpty(mData)) {
            msg("请选择勘查时间");
            return false;
        }
        if (ToolsKit.isEmpty(address)) {
            msg("请输入勘查地点");
            return false;
        }
        if (ToolsKit.isEmpty(name)) {
            msg("请输入勘查人员");
            return false;
        }
        if (ToolsKit.isEmpty(mFileList)) {
            msg("请选择图片");
            return false;
        }

        return true;
    }

    private DateChooseWheelViewDialog getDialogTime(String title) {
        DateChooseWheelViewDialog startDateChooseDialog = new DateChooseWheelViewDialog(this, new DateChooseWheelViewDialog.DateChooseInterface() {
            @Override
            public void getDateTime(String time, boolean longTimeChecked) {
                mData = time;
                et_time.setText(time);
            }
        });
        startDateChooseDialog.setDateDialogTitle(title);
        startDateChooseDialog.showDateChooseDialog();
        startDateChooseDialog.setCanceledOnTouchOutside(true);
        return startDateChooseDialog;
    }


    private void requestData() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        SharedPreferences preferences = getLoginUserSharedPre();
        String PID = XiangmuFragment.mInfoEntity.getId();//项目信息ID
        MultipartBuilder multipartBuilder = new MultipartBuilder();
        multipartBuilder.type(MultipartBuilder.FORM)//
                .addFormDataPart("xcTime", mData)
                .addFormDataPart("xcAdd", address)
                .addFormDataPart("xcPerson", name)
                .addFormDataPart("uid", getLoginUid())//用户ID
                .addFormDataPart("pid", PID);//项目信息ID
        for (File file : mFileList) {
            multipartBuilder.addFormDataPart(file.getName(), file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));//现场情况图片
        }
        RequestBody requestBody = multipartBuilder.build();
        Request request = builder.post(requestBody).url(ProtocolUrl.ROOT_URL + "/" + ProtocolUrl.PROJECT_SAVEXCKC).build();
        mActivityFragmentView.viewLoading(View.VISIBLE);
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
                mActivityFragmentView.viewLoading(View.GONE);
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
                            Toast.makeText(ProjectXckcSubmitActivity.this, result, Toast.LENGTH_SHORT).show();
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
                        tv_photo.setText("图片名：" + fileNames.toString());
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
