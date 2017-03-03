package com.henghao.parkland.model.entity;

import com.google.gson.annotations.Expose;
import com.henghao.parkland.model.IdEntity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/3 0003.
 */

public class ProjectTechnologEntity extends IdEntity {
  /*  dates:"xx",                            时间
    sites:"xx",                            地点
    isNo:0,                                数据状态信息，默认为0/1(忽略)
    content:"xx",                          内容
    photoId:"xx",                          现场情况图片编号
    uid:6,                                 用户编号
    wid:7                                  表ID
    url:-[                    返回图片路径集合（返回json为string）
            0:"xx"                    图片存放路径
    ]
}*/

    @Expose
    private String dates;

    @Expose
    private String sites;

    @Expose
    private String content;

    @Expose
    private String photoId;

    @Expose
    ArrayList<String> url;

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getSites() {
        return sites;
    }

    public void setSites(String sites) {
        this.sites = sites;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public ArrayList<String> getUrl() {
        return url;
    }

    public void setUrl(ArrayList<String> url) {
        this.url = url;
    }
}
