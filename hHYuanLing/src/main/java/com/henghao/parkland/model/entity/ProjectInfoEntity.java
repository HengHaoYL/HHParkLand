package com.henghao.parkland.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by 晏琦云 on 2017/2/26.
 * 项目管理 -- 项目信息实体
 * name :           项目名称
 * principal :      项目负责人
 * tel :            联系方式
 * personNum :      项目人数
 * address :        项目地点
 * company :        施工单位
 * startTime :      开工时间
 * endTime :        竣工时间
 * id :             表主键
 */

public class ProjectInfoEntity implements Serializable {

    private boolean checked;//多选框选中状态
    // 项目名称
    @Expose
    @SerializedName("name")
    private String name;
    // 项目负责人
    @Expose
    @SerializedName("principal")
    private String principal;
    // 联系方式
    @Expose
    @SerializedName("tel")
    private String tel;
    // 项目人数
    @Expose
    @SerializedName("personNum")
    private int personNum;
    // 项目地点
    @Expose
    @SerializedName("address")
    private String address;
    // 施工单位
    @Expose
    @SerializedName("company")
    private String company;
    // 开工时间
    @Expose
    @SerializedName("startTime")
    private String startTime;
    // 竣工时间
    @Expose
    @SerializedName("endTime")
    private String endTime;
    // 表主键
    @Expose
    @SerializedName("id")
    private String id;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getPersonNum() {
        return personNum;
    }

    public void setPersonNum(int personNum) {
        this.personNum = personNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
