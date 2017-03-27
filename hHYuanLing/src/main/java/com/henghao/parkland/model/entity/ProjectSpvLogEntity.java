package com.henghao.parkland.model.entity;

import com.google.gson.annotations.Expose;

/**
 * Supervisionlog entity.监理日志表实体
 *
 * @author 严彬荣
 */
public class ProjectSpvLogEntity implements java.io.Serializable {

    private boolean isChecked;//是否被选中
    // 表主键
    @Expose
    private int sid;
    // 工程名称
    @Expose
    private String projectName;
    // 监理部位
    @Expose
    private String supervisionPosition;
    // 工程施工进度情况
    @Expose
    private String progressSituation;
    // 监理工作情况
    @Expose
    private String workingSitustion;
    // 存在的问题及处理情况
    @Expose
    private String question;
    //其他有关事项
    @Expose
    private String other;
    // 日期
    @Expose
    private String dates;
    // 记录人
    @Expose
    private String noteTaker;
    // 总监理工工程师
    @Expose
    private String engineer;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getProjectName() {
        return this.projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getSupervisionPosition() {
        return this.supervisionPosition;
    }

    public void setSupervisionPosition(String supervisionPosition) {
        this.supervisionPosition = supervisionPosition;
    }

    public String getProgressSituation() {
        return this.progressSituation;
    }

    public void setProgressSituation(String progressSituation) {
        this.progressSituation = progressSituation;
    }

    public String getWorkingSitustion() {
        return this.workingSitustion;
    }

    public void setWorkingSitustion(String workingSitustion) {
        this.workingSitustion = workingSitustion;
    }

    public String getQuestion() {
        return this.question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getNoteTaker() {
        return noteTaker;
    }

    public void setNoteTaker(String noteTaker) {
        this.noteTaker = noteTaker;
    }

    public String getEngineer() {
        return engineer;
    }

    public void setEngineer(String engineer) {
        this.engineer = engineer;
    }

}