package com.henghao.parkland.activity.projectmanage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

import id.zelory.compressor.Compressor;


/**
 * 项目管理 --开工报告提交
 */
public class ProjectKGBGSubmitActivity extends ActivityFragmentSupport {

    private int REQUEST_FILE = 1001;

    File douFile;

    private String mData;
    private String mTIme;


    @ViewInject(R.id.tv_wenjian)
    private TextView tv_wenjian;

    @ViewInject(R.id.tv_data)
    private TextView tv_data;

    @ViewInject(R.id.tv_time)
    private TextView tv_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_project_kgbgsubmit);
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
        mCenterTextView.setText(XiangmuFragment.mInfoEntity.getXmName());
    }

    @Override
    public void initData() {
        super.initData();
    }

    @OnClick({R.id.tv_data, R.id.tv_time, R.id.tv_wenjian, R.id.tv_sub})
    private void viewClick(View v) {
        switch (v.getId()) {
            case R.id.tv_data:
                getDialogTime("请选择时间", 0);
                break;
            case R.id.tv_time:
                getDialogTime("请选择时间", 1);
                break;
            case R.id.tv_wenjian:
                showFileChooser();
                break;
            case R.id.tv_sub:
                if (ToolsKit.isEmpty(mData)) {
                    msg("请输入开工日期");
                    return;
                }
                if (douFile == null) {
                    msg("请选择文件");
                    return;
                }
                requestData();
                break;
        }
    }


    private DateChooseWheelViewDialog getDialogTime(String title, final int pos) {
        DateChooseWheelViewDialog startDateChooseDialog = new DateChooseWheelViewDialog(this, new DateChooseWheelViewDialog.DateChooseInterface() {
            @Override
            public void getDateTime(String time, boolean longTimeChecked) {
                switch (pos) {
                    case 0:
                        tv_data.setText(time);
                        mData = time;
                        break;
                    case 1:
                        tv_time.setText(time);
                        mTIme = time;
                        break;
                }
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
        int PID = XiangmuFragment.mInfoEntity.getPid();//项目信息ID
        MultipartBuilder multipartBuilder = new MultipartBuilder();
        multipartBuilder.type(MultipartBuilder.FORM)//
                .addFormDataPart("kgTime", mData)
                .addFormDataPart("uid", getLoginUid())//用户ID
                .addFormDataPart("pid", String.valueOf(PID));//用户ID
        //压缩文件(如果是图片的话)
        try {
            Compressor compressor = new Compressor.Builder(context)
                    .setQuality(90)
                    .setMaxHeight(2048)
                    .setMaxWidth(2048)
                    .setCompressFormat(Bitmap.CompressFormat.JPEG)
                    .build();
            douFile = compressor.compressToFile(douFile);
        } catch (Exception e) {
            Log.e("Compress", "压缩失败", e);
        }
        multipartBuilder.addFormDataPart(douFile.getName(), douFile.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), douFile));//图片
        RequestBody requestBody = multipartBuilder.build();
        Request request = builder.post(requestBody).url(ProtocolUrl.ROOT_URL + "/" + ProtocolUrl.PROJECT_SAVEKGBG).build();
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
                            Toast.makeText(ProjectKGBGSubmitActivity.this, result, Toast.LENGTH_SHORT).show();
                        }
                    });
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), REQUEST_FILE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case 1001:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    String path = FileUtils.getPath(this, uri);
                    douFile = new File(path);
                    tv_wenjian.setText("文件名：" + douFile.getName());
                }
                break;

        }
    }

}
