package com.henghao.parkland.activity.projectmanage;

import android.app.Activity;
import android.content.Intent;
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
import com.benefit.buy.library.utils.tools.ToolsRegex;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.ProtocolUrl;
import com.henghao.parkland.R;
import com.henghao.parkland.fragment.XiangmuFragment;
import com.henghao.parkland.utils.FileUtils;
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
 * 项目管理 -- 供货方信息提交
 */
public class ProjectGHFSubmitActivity extends ActivityFragmentSupport {

    @InjectView(R.id.et_epName)
    EditText etEpName;
    @InjectView(R.id.et_epAdd)
    EditText etEpAdd;
    @InjectView(R.id.tv_epDate)
    TextView tvEpDate;
    @InjectView(R.id.et_epTel)
    EditText etEpTel;
    @InjectView(R.id.tv_hehong)
    TextView tvHehong;
    @InjectView(R.id.tv_jianyan)
    TextView tvJianyan;
    @InjectView(R.id.tv_submit)
    TextView tvSubmit;

    private static final String TAG = "ProjectGHFSubmitActivit";
    private static final int REQUEST_HETONG = 0x00;//供货合同请求
    private static final int REQUEST_JIANYAN = 0x01;//检验检疫证请求
    private ArrayList<String> mSelectPath1;//被选中的供货合同图片地址集合
    private ArrayList<String> mSelectPath2;//被选中的检验检疫证书图片地址集合
    private ArrayList<File> mFileList1 = new ArrayList<>();//被选中的供货合同图片文件
    private ArrayList<File> mFileList2 = new ArrayList<>();//被选中的检验检疫证书图片文件

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
        this.mActivityFragmentView.viewMain(R.layout.activity_project_ghfsubmit);
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
        mCenterTextView.setText(XiangmuFragment.mInfoEntity.getName());
    }

    @Override
    public void initData() {
        super.initData();
    }

    @OnClick({R.id.tv_epDate, R.id.tv_hehong, R.id.tv_jianyan, R.id.tv_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_epDate:
                getDialogTime("请选择日期");
                break;
            case R.id.tv_hehong:
                addPic(REQUEST_HETONG);
                break;
            case R.id.tv_jianyan:
                addPic(REQUEST_JIANYAN);
                break;
            case R.id.tv_submit:
                if (checkData()) {
                    mActivityFragmentView.viewLoading(View.VISIBLE, getString(R.string.compressing));
                    FileUtils.compressImagesFromList(context, handler, mFileList1, mFileList2);
                }
                break;
        }
    }

    /**
     * 访问网络
     */
    private void requestNetwork() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        MultipartBuilder multipartBuilder = new MultipartBuilder();
        String PID = XiangmuFragment.mInfoEntity.getId();//项目信息ID
        String epName = etEpName.getText().toString().trim();//企业名称
        String epAdd = etEpAdd.getText().toString().trim();//企业地址
        String epDate = tvEpDate.getText().toString().trim();//供货日期
        String epTel = etEpTel.getText().toString().trim();//联系方式
        multipartBuilder.type(MultipartBuilder.FORM)//
                .addFormDataPart("uid", getLoginUid())//用户ID
                .addFormDataPart("pid", PID)//项目信息ID
                .addFormDataPart("epName", epName)//企业名称
                .addFormDataPart("epAdd", epAdd)//企业地址
                .addFormDataPart("epDate", epDate)//供货日期
                .addFormDataPart("epTel", epTel);//联系方式
        for (File file : mFileList1) {
            multipartBuilder.addFormDataPart(file.getName(), file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));//供货合同图片
        }
        for (File file : mFileList2) {
            multipartBuilder.addFormDataPart(file.getName(), file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));//检验检疫证书图片
        }
        RequestBody requestBody = multipartBuilder.build();
        Request request = builder.post(requestBody).url(ProtocolUrl.ROOT_URL + "/" + ProtocolUrl.PROJECT_SAVESUPPLIERMSG).build();
        mActivityFragmentView.viewLoading(View.VISIBLE);
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e(TAG, "onFailure: " + e.getMessage());
                e.printStackTrace();
                mActivityFragmentView.viewLoading(View.GONE);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        msg("网络请求错误！");
                    }
                });
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
                            Toast.makeText(ProjectGHFSubmitActivity.this, result, Toast.LENGTH_SHORT).show();
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
     * 判断数据是否正确
     *
     * @return
     */
    private boolean checkData() {
        if (ToolsKit.isEmpty(etEpName.getText().toString().trim())) {
            msg("企业名称不能为空！");
            etEpName.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(etEpAdd.getText().toString().trim())) {
            msg("企业地址不能为空！");
            etEpAdd.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(tvEpDate.getText().toString().trim())) {
            msg("请选择供货日期！");
            return false;
        }
        if (ToolsKit.isEmpty(etEpTel.getText().toString().trim())) {
            msg("联系方式不能为空！");
            etEpTel.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(tvHehong.getText().toString().trim())) {
            msg("请选择供货合同图片！");
            return false;
        }
        if (ToolsKit.isEmpty(tvJianyan.getText().toString().trim())) {
            msg("请选择检验检疫证书图片！");
            return false;
        }
        if (!ToolsRegex.isMobileNumber(etEpTel.getText().toString().trim())) {
            msg("联系方式格式错误！");
            etEpTel.requestFocus();
            return false;
        }
        return true;
    }

    private DateChooseWheelViewDialog getDialogTime(String title) {
        DateChooseWheelViewDialog startDateChooseDialog = new DateChooseWheelViewDialog(this, new DateChooseWheelViewDialog.DateChooseInterface() {
            @Override
            public void getDateTime(String time, boolean longTimeChecked) {
                tvEpDate.setText(time);
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
        if (request == REQUEST_HETONG) {
            if ((this.mSelectPath1 != null) && (this.mSelectPath1.size() > 0)) {
                picIntent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, this.mSelectPath1);
            }
            startActivityForResult(picIntent, REQUEST_HETONG);
        } else if (request == REQUEST_JIANYAN) {
            if ((this.mSelectPath2 != null) && (this.mSelectPath2.size() > 0)) {
                picIntent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, this.mSelectPath2);
            }
            startActivityForResult(picIntent, REQUEST_JIANYAN);
        }
    }

    @SuppressWarnings("static-access")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == REQUEST_HETONG) {
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
                        Log.i("mFileList1", String.valueOf(mFileList1.size()));
                        tvHehong.setText("图片名：" + fileNames.toString());
                        //                        this.mBitmapUtils.display(this.mUserHeaderImageView, headerImg);
                    }
                }
            } else if (requestCode == REQUEST_JIANYAN) {
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
                        Log.i("mFileList2", String.valueOf(mFileList2.size()));
                        tvJianyan.setText("图片名：" + fileNames.toString());
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
