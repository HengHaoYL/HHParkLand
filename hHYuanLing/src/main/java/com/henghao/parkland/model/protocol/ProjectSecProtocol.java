/*
 * 文件名：LoginFilfterProtocol.java
 * 版权：Copyright 2009-2010 companyName MediaNet. Co. Ltd. All Rights Reserved.
 * 描述：
 * 修改人：
 * 修改时间：
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */
package com.henghao.parkland.model.protocol;

import android.content.Context;

import com.benefit.buy.library.http.query.callback.AjaxStatus;
import com.benefit.buy.library.utils.tools.ToolsJson;
import com.benefit.buy.library.utils.tools.ToolsKit;
import com.henghao.parkland.ProtocolUrl;
import com.henghao.parkland.model.ascyn.BaseModel;
import com.henghao.parkland.model.ascyn.BeeCallback;
import com.henghao.parkland.model.entity.BaseEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author 张宪文
 * @version HDMNV100R001, 2017年2月20日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ProjectSecProtocol extends BaseModel {

    public ProjectSecProtocol(Context context) {
        super(context);
    }


    /**
     * 查询 现场勘查情况
     * @param uid
     */
    public void queryXCKC(String uid) {
        try {
            String url = ProtocolUrl.PROJECT_QUERYXCKC;
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("uid", uid);
            this.mBeeCallback.url(url).type(String.class).params(params);
            this.aq.ajax(this.mBeeCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 提交 我的轨迹
     * @param uid
     */
    public void saveMylocusMsg(String uid,String personnel,String details,String dates,String workType) {
        try {
            String url = ProtocolUrl.PROJECT_SAVE_MYLOCUSMSG;
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("uid", uid);
            params.put("personnel", personnel);
            params.put("details", details);
            params.put("dates", dates);
            params.put("workType", workType);
            this.mBeeCallback.url(url).type(String.class).params(params);
            this.aq.ajax(this.mBeeCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 查询 我的轨迹
     * @param uid
     */
    public void queryMylocusMsg(String uid) {
        try {
            String url = ProtocolUrl.PROJECT_QUERY_MYLOCUSMSG;
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("uid", uid);
            this.mBeeCallback.url(url).type(String.class).params(params);
            this.aq.ajax(this.mBeeCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private final BeeCallback<String> mBeeCallback = new BeeCallback<String>() {

        @Override
        public void callback(String url, String object, AjaxStatus status) {
            try {
                /**** start ****/
                BaseEntity mBaseEntity = ToolsJson.parseObjecta(object, BaseEntity.class);
                if (mBaseEntity == null) {
                    ProjectSecProtocol.this.OnMessageResponse(url, mBaseEntity, status);
                    return;
                }
                if (mBaseEntity.getData() == null || mBaseEntity.getData() == "null") {
                    ProjectSecProtocol.this.OnMessageResponse(url, mBaseEntity, status);
                    return;
                }
                String data = ToolsJson.toJson(mBaseEntity.getData());
                if (ToolsKit.isEmpty(data)) {
                    ProjectSecProtocol.this.OnMessageResponse(url, mBaseEntity, status);
                    return;
                }
                /**** end ****/
                if (url.endsWith(ProtocolUrl.PROJECT_QUERYXCKC) || url.endsWith(ProtocolUrl.PROJECT_QUERY_MYLOCUSMSG)) {
                    // 现场勘查
                   /* Type type = new TypeToken<List<ProjectXcKcEntity>>() {
                    }.getType();
                    List<ProjectXcKcEntity> homeData = ToolsJson.parseObjecta(data, type);*/
                    ProjectSecProtocol.this.OnMessageResponse(url, mBaseEntity, status);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}
