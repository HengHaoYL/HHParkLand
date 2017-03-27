package com.henghao.parkland.model.entity;

import com.google.gson.annotations.Expose;
import com.henghao.parkland.model.IdEntity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/3 0003.
 */

public class ProjectDeclareEntity extends IdEntity {
    /**
     * did:"xx",                              表主键
     * dates:"xx",                            时间
     * name:"xx",                             项目名称
     * sites:"xx",                            地点
     * content:"xx",                          内容
     * photoId:"xx",                          现场情况图片编号
     * url:-[                    返回图片路径集合（返回json为string）
     * 0:"xx"                    图片存放路径
     * ]
     */
    private boolean isChecked;//是否被选中

    @Expose
    private int did;

    @Expose
    private String dates;

    @Expose
    private String name;

    @Expose
    private String photoId;

    @Expose
    private ArrayList<String> url;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public ArrayList<String> getUrl() {
        return url;
    }

    public void setUrl(ArrayList<String> url) {
        this.url = url;
    }
}
