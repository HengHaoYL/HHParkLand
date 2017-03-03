package com.henghao.parkland.model.entity;

import com.google.gson.annotations.Expose;

/**
 * Created by 晏琦云 on 2017/2/26.
 */

public class ProjectKGBGEntity {
    /*
kgDocument:"xx",开工确认文件存放路径编号
kgTime:"xx",开工时间
wgTime:"xxx"竣工时间
     */
    @Expose
    private String kgDocument;//开工确认文件存放路径编号
    @Expose
    private String kgTime;//开工时间


    public String getKgDocument() {
        return kgDocument;
    }

    public void setKgDocument(String kgDocument) {
        this.kgDocument = kgDocument;
    }

    public String getKgTime() {
        return kgTime;
    }

    public void setKgTime(String kgTime) {
        this.kgTime = kgTime;
    }
}
