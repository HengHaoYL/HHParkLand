package com.henghao.parkland.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.henghao.parkland.model.IdEntity;

import java.util.List;

/**
 * Created by 晏琦云 on 2017/2/24.
 */

public class SGMaterialEntity extends IdEntity {
    /*"bid":1,
            "content":"恒昊",
            "workImg":"9170a9302759431bab290fc15e084f30",
            "uid":6,
            "url":[
            "/construct/Img/Picture_01_Lake.jpg"
            ],
            "isNo":0*/

    @Expose
    @SerializedName("bid")
    private String bid;

    @Expose
    @SerializedName("content")
    private String content;

    @Expose
    @SerializedName("workImg")
    private String workImg;

    @Expose
    @SerializedName("url")
    private List<String> url;

    @Expose
    @SerializedName("isNo")
    private String isNo;

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWorkImg() {
        return workImg;
    }

    public void setWorkImg(String workImg) {
        this.workImg = workImg;
    }

    public List<String> getUrl() {
        return url;
    }

    public void setUrl(List<String> url) {
        this.url = url;
    }

    public String getIsNo() {
        return isNo;
    }

    public void setIsNo(String isNo) {
        this.isNo = isNo;
    }
}
