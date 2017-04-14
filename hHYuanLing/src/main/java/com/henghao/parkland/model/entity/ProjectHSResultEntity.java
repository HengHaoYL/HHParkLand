package com.henghao.parkland.model.entity;

import com.google.gson.annotations.Expose;
import com.henghao.parkland.model.IdEntity;

import java.util.ArrayList;

/**
 * Created by 晏琦云 on 2017/2/26.
 */

public class ProjectHSResultEntity extends IdEntity {
    /**
     * hsDeparment:"xx",会审单位
     * hid:"xx",表主键
     * hsImgId:null,会审图片路径
     * name:"xx"    项目名称
     * "url":[
     * "/construct/Img/Picture_01_Lake.jpg"
     * ],
     */
    private boolean isChecked;//是否被选中
    @Expose
    private int hid;//表主键
    @Expose
    private String hsDeparment;//会审单位
    @Expose
    private String hsImgId;//会审图片路径
    @Expose
    private String name;//项目名称
    @Expose
    private ArrayList<String> url;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getHid() {
        return hid;
    }

    public void setHid(int hid) {
        this.hid = hid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHsDeparment() {
        return hsDeparment;
    }

    public void setHsDeparment(String hsDeparment) {
        this.hsDeparment = hsDeparment;
    }

    public String getHsImgId() {
        return hsImgId;
    }

    public void setHsImgId(String hsImgId) {
        this.hsImgId = hsImgId;
    }

    public ArrayList<String> getUrl() {
        return url;
    }

    public void setUrl(ArrayList<String> url) {
        this.url = url;
    }
}
