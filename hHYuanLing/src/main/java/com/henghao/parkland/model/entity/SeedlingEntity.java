package com.henghao.parkland.model.entity;

import com.google.gson.annotations.Expose;
import com.henghao.parkland.model.IdEntity;

import java.util.ArrayList;

/**
 * Created by 晏琦云 on 2017/3/15.
 */

public class SeedlingEntity extends IdEntity {
    /**
     * address:"qwer",
     * contacts:"qwer",
     * content:"qwer",
     * dates:"2018",
     * filesId:"1f51390d8b144e4c9b94d57319bc485c",
     * isNo:0,
     * sid:2,
     * subclass:null,
     * supplier:"qwer",
     * tel:"qwer",
     * titleName:"qwer",
     * type:"asdas",
     * uid:1,
     * url:-[
     * 0:"/ylBigData/project/HsResult/Img/1489038207097.jpg",
     * 1:"/ylBigData/project/HsResult/Img/1489117707113.jpg",
     * 2:"/ylBigData/project/HsResult/Img/1489117713131.jpg"
     * ]
     */
    // 标题名称
    @Expose
    private String titleName;
    // 内容
    @Expose
    private String content;
    // 联系人
    @Expose
    private String contacts;
    // 联系电话
    @Expose
    private String tel;
    // 发布时间
    @Expose
    private String dates;
    // 供应商地址
    @Expose
    private String address;
    // 供应商
    @Expose
    private String supplier;
    // 苗木种类
    @Expose
    private String type;
    // 苗木子种类
    @Expose
    private String subclass;
    // 文件编号
    @Expose
    private String filesId;
    // 文件路径
    @Expose
    private ArrayList<String> url;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubclass() {
        return subclass;
    }

    public void setSubclass(String subclass) {
        this.subclass = subclass;
    }

    public String getFilesId() {
        return filesId;
    }

    public void setFilesId(String filesId) {
        this.filesId = filesId;
    }

    public ArrayList<String> getUrl() {
        return url;
    }

    public void setUrl(ArrayList<String> url) {
        this.url = url;
    }
}
