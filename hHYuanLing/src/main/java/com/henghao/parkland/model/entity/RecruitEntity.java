package com.henghao.parkland.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.henghao.parkland.model.IdEntity;


/**
 * Created by 晏琦云 on 2017/8/10.
 * 人员招聘实体
 * "rid":               数据id,
 * "positions":         职位,
 * "companyName":       公司名称,
 * "companyIntro":      公司简介,
 * "companyAdress":     公司地址,
 * "workAdress":        工作地址,
 * "content":           工作内容,
 * "contact":           联系人,
 * "email":             邮箱,
 * "money":             月薪,
 * "experience":        工作经历,
 * "tel":               联系方式,
 * "evaluate":          自我评价,
 * "date":              发布日期,
 * "time":              发布时间
 * "type":              类型（招聘/应聘）
 */
public class RecruitEntity extends IdEntity {

    // 数据id
    @Expose
    @SerializedName("rid")
    private int rid;
    // 职位
    @Expose
    @SerializedName("positions")
    private String positions;
    // 公司名称
    @Expose
    @SerializedName("companyName")
    private String companyName;
    // 公司简介
    @Expose
    @SerializedName("companyIntro")
    private String companyIntro;
    // 公司地址
    @Expose
    @SerializedName("companyAdress")
    private String companyAdress;
    // 工作地址
    @Expose
    @SerializedName("workAdress")
    private String workAdress;
    // 工作内容
    @Expose
    @SerializedName("content")
    private String content;
    // 联系人
    @Expose
    @SerializedName("contact")
    private String contact;
    // 邮箱
    @Expose
    @SerializedName("email")
    private String email;
    // 月薪
    @Expose
    @SerializedName("money")
    private double money;
    // 工作经历
    @Expose
    @SerializedName("experience")
    private String experience;
    // 联系方式
    @Expose
    @SerializedName("tel")
    private String tel;
    // 自我评价
    @Expose
    @SerializedName("evaluate")
    private String evaluate;
    // 发布日期
    @Expose
    @SerializedName("date")
    private String date;
    // 发布时间
    @Expose
    @SerializedName("time")
    private String time;
    // 类型（招聘/应聘）
    private String type;

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getPositions() {
        return positions;
    }

    public void setPositions(String positions) {
        this.positions = positions;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyIntro() {
        return companyIntro;
    }

    public void setCompanyIntro(String companyIntro) {
        this.companyIntro = companyIntro;
    }

    public String getCompanyAdress() {
        return companyAdress;
    }

    public void setCompanyAdress(String companyAdress) {
        this.companyAdress = companyAdress;
    }

    public String getWorkAdress() {
        return workAdress;
    }

    public void setWorkAdress(String workAdress) {
        this.workAdress = workAdress;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
