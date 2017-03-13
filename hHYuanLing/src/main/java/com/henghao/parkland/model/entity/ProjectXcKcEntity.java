package com.henghao.parkland.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.henghao.parkland.model.IdEntity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/26 0026.
 */

public class ProjectXcKcEntity extends IdEntity {

    /**
     * xcAdd:"xx",                           现场勘探地址
     * xcPerson:"xx",                        现场勘探人员姓名
     * xcSituation:xx,                       现场勘探图片路径编号
     * xcTime:"xx"                           现场勘探时间
     * name:"xx"                             项目名称
     * "url":[
     * "/Prospect/Img/IMG_20170221_094513.jpg"
     * ]
     */

    @Expose
    @SerializedName("url")
    private ArrayList<String> url;

    @Expose
    private String name;

    @Expose
    private String xcAdd;

    @Expose
    private String xcPerson;

    @Expose
    private String xcSituation;

    @Expose
    private String xcTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getXcAdd() {
        return xcAdd;
    }

    public void setXcAdd(String xcAdd) {
        this.xcAdd = xcAdd;
    }

    public String getXcPerson() {
        return xcPerson;
    }

    public void setXcPerson(String xcPerson) {
        this.xcPerson = xcPerson;
    }

    public String getXcSituation() {
        return xcSituation;
    }

    public void setXcSituation(String xcSituation) {
        this.xcSituation = xcSituation;
    }

    public String getXcTime() {
        return xcTime;
    }

    public void setXcTime(String xcTime) {
        this.xcTime = xcTime;
    }

    public ArrayList<String> getUrl() {
        return url;
    }

    public void setUrl(ArrayList<String> url) {
        this.url = url;
    }
}
