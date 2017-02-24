package com.henghao.parkland.model.protocol;

/**
 * Created by 晏琦云 on 2017/2/13.
 * 公共网络接口类
 */

public class HttpPublic {
    public static final String SAVETREE = "http://172.16.13.101:8080/YL_BigData/YhManage/saveTreeMsg";//植物信息录入接口
    public static final String QUERYTREEMSGBYID = "http://172.16.13.101:8080/YL_BigData/YhManage/queryTreeMsgById";//通过植物编号查询树木信息访问接口
    public static final String SAVESTATUSMSG = "http://172.16.13.101:8080/YL_BigData/YhManage/saveStatusMsg";//养护状态信息数据保存接口
    public static final String QUERYYGSTATUSMSG = "http://172.16.13.101:8080/YL_BigData/YhManage/queryYhStatusMsg";//查询当天养护状态信息数据访问接口
    public static final String SAVEGHMANAGEMSG = "http://172.16.13.101:8080/YL_BigData/YhManage/saveGhManageMsg";//管护信息数据保存接口
    public static final String QIANDAO = "http://172.16.13.101:8080/YL_BigData/report";//签到
    public static final String QUERYSGMEMOMSG = "http://172.16.13.101:8080/YL_BigData/projectManage/querySgmemoMsg";//查询用户施工备忘信息数据访问接口
    public static final String QUERYWORKMEMOMSG = "http://172.16.13.101:8080/YL_BigData/projectManage/queryWorkMemoMsg";//查询用户工作备忘信息数据访问接口
    public static final String QUERYBUILDDATEMSG = "http://172.16.13.101:8080/YL_BigData/projectManage/queryBuildDateMsg";//查询用户施工资料信息数据访问接口
}
