package com.henghao.parkland.fragment.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.benefit.buy.library.phoneview.MultiImageSelectorActivity;
import com.benefit.buy.library.utils.tools.ToolsJson;
import com.benefit.buy.library.utils.tools.ToolsKit;
import com.benefit.buy.library.utils.tools.ToolsRegex;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.henghao.parkland.BuildConfig;
import com.henghao.parkland.R;
import com.henghao.parkland.activity.user.LoginAndRegActivity;
import com.henghao.parkland.fragment.FragmentSupport;
import com.henghao.parkland.model.entity.BaseEntity;
import com.henghao.parkland.utils.Requester;
import com.henghao.parkland.utils.FileUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.squareup.okhttp.Request;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 我的注册〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author zhangxianwen
 * @version HDMNV100R001, 2016年8月15日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class RegisterFragment extends FragmentSupport {

    /**
     * 用户名
     */
    @ViewInject(R.id.login_user)
    private EditText login_user;
    /**
     * 手机号
     */
    @ViewInject(R.id.login_phone)
    private EditText login_phone;
    /**
     * 联系人姓名
     */
    @ViewInject(R.id.login_contact)
    private EditText login_contact;
    /**
     * 身份证号
     */
    @ViewInject(R.id.login_IDCard)
    private EditText login_IDCard;
    /**
     * 邮箱
     */
    @ViewInject(R.id.login_email)
    private EditText login_email;
    /**
     * 密码
     */
    @ViewInject(R.id.login_pass)
    private EditText login_pass;
    /**
     * 确认密码
     */
    @ViewInject(R.id.login_passagain)
    private EditText login_passagain;
    /**
     * 确认密码
     */
    @ViewInject(R.id.tv_pic)
    private TextView tv_pic;

    private static final int REQUEST_IMAGE = 0x00;
    private ArrayList<String> mSelectPath;//图片地址
    private List<File> mFileList;//图片文件

    private static final String TAG = "RegisterFragment";

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

    public static FragmentSupport newInstance(Object obj) {
        RegisterFragment fragment = new RegisterFragment();
        if (fragment.object == null) {
            fragment.object = obj;
        }
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_reg);
        this.mActivityFragmentView.viewEmpty(R.layout.activity_empty);
        this.mActivityFragmentView.viewEmptyGone();
        this.mActivityFragmentView.viewLoading(View.GONE);
        mActivityFragmentView.getNavitionBarView().setVisibility(View.GONE);
        ViewUtils.inject(this, this.mActivityFragmentView);
        initWidget();
        initData();
        return this.mActivityFragmentView;
    }

    private void initData() {
        mLeftImageView = (ImageView) getActivity().findViewById(R.id.bar_left_img);
        mLeftImageView.setImageDrawable(getResources().getDrawable(R.drawable.btn_back));
        mCenterTextView = (TextView) getActivity().findViewById(R.id.bar_center_title);
        mCenterTextView.setText("注册");
    }


    public void initWidget() {
        mFileList = new ArrayList<>();
    }


    @OnClick({R.id.tv_pic, R.id.btn_layout})
    public void viewOnClick(View v) {
        switch (v.getId()) {
            case R.id.tv_pic:
                addPic();
                break;
            case R.id.btn_layout:
                //注册
                if (checkData()) {
                    mActivityFragmentView.viewLoading(View.VISIBLE, getString(R.string.compressing));
                    FileUtils.compressImagesFromList(getContext(), handler, mFileList);
                }
                break;
        }
    }

    private void request() {
        String userName = login_user.getText().toString().trim();//用户名
        String phone = login_phone.getText().toString().trim();//手机号
        final String contact = login_contact.getText().toString().trim();//姓名
        String IDCard = login_IDCard.getText().toString().trim();//身份证
        String email = login_email.getText().toString().trim();//邮箱
        String pwd = login_pass.getText().toString().trim();//密码

        //提交请求
        Requester.register(userName, pwd, phone, contact, IDCard, email, mFileList, registerCallback);
    }

    //请求回调
    private DefaultCallback registerCallback = new DefaultCallback() {
        @Override
        public void onFailure(Request request, Exception e, int code) {
            Log.e(TAG, "onFailure: " + e.getMessage());
            e.printStackTrace();
        }

        @Override
        public void onSuccess(String response) {
            String content = response;
            if (BuildConfig.DEBUG) Log.d(TAG, "result: " + content);
            try {
                Type type = new TypeToken<BaseEntity>() {
                }.getType();
                final BaseEntity mEntity = ToolsJson.parseObjecta(content, type);
                final int status = mEntity.getStatus();
                mActivity.msg(mEntity.getMsg());
                //注册成功
                if (status == 0) {
                    clearData();
                    LoginAndRegActivity loginAndRegActivity = (LoginAndRegActivity) getActivity();
                    loginAndRegActivity.setLoginFragment();
                }
            } catch (JsonSyntaxException e) {
                Log.e(TAG, "e:" + e);
                Toast.makeText(mActivity, "数据拉取失败，请稍后再试", Toast.LENGTH_SHORT).show();
            }
        }

    };

    private boolean checkData() {
        if (ToolsKit.isEmpty(login_user.getText().toString().trim())) {
            mActivity.msg("用户名不能为空");
            login_user.requestFocus();
            return false;
        } else if (ToolsKit.isEmpty(login_phone.getText().toString().trim())) {
            mActivity.msg("手机号码不能为空");
            login_phone.requestFocus();
            return false;
        } else if (!ToolsRegex.isMobileNumber(login_phone.getText().toString().trim())) {
            mActivity.msg("手机号码格式不正确");
            login_phone.requestFocus();
            return false;
        } else if (ToolsKit.isEmpty(login_contact.getText().toString().trim())) {
            mActivity.msg("姓名不能为空");
            login_contact.requestFocus();
            return false;
        } else if (ToolsKit.isEmpty(login_IDCard.getText().toString().trim())) {
            mActivity.msg("身份证号不能为空");
            login_IDCard.requestFocus();
            return false;
        } else if (!ToolsRegex.isIDCode(login_IDCard.getText().toString().trim())) {
            mActivity.msg("身份证号码格式不正确");
            login_IDCard.requestFocus();
            return false;
        } else if (ToolsKit.isEmpty(login_email.getText().toString().trim())) {
            mActivity.msg("邮箱不能为空");
            login_email.requestFocus();
            return false;
        } else if (!ToolsRegex.isEmail(login_email.getText().toString().trim())) {
            mActivity.msg("邮箱格式不正确");
            login_email.requestFocus();
            return false;
        } else if (ToolsKit.isEmpty(login_pass.getText().toString().trim())) {
            mActivity.msg("密码不能为空");
            login_pass.requestFocus();
            return false;
        } else if (ToolsKit.isEmpty(login_passagain.getText().toString().trim())) {
            mActivity.msg("密码确认不能为空");
            login_passagain.requestFocus();
            return false;
        } else if (!login_passagain.getText().toString().trim().equals(login_pass.getText().toString().trim())) {
            mActivity.msg("两次输入密码不相同");
            login_pass.requestFocus();
            return false;
        } else if (login_pass.getText().toString().trim().length() < 6 || login_pass.getText().toString().trim().length() > 16) {
            mActivity.msg("密码格式不正确，最短不能低于6位，最长不能高于16位");
            login_pass.requestFocus();
            return false;
        } else if (ToolsKit.isEmpty(tv_pic.getText().toString().trim())) {
            mActivity.msg("请上传身份证照片");
            return false;
        }
        return true;
    }

    /**
     * 清空数据
     */
    private void clearData() {
        login_user.setText("");
        login_phone.setText("");
        login_pass.setText("");
        login_passagain.setText("");
    }

    /**
     * 添加图片
     */
    private void addPic() {
        // 查看session是否过期
//        int selectedMode = MultiImageSelectorActivity.MODE_SINGLE;
        int selectedMode = MultiImageSelectorActivity.MODE_MULTI;
        int maxNum = 9;
        Intent picIntent = new Intent(getContext(), MultiImageSelectorActivity.class);
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
                        mFileList.clear();
                        for (String filePath : mSelectPath) {
                            File file = new File(filePath);
                            mFileList.add(file);
                        }
                        Log.i("mFileList", String.valueOf(mFileList.size()));
                        tv_pic.setText("已选择");
                    } else {//如果未选择图片，则清空数据
                        tv_pic.setText("");
                        tv_pic.setHint("请上传身份证正反照");
                    }
                }
            }
        }
    }
}
