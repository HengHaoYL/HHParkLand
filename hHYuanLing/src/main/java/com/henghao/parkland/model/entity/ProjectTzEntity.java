package com.henghao.parkland.model.entity;

import com.google.gson.annotations.Expose;

/**
 * Created by 晏琦云 on 2017/2/26.
 */

public class ProjectTzEntity {
    /*
tzAdd:"xx",设计单位地址
tzHead:"xx",设计负责人
tzImg:null,设计图纸路径
tzName:"xx",设计单位
tzTel:"xx",联系方式
     */
    @Expose
    private String tzAdd;//设计单位地址
    @Expose
    private String tzHead;//设计负责人
    @Expose
    private String tzImg;//设计图纸路径
    @Expose
    private String tzName;//设计单位
    @Expose
    private String tzTel;//联系方式

    public String getTzAdd() {
        return tzAdd;
    }

    public void setTzAdd(String tzAdd) {
        this.tzAdd = tzAdd;
    }

    public String getTzHead() {
        return tzHead;
    }

    public void setTzHead(String tzHead) {
        this.tzHead = tzHead;
    }

    public String getTzImg() {
        return tzImg;
    }

    public void setTzImg(String tzImg) {
        this.tzImg = tzImg;
    }

    public String getTzName() {
        return tzName;
    }

    public void setTzName(String tzName) {
        this.tzName = tzName;
    }

    public String getTzTel() {
        return tzTel;
    }

    public void setTzTel(String tzTel) {
        this.tzTel = tzTel;
    }
}
