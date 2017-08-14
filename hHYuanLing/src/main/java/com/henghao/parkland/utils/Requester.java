package com.henghao.parkland.utils;

import com.alibaba.fastjson.JSON;
import com.henghao.parkland.ProtocolUrl;
import com.henghao.parkland.model.entity.DeleteEntity;
import com.higdata.okhttphelper.OkHttpController;
import com.higdata.okhttphelper.callback.BaseCallback;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * 请求管理class，将所有网络请求写入此类以方便维护
 */
public class Requester {

    /**
     * 获取请求地址
     *
     * @param url 接口url
     * @return 请求url
     */
    public static String getRequestURL(String url) {
        String separator = "/";
        String host = ProtocolUrl.ROOT_URL;
        if (!url.startsWith(separator)) url = "/" + url;
        return String.format("%s%s", host, url);
    }

    /************************
     * 用户登录相关
     **************************/
    /**
     * 获取验证码
     *
     * @param headers  请求头
     * @param callback 回调
     * @return
     */
    public static Call authCode(Map<String, String> headers, BaseCallback callback) {
        return OkHttpController.doRequest(getRequestURL(ProtocolUrl.AUTHCODE), headers, callback);
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
        return OkHttpController.doRequest(getRequestURL(ProtocolUrl.APP_REG), params, files, null, callback);
    }

    /**
     * 登录
     *
     * @param userName 用户名
     * @param passWord 密码
     * @param userCode 验证码
     * @param headers  请求头
     * @param callback 回调
     * @return {@link Call}
     */
    public static Call login(String userName, String passWord, String userCode, Map<String, String> headers, BaseCallback callback) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userName", userName);
        params.put("passWord", passWord);
        params.put("userCode", userCode);
        return OkHttpController.doRequest(getRequestURL(ProtocolUrl.APP_LOGIN), params, headers, callback);
    }
    /************************ 用户登录相关end **************************/

    /************************
     * 工作台展示
     **************************/
    /**
     * 苗木信息查询
     *
     * @param page     页数
     * @param callback 回调
     * @return
     */
    public static Call findSeedling(int page, BaseCallback callback) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("page", page);
        return OkHttpController.doRequest(getRequestURL(ProtocolUrl.FIND_SEEDLING), params, null, callback);
    }

    /**
     * 设备租赁查询
     *
     * @param page     页数
     * @param callback 回调
     * @return
     */
    public static Call findEquipment(int page, BaseCallback callback) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("page", page);
        return OkHttpController.doRequest(getRequestURL(ProtocolUrl.FIND_EQUIPMENT), params, null, callback);
    }

    /**
     * 人员招聘查询
     *
     * @param page     页数
     * @param callback 回调
     * @return
     */
    public static Call findRecruit(int page, BaseCallback callback) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("page", page);
        return OkHttpController.doRequest(getRequestURL(ProtocolUrl.FIND_RECRUIT), params, null, callback);
    }

    /**
     * 招标信息查询
     *
     * @param page     页数
     * @param callback 回调
     * @return
     */
    public static Call findBid(int page, BaseCallback callback) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("page", page);
        return OkHttpController.doRequest(getRequestURL(ProtocolUrl.FIND_BID), params, null, callback);
    }

    /************************ 工作台展示end **************************/
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
     * @param fileBefore 养护前图片
     * @param fileAfter  养护后图片
     * @param callback   回调
     * @return {@link Call}
     */
    public static Call guanhuSubmit(String yid, String uid, String treeId, String yhSite, String yhWorker, String yhDetails, String yhTime, String yhQuestion, String yhClean, String treeGrowup, String yhComment, File fileBefore, File fileAfter, BaseCallback callback) {
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
        Map<String, File> files = new HashMap<>();
        files.put("file1", fileBefore);
        files.put("file2", fileAfter);
        return OkHttpController.doRequest(getRequestURL(ProtocolUrl.SAVEGHMANAGEMSG), params, files, null, callback);
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
        return OkHttpController.doRequest(getRequestURL(ProtocolUrl.SAVESTATUSMSG), params, null, callback);
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
        return OkHttpController.doRequest(getRequestURL(ProtocolUrl.APP_NUMBEROFQIANDAO), params, null, callback);
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
        return OkHttpController.doRequest(getRequestURL(ProtocolUrl.SAVETREE), params, null, callback);
    }

    /**
     * 查询养护管理信息
     *
     * @param uid      uid
     * @param callback 回调
     * @return {@link Call}
     */
    public static Call yhManageQueryList(String uid, BaseCallback callback) {
        Map<String, Object> params = new HashMap<>();
        params.put("uid", uid);
        return OkHttpController.doRequest(getRequestURL(ProtocolUrl.QUERYYGSTATUSMSG), params, null, callback);
    }

    /**
     * 根据ID查询植物是否录入
     *
     * @param treeId   植物二维码
     * @param callback 回调
     * @return {@link Call}
     */
    public static Call yhManageQueryId(String treeId, BaseCallback callback) {
        Map<String, Object> params = new HashMap<>();
        params.put("treeId", treeId);
        return OkHttpController.doRequest(getRequestURL(ProtocolUrl.QUERYTREEMSGBYID), params, null, callback);
    }

    /**
     * 删除变更管理条目
     *
     * @param dataList 要删除的条目List
     * @param callback 回调
     * @return {@link Call}
     */
    public static Call changeManageDeleteInfo(List<DeleteEntity> dataList, BaseCallback callback) {
        String json = JSON.toJSONString(dataList);
        return OkHttpController.doJsonRequest(getRequestURL(ProtocolUrl.DELETE_ALTERATION), json, null, callback);
    }

    /**
     * 提交变更管理条目
     *
     * @param uid             uid
     * @param pid             项目信息ID
     * @param confirmingParty 确认方
     * @param times           变更时间
     * @param files           变更依据
     * @param callback        回调
     * @return {@link Call}
     */
    public static Call changeManageSubmit(String uid, String pid, String confirmingParty, String times, List<File> files, BaseCallback callback) {
        Map<String, Object> params = new HashMap<>();
        params.put("uid", uid);
        params.put("pid", pid);
        params.put("confirmingParty", confirmingParty);
        params.put("times", times);
        Map<String, File> fileMap = new HashMap<>();
        for (File file : files) {
            fileMap.put(file.getName(), file);
        }
        return OkHttpController.doRequest(getRequestURL(ProtocolUrl.PROJECT_SAVEALTERATIONMSG), params, fileMap, null, callback);
    }

    /**
     * 删除进度申报条目
     *
     * @param dataList 要删除的条目List
     * @param callback 回调
     * @return {@link Call}
     */
    public static Call declareDeleteInfo(List<DeleteEntity> dataList, BaseCallback callback) {
        String json = JSON.toJSONString(dataList);
        return OkHttpController.doJsonRequest(getRequestURL(ProtocolUrl.DELETE_DECLARATION), json, null, callback);
    }

    /**
     * 进度申报提交
     *
     * @param dates    时间
     * @param uid      uid
     * @param pid      项目ID
     * @param files    申报文件
     * @param callback 回调
     * @return {@link Call}
     */
    public static Call declareSubmit(String dates, String uid, String pid, List<File> files, BaseCallback callback) {
        Map<String, Object> params = new HashMap<>();
        params.put("dates", dates);
        params.put("uid", uid);
        params.put("pid", pid);
        Map<String, File> fileMap = new HashMap<>();
        for (File file : files) {
            fileMap.put(file.getName(), file);
        }
        return OkHttpController.doRequest(getRequestURL(ProtocolUrl.PROJECT_SAVEJDSB), params, fileMap, null, callback);
    }
}
