package com.henghao.parkland.model.protocol;

import com.henghao.parkland.BuildConfig;
import com.henghao.parkland.ProtocolUrl;
import com.squareup.okhttp.Call;

import net.zombie_sama.okhttphelper.OkHttpController;
import net.zombie_sama.okhttphelper.callback.BaseCallback;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 请求管理类，将所有网络请求写入此类以方便维护
 */

public class Requester {

    public static final String HOST_DEBUG =
            "http://172.16.13.113:8080/Garden";
    //        "http://222.85.156.43:81/Garden";
    //        "http://safe.higdata.com/Java_Nfc/";
    //        "http://192.168.1.12/Java_Nfc";
    public static final String HOST_RELEASE = "http://222.85.156.43:81/Garden";

    /**
     * 注册
     *
     * @param userName          用户名
     * @param password          密码
     * @param tel               手机号
     * @param contact           姓名
     * @param legalPersonIDcard 身份证
     * @param email             邮箱
     * @param images            身份证正反面
     * @param callback          回调
     * @return Call
     */
    public static Call register(String userName, String password, String tel, String contact, String legalPersonIDcard, String email, List<File> images, BaseCallback callback) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", userName);
        params.put("password", password);
        params.put("tel", tel);
        params.put("contact", contact);
        params.put("legalPersonIDcard", legalPersonIDcard);//身份证
        params.put("email", email);//邮箱
        Map<String, File> files = new HashMap<>();
        for (File file : images) {
            files.put(file.getName(), file);
        }
        return OkHttpController.doRequest(getRequestURL(ProtocolUrl.APP_REG), params, files, callback);
    }

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @param utid     utid
     * @param callback 回调
     * @return Call
     */
    public static Call login(String username, String password, String utid, BaseCallback callback) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username", username);
        params.put("password", password);
        params.put("utid", utid);
        return OkHttpController.doRequest(getRequestURL(ProtocolUrl.APP_LOGIN), params, callback);
    }

    /**
     * 获取请求地址
     *
     * @param url 接口url
     * @return 请求url
     */
    private static String getRequestURL(String url) {
        String host = BuildConfig.DEBUG ? HOST_DEBUG : HOST_RELEASE;
        return String.format("%s/%s", host, url);
    }
}
