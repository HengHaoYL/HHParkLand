package com.henghao.parkland.model.entity;

import com.google.gson.annotations.Expose;
import com.henghao.parkland.model.IdEntity;

/**
 * Created by 晏琦云 on 2017/2/27.
 */

public class ProjectTeamEntity extends IdEntity {
    /**
     * personnelType:"xx",人员类型
     * workPost:"xx",工作岗位
     * psIdcard:"xx",身份证号
     * psName:"xx",姓名
     * name:"xx",项目名称
     * psTel:"xx",联系电话
     * uid:6 用户编号
     */
    @Expose
    private String psIdcard;//身份证号
    @Expose
    private String name;//项目名称
    @Expose
    private String personnelType;//personnelType
    @Expose
    private String workPost;//工作岗位
    @Expose
    private String psName;//姓名
    @Expose
    private String psTel;//联系电话
    @Expose
    private String uid;//用户编号

    public String getPersonnelType() {
        return personnelType;
    }

    public void setPersonnelType(String personnelType) {
        this.personnelType = personnelType;
    }

    public String getPsIdcard() {
        return psIdcard;
    }

    public void setPsIdcard(String psIdcard) {
        this.psIdcard = psIdcard;
    }

    public String getWorkPost() {
        return workPost;
    }

    public void setWorkPost(String workPost) {
        this.workPost = workPost;
    }

    public String getPsName() {
        return psName;
    }

    public void setPsName(String psName) {
        this.psName = psName;
    }

    public String getPsTel() {
        return psTel;
    }

    public void setPsTel(String psTel) {
        this.psTel = psTel;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
