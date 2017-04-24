package com.henghao.parkland.model.entity;

import com.google.gson.annotations.Expose;
import com.henghao.parkland.model.IdEntity;

import java.util.ArrayList;

/**
 * GhCompact entity. 景观类合同实体类
 *
 * @author 严彬荣
 */
public class CompactEntity extends IdEntity {
    /**
     * bid:6,
     * checking:"正在审核",
     * compactId:"50cbe8cceded46e48b8395d35d34a8b2",
     * dates:"2017-04-21 11:02",
     * document:"/gardenFile/compact/buildCompact/document/2017042111/5a8db089eb63495ba15ed652c5ca9f19.docx",
     * genre:"建设类",
     * isNo:0,
     * pictureId:"7dfb4336562d41fa9d4cff26dd7391b0",
     * uid:"123",
     * url:-[
     * 0:"/gardenFile/compact/buildCompact/img/2017042111020607521102060752/413ff5e85a504e01b27001c69e4a86eb.JPG",
     * 1:"/gardenFile/compact/buildCompact/img/2017042111020607521102060752/9867d6f0758346908642c9d3c8e93d6f.jpg"
     * ]
     */
    private boolean isChecked;//是否被选中
    @Expose
    private Integer bid;// 表ID
    @Expose
    private String compactId; // 合同存档编号
    @Expose
    private String pictureId;  // 合同图片编号
    @Expose
    private String document; // 合同文档
    @Expose
    private String genre;    // 合同类型
    @Expose
    private String dates;   // 录入时间
    @Expose
    private String checking;    //审核状态
    @Expose
    private ArrayList<String> url;    // 图片路径集合

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public String getCompactId() {
        return compactId;
    }

    public void setCompactId(String compactId) {
        this.compactId = compactId;
    }

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getChecking() {
        return checking;
    }

    public void setChecking(String checking) {
        this.checking = checking;
    }

    public ArrayList<String> getUrl() {
        return url;
    }

    public void setUrl(ArrayList<String> url) {
        this.url = url;
    }
}