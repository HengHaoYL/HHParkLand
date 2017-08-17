package com.henghao.parkland.fragment.user;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.henghao.parkland.utils.FileUtils;
import com.henghao.parkland.utils.Requester;
import com.henghao.parkland.views.FlowRadioGroup;
import com.higdata.okhttphelper.callback.BytesCallback;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 我的注册〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author zhangxianwen
 * @version HDMNV100R001, 2016年8月15日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class RegisterFragment extends FragmentSupport {

    @InjectView(R.id.et_userName_register)
    EditText etUserName;
    @InjectView(R.id.et_tel_register)
    EditText etTel;
    @InjectView(R.id.et_name_register)
    EditText etName;
    @InjectView(R.id.et_idCard_register)
    EditText etIdCard;
    @InjectView(R.id.et_email_register)
    EditText etEmail;
    @InjectView(R.id.et_passWord_register)
    EditText etPassWord;
    @InjectView(R.id.et_passWord_confirm_register)
    EditText etPassWordConfirm;
    @InjectView(R.id.tv_picture_register)
    TextView tvPicture;
    @InjectView(R.id.rb_male_register)
    RadioButton rbMale;
    @InjectView(R.id.rb_female_register)
    RadioButton rbFemale;
    @InjectView(R.id.rg_sex_register)
    FlowRadioGroup rgSex;
    @InjectView(R.id.et_companyName_register)
    EditText etCompanyName;
    @InjectView(R.id.et_userCode_register)
    EditText etUserCode;
    @InjectView(R.id.iv_authCode_register)
    ImageView ivAuthCode;

    private static final int REQUEST_IMAGE = 0x00;

    private int sex;//性别（0：男 1：女）
    private ArrayList<String> mSelectPath;//图片地址
    private List<File> mFileList;//图片文件
    private Call registerCall;//注册请求
    private Call authCodeCall;//验证码请求

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
        this.mActivityFragmentView.viewMain(R.layout.activity_register);
        this.mActivityFragmentView.viewEmpty(R.layout.activity_empty);
        this.mActivityFragmentView.viewEmptyGone();
        this.mActivityFragmentView.viewLoading(View.GONE);
        mActivityFragmentView.getNavitionBarView().setVisibility(View.GONE);
        ButterKnife.inject(this, this.mActivityFragmentView);
        initWidget();
        initData();
        return this.mActivityFragmentView;
    }

    private void initData() {
        mLeftImageView = (ImageView) getActivity().findViewById(R.id.bar_left_img);
        mLeftImageView.setImageDrawable(getResources().getDrawable(R.drawable.btn_back));
        mCenterTextView = (TextView) getActivity().findViewById(R.id.bar_center_title);
        mCenterTextView.setText("注册");
        //设置性别选择监听器
        ((RadioButton) rgSex.getChildAt(0)).setChecked(true);
        rgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_male_register://男
                        sex = 0;
                        break;
                    case R.id.rb_female_register://女
                        sex = 1;
                        break;
                }
            }
        });
    }

    public void initWidget() {
        mFileList = new ArrayList<>();
    }

    @OnClick({R.id.tv_picture_register, R.id.iv_authCode_register, R.id.btn_layout})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.tv_picture_register:
                addPic();
                break;
            case R.id.iv_authCode_register:
                authCodeCall = Requester.authCode(authCodeCallBack);//请求服务器更换验证码
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
        String userName = etUserName.getText().toString().trim();//用户名
        String passWord = etPassWord.getText().toString().trim();//密码
        String name = etName.getText().toString().trim();//姓名
        String tel = etTel.getText().toString().trim();//手机号
        String email = etEmail.getText().toString().trim();//邮箱
        String idCard = etIdCard.getText().toString().trim();//身份证号
        String companyName = etCompanyName.getText().toString().trim();//企业名称
        if (ToolsKit.isEmpty(companyName)) {
            companyName = "无权限";
        }
        String userCode = etUserCode.getText().toString().trim();//验证码
        //提交请求
        registerCall = Requester.register(userName, passWord, name, tel, email, sex, idCard, companyName, mFileList, userCode, registerCallback);
    }

    private BytesCallback authCodeCallBack = new BytesDefaultCallback() {//验证码回调
        @Override
        public void onFailure(Exception e, int code) {
            if (BuildConfig.DEBUG) Log.e(TAG, "onFailure: ", e);
            Toast.makeText(mActivity, "网络访问错误！", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onSuccess(byte[] response) {
            if (BuildConfig.DEBUG) Log.d(TAG, "onSuccess: ========================验证码");
            Bitmap bitmap = BitmapFactory.decodeByteArray(response, 0, response.length);
            ivAuthCode.setImageBitmap(bitmap);
        }
    };
    //请求回调
    private DefaultCallback registerCallback = new DefaultCallback() {
        @Override
        public void onFailure(Exception e, int code) {
            if (BuildConfig.DEBUG) Log.e(TAG, "onFailure: code = " + code, e);
            e.printStackTrace();
            Toast.makeText(mActivity, "网络访问错误！", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onSuccess(String response) {
            String content = response;
            if (BuildConfig.DEBUG) Log.d(TAG, "result: " + content);
            try {
                Type type = new TypeToken<BaseEntity>() {
                }.getType();
                BaseEntity mEntity = ToolsJson.parseObjecta(content, type);
                int errorCode = mEntity.getErrorCode();
                if (errorCode > 0) {
                    mActivity.msg(mEntity.getMsg());
                    return;
                } else if (errorCode == 0) {
                    mActivity.msg(mEntity.getMsg());
                    //注册成功
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
        if (ToolsKit.isEmpty(etUserName.getText().toString().trim())) {
            mActivity.msg("用户名不能为空");
            etUserName.requestFocus();
            return false;
        } else if (ToolsKit.isEmpty(etPassWord.getText().toString().trim())) {
            mActivity.msg("密码不能为空");
            etPassWord.requestFocus();
            return false;
        } else if (ToolsKit.isEmpty(etPassWordConfirm.getText().toString().trim())) {
            mActivity.msg("确认密码不能为空");
            etPassWordConfirm.requestFocus();
            return false;
        } else if (!etPassWord.getText().toString().trim().equals(etPassWordConfirm.getText().toString().trim())) {
            mActivity.msg("两次输入密码不相同");
            etPassWord.requestFocus();
            return false;
        } else if (etPassWord.getText().toString().trim().length() < 6 || etPassWord.getText().toString().trim().length() > 16) {
            mActivity.msg("密码格式不正确，最短不能低于6位，最长不能高于16位");
            etPassWord.requestFocus();
            return false;
        } else if (ToolsKit.isEmpty(etName.getText().toString().trim())) {
            mActivity.msg("姓名不能为空");
            etName.requestFocus();
            return false;
        } else if (ToolsKit.isEmpty(etTel.getText().toString().trim())) {
            mActivity.msg("手机号码不能为空");
            etTel.requestFocus();
            return false;
        } else if (!ToolsRegex.isMobileNumber(etTel.getText().toString().trim())) {
            mActivity.msg("手机号码格式不正确");
            etTel.requestFocus();
            return false;
        } else if (ToolsKit.isEmpty(etEmail.getText().toString().trim())) {
            mActivity.msg("邮箱不能为空");
            etEmail.requestFocus();
            return false;
        } else if (!ToolsRegex.isEmail(etEmail.getText().toString().trim())) {
            mActivity.msg("邮箱格式不正确");
            etEmail.requestFocus();
            return false;
        } else if (ToolsKit.isEmpty(etIdCard.getText().toString().trim())) {
            mActivity.msg("身份证号不能为空");
            etIdCard.requestFocus();
            return false;
        } else if (!ToolsRegex.isIDCode(etIdCard.getText().toString().trim())) {
            mActivity.msg("身份证号码格式不正确");
            etIdCard.requestFocus();
            return false;
        } else if (ToolsKit.isEmpty(tvPicture.getText().toString().trim())) {
            mActivity.msg("请上传身份证照片");
            return false;
        } else if (ToolsKit.isEmpty(etUserCode.getText().toString().trim())) {
            mActivity.msg("请输入验证码");
            etUserCode.requestFocus();
            return false;
        }
        return true;
    }

    /**
     * 清空数据
     */
    private void clearData() {
        etUserName.setText("");
        etPassWord.setText("");
        etPassWordConfirm.setText("");
        etName.setText("");
        etTel.setText("");
        etEmail.setText("");
        etIdCard.setText("");
        etCompanyName.setText("");
        etUserCode.setText("");
        mSelectPath = null;
        mFileList.clear();
        tvPicture.setText("");
        tvPicture.setHint("请上传身份证正反照");
    }

    /**
     * 添加图片
     */
    private void addPic() {
        // 查看session是否过期
//        int selectedMode = MultiImageSelectorActivity.MODE_SINGLE;
        int selectedMode = MultiImageSelectorActivity.MODE_MULTI;
        int maxNum = 2;
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
                        tvPicture.setText("已选择");
                    } else {//如果未选择图片，则清空数据
                        tvPicture.setText("");
                        tvPicture.setHint("请上传身份证正反照");
                    }
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //刷新验证码，以防失效
        authCodeCall = Requester.authCode(authCodeCallBack);//请求服务器更换验证码
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (registerCall != null && !registerCall.isCanceled()) {
            registerCall.cancel();
        }
        if (authCodeCall != null && !authCodeCall.isCanceled()) {
            authCodeCall.cancel();
        }
        ButterKnife.reset(this);
    }
}
