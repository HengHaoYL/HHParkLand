package com.henghao.parkland.utils;

import com.henghao.parkland.BuildConfig;
import com.henghao.parkland.ProtocolUrl;
import com.higdata.okhttphelper.callback.StringCallback;
import com.squareup.okhttp.Call;

import com.higdata.okhttphelper.OkHttpController;
import com.higdata.okhttphelper.callback.BaseCallback;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 请求管理类，将所有网络请求写入此类以方便维护
 */

@SuppressWarnings("WeakerAccess")
public class Requester {

    public static final String HOST_DEBUG = "http://172.16.13.113:8080/Garden";
    public static final String HOST_RELEASE = "http://222.85.156.43:81/Garden";

    /**
     * 获取请求地址
     *
     * @param url 接口url
     * @return 请求url
     */
    public static String getRequestURL(String url) {
        String separator = "/";
        String host = BuildConfig.DEBUG ? HOST_DEBUG : HOST_RELEASE;
        if (!url.startsWith(separator)) url = "/" + url;
        return String.format("%s%s", host, url);
    }

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
     * @return {@link Call}
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
     * @return {@link Call}
     */
    public static Call login(String username, String password, String utid, BaseCallback callback) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username", username);
        params.put("password", password);
        params.put("utid", utid);
        return OkHttpController.doRequest(getRequestURL(ProtocolUrl.APP_LOGIN), params, callback);
    }

    /**
     * 养护信息填写
     *
     * @param yid        养护信息ID
     * @param uid        uid
     * @param treeId     植物二维码
     * @param yhSite     养护地点
     * @param yhWorker   养护人员
     * @param yhDetails  养护内容
     * @param yhTime     养护时间
     * @param yhQuestion 问题发现
     * @param yhClean    陆地保洁情况
     * @param treeGrowup 植物长势
     * @param yhComment  备注信息
     * @param callback   回调
     * @return {@link Call}
     */
    public static Call guanhuSubmit(String yid, String uid, String treeId, String yhSite, String yhWorker, String yhDetails, String yhTime, String yhQuestion, String yhClean, String treeGrowup, String yhComment, BaseCallback callback) {
        Map<String, Object> params = new HashMap<>();
        params.put("yid", yid);
        params.put("uid", uid);
        params.put("treeId", treeId);
        params.put("yhSite", yhSite);
        params.put("yhWorker", yhWorker);
        params.put("yhDetails", yhDetails);
        params.put("yhTime", yhTime);
        params.put("yhQuestion", yhQuestion);
        params.put("yhClean", yhClean);
        params.put("treeGrowup", treeGrowup);
        params.put("yhComment", yhComment);
        return OkHttpController.doRequest(getRequestURL(ProtocolUrl.SAVEGHMANAGEMSG), params, callback);
    }

    /**
     * 管护内容提交
     *
     * @param treeId       植物二维码
     * @param yhStatusname 养护状态
     * @param yhStatustime 养护时间
     * @param yhStatussite 养护地点
     * @param uid          uid
     * @param callback     回调
     * @return {@link Call}
     */
    public static Call maintenanceSubmit(String treeId, String yhStatusname, String yhStatustime, String yhStatussite, String uid, BaseCallback callback) {
        Map<String, Object> params = new HashMap<>();
        params.put("treeId", treeId);
        params.put("yhStatusname", yhStatusname);
        params.put("yhStatustime", yhStatustime);
        params.put("yhStatussite", yhStatussite);
        params.put("uid", uid);
        return OkHttpController.doRequest(getRequestURL(ProtocolUrl.SAVESTATUSMSG), params, callback);
    }

    /**
     * 查询当天签到次数
     *
     * @param uid      uid
     * @param callback 回调
     * @return {@link Call}
     */
    public static Call qiandaoQuery(String uid, BaseCallback callback) {
        Map<String, Object> params = new HashMap<>();
        params.put("uid", uid);
        return OkHttpController.doRequest(getRequestURL(ProtocolUrl.APP_NUMBEROFQIANDAO), params, callback);
    }

    /**
     * 提交植物信息
     *
     * @param treeId            植物二维码
     * @param treeName          植物名称
     * @param treeUse           植物用途
     * @param treeSpecification 植物规格
     * @param treeSite          种植地点
     * @param treeTime          录入时间
     * @param callback          回调
     * @return {@link Call}
     */
    public static Call treeSumit(String treeId, String treeName, String treeUse, String treeSpecification, String treeSite, String treeTime, BaseCallback callback) {
        Map<String, Object> params = new HashMap<>();
        params.put("treeId", treeId);
        params.put("treeName", treeName);
        params.put("treeUse", treeUse);
        params.put("treeSpecification", treeSpecification);
        params.put("treeSite", treeSite);
        params.put("treeTime", treeTime);
        return OkHttpController.doRequest(getRequestURL(ProtocolUrl.SAVETREE), params, callback);
    }
}
