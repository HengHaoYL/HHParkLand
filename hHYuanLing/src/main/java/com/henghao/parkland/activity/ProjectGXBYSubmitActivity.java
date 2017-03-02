package com.henghao.parkland.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.benefit.buy.library.phoneview.MultiImageSelectorActivity;
import com.benefit.buy.library.utils.tools.ToolsKit;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.ProtocolUrl;
import com.henghao.parkland.R;
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

    @InjectView(R.id.et_gxName)
    EditText etGxName;
    @InjectView(R.id.et_gxProcedure)
    EditText etGxProcedure;
    @InjectView(R.id.sp_personnelType)
    Spinner spPersonnelType;
    @InjectView(R.id.et_workPost)
    EditText etWorkPost;
    @InjectView(R.id.tv_gxTime)
    TextView tvGxTime;
    @InjectView(R.id.tv_uploadImage)
    TextView tvUploadImage;
    @InjectView(R.id.tv_submit)
    TextView tvSubmit;

    private static final int REQUEST_IMAGE = 0x00;
    private String personnelType;//交接者
    private ArrayList<String> mSelectPath;
    private ArrayList<File> mFileList = new ArrayList<>();
    private String[] data = {"施工员", "监理员", "管理员"};//下拉框选项内容

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
        mLeftTextView.setText("工序报验");
        mLeftTextView.setVisibility(View.VISIBLE);
        /**
         * 初始化下拉列表框内容
         */
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPersonnelType.setAdapter(adapter);
        spPersonnelType.setSelection(0);
    }

    @Override
    public void initData() {
        super.initData();
        spPersonnelType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                personnelType = data[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
                    requestData();
                }
                break;
        }
    }

    private boolean checkData() {
        if (ToolsKit.isEmpty(etGxName.getText().toString().trim())) {
            msg("工程名称不能为空！");
            etGxName.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(etGxProcedure.getText().toString().trim())) {
            msg("工序名称不能为空！");
            etGxProcedure.requestFocus();
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
        String gxName = etGxName.getText().toString().trim();//工程名称
        String gxProcedure = etGxProcedure.getText().toString().trim();//工序名称
        String workPost = etWorkPost.getText().toString().trim();//工作岗位
        String gxTime = tvGxTime.getText().toString().trim();//施工日期
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        String UID = getLoginUid();
        MultipartBuilder multipartBuilder = new MultipartBuilder();
        multipartBuilder.type(MultipartBuilder.FORM)//
                .addFormDataPart("uid", UID)//用户ID
                .addFormDataPart("gxName", gxName)
                .addFormDataPart("gxProcedure", gxProcedure)
                .addFormDataPart("personnelType", personnelType)
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
