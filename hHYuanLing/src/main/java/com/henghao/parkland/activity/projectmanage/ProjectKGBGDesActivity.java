package com.henghao.parkland.activity.projectmanage;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.benefit.buy.library.utils.tools.ToolsKit;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
import com.henghao.parkland.ProtocolUrl;
import com.henghao.parkland.R;
import com.henghao.parkland.model.entity.ProjectKGBGEntity;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.henghao.parkland.activity.projectmanage.ProjectMoneyActivity.makeDir;

/**
 * 项目管理 -- 开工报告详细信息
 */
public class ProjectKGBGDesActivity extends ActivityFragmentSupport {

    @InjectView(R.id.tv_time)
    TextView tvTime;
    @InjectView(R.id.tv_fileName)
    TextView tvFileName;
    @InjectView(R.id.tv_download)
    TextView tvDownload;
    @InjectView(R.id.tv_name)
    TextView tvName;
    private ProjectKGBGEntity mEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_project_kgbgdes);
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
        initWithCenterBar();
        mCenterTextView.setText("开工报告");
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        mEntity = (ProjectKGBGEntity) bundle.getSerializable(Constant.INTNET_DATA);
        tvName.setText(mEntity.getName());
        tvTime.setText(mEntity.getKgTime());
        tvFileName.setText(getFileName(mEntity.getKgDocument()));
    }

    private String getFileName(String filePath) {
        return filePath.substring(filePath.lastIndexOf("/") + 1);
    }

    @OnClick(R.id.tv_download)
    public void onClick() {
        downloadFile();
    }

    /**
     * 下载文件
     */
    private void downloadFile() {
        mActivityFragmentView.viewLoading(View.VISIBLE);
        String sdPath = getSDPath();
        if (!ToolsKit.isEmpty(sdPath)) {
            final File parentfile = new File(sdPath + "/ParKLand");
            makeDir(parentfile);
            /**
             * 访问网络下载文件
             */
            OkHttpClient okHttpClient = new OkHttpClient();
            Request.Builder builder = new Request.Builder();
            FormEncodingBuilder requestBodyBuilder = new FormEncodingBuilder();
            requestBodyBuilder.add("uid", getLoginUid());
            requestBodyBuilder.add("kid", String.valueOf(mEntity.getKid()));
            RequestBody requestBody = requestBodyBuilder.build();
            Request request = builder.post(requestBody).url(ProtocolUrl.ROOT_URL + "/" + ProtocolUrl.DOWNLOAD_KGREPORTFILE).build();
            Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ProjectKGBGDesActivity.this, "网络访问错误！", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    InputStream inputStream = response.body().byteStream();
                    final File file = new File(parentfile, getFileName(mEntity.getKgDocument()));
                    FileOutputStream fos = new FileOutputStream(file);
                    int len;
                    byte[] buffer = new byte[1024];
                    while ((len = inputStream.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mActivityFragmentView.viewLoading(View.GONE);
                            Toast.makeText(context, "文件下载成功！" + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        } else {
            Toast.makeText(context, "您当前没有SD卡，请插入SD卡后进行操作！", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 返回SD卡路径
     *
     * @return
     */
    public String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();
        }
        String dir = sdDir.toString();
        return dir;
    }
}
