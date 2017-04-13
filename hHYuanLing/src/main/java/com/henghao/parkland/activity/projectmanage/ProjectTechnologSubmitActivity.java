package com.henghao.parkland.activity.projectmanage;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.benefit.buy.library.phoneview.MultiImageSelectorActivity;
import com.benefit.buy.library.utils.tools.ToolsKit;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
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
 * 项目管理 -- 技术交底
 */
public class ProjectTechnologSubmitActivity extends ActivityFragmentSupport {


    @ViewInject(R.id.tv_dates)
    private TextView tv_dates;

    @ViewInject(R.id.et_sites)
    private EditText et_sites;

    @ViewInject(R.id.tv_photo)
    private TextView tv_photo;

    @ViewInject(R.id.et_content)
    private EditText et_content;

    private ArrayList<String> mSelectPath;
    private ArrayList<File> mFileList = new ArrayList<>();
    private static final int REQUEST_IMAGE = 0x00;

    Handler handler = new Handler() {
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
        this.mActivityFragmentView.viewMain(R.layout.activity_project_technologysubmit);
        this.mActivityFragmentView.viewEmpty(R.layout.activity_empty);
        this.mActivityFragmentView.viewEmptyGone();
        this.mActivityFragmentView.viewLoading(View.GONE);
        this.mActivityFragmentView.getNavitionBarView().setVisibility(View.VISIBLE);
        setContentView(this.mActivityFragmentView);
        ViewUtils.inject(this, this.mActivityFragmentView);
        initWidget();
        initData();
    }

    @Override
    public void initData() {
        super.initData();
        initWithBar();
        mLeftTextView.setText("");
        mLeftTextView.setVisibility(View.VISIBLE);
        initWithCenterBar();
        mCenterTextView.setText(XiangmuFragment.mInfoEntity.getXmName());
    }

    @OnClick({R.id.tv_submit, R.id.tv_dates, R.id.tv_photo})
    private void viewOnclick(View v) {
        switch (v.getId()) {
            case R.id.tv_submit:
                if (checkData()) {
                    mActivityFragmentView.viewLoading(View.VISIBLE, getString(R.string.compressing));
                    FileUtils.compressImagesFromList(context, handler, mFileList);
                }
                break;
            case R.id.tv_dates:
                getDialogTime("请选择日期");
                break;
            case R.id.tv_photo:
                addPic();
                break;
        }
    }

    private boolean checkData() {
        if (ToolsKit.isEmpty(tv_dates.getText().toString().trim())) {
            msg("请选择时间！");
            return false;
        }
        if (ToolsKit.isEmpty(et_sites.getText().toString().trim())) {
            msg("地点不能为空！");
            et_sites.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(et_content.getText().toString().trim())) {
            msg("内容不能为空！");
            et_content.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(mFileList)) {
            msg("请选择图片！");
            return false;
        }
        return true;
    }

    private DateChooseWheelViewDialog getDialogTime(String title) {
        DateChooseWheelViewDialog startDateChooseDialog = new DateChooseWheelViewDialog(this, new DateChooseWheelViewDialog.DateChooseInterface() {
            @Override
            public void getDateTime(String time, boolean longTimeChecked) {
                tv_dates.setText(time);
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
        String UID = preferences.getString(Constant.USERID, "");//用户ID
        int PID = XiangmuFragment.mInfoEntity.getPid();//项目信息ID
        String dates = tv_dates.getText().toString().trim();//日期
        String sites = et_sites.getText().toString().trim();//地点
        String content = et_content.getText().toString().trim();//内容
        MultipartBuilder multipartBuilder = new MultipartBuilder();
        multipartBuilder.type(MultipartBuilder.FORM)//
                .addFormDataPart("dates", dates)//日期
                .addFormDataPart("sites", sites)//地点
                .addFormDataPart("content", content)//内容
                .addFormDataPart("uid", UID)//用户ID
                .addFormDataPart("pid", String.valueOf(PID));//项目信息ID
        for (File file : mFileList) {
            multipartBuilder.addFormDataPart(file.getName(), file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));//图片
        }
        RequestBody requestBody = multipartBuilder.build();
        Request request = builder.post(requestBody).url(ProtocolUrl.ROOT_URL + "/" + ProtocolUrl.PROJECT_SAVEJSJD).build();
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
                            Toast.makeText(ProjectTechnologSubmitActivity.this, result, Toast.LENGTH_SHORT).show();
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
                        List<String> imgNames = new ArrayList<>();
                        for (String filePath : mSelectPath) {
                            String imageName = getImageName(filePath);
                            imgNames.add(imageName);
                            File file = new File(filePath);
                            mFileList.add(file);
                        }
                        tv_photo.setText("图片名：" + imgNames.toString());
                    }
                }
            }
        }
    }

    private String getImageName(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }
}
