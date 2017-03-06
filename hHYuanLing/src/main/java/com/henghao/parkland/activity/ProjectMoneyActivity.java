package com.henghao.parkland.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.benefit.buy.library.http.query.callback.AjaxStatus;
import com.benefit.buy.library.utils.tools.ToolsKit;
import com.benefit.buy.library.views.xlistview.XListView;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
import com.henghao.parkland.ProtocolUrl;
import com.henghao.parkland.R;
import com.henghao.parkland.adapter.ProjectMoneyAdapter;
import com.henghao.parkland.model.entity.SGWalletEntity;
import com.henghao.parkland.model.protocol.ProjectProtocol;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 施工钱包
 */
public class ProjectMoneyActivity extends ActivityFragmentSupport implements XListView.IXListViewListener {

    private String[] title = {"日期", "支出类型", "金额"};

    @ViewInject(R.id.listview)
    private XListView mXlistview;

    private TextView tv_total_money;

    private ProjectMoneyAdapter mMoneyAdapter;

    private List<SGWalletEntity> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_project_money);
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
    }

    @Override
    public void initData() {
        super.initData();
        initWithBar();
        initWithRightBar();
        mRightTextView.setText("导出EXCEL");
        mRightTextView.setVisibility(View.VISIBLE);
        mLeftTextView.setVisibility(View.VISIBLE);
        mLeftTextView.setText("施工钱包");
        mXlistview.setXListViewListener(this);
        initView();
        mMoneyAdapter = new ProjectMoneyAdapter(this);
        /**
         * 下载文件
         */
        mRightLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sdPath = getSDPath();
                if (!ToolsKit.isEmpty(sdPath)) {
                    final File parentfile = new File(sdPath + "/ParKLand");
                    makeDir(parentfile);
                    /**
                     * 访问网络
                     */
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request.Builder builder = new Request.Builder();
                    FormEncodingBuilder requestBodyBuilder = new FormEncodingBuilder();
                    requestBodyBuilder.add("uid", getLoginUid());
                    RequestBody requestBody = requestBodyBuilder.build();
                    Request request = builder.post(requestBody).url(ProtocolUrl.ROOT_URL + "/" + ProtocolUrl.DOWNLOAD_WALLETEXCEL).build();
                    Call call = okHttpClient.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ProjectMoneyActivity.this, "网络访问错误！", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onResponse(Response response) throws IOException {
                            InputStream inputStream = response.body().byteStream();
                            Date date = new Date();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
                            String fileName = dateFormat.format(date);
                            final File file = new File(parentfile, fileName + ".xls");
                            file.createNewFile();
                            file.setWritable(Boolean.TRUE);
                            FileOutputStream fos = new FileOutputStream(file);
                            int len;
                            byte[] buffer = new byte[1024];
                            while ((len = inputStream.read(buffer)) != -1) {
                                fos.write(buffer, 0, len);
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context, "文件下载成功！" + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    });
                } else {
                    Toast.makeText(context, "您当前没有SD卡，请插入SD卡后进行操作！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
        View HeaderView = LayoutInflater.from(this).inflate(R.layout.include_poject_money, mXlistview, false);
        TextView tv_title = (TextView) HeaderView.findViewById(R.id.tv_title);
        tv_total_money = (TextView) HeaderView.findViewById(R.id.tv_total_money);
        tv_title.setText("施工钱包");
        mXlistview.addHeaderView(HeaderView);
        /**
         * 访问网络数据
         */
        ProjectProtocol mProjectProtocol = new ProjectProtocol(this);
        mProjectProtocol.addResponseListener(this);
        SharedPreferences preferences = getLoginUserSharedPre();
        String UID = preferences.getString(Constant.USERID, null);
        mProjectProtocol.querySGWallet(UID);
        mActivityFragmentView.viewLoading(View.VISIBLE);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void OnMessageResponse(String url, Object jo, AjaxStatus status) throws JSONException {
        super.OnMessageResponse(url, jo, status);
        if (url.endsWith(ProtocolUrl.PROJECT_SGWALLET)) {
            if (jo instanceof List) {
                List<SGWalletEntity> data = (List<SGWalletEntity>) jo;
                mList.addAll(data);
                mMoneyAdapter.setData(data);
                mXlistview.setAdapter(mMoneyAdapter);
                mMoneyAdapter.notifyDataSetChanged();
                SGWalletEntity entity = data.get(data.size() - 1);
                final double money = entity.getMoney();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_total_money.setText("总金额：" + money + "元");
                    }
                });
            }
        }
    }

    public static void makeDir(File dir) {
        if (!dir.getParentFile().exists()) {
            makeDir(dir.getParentFile());
        }
        dir.mkdir();
    }

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
