package com.henghao.parkland.model.entity;

import com.google.gson.annotations.Expose;
import com.henghao.parkland.model.IdEntity;

import java.util.ArrayList;

/**
 * Created by 晏琦云 on 2017/3/15.
 * 设备租赁信息实体
 */

public class EquipmentLeasingEntity extends IdEntity {
    /**
     * contacts:联系人
     * content:内容
     * dates:发布时间
     * filesId:图片根路径
     * tel:联系电话
     * titleName:标题名称
     * url:[xxxxxx,xxxxx]
     */
    @Expose
    private String contacts;
    @Expose
    private String content;
    @Expose
    private String dates;
    @Expose
    private String filesId;
    @Expose
    private String tel;
    @Expose
    private String titleName;
    @Expose
    private ArrayList<String> url;

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getFilesId() {
        return filesId;
    }

    public void setFilesId(String filesId) {
        this.filesId = filesId;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public ArrayList<String> getUrl() {
        return url;
    }

    public void setUrl(ArrayList<String> url) {
        this.url = url;
    }
}
