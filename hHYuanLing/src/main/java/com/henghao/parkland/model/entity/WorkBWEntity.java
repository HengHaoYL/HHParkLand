package com.henghao.parkland.model.entity;

/**
 * Created by 晏琦云 on 2017/2/24.
 */

public class WorkBWEntity {
    private String start_time;//开始时间
    private String end_time;//结束时间
    private String work_content;//工作内容

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getWork_content() {
        return work_content;
    }

    public void setWork_content(String work_content) {
        this.work_content = work_content;
    }
}
