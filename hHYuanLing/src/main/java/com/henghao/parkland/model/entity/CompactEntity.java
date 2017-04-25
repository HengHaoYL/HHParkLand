package com.henghao.parkland.model.entity;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * CompactEntity entity. 合同实体类
 *
 * @author 严彬荣
 */
public class CompactEntity implements Serializable {
    /**
     * checking:"审核未通过",
     * comment:"清晰度未达标准",
     * compactId:"2b238c9ee9c546c3a6355d515b671b9f",
     * dates:"2017-04-25 10:18",
     * document:"/gardenFile/compact/buildCompact/document/2017042510/5f6ff32f41be4ff082b53a808363c758.docx",
     * genre:"建设类",
     * id:13,
     * url:-[
     * 0:"/gardenFile/compact/buildCompact/img/2017042510180508101018050810/d7f90aca270e4410a9c9978b356494b2.jpeg",
     * 1:"/gardenFile/compact/buildCompact/img/2017042510180508101018050810/353d4473c3f1431285dd3656c84e94c8.jpeg",
     * 2:"/gardenFile/compact/buildCompact/img/2017042510180508101018050810/ffa51b3cbc9e4107b273a12b42b35fcd.jpeg",
     * 3:"/gardenFile/compact/buildCompact/img/2017042510180508101018050810/7c643cea360d41fb8e4ffaeac39c7f73.jpeg",
     * 4:"/gardenFile/compact/buildCompact/img/2017042510180508101018050810/91c65d9d8633447280a9f504b7dba984.jpeg"
     * ]
     */
    private boolean isChecked;//是否被选中
    @Expose
    private Integer id;// 表ID
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
    private String comment;   // 备注信息
    @Expose
    private String checking;    //审核状态
    @Expose
    private ArrayList<String> url;    // 图片路径集合

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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