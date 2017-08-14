package com.henghao.parkland.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.henghao.parkland.model.IdEntity;

import java.util.ArrayList;

/**
 * Created by 晏琦云 on 2017/8/10.
 * 设备租赁实体
 * "eid":           数据id,
 * "titleName":     标题名称,
 * "content":       内容,
 * "date":          发布日期,
 * "time":          发布时间,
 * "contact":       联系人,
 * "tel":           联系方式,
 * "picture":       文件
 */

public class EquipmentEntity extends IdEntity {
    //数据id
    @Expose
    @SerializedName("eid")
    private int eid;
    //标题名称
    @Expose
    @SerializedName("titleName")
    private String titleName;
    //内容
    @Expose
    @SerializedName("content")
    private String content;
    //发布日期
    @Expose
    @SerializedName("date")
    private String date;
    //发布时间
    @Expose
    @SerializedName("time")
    private String time;
    //联系人
    @Expose
    @SerializedName("contact")
    private String contact;
    //联系方式
    @Expose
    @SerializedName("tel")
    private String tel;
    //文件
    @Expose
    @SerializedName("picture")
    private ArrayList<String> picture;

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public ArrayList<String> getPicture() {
        return picture;
    }

    public void setPicture(ArrayList<String> picture) {
        this.picture = picture;
    }
}
