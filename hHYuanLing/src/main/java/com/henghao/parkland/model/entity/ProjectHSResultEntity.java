package com.henghao.parkland.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 晏琦云 on 2017/2/26.
 * 项目管理 -- 会审结果实体
 * name :           项目名称
 * company :        会审单位
 * picture :        会审文件图片
 * id :             表主键
 * pid :            项目信息表主键
 */

public class ProjectHSResultEntity implements Serializable {

    private boolean isChecked;//是否被选中
    // 项目名称
    @Expose
    @SerializedName("name")
    private String name;
    // 会审单位
    @Expose
    @SerializedName("company")
    private String company;
    // 表主键
    @Expose
    @SerializedName("id")
    private String id;
    // 项目信息表主键
    @Expose
    @SerializedName("pid")
    private String pid;
    // 会审文件图片
    @Expose
    @SerializedName("picture")
    private ArrayList<String> picture;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public ArrayList<String> getPicture() {
        return picture;
    }

    public void setPicture(ArrayList<String> picture) {
        this.picture = picture;
    }
}
