package com.henghao.parkland.model.entity;

import com.google.gson.annotations.Expose;
import com.henghao.parkland.model.IdEntity;

/**
 * Created by 晏琦云 on 2017/2/26.
 */

public class ProjectInfoEntity extends IdEntity {
    /**
     * xmAdd:"xx",项目地址
     * constructionUnit:"xx",施工单位
     * startTime:"xx",开工时间
     * completionTime:"xx",竣工时间
     * xmContact:"xx",联系方式
     * xmName:"xx",项目名称
     * xmPerson:"xx",项目负责人
     * xmPersonNum:10 项目人数
     * pid:xx 项目信息ID
     */
    private boolean checked;//多选框选中状态

    @Expose
    private String xmAdd;
    @Expose
    private String constructionUnit;
    @Expose
    private String startTime;
    @Expose
    private String completionTime;
    @Expose
    private int pid;
    @Expose
    private String xmContact;
    @Expose
    private String xmName;
    @Expose
    private String xmPerson;
    @Expose
    private int xmPersonNum;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(String completionTime) {
        this.completionTime = completionTime;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getXmAdd() {
        return xmAdd;
    }

    public void setXmAdd(String xmAdd) {
        this.xmAdd = xmAdd;
    }

    public String getXmContact() {
        return xmContact;
    }

    public void setXmContact(String xmContact) {
        this.xmContact = xmContact;
    }

    public String getXmName() {
        return xmName;
    }

    public String getConstructionUnit() {
        return constructionUnit;
    }

    public void setConstructionUnit(String constructionUnit) {
        this.constructionUnit = constructionUnit;
    }

    public void setXmName(String xmName) {
        this.xmName = xmName;
    }

    public String getXmPerson() {
        return xmPerson;
    }

    public void setXmPerson(String xmPerson) {
        this.xmPerson = xmPerson;
    }

    public int getXmPersonNum() {
        return xmPersonNum;
    }

    public void setXmPersonNum(int xmPersonNum) {
        this.xmPersonNum = xmPersonNum;
    }
}
