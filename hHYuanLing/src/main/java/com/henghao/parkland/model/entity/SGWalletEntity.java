package com.henghao.parkland.model.entity;

import com.google.gson.annotations.Expose;
import com.henghao.parkland.model.IdEntity;

/**
 * Created by 晏琦云 on 2017/2/21.
 * 施工钱包实体
 */

public class SGWalletEntity extends IdEntity {
    /*
    "wid": 4,
    "uid": "a41f453a7bb84b398af7a409c202783b",
    "money": 333,
    "types": 1,
    "comment": "333",
    "transactionTime": "2017-05-04 14:45",
    "isNo": 0
     */
    @Expose
    private int wid;
    @Expose
    private String uid;
    @Expose
    private int types;
    @Expose
    private double money;
    @Expose
    private String comment;
    @Expose
    private String transactionTime;//交易时间
    @Expose
    private int isNo;//是否不显示

    public int getWid() {
        return wid;
    }

    public void setWid(int wid) {
        this.wid = wid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getTypes() {
        return types;
    }

    public void setTypes(int types) {
        this.types = types;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    public int getIsNo() {
        return isNo;
    }

    public void setIsNo(int isNo) {
        this.isNo = isNo;
    }
}
