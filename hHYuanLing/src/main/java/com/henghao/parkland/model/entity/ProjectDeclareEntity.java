package com.henghao.parkland.model.entity;

import com.google.gson.annotations.Expose;
import com.henghao.parkland.model.IdEntity;

import java.util.List;

/**
 * Created by Administrator on 2017/3/3 0003.
 */

public class ProjectDeclareEntity extends IdEntity {
    /*dates:"xx",                            时间
    sites:"xx",                            地点
    isNo:0,                                数据状态信息，默认为0/1(忽略)
    content:"xx",                          内容
    photoId:"xx",                          现场情况图片编号
    uid:6,                                 用户编号
    wid:7                                  表ID
    url:-[                    返回图片路径集合（返回json为string）
            0:"xx"                    图片存放路径
    ]*/

    @Expose
    private String dates;

    @Expose
    private String photoId;

    @Expose
    private List<String> url;

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }


    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public List<String> getUrl() {
        return url;
    }

    public void setUrl(List<String> url) {
        this.url = url;
    }
}
