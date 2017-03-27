package com.henghao.parkland.model.entity;

import com.google.gson.annotations.Expose;
import com.henghao.parkland.model.IdEntity;

import java.util.ArrayList;

/**
 * Created by 晏琦云 on 2017/3/1.
 * 工序报验
 */

public class ProjectGXBYEntity extends IdEntity {
    private boolean isChecked;//是否被选中
    // 表主键
    @Expose
    private int cid;
    // 工程名称
    @Expose
    private String gxName;
    // 工序名称
    @Expose
    private String gxProcedure;
    // 交接者
    @Expose
    private String personnelType;
    // 工作岗位
    @Expose
    private String workPost;
    // 施工日期
    @Expose
    private String gxTime;
    // 影像资料图
    @Expose
    private String gxImgId;
    @Expose
    private ArrayList<String> url;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getGxName() {
        return gxName;
    }

    public void setGxName(String gxName) {
        this.gxName = gxName;
    }

    public String getGxProcedure() {
        return gxProcedure;
    }

    public void setGxProcedure(String gxProcedure) {
        this.gxProcedure = gxProcedure;
    }

    public String getPersonnelType() {
        return personnelType;
    }

    public void setPersonnelType(String personnelType) {
        this.personnelType = personnelType;
    }

    public String getWorkPost() {
        return workPost;
    }

    public void setWorkPost(String workPost) {
        this.workPost = workPost;
    }

    public String getGxTime() {
        return gxTime;
    }

    public void setGxTime(String gxTime) {
        this.gxTime = gxTime;
    }

    public String getGxImgId() {
        return gxImgId;
    }

    public void setGxImgId(String gxImgId) {
        this.gxImgId = gxImgId;
    }

    public ArrayList<String> getUrl() {
        return url;
    }

    public void setUrl(ArrayList<String> url) {
        this.url = url;
    }
}
