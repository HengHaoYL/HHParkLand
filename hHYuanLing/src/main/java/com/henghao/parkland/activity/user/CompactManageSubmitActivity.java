package com.henghao.parkland.activity.user;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.benefit.buy.library.phoneview.MultiImageSelectorActivity;
import com.benefit.buy.library.utils.tools.ToolsKit;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.ProtocolUrl;
import com.henghao.parkland.R;
import com.henghao.parkland.utils.FileUtils;
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
 * Created by 晏琦云 on 2017/4/21.
 * 合同管理提交界面
 */

public class CompactManageSubmitActivity extends ActivityFragmentSupport {

    @InjectView(R.id.tv_genre)
    TextView tvGenre;
    @InjectView(R.id.tv_pic)
    TextView tvPic;
    @InjectView(R.id.tv_document)
    TextView tvDocument;
    @InjectView(R.id.tv_submit)
    TextView tvSubmit;

    private static final String TAG = "ProjectGHFSubmitActivit";
    private static final int REQUEST_HETONG = 0x00;//合同请求
    private static final int REQUEST_FILE = 0x1001;//选择文件请求

    private ArrayList<String> mSelectPath;//被选中的合同图片地址集合
    private ArrayList<File> mFileList = new ArrayList<>();//被选中的合同图片
    private File mFile;//被选中的合同文件
    private String URL;//网络请求地址

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
        this.mActivityFragmentView.viewMain(R.layout.activity_compact_managesubmit);
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
        Intent intent = getIntent();
        tvGenre.setText(intent.getStringExtra("Type"));
        initWithBar();
        mLeftTextView.setText("");
        mLeftTextView.setVisibility(View.VISIBLE);
        initWithCenterBar();
        mCenterTextView.setText("合同管理");
    }

    @Override
    public void initData() {
        super.initData();
    }

    @OnClick({R.id.tv_pic, R.id.tv_document, R.id.tv_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_pic:
                addPic(REQUEST_HETONG);
                break;
            case R.id.tv_document:
                showFileChooser();
                break;
            case R.id.tv_submit:
                if (checkData()) {
                    mActivityFragmentView.viewLoading(View.VISIBLE, getString(R.string.compressing));
                    FileUtils.compressImagesFromList(context, handler, mFileList);
                }
                break;
        }
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

    /**
     * 设置网络访问地址
     */
    private void setURL() {
        String mType = tvGenre.getText().toString().trim();
        switch (mType) {
            case "园林类":
                URL = ProtocolUrl.ROOT_URL + "/" + ProtocolUrl.COMPACT_SAVEGARDENCOMPACT;
                break;
            case "建设类":
                URL = ProtocolUrl.ROOT_URL + "/" + ProtocolUrl.COMPACT_SAVEBUILDCOMPACT;
                break;
            case "园林工程类":
                URL = ProtocolUrl.ROOT_URL + "/" + ProtocolUrl.COMPACT_SAVEENGINEERINGCOMPACT;
                break;
            case "景观类":
                URL = ProtocolUrl.ROOT_URL + "/" + ProtocolUrl.COMPACT_SAVELANDSCAPECOMPACT;
                break;
        }
    }

    /**
     * 访问网络
     */
    private void requestNetwork() {
        setURL();
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        MultipartBuilder multipartBuilder = new MultipartBuilder();
        multipartBuilder.type(MultipartBuilder.FORM)//
                .addFormDataPart("uid", getLoginUid())//用户ID
                .addFormDataPart("genre", tvGenre.getText().toString().trim())//合同类型
                .build();
        for (File file : mFileList) {
            multipartBuilder.addFormDataPart("files", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));//合同图片
        }
        multipartBuilder.addFormDataPart("file", mFile.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), mFile));//合同文件
        RequestBody requestBody = multipartBuilder.build();
        Request request = builder.post(requestBody).url(URL).build();
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
                            Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
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
        if (ToolsKit.isEmpty(tvPic.getText().toString().trim())) {
            msg("请选择合同图片！");
            return false;
        }
        if (ToolsKit.isEmpty(tvDocument.getText().toString().trim())) {
            msg("请选择合同文件！");
            return false;
        }
        return true;
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
            if ((this.mSelectPath != null) && (this.mSelectPath.size() > 0)) {
                picIntent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, this.mSelectPath);
            }
            startActivityForResult(picIntent, REQUEST_HETONG);
        }
    }

    @SuppressWarnings("static-access")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == REQUEST_HETONG) {
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
                        Log.i("mFileList1", String.valueOf(mFileList.size()));
                        tvPic.setText("图片名：" + fileNames.toString());
                        //                        this.mBitmapUtils.display(this.mUserHeaderImageView, headerImg);
                    }
                }
            }
            if (requestCode == REQUEST_FILE) {
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    String path = FileUtils.getPath(this, uri);
                    if (!checkFile(path)) {
                        Toast.makeText(context, "文件格式不正确", Toast.LENGTH_SHORT).show();
                    }
                    mFile = new File(path);
                    tvDocument.setText("文件名：" + mFile.getName());
                }
            }
        }
    }

    private String getImageName(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    private String[] suffixEnable = {".doc", ".docx", ".pdf"};

    private boolean checkFile(String path) {
        for (String suffix : suffixEnable)
            if (path.endsWith(suffix)) return true;
        return false;
    }
}
