package com.henghao.parkland.activity.user;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.benefit.buy.library.utils.tools.ToolsKit;
import com.benefit.buy.library.views.NoScrollGridView;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
import com.henghao.parkland.R;
import com.henghao.parkland.adapter.CommonGridViewAdapter;
import com.henghao.parkland.model.entity.CompactEntity;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.henghao.parkland.activity.projectmanage.ProjectMoneyActivity.makeDir;

/**
 * Created by 晏琦云 on 2017/4/21.
 * 合同管理详情界面
 */

public class CompactManageDesActivity extends ActivityFragmentSupport {

    @InjectView(R.id.tv_genre)
    TextView tvGenre;
    @InjectView(R.id.tv_checking)
    TextView tvChecking;
    @InjectView(R.id.tv_dates)
    TextView tvDates;
    @InjectView(R.id.tv_fileName)
    TextView tvFileName;
    @InjectView(R.id.tv_download)
    TextView tvDownload;
    @InjectView(R.id.gridView)
    NoScrollGridView gridView;
    CommonGridViewAdapter mAdapter;
    @InjectView(R.id.tv_comment)
    TextView tvComment;
    @InjectView(R.id.ll_comment)
    LinearLayout llComment;
    private CompactEntity mEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_compact_managedes);
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
        mCenterTextView.setText("合同管理");
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        mEntity = (CompactEntity) bundle.getSerializable(Constant.INTNET_DATA);
        /**
         * 拼接图片URL地址
         */
        ArrayList<String> data = new ArrayList<>();
        ArrayList<String> urls = mEntity.getUrl();
        for (String url : urls) {
            data.add(mEntity.getPictureId() + url);
        }
        mAdapter = new CommonGridViewAdapter(this, data);
        gridView.setAdapter(mAdapter);
        tvChecking.setText(mEntity.getChecking());
        tvDates.setText(mEntity.getDates());
        tvGenre.setText(mEntity.getGenre());
        tvFileName.setText(getFileName(mEntity.getDocument()));
        switch (mEntity.getChecking()) {
            case "正在审核":
                llComment.setVisibility(View.GONE);
                tvChecking.setTextColor(getContext().getResources().getColor(R.color.orange));
                break;
            case "审核通过":
                llComment.setVisibility(View.GONE);
                tvChecking.setTextColor(getContext().getResources().getColor(R.color.green));
                break;
            case "审核未通过":
                llComment.setVisibility(View.VISIBLE);
                tvComment.setText(mEntity.getComment());
                tvChecking.setTextColor(getContext().getResources().getColor(R.color.red));
                break;
        }
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
            final File parentfile = new File(sdPath + "/ParKLand/Compact/");
            makeDir(parentfile);
            /**
             * 访问网络下载文件
             */
            OkHttpClient okHttpClient = new OkHttpClient();
            Request.Builder builder = new Request.Builder();
            Request request = builder.get().url(mEntity.getCompactId() + mEntity.getDocument()).build();
            Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(CompactManageDesActivity.this, "网络访问错误！", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    InputStream inputStream = response.body().byteStream();
                    final File file = new File(parentfile, getFileName(mEntity.getDocument()));
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
                            msg("文件下载成功！" + file.getAbsolutePath());
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
