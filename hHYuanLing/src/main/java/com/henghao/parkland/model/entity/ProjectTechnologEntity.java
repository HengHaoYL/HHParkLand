package com.henghao.parkland.model.entity;

import com.google.gson.annotations.Expose;
import com.henghao.parkland.model.IdEntity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/3 0003.
 */

public class ProjectTechnologEntity extends IdEntity {
    /**
     * tid:"xx",                              表主键
     * dates:"xx",                            时间
     * name:"xx",                             项目名称
     * sites:"xx",                            地点
     * content:"xx",                          内容
     * url:-[                    返回图片路径集合（返回json为string）
     * 0:"xx"                    图片存放路径
     * ]
     */

    private boolean isChecked;//是否被选中

    @Expose
    private int tid;

    @Expose
    private String dates;

    @Expose
    private String name;

    @Expose
    private String sites;

    @Expose
    private String content;

    @Expose
    private String photoId;

    @Expose
    ArrayList<String> url;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
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
