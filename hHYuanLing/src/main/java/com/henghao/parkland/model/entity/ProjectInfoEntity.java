package com.henghao.parkland.model.entity;

import com.google.gson.annotations.Expose;
import com.henghao.parkland.model.IdEntity;

/**
 * Created by 晏琦云 on 2017/2/26.
 */

public class ProjectInfoEntity extends IdEntity {
    /*
    xmAdd:"xx",项目地址
    xmContact:"xx",联系方式
    xmName:"xx",项目名称
    xmPerson:"xx",项目负责人
    xmPersonNum:10 项目人数
     */
    @Expose
    private String xmAdd;
    @Expose
    private String xmContact;
    @Expose
    private String xmName;
    @Expose
    private String xmPerson;
    @Expose
    private String xmPersonNum;

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

    public void setXmName(String xmName) {
        this.xmName = xmName;
    }

    public String getXmPerson() {
        return xmPerson;
    }

    public void setXmPerson(String xmPerson) {
        this.xmPerson = xmPerson;
    }

    public String getXmPersonNum() {
        return xmPersonNum;
    }

    public void setXmPersonNum(String xmPersonNum) {
        this.xmPersonNum = xmPersonNum;
    }
}
