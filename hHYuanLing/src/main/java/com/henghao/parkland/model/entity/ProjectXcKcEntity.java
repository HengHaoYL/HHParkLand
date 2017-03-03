package com.henghao.parkland.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.henghao.parkland.model.IdEntity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/26 0026.
 */

public class ProjectXcKcEntity extends IdEntity {

   /* "pid":5,
            "xcTime":"2017-2-27 9:59",
            "xcAdd":"hh",
            "xcPerson":"hj",
            "xcSituation":"ee85839c04ca486eab2b903befe3a625",
            "uid":25,
            "isNo":0,
            "url":[
            "/Prospect/Img/IMG_20170221_094513.jpg"
            ]*/

    @Expose
    @SerializedName("url")
    private ArrayList<String> url;

    @Expose
    private String pid;

    @Expose
    private String xcAdd;

    @Expose
    private String xcPerson;

    @Expose
    private String xcSituation;

    @Expose
    private String xcTime;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
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
