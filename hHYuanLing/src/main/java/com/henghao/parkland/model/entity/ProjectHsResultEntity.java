package com.henghao.parkland.model.entity;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by 晏琦云 on 2017/2/26.
 */

public class ProjectHSResultEntity {
    /*
    hsDeparment:"xx",会审单位
    hsImgId:null,会审图片路径
    "url":[
     "/construct/Img/Picture_01_Lake.jpg"
     ],
     */
    @Expose
    private String hsDeparment;//会审单位
    @Expose
    private String hsImgId;//会审图片路径
    @Expose
    private List<String> url;

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

    public List<String> getUrl() {
        return url;
    }

    public void setUrl(List<String> url) {
        this.url = url;
    }
}
