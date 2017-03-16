/*
 * 文件名：ProtocolUrl.java
 * 版权：Copyright 2009-2010 companyName MediaNet. Co. Ltd. All Rights Reserved.
 * 描述：
 * 修改人：
 * 修改时间：
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */
package com.henghao.parkland;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author zhangxianwen
 * @version HDMNV100R001, 2015-4-20
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ProtocolUrl {

    /**
     * 服务端根地址
     */
    public static String ROOT_URL = "";

    public static boolean isURL = true;

    static {
        if (!isURL) {
            // 生产地址
            ROOT_URL = "http://safe.higdata.com/YL_BigData";
        } else {
            // 测试地址/192.168.1.12
            ROOT_URL = "http://172.16.13.101:8080/YL_BigData";
            //172.16.13.101:8080/login/az?username=?&password=?
            /*ROOT_URL = "http://safe.higdata.com/Java_Nfc/";*/
//			ROOT_URL = "http://192.168.1.12/Java_Nfc";
        }
    }

    // TODO 用户相关
    /************************
     * 用户相关
     **************************/
    public static final String USER = "user/";

    /**
     * 用户登录172.16.13.101:8080/YL_BigData/login?username=?&password=?
     */
    public static String APP_LOGIN = "login";

    public static String APP_GET_NFCBYID = "login";
    /**
     * 用户注册
     */
    public static String APP_REG = "register";

    /************************ 用户相关 end **************************/

    // TODO app系统 相关
    /************************
     * app系统 相关
     **************************/
    public static final String SYSTEM = "j_appSystem/";

    /**
     * app启动页面信息
     */
    public static final String APP_START = SYSTEM + "appStart";

    /**
     * app引导页面信息
     */
    public static final String APP_GUIDE = SYSTEM + "appGuide";

    /**
     * app系统版本更新
     */
    public static final String APP_SYS_UPDATE = SYSTEM + "appUserUpdate";
    /************************ app系统 end **************************/
    /************************
     * 签到
     **************************/

    public static final String APP_QIANDAO = "report";

    /************************ 签到 end **************************/
    /************************
     * 养护管理
     **************************/
//   http://172.16.13.101:8080/YL_BigData/queryGhManagemsg?yid&treeId

    public static final String APP_YANGHU = "queryGhManagemsg";

    /**
     * 通过植物编号查询管护信息访问接口
     */
    public static final String APP_GHMANAGEMSGBYID = "/YhManage/queryGhManageMsgByid";

    /************************ 养护管理 end **************************/
    /************************
     * 项目管理
     **************************/

    /**
     * 项目信息删除
     */
    public static final String DELETE_PROJECT = "/projectManage/delete/project";

    /**
     * 会审结果删除
     */
    public static final String DELETE_HSRESULT = "/projectManage/delete/hsResult";

    /**
     * 供货方信息删除
     */
    public static final String DELETE_SUPPLIER = "/projectManage/delete/supplier";

    /**
     * 项目结算删除
     */
    public static final String DELETE_SETTLEMENT = "/projectManage/delete/settlement";

    /**
     * 施工钱包查询
     */
    public static final String PROJECT_SGWALLET = "projectManage/queryWalletMsg";

    /**
     * 施工钱包Excel下载
     */
    public static final String DOWNLOAD_WALLETEXCEL = "download/walletExcel";

    /**
     * 开工报告文件下载
     */
    public static final String DOWNLOAD_KGREPORTFILE = "download/kgReportFile";

    /**
     * 查询施工信息
     */
    public static final String PROJECT_SGMSG = "projectManage/queryProjectMsg";

    /**
     * 施工备忘提交
     */
    public static final String PROJECT_SGBEIWANG = "projectManage/saveSgmemoMsg";

    /**
     * 工作备忘提交
     */
    public static final String PROJECT_WORKBEIWANG = "projectManage/saveWorkMemoMsg";

    /**
     * 施工资料查询
     */
    public static final String PROJECT_QUARYSGZL = "projectManage/queryBuildDateMsg";
    /**
     * 现场勘查
     */
    public static final String PROJECT_SAVEXCKC = "projectManage/saveProspectMsg";
    /**
     * 技术交底添加
     */
    public static final String PROJECT_SAVEJSJD = "projectManage/saveTechnologyMsg";
    /**
     * 进度申报提交
     */
    public static final String PROJECT_SAVEJDSB = "projectManage/saveDeclarationMsg";

    /**
     * 工序报验提交
     */
    public static final String PROJECT_SAVECHECKOUTMSG = "projectManage/saveCheckoutMsg";
    /**
     * 开工报告
     */
    public static final String PROJECT_SAVEKGBG = "projectManage/saveKgReportMsg";
    /**
     * 现场勘查查询
     */
    public static final String PROJECT_QUERYXCKC = "projectManage/queryProspectMsg";

    /**
     * 施工资料查询
     */
    public static final String PROJECT_QUERYSGZL = "projectManage/queryBuildDateMsg";

    /**
     * 施工资料提交
     */
    public static final String PROJECT_SGINFO = "http://172.16.13.101:8080/YL_BigData/" + "projectManage/saveBiuldDataMsg";

    /**
     * 项目信息查询
     */
    public static final String PROJECT_QUERYPROJECTMSG = "projectManage/queryProjectMsg";

    /**
     * 施工日志查询
     */
    public static final String PROJECT_QUERYCONSTRUCTIONLOGMSG = "projectManage/queryConstructionLogMsg";

    /**
     * 施工安全日志查询
     */
    public static final String PROJECT_QUERYSUMMARYLOGMSG = "projectManage/querySummaryLogMsg";

    /**
     * 监理日志查询
     */
    public static final String PROJECT_QUERYSUPERVISIONLOGMSG = "projectManage/querySupervisionlogMsg";

    /**
     * 竣工验收查询
     */
    public static final String PROJECT_QUERYFINALACCEPTANCEMSG = "projectManage/queryFinalacceptanceMsg";

    /**
     * 变更管理查询
     */
    public static final String PROJECT_QUERYALTERATIONMSG = "projectManage/queryAlterationMsg";

    /**
     * 项目结算查询
     */
    public static final String PROJECT_QUERYSETTLEMENTMSG = "projectManage/querySettlementMsg";

    /**
     * 供货方信息查询
     */
    public static final String PROJECT_QUERYSUPPLIERMSG = "projectManage/querySupplierMsg";

    /**
     * 设备信息查询
     */
    public static final String PROJECT_QUERYEQUIPMENTMSG = "projectManage/queryEquipmentMsg";

    /**
     * 开工报告查询
     */
    public static final String PROJECT_QUERYKGREPORTMSG = "projectManage/queryKgReportMsg";

    /**
     * 项目信息提交
     */
    public static final String PROJECT_SAVEPROJECTMSG = "projectManage/saveProjectMsg";

    /**
     * 施工日志提交
     */
    public static final String PROJECT_SAVECONSTRUCTIONLOGMSG = "projectManage/saveConstructionLogMsg";

    /**
     * 施工安全日志提交
     */
    public static final String PROJECT_SAVESUMMARYLOGMSG = "projectManage/saveSummaryLogMsg";

    /**
     * 监理日志提交
     */
    public static final String PROJECT_SAVESUPERVISIONLOGMSG = "projectManage/saveSupervisionlogMsg";

    /**
     * 施工人员提交
     */
    public static final String PROJECT_SAVESGPERSONNELMSG = "projectManage/saveSgPersonnelMsg";

    /**
     * 设备信息提交
     */
    public static final String PROJECT_SAVEEQUIPMENTMSG = "projectManage/saveEquipmentMsg";

    /**
     * 变更管理提交
     */
    public static final String PROJECT_SAVEALTERATIONMSG = "projectManage/saveAlterationMsg";

    /**
     * 供货方信息提交
     */
    public static final String PROJECT_SAVESUPPLIERMSG = "projectManage/saveSupplierMsg";

    /**
     * 竣工验收提交
     */
    public static final String PROJECT_SAVEFINALACCEPTANCEMSG = "projectManage/saveFinalacceptanceMsg";

    /**
     * 项目结算提交
     */
    public static final String PROJECT_SAVESETTLEMENT = "projectManage/saveSettlementMsg";

    /**
     * 会审结果查询
     */
    public static final String PROJECT_QUERYHSRESULTMSG = "projectManage/queryHsResultMsg";

    /**
     * 工序报验查询
     */
    public static final String PROJECT_QUERYCHECKOUTMSG = "projectManage/queryCheckoutMsg";

    /**
     * 施工人员查询
     */
    public static final String PROJECT_QUERYSGPERSONNELMSG = "projectManage/querySgPersonnelMsg";

    /**
     * 查询技术交底
     */
    public static final String PROJECT_QUERYDECLARATIONMSG = "projectManage/queryTechnologyMsg";

    /**
     * 查询进度申报
     */
    public static final String PROJECT_QUERYJDSB = "projectManage/queryDeclarationMsg";

    /**
     * 添加我的轨迹
     */
    public static final String PROJECT_SAVE_MYLOCUSMSG = "projectManage/saveMylocusMsg";

    /**
     * 查询我的轨迹
     */
    public static final String PROJECT_QUERY_MYLOCUSMSG = "projectManage/queryMylocusMsg";

    /************************ 项目管理 end **************************/

    /************************
     * 工作台
     **************************/

    /**
     * 设备租赁信息查询
     */
    public static final String RELEASE_QUERYEQUIPMENTLEASING = "release/queryEquipmentLeasing";

    /**
     * 苗木信息查询
     */
    public static final String RELEASE_QUERYSEEDLINGMESSAGE = "release/querySeedlingmessage";

    /**
     * 招标信息查询
     */
    public static final String RELEASE_QUERYBID = "release/queryBid";

    /**
     * 人员招聘查询
     */
    public static final String RELEASE_QUERYRECRUIT = "release/queryRecruit";

    /************************ 工作台 end **************************/

    /**
     * 上传错误日志到服务器
     */
    public static final String UPLOAD_ERROR_SERVER = "appError";


}
