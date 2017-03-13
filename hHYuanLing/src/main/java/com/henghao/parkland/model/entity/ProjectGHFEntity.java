package com.henghao.parkland.model.entity;

import com.google.gson.annotations.Expose;
import com.henghao.parkland.model.IdEntity;

import java.util.ArrayList;

/**
 * Created by 晏琦云 on 2017/3/3.
 */

public class ProjectGHFEntity extends IdEntity {
    /**
     * epAdd: "xx",                          企业地址
     * epCompactId: "xx",                    供货合同图片编号
     * epDate:"xx",                          供货日期
     * epName: "xx"                          企业名称
     * epTel: "xx",                          联系方式
     * name:"xx",                            项目名称
     * url:-[                                返回图片路径集合（返回json为string）
     * 0:"/checkout/Img/1IMG_20170301_164119.jpg"         供货商图片文件存放路径
     * ],
     */
    @Expose
    private String epAdd;//企业地址
    @Expose
    private String name;//项目名称
    @Expose
    private String epCompactId;//供货合同图片编号
    @Expose
    private String epDate;//供货日期
    @Expose
    private String epName;//企业名称
    @Expose
    private String epTel;//联系方式
    @Expose
    private ArrayList<String> url;//返回图片路径集合

    public String getEpAdd() {
        return epAdd;
    }

    public void setEpAdd(String epAdd) {
        this.epAdd = epAdd;
    }

    public String getEpCompactId() {
        return epCompactId;
    }

    public void setEpCompactId(String epCompactId) {
        this.epCompactId = epCompactId;
    }

    public String getEpDate() {
        return epDate;
    }

    public void setEpDate(String epDate) {
        this.epDate = epDate;
    }

    public String getEpName() {
        return epName;
    }

    public void setEpName(String epName) {
        this.epName = epName;
    }

    public String getEpTel() {
        return epTel;
    }

    public void setEpTel(String epTel) {
        this.epTel = epTel;
    }

    public ArrayList<String> getUrl() {
        return url;
    }

    public void setUrl(ArrayList<String> url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
