package com.henghao.parkland.model.entity;

import com.google.gson.annotations.Expose;
import com.henghao.parkland.model.IdEntity;

/**
 * buildersdiary entity.施工安全日志表实体
 *
 * @author 严彬荣
 */
public class ProjectSGSafeLogEntity extends IdEntity {

    private boolean isChecked;//是否被选中
    //表主键
    @Expose
    private int cid;
    //工程名称
    @Expose
    private String name;
    //施工单位
    @Expose
    private String constructionUnit;
    //开工时间
    @Expose
    private String startTime;
    //合同竣工时间
    @Expose
    private String completionTime;
    // 日期
    @Expose
    private String dates;
    // 施工部位
    @Expose
    private String constructionSite;
    // 施工工序动态
    @Expose
    private String constructionDynamic;
    // 安全状况
    @Expose
    private String safetySituation;
    // 安全问题的处理
    @Expose
    private String safetyProblems;
    // 填写人
    @Expose
    private String fillPeople;

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

    public String getConstructionUnit() {
        return constructionUnit;
    }

    public void setConstructionUnit(String constructionUnit) {
        this.constructionUnit = constructionUnit;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(String completionTime) {
        this.completionTime = completionTime;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getConstructionSite() {
        return constructionSite;
    }

    public void setConstructionSite(String constructionSite) {
        this.constructionSite = constructionSite;
    }

    public String getConstructionDynamic() {
        return constructionDynamic;
    }

    public void setConstructionDynamic(String constructionDynamic) {
        this.constructionDynamic = constructionDynamic;
    }

    public String getSafetySituation() {
        return safetySituation;
    }

    public void setSafetySituation(String safetySituation) {
        this.safetySituation = safetySituation;
    }

    public String getSafetyProblems() {
        return safetyProblems;
    }

    public void setSafetyProblems(String safetyProblems) {
        this.safetyProblems = safetyProblems;
    }

    public String getFillPeople() {
        return fillPeople;
    }

    public void setFillPeople(String fillPeople) {
        this.fillPeople = fillPeople;
    }

}
