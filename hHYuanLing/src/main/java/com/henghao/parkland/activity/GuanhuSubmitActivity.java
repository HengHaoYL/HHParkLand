package com.henghao.parkland.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.benefit.buy.library.phoneview.MultiImageSelectorActivity;
import com.benefit.buy.library.utils.tools.ToolsKit;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.BuildConfig;
import com.henghao.parkland.R;
import com.henghao.parkland.utils.FileUtils;
import com.henghao.parkland.utils.Requester;
import com.henghao.parkland.views.FlowRadioGroup;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;


/**
 * Created by 晏琦云 on 2017/2/13.
 * 管护信息填写界面
 */

public class GuanhuSubmitActivity extends ActivityFragmentSupport {
    @InjectView(R.id.tv_treeid_guanhu)
    TextView tvTreeId;
    @InjectView(R.id.tv_yhsite_guanhu)
    TextView tvYhSite;
    @InjectView(R.id.tv_yhtime_guanhu)
    TextView tvYhTime;
    @InjectView(R.id.et_yhWorkder_guanhu)
    EditText etYhWorkder;
    @InjectView(R.id.et_yhDetails_guanhu)
    EditText etYhDetails;
    @InjectView(R.id.et_comment_guanhu)
    EditText etComment;
    @InjectView(R.id.btn_submit_guanhu)
    Button btnSubmit;
    @InjectView(R.id.btn_cancel_guanhu)
    Button btnCancel;
    @InjectView(R.id.rg_clean_guanhu)
    RadioGroup rgClean;
    @InjectView(R.id.rg_treegrowup_guanhu)
    RadioGroup rgTreegrowup;
    @InjectView(R.id.rg_question_guanhu)
    FlowRadioGroup rgQuestion;
    @InjectView(R.id.tv_yhbefore)
    TextView tv_yhbefore;
    @InjectView(R.id.tv_yhafter)
    TextView tv_yhafter;

    private int yid;//养护信息ID
    private String treeId;//植物二维码
    private String yhSite;//养护地点
    private String yhTime;//养护时间
    private String yhWorker;//养护人员
    private String yhDetails;//养护内容
    private String yhQuestion = "无";//问题发现
    private String yhClean = "好";//陆地保洁情况
    private String treeGrowup = "好";//植物长势
    private String yhComment;//备注信息
    private static final String TAG = "GuanhuSubmitActivity";
    private Call call;

    private static final int REQUEST_BEFORE = 0x00;
    private static final int REQUEST_AFTER = 0x01;
    private ArrayList<String> mSelectPathBefore;//养护前图片地址
    private ArrayList<String> mSelectPathAfter;//养护后图片地址
    private List<File> mFileList;//图片文件集合
    private File mFileBefore;//养护前图片文件
    private File mFileAfter;//养护后图片文件

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case FileUtils.COMPRESS_FINISH:
                    mActivityFragmentView.viewLoading(View.GONE);
                    request();
                    break;
                case FileUtils.COMPRESS_PROGRESS://压缩进度
                    mActivityFragmentView.setLoadingText(getString(R.string.compressing) + " " + msg.arg1 + "/" + msg.arg2);
                    break;
            }
        }
    };

    private void request() {
        //访问网络，提交数据
        call = Requester.guanhuSubmit(
                String.valueOf(yid),
                getLoginUid(),
                treeId,
                yhSite,
                yhWorker,
                yhDetails,
                yhTime,
                yhQuestion,
                yhClean,
                treeGrowup,
                yhComment,
                mFileBefore,
                mFileAfter,
                callback);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_guanhu);
        this.mActivityFragmentView.viewEmpty(R.layout.activity_empty);
        this.mActivityFragmentView.viewEmptyGone();
        this.mActivityFragmentView.viewLoading(View.GONE);
        this.mActivityFragmentView.getNavitionBarView().setVisibility(View.VISIBLE);
        setContentView(this.mActivityFragmentView);
        ButterKnife.inject(this);
        initData();
    }

    /**
     * 初始化数据
     */
    public void initData() {
        initWithBar();
        initWithCenterBar();
        mCenterTextView.setText("管护信息");
        Intent intent = getIntent();
        yid = intent.getIntExtra("yid", 0);
        treeId = intent.getStringExtra("treeId");
        yhSite = intent.getStringExtra("yhSite");
        yhTime = intent.getStringExtra("yhTime");
        tvTreeId.setText(treeId);
        tvYhSite.setText(yhSite);
        tvYhTime.setText(yhTime);
        ((RadioButton) rgQuestion.getChildAt(0)).setChecked(true);
        ((RadioButton) rgTreegrowup.getChildAt(0)).setChecked(true);
        ((RadioButton) rgClean.getChildAt(0)).setChecked(true);
        rgQuestion.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_question_one://无
                        yhQuestion = "无";
                        break;
                    case R.id.rb_question_two://有病虫害
                        yhQuestion = "有病虫害";
                        break;
                    case R.id.rb_question_three://有病虫害
                        yhQuestion = "施肥";
                        break;
                    case R.id.rb_question_four://有病虫害
                        yhQuestion = "破坏";
                        break;
                    case R.id.rb_question_five://有病虫害
                        yhQuestion = "须冲洗";
                        break;
                }
            }
        });
        rgClean.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_clean_one:
                        yhClean = "好";
                        break;
                    case R.id.rb_clean_two:
                        yhClean = "良好";
                        break;
                    case R.id.rb_clean_three:
                        yhClean = "差";
                        break;
                }
            }
        });
        rgTreegrowup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_treegroup_one:
                        treeGrowup = "好";
                        break;
                    case R.id.rb_treegroup_two:
                        treeGrowup = "良好";
                        break;
                    case R.id.rb_treegroup_three:
                        treeGrowup = "差";
                        break;
                }
            }
        });
    }

    @OnClick({R.id.btn_submit_guanhu, R.id.btn_cancel_guanhu, R.id.tv_yhbefore, R.id.tv_yhafter})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_yhbefore:
                addPicBefore();
                break;
            case R.id.tv_yhafter:
                addPicAfter();
                break;
            case R.id.btn_submit_guanhu:
                if (CheckData()) {
                    mActivityFragmentView.viewLoading(View.VISIBLE, getString(R.string.compressing));
                    mFileList = new ArrayList<>();
                    mFileList.add(mFileBefore);
                    mFileList.add(mFileAfter);
                    FileUtils.compressImagesFromList(context, handler, mFileList);
                }
                break;
            case R.id.btn_cancel_guanhu:
                finish();
                break;
        }
    }

    private boolean CheckData() {
        yhWorker = etYhWorkder.getText().toString().trim();
        yhDetails = etYhDetails.getText().toString().trim();
        yhComment = etComment.getText().toString().trim();
        if (yhWorker.equals("") || yhWorker == null) {
            msg("请输入养护人员！");
            etYhWorkder.requestFocus();
            return false;
        }
        if (yhDetails.equals("") || yhDetails == null) {
            msg("请输入养护内容！");
            etYhDetails.requestFocus();
            return false;
        }
        if (mFileBefore == null) {
            msg("请添加养护前图片！");
            return false;
        }
        if (mFileAfter == null) {
            msg("请添加养护后图片！");
            return false;
        }
        if (yhComment.equals("") || yhComment == null) {
            yhComment = "无";
        }
        return true;
    }

    /**
     * 添加养护前图片
     */
    private void addPicBefore() {
        // 查看session是否过期
        int selectedMode = MultiImageSelectorActivity.MODE_SINGLE;
//        int selectedMode = MultiImageSelectorActivity.MODE_MULTI;
        int maxNum = 1;
        Intent picIntent = new Intent(this, MultiImageSelectorActivity.class);
        // 是否显示拍摄图片
        picIntent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        // 最大可选择图片数量
        picIntent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, maxNum);
        // 选择模式
        picIntent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, selectedMode);
        // 默认选择
        if ((this.mSelectPathBefore != null) && (this.mSelectPathBefore.size() > 0)) {
            picIntent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, this.mSelectPathBefore);
        }
        startActivityForResult(picIntent, REQUEST_BEFORE);
    }

    /**
     * 添加养护后图片
     */
    private void addPicAfter() {
        // 查看session是否过期
        int selectedMode = MultiImageSelectorActivity.MODE_SINGLE;
//        int selectedMode = MultiImageSelectorActivity.MODE_MULTI;
        int maxNum = 1;
        Intent picIntent = new Intent(this, MultiImageSelectorActivity.class);
        // 是否显示拍摄图片
        picIntent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        // 最大可选择图片数量
        picIntent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, maxNum);
        // 选择模式
        picIntent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, selectedMode);
        // 默认选择
        if ((this.mSelectPathAfter != null) && (this.mSelectPathAfter.size() > 0)) {
            picIntent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, this.mSelectPathAfter);
        }
        startActivityForResult(picIntent, REQUEST_AFTER);
    }

    private DefaultCallback callback = new DefaultCallback() {
        @Override
        public void onFailure(Exception e, int code) {
            if (BuildConfig.DEBUG) Log.e(TAG, "onFailure: code = " + code, e);
            e.printStackTrace();
            Toast.makeText(context, "网络访问错误！", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onSuccess(String response) {
            if (BuildConfig.DEBUG) Log.i(TAG, "onResponse: " + response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                int status = jsonObject.getInt("status");//错误代码 0 正确 1 错误
                if (status == 0) {
                    final String result = jsonObject.getString("result");
                    Toast.makeText(GuanhuSubmitActivity.this, result, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(GuanhuSubmitActivity.this, "添加失败，请重试！", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                Toast.makeText(GuanhuSubmitActivity.this, "服务器错误，请稍后重试！", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (call != null && !call.isCanceled()) {
            call.cancel();
        }
    }

    @SuppressWarnings("static-access")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == REQUEST_BEFORE) {
                if ((resultCode == Activity.RESULT_OK) || (resultCode == Activity.RESULT_CANCELED)) {
                    this.mSelectPathBefore = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    if (!ToolsKit.isEmpty(this.mSelectPathBefore)) {
                        String filePath = mSelectPathBefore.get(0);
                        String imageName = getImageName(filePath);
                        mFileBefore = new File(filePath);
                        List<String> imgNames = new ArrayList<>();
                        imgNames.add(imageName);
                        tv_yhbefore.setText("图片名：" + imgNames.toString());
                    }
                }
            }
            if (requestCode == REQUEST_AFTER) {
                if ((resultCode == Activity.RESULT_OK) || (resultCode == Activity.RESULT_CANCELED)) {
                    this.mSelectPathAfter = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    if (!ToolsKit.isEmpty(this.mSelectPathAfter)) {
                        String filePath = mSelectPathAfter.get(0);
                        String imageName = getImageName(filePath);
                        mFileAfter = new File(filePath);
                        List<String> imgNames = new ArrayList<>();
                        imgNames.add(imageName);
                        tv_yhafter.setText("图片名：" + imgNames.toString());
                    }
                }
            }
        }
    }

    private String getImageName(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }
}
