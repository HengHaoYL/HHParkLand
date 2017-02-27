package com.henghao.parkland.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.benefit.buy.library.phoneview.MultiImageSelectorActivity;
import com.benefit.buy.library.utils.tools.ToolsKit;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.R;
import com.henghao.parkland.model.protocol.HttpPublic;
import com.lidroid.xutils.ViewUtils;
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
 * 项目管理 -- 项目图纸提交
 */
public class ProjectTZSubmitActivity extends ActivityFragmentSupport {

    private static final int REQUEST_IMAGE = 0x00;
    private ArrayList<String> mSelectPath;
    private ArrayList<File> mFileList;
    @InjectView(R.id.et_tzName)
    EditText etTzName;
    @InjectView(R.id.et_tzAdd)
    EditText etTzAdd;
    @InjectView(R.id.et_tzHead)
    EditText etTzHead;
    @InjectView(R.id.et_tzTel)
    EditText etTzTel;
    @InjectView(R.id.tv_tzImg)
    TextView tvTzImg;
    @InjectView(R.id.tv_submit)
    TextView tvSubmit;
    private static final String TAG = "ProjectTZSubmitActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_projecttzsubmit);
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
        mLeftTextView.setText("项目图纸");
        mLeftTextView.setVisibility(View.VISIBLE);
        mFileList = new ArrayList<>();
    }

    @Override
    public void initData() {
        super.initData();
    }

    @OnClick({R.id.tv_tzImg, R.id.tv_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_tzImg:
                addPic();
                break;
            case R.id.tv_submit:
                if (checkData()) {
                    /**
                     * 请求访问网络
                     */
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request.Builder builder = new Request.Builder();
                    String tzAdd = etTzAdd.getText().toString().trim();
                    String tzHead = etTzHead.getText().toString().trim();
                    String tzName = etTzName.getText().toString().trim();
                    String tzTel = etTzTel.getText().toString().trim();
                    MultipartBuilder multipartBuilder = new MultipartBuilder();
                    multipartBuilder.type(MultipartBuilder.FORM)//
                            .addFormDataPart("uid", getLoginUid())//用户ID
                            .addFormDataPart("tzAdd", tzAdd)//设计单位地址
                            .addFormDataPart("tzHead", tzHead)//设计负责人
                            .addFormDataPart("tzName", tzName)//设计单位
                            .addFormDataPart("tzTel", tzTel);//联系方式
                    for (File file : mFileList) {
                        multipartBuilder.addFormDataPart(file.getName(), file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));//设计图纸
                    }
                    RequestBody requestBody = multipartBuilder.build();
                    Request request = builder.post(requestBody).url(HttpPublic.SAVEBLUEPRINTMSG).build();
                    mActivityFragmentView.viewLoading(View.VISIBLE);
                    Call call = okHttpClient.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {
                            Log.e(TAG, "onFailure: " + e.getMessage());
                            e.printStackTrace();
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
                                        Toast.makeText(ProjectTZSubmitActivity.this, result, Toast.LENGTH_SHORT).show();
                                    }
                                });
                                finish();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                break;
        }
    }

    private boolean checkData() {
        if (ToolsKit.isEmpty(etTzName.getText().toString().trim())) {
            msg("设计单位不能为空！");
            etTzName.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(etTzAdd.getText().toString().trim())) {
            msg("单位地址不能为空！");
            etTzAdd.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(etTzHead.getText().toString().trim())) {
            msg("负责人不能为空！");
            etTzHead.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(etTzTel.getText().toString().trim())) {
            msg("联系方式不能为空！");
            etTzTel.requestFocus();
            return false;
        }
        if (tvTzImg.getText().equals("设计图纸")) {
            msg("请选择设计图纸！");
            return false;
        }
        return true;
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
                        tvTzImg.setText("图片名：" + imgNames.toString());
                    }
                }
            }
        }
    }

    private String getImageName(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }
}
