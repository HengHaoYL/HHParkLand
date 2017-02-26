package com.henghao.parkland.model.entity;

/**
 * Created by 晏琦云 on 2017/2/24.
 */

public class LogEntity {
    /*
    comments:"xx",备注
    predictfinishTime:"xx",预计完成日期
    projectName:"xx",项目名称
    projectDetail:"xx",项目内容
    projectPlan:"xx",项目进度
    projectTime:"xx",日期
    reason:"xxx",差异原因
    requests:"xx",资源需求
    send:"xx",接收者
     */
    private String comments;//备注
    private String predictfinishTime;//预计完成日期
    private String projectName;//项目名称
    private String projectDetail;//项目内容
    private String projectPlan;//项目进度
    private String projrectTime;//日期
    private String reason;//差异原因
    private String requests;//资源需求
    private String send;//接收者

    public String getProjectDetail() {
        return projectDetail;
    }

    public void setProjectDetail(String projectDetail) {
        this.projectDetail = projectDetail;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getPredictfinishTime() {
        return predictfinishTime;
    }

    public void setPredictfinishTime(String predictfinishTime) {
        this.predictfinishTime = predictfinishTime;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectPlan() {
        return projectPlan;
    }

    public void setProjectPlan(String projectPlan) {
        this.projectPlan = projectPlan;
    }

    public String getProjrectTime() {
        return projrectTime;
    }

    public void setProjrectTime(String projrectTime) {
        this.projrectTime = projrectTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRequests() {
        return requests;
    }

    public void setRequests(String requests) {
        this.requests = requests;
    }

    public String getSend() {
        return send;
    }

    public void setSend(String send) {
        this.send = send;
    }
}
