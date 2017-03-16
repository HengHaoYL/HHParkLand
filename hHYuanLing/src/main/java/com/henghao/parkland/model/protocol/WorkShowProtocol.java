package com.henghao.parkland.model.protocol;

import android.content.Context;

import com.benefit.buy.library.http.query.callback.AjaxStatus;
import com.benefit.buy.library.utils.tools.ToolsJson;
import com.benefit.buy.library.utils.tools.ToolsKit;
import com.google.gson.reflect.TypeToken;
import com.henghao.parkland.ProtocolUrl;
import com.henghao.parkland.model.ascyn.BaseModel;
import com.henghao.parkland.model.ascyn.BeeCallback;
import com.henghao.parkland.model.entity.BaseEntity;
import com.henghao.parkland.model.entity.BidEntity;
import com.henghao.parkland.model.entity.RecruitEntity;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by 晏琦云 on 2017/3/15.
 */

public class WorkShowProtocol extends BaseModel {
    public WorkShowProtocol(Context context) {
        super(context);
    }

    /**
     * 查询设备租赁
     */
    public void queryEquipmentLeasing() {
        try {
            String url = ProtocolUrl.RELEASE_QUERYEQUIPMENTLEASING;
            this.mBeeCallback.url(url).type(String.class);
            this.aq.ajax(this.mBeeCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询苗木信息
     */
    public void querySeedlingmessage() {
        try {
            String url = ProtocolUrl.RELEASE_QUERYSEEDLINGMESSAGE;
            this.mBeeCallback.url(url).type(String.class);
            this.aq.ajax(this.mBeeCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询招标信息
     */
    public void queryBid() {
        try {
            String url = ProtocolUrl.RELEASE_QUERYBID;
            this.mBeeCallback.url(url).type(String.class);
            this.aq.ajax(this.mBeeCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询人员招聘
     */
    public void queryRecruit() {
        try {
            String url = ProtocolUrl.RELEASE_QUERYRECRUIT;
            this.mBeeCallback.url(url).type(String.class);
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
                    WorkShowProtocol.this.OnMessageResponse(url, mBaseEntity, status);
                    return;
                }
                if (mBaseEntity.getData() == null || mBaseEntity.getData() == "null") {
                    WorkShowProtocol.this.OnMessageResponse(url, mBaseEntity, status);
                    return;
                }
                String data = ToolsJson.toJson(mBaseEntity.getData());
                if (ToolsKit.isEmpty(data)) {
                    WorkShowProtocol.this.OnMessageResponse(url, mBaseEntity, status);
                    return;
                }
                /**** end ****/
                if (url.endsWith(ProtocolUrl.RELEASE_QUERYEQUIPMENTLEASING)) {
                    // 查询设备租赁
                    WorkShowProtocol.this.OnMessageResponse(url, mBaseEntity, status);
                }
                if (url.endsWith(ProtocolUrl.RELEASE_QUERYSEEDLINGMESSAGE)) {
                    // 查询苗木信息
                    WorkShowProtocol.this.OnMessageResponse(url, mBaseEntity, status);
                }
                if (url.endsWith(ProtocolUrl.RELEASE_QUERYBID)) {
                    // 查询招标信息
                    Type type = new TypeToken<List<BidEntity>>() {
                    }.getType();
                    List<BidEntity> homeData = ToolsJson.parseObjecta(data, type);
                    WorkShowProtocol.this.OnMessageResponse(url, homeData, status);
                }
                if (url.endsWith(ProtocolUrl.RELEASE_QUERYRECRUIT)) {
                    // 查询人员招聘
                    Type type = new TypeToken<List<RecruitEntity>>() {
                    }.getType();
                    List<RecruitEntity> homeData = ToolsJson.parseObjecta(data, type);
                    WorkShowProtocol.this.OnMessageResponse(url, homeData, status);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}
