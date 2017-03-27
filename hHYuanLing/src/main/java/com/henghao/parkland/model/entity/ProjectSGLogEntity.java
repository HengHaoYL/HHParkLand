package com.henghao.parkland.model.entity;

import com.google.gson.annotations.Expose;
import com.henghao.parkland.model.IdEntity;

/**
 * buildersdiary entity.施工日志表实体
 *
 * @author 严彬荣
 */
public class ProjectSGLogEntity extends IdEntity {

    private boolean isChecked;//是否被选中
    // 表主键
    @Expose
    private int cid;
    // 项目名称
    @Expose
    private String name;
    // 施工日期
    @Expose
    private String dates;
    // 生产活动内容
    @Expose
    private String proactContent;
    // 质量安全技术指标
    @Expose
    private String technicalIndex;
    // 施工员
    @Expose
    private String builder;
    // 工程项目负责人
    @Expose
    private String principal;
    // 完成工作情况
    @Expose
    private String workingCondition;

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

    public String getProactContent() {
        return proactContent;
    }

    public void setProactContent(String proactContent) {
        this.proactContent = proactContent;
    }

    public String getTechnicalIndex() {
        return technicalIndex;
    }

    public void setTechnicalIndex(String technicalIndex) {
        this.technicalIndex = technicalIndex;
    }

    public String getBuilder() {
        return builder;
    }

    public void setBuilder(String builder) {
        this.builder = builder;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getWorkingCondition() {
        return workingCondition;
    }

    public void setWorkingCondition(String workingCondition) {
        this.workingCondition = workingCondition;
    }
}