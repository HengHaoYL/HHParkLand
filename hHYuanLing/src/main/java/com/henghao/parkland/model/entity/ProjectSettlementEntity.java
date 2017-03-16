package com.henghao.parkland.model.entity;

import com.google.gson.annotations.Expose;
import com.henghao.parkland.model.IdEntity;

import java.util.ArrayList;

/**
 * Created by 晏琦云 on 2017/3/3.
 * 项目结算
 */

public class ProjectSettlementEntity extends IdEntity {
    /**
     * settlementBookId: "xx",               结算书编号
     * isChecked: "xx",                      是否被选中
     * dates:"xx",                           结算日期
     * sid:"xx",                             表主键
     * name:"xx",                            项目名称
     * url:-[                                返回图片路径集合（返回json为string）
     * 0:"xx"                                结算书文件存放路径
     * ],
     */
    private boolean isChecked;
    @Expose
    private String dates;
    @Expose
    private int sid;
    @Expose
    private String settlementBookId;
    @Expose
    private ArrayList<String> url;
    @Expose
    private String name;

    public String getName() {
        return name;
    }

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

    public void setName(String name) {
        this.name = name;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getSettlementBookId() {
        return settlementBookId;
    }

    public void setSettlementBookId(String settlementBookId) {
        this.settlementBookId = settlementBookId;
    }

    public ArrayList<String> getUrl() {
        return url;
    }

    public void setUrl(ArrayList<String> url) {
        this.url = url;
    }
}
