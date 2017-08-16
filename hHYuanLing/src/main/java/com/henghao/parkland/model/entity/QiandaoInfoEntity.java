package com.henghao.parkland.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ASUS on 2017/8/16.
 * 签到情况实体
 * address :    签到地址
 * time :       签到时间
 * latitude :   纬度
 * longitude :  经度
 * company :    当前企业
 * comments :   备注
 * name :       姓名
 */

public class QiandaoInfoEntity implements Serializable {

    // 签到地址
    @Expose
    @SerializedName("address")
    private String address;
    // 签到时间
    @Expose
    @SerializedName("time")
    private String time;
    // 纬度
    @Expose
    @SerializedName("latitude")
    private double latitude;
    // 经度
    @Expose
    @SerializedName("longitude")
    private double longitude;
    // 当前企业
    @Expose
    @SerializedName("company")
    private String company;
    // 备注
    @Expose
    @SerializedName("comments")
    private String comments;
    // 姓名
    private String name;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
