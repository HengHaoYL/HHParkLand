/* 
 * 文件名：BaseModel.java
 * 版权：Copyright 2009-2010 companyName MediaNet. Co. Ltd. All Rights Reserved. 
 * 描述： 
 * 修改人：
 * 修改时间：
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */
package com.henghao.parkland.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author qyl
 * @version HDMNV100R001, 2015-4-23
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class BaseEntity implements Serializable {

    @Expose
    @SerializedName("result")
    private String msg;

    @Expose
    @SerializedName("status")
    private int status;
    @Expose
    @SerializedName("path")
    private String path;

    @Expose
    @SerializedName("data")
    private Object data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
