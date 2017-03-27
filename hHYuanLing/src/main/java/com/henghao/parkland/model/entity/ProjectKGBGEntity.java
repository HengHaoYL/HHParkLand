package com.henghao.parkland.model.entity;

import com.google.gson.annotations.Expose;
import com.henghao.parkland.model.IdEntity;

/**
 * Created by 晏琦云 on 2017/2/26.
 */

public class ProjectKGBGEntity extends IdEntity {
    /**
     * kid:"xx",表主键
     * kgDocument:"xx",开工文件
     * kgTime:"xx",开工时间
     * wgTime:"xxx"竣工时间
     * name:"xxx"项目名称
     * kid:"xxx"表主键
     */
    private boolean isChecked;//是否被选中
    @Expose
    private String kgDocument;//开工文件
    @Expose
    private int kid;//表主键
    @Expose
    private String kgTime;//开工时间
    @Expose
    private String name;//项目名称

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getKid() {
        return kid;
    }

    public void setKid(int kid) {
        this.kid = kid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
