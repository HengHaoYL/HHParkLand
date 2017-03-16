package com.henghao.parkland.model.entity;

import com.google.gson.annotations.Expose;
import com.henghao.parkland.model.IdEntity;


/**
 * Blueprint entity. 人员招聘表实体
 *
 * @author 严彬荣
 */
public class RecruitEntity extends IdEntity {

    // 职位
    @Expose
    private String position;
    // 企业名称
    @Expose
    private String companyName;
    // 工作地点
    @Expose
    private String workingAdress;
    // 联系人
    @Expose
    private String contact;
    // 联系电话
    @Expose
    private String tel;
    // 发布日期
    @Expose
    private String dates;
    // 招聘详情
    @Expose
    private String detail;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getWorkingAdress() {
        return workingAdress;
    }

    public void setWorkingAdress(String workingAdress) {
        this.workingAdress = workingAdress;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

}
