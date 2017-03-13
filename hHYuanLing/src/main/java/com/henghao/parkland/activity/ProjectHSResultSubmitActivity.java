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
import com.henghao.parkland.fragment.XiangmuFragment;
import com.henghao.parkland.model.protocol.HttpPublic;
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
 * 项目管理 -- 会审结果提交
 */
public class ProjectHSResultSubmitActivity extends ActivityFragmentSupport {

    private static final int REQUEST_IMAGE = 0x00;
    private ArrayList<String> mSelectPath;//图片地址
    private List<File> mFileList;//图片文件

    @InjectView(R.id.et_hsDeparment)
    EditText etHsDeparment;
    @InjectView(R.id.tv_hsImg)
    TextView tvHsImg;
    @InjectView(R.id.tv_submit)
    TextView tvSubmit;

    private static final String TAG = "HSResultSubmit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_project_hsresultsubmit);
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
        mLeftTextView.setText("会审结果");
        mLeftTextView.setVisibility(View.VISIBLE);
        mFileList = new ArrayList<>();
        initWithCenterBar();
        mCenterTextView.setText(XiangmuFragment.mInfoEntity.getXmName());
    }

    @Override
    public void initData() {
        super.initData();
    }

    @OnClick({R.id.tv_hsImg, R.id.tv_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_hsImg:
                addPic();
                break;
            case R.id.tv_submit:
                if (checkData()) {
                    /**
                     * 请求访问网络
                     */
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request.Builder builder = new Request.Builder();
                    String hsDeparment = etHsDeparment.getText().toString().trim();
                    int PID = XiangmuFragment.mInfoEntity.getPid();//项目信息ID
                    MultipartBuilder multipartBuilder = new MultipartBuilder();
                    multipartBuilder.type(MultipartBuilder.FORM)//
                            .addFormDataPart("uid", getLoginUid())//用户ID
                            .addFormDataPart("pid", String.valueOf(PID))//项目信息ID
                            .addFormDataPart("hsDeparment", hsDeparment);//会审单位
                    for (File file : mFileList) {
                        multipartBuilder.addFormDataPart(file.getName(), file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));//会审图片
                    }
                    RequestBody requestBody = multipartBuilder.build();
                    Request request = builder.post(requestBody).url(HttpPublic.SAVEHSRESULTMSG).build();
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
                                        Toast.makeText(ProjectHSResultSubmitActivity.this, result, Toast.LENGTH_SHORT).show();
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
        if (ToolsKit.isEmpty(etHsDeparment.getText().toString().trim())) {
            msg("会审单位不能为空！");
            etHsDeparment.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(tvHsImg.getText().toString().trim())) {
            msg("请选择图片！");
            return false;
        }
        return true;
    }

    /**
     * 添加图片
     */
    private void addPic() {
        // 查看session是否过期
//        int selectedMode = MultiImageSelectorActivity.MODE_SINGLE;
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
                        tvHsImg.setText("图片名：" + imgNames.toString());
                    }
                }
            }
        }
    }

    private String getImageName(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }
}
