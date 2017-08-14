package com.henghao.parkland.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.henghao.parkland.model.IdEntity;

import java.util.ArrayList;

/**
 * Created by 晏琦云 on 2017/8/10
 * 苗木信息实体
 * "sid":           数据id,
 * "titleName":     标题名称,
 * "content":       内容,
 * "contact":       联系人,
 * "tel":           联系方式,
 * "date":          发布日期,
 * "time":          发布时间,
 * "address":       供应商地址,
 * "supplier":      供应商,
 * "breed":         品种,
 * "sub":           二级品种,
 * "picture":       文件路径（多数据）
 */

public class SeedlingEntity extends IdEntity {
    // 数据id
    @Expose
    @SerializedName("sid")
    private int sid;
    // 标题名称
    @Expose
    @SerializedName("titleName")
    private String titleName;
    // 内容
    @Expose
    @SerializedName("content")
    private String content;
    // 联系人
    @Expose
    @SerializedName("contact")
    private String contact;
    // 联系电话
    @Expose
    @SerializedName("tel")
    private String tel;
    // 发布日期
    @Expose
    @SerializedName("date")
    private String date;
    // 发布时间
    @Expose
    @SerializedName("time")
    private String time;
    // 供应商地址
    @Expose
    @SerializedName("address")
    private String address;
    // 供应商
    @Expose
    @SerializedName("supplier")
    private String supplier;
    // 品种
    @Expose
    @SerializedName("breed")
    private String breed;
    // 二级品种
    @Expose
    @SerializedName("sub")
    private String sub;
    // 文件路径（多数据）
    @Expose
    @SerializedName("picture")
    private ArrayList<String> picture;

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public ArrayList<String> getPicture() {
        return picture;
    }

    public void setPicture(ArrayList<String> picture) {
        this.picture = picture;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
