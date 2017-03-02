package com.henghao.parkland.model.entity;

import com.google.gson.annotations.Expose;
import com.henghao.parkland.model.IdEntity;

/**
 * Created by 晏琦云 on 2017/3/1.
 * 设备信息
 */

public class ProjectSBDataEntity extends IdEntity {
    // 设备名称
    @Expose
    private String sbName;
    // 设备型号
    @Expose
    private String sbSpec;
    // 设备数量
    @Expose
    private int sbNum;
    // 设备用途
    @Expose
    private String sbPurpose;
    // 设备来源
    @Expose
    private String sbSource;

    public String getSbName() {
        return sbName;
    }

    public void setSbName(String sbName) {
        this.sbName = sbName;
    }

    public int getSbNum() {
        return sbNum;
    }

    public void setSbNum(int sbNum) {
        this.sbNum = sbNum;
    }

    public String getSbSpec() {
        return sbSpec;
    }

    public void setSbSpec(String sbSpec) {
        this.sbSpec = sbSpec;
    }

    public String getSbPurpose() {
        return sbPurpose;
    }

    public void setSbPurpose(String sbPurpose) {
        this.sbPurpose = sbPurpose;
    }

    public String getSbSource() {
        return sbSource;
    }

    public void setSbSource(String sbSource) {
        this.sbSource = sbSource;
    }
}
