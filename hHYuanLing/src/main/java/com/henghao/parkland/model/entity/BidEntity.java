package com.henghao.parkland.model.entity;

import com.google.gson.annotations.Expose;
import com.henghao.parkland.model.IdEntity;

/**
 * BidEntity entity. 招标信息表实体
 *
 * @author 严彬荣
 */
public class BidEntity extends IdEntity {

    // 标题名称
    @Expose
    private String titleName;
    // 招标内容
    @Expose
    private String content;
    // 联系人
    @Expose
    private String contacts;
    // 联系电话
    @Expose
    private String tel;
    // 日期
    @Expose
    private String dates;

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }
}