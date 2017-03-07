package com.henghao.parkland.model.entity;

/**
 * BidEntity entity. 招标信息表实体
 *
 * @author 严彬荣
 */
public class BidEntity implements java.io.Serializable {

    // 企业名称
    private String companyName;
    // 企业地址
    private String companyAdd;
    // 联系人
    private String contacts;
    // 联系电话
    private String tel;
    // 投标信息
    private String tenders;
    // 招标信息
    private String bids;
    // 日期
    private String dates;
    // 招标类型
    private String type;
    // 用户ID
    private Integer uid;

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAdd() {
        return this.companyAdd;
    }

    public void setCompanyAdd(String companyAdd) {
        this.companyAdd = companyAdd;
    }

    public String getContacts() {
        return this.contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getTel() {
        return this.tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTenders() {
        return this.tenders;
    }

    public void setTenders(String tenders) {
        this.tenders = tenders;
    }

    public String getBids() {
        return this.bids;
    }

    public void setBids(String bids) {
        this.bids = bids;
    }

    public String getDates() {
        return this.dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getUid() {
        return this.uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

}