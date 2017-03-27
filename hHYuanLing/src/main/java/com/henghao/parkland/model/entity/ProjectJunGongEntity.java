package com.henghao.parkland.model.entity;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;


/**
 * Finalacceptance entity.竣工验收表实体
 *
 * @author 严彬荣
 */
public class ProjectJunGongEntity implements java.io.Serializable {

    private boolean isChecked;//是否被选中
    // 表主键
    @Expose
    private int fid;
    // 工程名称
    @Expose
    private String projectName;
    // 竣工时间
    @Expose
    private String dates;
    // 验收人员
    @Expose
    private String inspectionPersonnel;
    // 验收图路径编号
    @Expose
    private String completionDrawingId;
    // 文件路径
    @Expose
    private ArrayList<String> url;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getInspectionPersonnel() {
        return inspectionPersonnel;
    }

    public void setInspectionPersonnel(String inspectionPersonnel) {
        this.inspectionPersonnel = inspectionPersonnel;
    }

    public String getCompletionDrawingId() {
        return completionDrawingId;
    }

    public void setCompletionDrawingId(String completionDrawingId) {
        this.completionDrawingId = completionDrawingId;
    }

    public ArrayList<String> getUrl() {
        return url;
    }

    public void setUrl(ArrayList<String> url) {
        this.url = url;
    }
}