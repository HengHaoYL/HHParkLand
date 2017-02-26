package com.henghao.parkland.model.entity;

import com.google.gson.annotations.Expose;
import com.henghao.parkland.model.IdEntity;

/**
 * Created by Administrator on 2017/2/26 0026.
 */

public class ProjectXcKcEntity extends IdEntity {

   /* isNo:0,                               数据状态信息，默认为0/1(忽略)
    pid:2,                                表id，主键
    uid:6,                                用户编号
    xcAdd:"xx",                           现场勘探地址
    xcPerson:"xx",                        现场勘探人员姓名
    xcSituation:xx,                       现场勘探图片路径编号
    xcTime:"xx"                           现场勘探时间*/

    @Expose
    private String pid;

    @Expose
    private String xcAdd;

    @Expose
    private String xcPerson;

    @Expose
    private String xcSituation;

    @Expose
    private String xcTime;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getXcAdd() {
        return xcAdd;
    }

    public void setXcAdd(String xcAdd) {
        this.xcAdd = xcAdd;
    }

    public String getXcPerson() {
        return xcPerson;
    }

    public void setXcPerson(String xcPerson) {
        this.xcPerson = xcPerson;
    }

    public String getXcSituation() {
        return xcSituation;
    }

    public void setXcSituation(String xcSituation) {
        this.xcSituation = xcSituation;
    }

    public String getXcTime() {
        return xcTime;
    }

    public void setXcTime(String xcTime) {
        this.xcTime = xcTime;
    }
}
