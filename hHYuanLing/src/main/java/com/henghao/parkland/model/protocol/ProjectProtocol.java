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
import com.google.gson.reflect.TypeToken;
import com.henghao.parkland.ProtocolUrl;
import com.henghao.parkland.model.ascyn.BaseModel;
import com.henghao.parkland.model.ascyn.BeeCallback;
import com.henghao.parkland.model.entity.BaseEntity;
import com.henghao.parkland.model.entity.ProjectInfoEntity;
import com.henghao.parkland.model.entity.ProjectKGBGEntity;
import com.henghao.parkland.model.entity.ProjectSBDataEntity;
import com.henghao.parkland.model.entity.ProjectTeamEntity;
import com.henghao.parkland.model.entity.SGWalletEntity;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author 张宪文
 * @version HDMNV100R001, 2017年2月20日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ProjectProtocol extends BaseModel {

    public ProjectProtocol(Context context) {
        super(context);
    }

    /**
     * 施工备忘提交
     *
     * @param projectName
     * @param projectDetail
     * @param uid
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public void upLoadSGBW(String projectName, String projectDetail, String uid) {
        try {
            String url = ProtocolUrl.PROJECT_SGBEIWANG;
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("projectName", projectName);
            params.put("projectDetail", projectDetail);
            params.put("uid", uid);
            this.mBeeCallback.url(url).type(String.class).params(params);
            this.aq.ajax(this.mBeeCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 工作备忘提交
     *
     * @param startTime
     * @param endTime
     * @param content
     * @param uid
     */
    public void upLoadWorkBW(String startTime, String endTime, String content, String uid) {
        try {
            String url = ProtocolUrl.PROJECT_WORKBEIWANG;
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("startTime", startTime);
            params.put("endTime", endTime);
            params.put("content", content);
            params.put("uid", uid);
            this.mBeeCallback.url(url).type(String.class).params(params);
            this.aq.ajax(this.mBeeCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 施工钱包查询
     *
     * @param uid
     */
    public void querySGWallet(String uid) {
        try {
            String url = ProtocolUrl.PROJECT_SGWALLET;
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("uid", uid);
            this.mBeeCallback.url(url).type(String.class).params(params);
            this.aq.ajax(this.mBeeCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询用户项目信息
     *
     * @param uid
     */
    public void queryXMMsg(String uid) {
        try {
            String url = ProtocolUrl.PROJECT_SGMSG;
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("uid", uid);
            this.mBeeCallback.url(url).type(String.class).params(params);
            this.aq.ajax(this.mBeeCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询施工资料
     *
     * @param uid
     */
    public void querySGZL(String uid) {
        try {
            String url = ProtocolUrl.PROJECT_QUERYSGZL;
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("uid", uid);
            this.mBeeCallback.url(url).type(String.class).params(params);
            this.aq.ajax(this.mBeeCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询项目信息
     *
     * @param uid
     */
    public void queryProjectMsg(String uid) {
        try {
            String url = ProtocolUrl.PROJECT_QUERYPROJECTMSG;
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("uid", uid);
            this.mBeeCallback.url(url).type(String.class).params(params);
            this.aq.ajax(this.mBeeCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询变更管理
     *
     * @param uid
     */
    public void queryAlterationMsg(String uid) {
        try {
            String url = ProtocolUrl.PROJECT_QUERYALTERATIONMSG;
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("uid", uid);
            this.mBeeCallback.url(url).type(String.class).params(params);
            this.aq.ajax(this.mBeeCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询项目结算
     *
     * @param uid
     */
    public void querySettlementMsg(String uid) {
        try {
            String url = ProtocolUrl.PROJECT_QUERYSETTLEMENTMSG;
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("uid", uid);
            this.mBeeCallback.url(url).type(String.class).params(params);
            this.aq.ajax(this.mBeeCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询供货方信息
     *
     * @param uid
     */
    public void querySupplierMsg(String uid) {
        try {
            String url = ProtocolUrl.PROJECT_QUERYSUPPLIERMSG;
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("uid", uid);
            this.mBeeCallback.url(url).type(String.class).params(params);
            this.aq.ajax(this.mBeeCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询设备信息
     *
     * @param uid
     */
    public void queryEquipmentMsg(String uid) {
        try {
            String url = ProtocolUrl.PROJECT_QUERYEQUIPMENTMSG;
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("uid", uid);
            this.mBeeCallback.url(url).type(String.class).params(params);
            this.aq.ajax(this.mBeeCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询会审结果
     *
     * @param uid
     */
    public void queryHsResultMsg(String uid) {
        try {
            String url = ProtocolUrl.PROJECT_QUERYHSRESULTMSG;
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("uid", uid);
            this.mBeeCallback.url(url).type(String.class).params(params);
            this.aq.ajax(this.mBeeCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询工序报验
     *
     * @param uid
     */
    public void queryCheckoutMsg(String uid) {
        try {
            String url = ProtocolUrl.PROJECT_QUERYCHECKOUTMSG;
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("uid", uid);
            this.mBeeCallback.url(url).type(String.class).params(params);
            this.aq.ajax(this.mBeeCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询施工人员
     *
     * @param uid
     */
    public void querySgPersonnelMsg(String uid) {
        try {
            String url = ProtocolUrl.PROJECT_QUERYSGPERSONNELMSG;
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("uid", uid);
            this.mBeeCallback.url(url).type(String.class).params(params);
            this.aq.ajax(this.mBeeCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询技术交底
     *
     * @param uid
     */
    public void queryTechnologyMsg(String uid) {
        try {
            String url = ProtocolUrl.PROJECT_QUERYDECLARATIONMSG;
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("uid", uid);
            this.mBeeCallback.url(url).type(String.class).params(params);
            this.aq.ajax(this.mBeeCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询开工报告
     *
     * @param uid
     */
    public void queryKgReportMsg(String uid) {
        try {
            String url = ProtocolUrl.PROJECT_QUERYKGREPORTMSG;
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("uid", uid);
            this.mBeeCallback.url(url).type(String.class).params(params);
            this.aq.ajax(this.mBeeCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 项目信息提交
     *
     * @param uid
     * @param xmAdd
     * @param xmContact
     * @param xmName
     * @param xmPerson
     * @param xmPersonNum
     */
    public void saveProjectMsg(String uid, String xmAdd, String xmContact, String xmName, String xmPerson, int xmPersonNum) {
        try {
            String url = ProtocolUrl.PROJECT_SAVEPROJECTMSG;
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("uid", uid);
            params.put("xmAdd", xmAdd);
            params.put("xmContact", xmContact);
            params.put("xmName", xmName);
            params.put("xmPerson", xmPerson);
            params.put("xmPersonNum", xmPersonNum);
            this.mBeeCallback.url(url).type(String.class).params(params);
            this.aq.ajax(this.mBeeCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 施工人员提交
     *
     * @param personnelType
     * @param workPost
     * @param psIdcard
     * @param psName
     * @param psTel
     * @param uid
     */
    public void saveSgPersonnelMsg(String personnelType, String workPost, String psIdcard, String psName, String psTel, String uid) {
        try {
            String url = ProtocolUrl.PROJECT_SAVESGPERSONNELMSG;
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("uid", uid);
            params.put("personnelType", personnelType);
            params.put("workPost", workPost);
            params.put("psIdcard", psIdcard);
            params.put("psName", psName);
            params.put("psTel", psTel);
            this.mBeeCallback.url(url).type(String.class).params(params);
            this.aq.ajax(this.mBeeCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设备信息提交
     *
     * @param sbName
     * @param sbSpec
     * @param sbNum
     * @param sbPurpose
     * @param sbSource
     * @param uid
     */
    public void saveEquipmentMsg(String sbName, String sbSpec, int sbNum, String sbPurpose, String sbSource, String uid) {
        try {
            String url = ProtocolUrl.PROJECT_SAVEEQUIPMENTMSG;
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("uid", uid);
            params.put("sbName", sbName);
            params.put("sbSpec", sbSpec);
            params.put("sbNum", sbNum);
            params.put("sbPurpose", sbPurpose);
            params.put("sbSource", sbSource);
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
                    ProjectProtocol.this.OnMessageResponse(url, mBaseEntity, status);
                    return;
                }
                if (mBaseEntity.getData() == null || mBaseEntity.getData() == "null") {
                    ProjectProtocol.this.OnMessageResponse(url, mBaseEntity, status);
                    return;
                }
                String data = ToolsJson.toJson(mBaseEntity.getData());
                if (ToolsKit.isEmpty(data)) {
                    ProjectProtocol.this.OnMessageResponse(url, mBaseEntity, status);
                    return;
                }
                /**** end ****/
                if (url.endsWith(ProtocolUrl.PROJECT_SGWALLET)) {
                    // 查询施工钱包信息
                    Type type = new TypeToken<List<SGWalletEntity>>() {
                    }.getType();
                    List<SGWalletEntity> homeData = ToolsJson.parseObjecta(data, type);
                    ProjectProtocol.this.OnMessageResponse(url, homeData, status);
                }
                if (url.endsWith(ProtocolUrl.PROJECT_QUERYSGZL)) {
                    // 查询施工资料
                    ProjectProtocol.this.OnMessageResponse(url, mBaseEntity, status);
                }
                if (url.endsWith(ProtocolUrl.PROJECT_QUERYPROJECTMSG)) {
                    // 查询项目信息
                    Type type = new TypeToken<List<ProjectInfoEntity>>() {
                    }.getType();
                    List<ProjectInfoEntity> homeData = ToolsJson.parseObjecta(data, type);
                    ProjectProtocol.this.OnMessageResponse(url, homeData, status);
                }
                if (url.endsWith(ProtocolUrl.PROJECT_QUERYHSRESULTMSG)) {
                    // 查询会审结果
                    ProjectProtocol.this.OnMessageResponse(url, mBaseEntity, status);
                }
                if (url.endsWith(ProtocolUrl.PROJECT_QUERYKGREPORTMSG)) {
                    // 查询开工报告
                    Type type = new TypeToken<List<ProjectKGBGEntity>>() {
                    }.getType();
                    List<ProjectKGBGEntity> homeData = ToolsJson.parseObjecta(data, type);
                    ProjectProtocol.this.OnMessageResponse(url, homeData, status);
                }
                if (url.endsWith(ProtocolUrl.PROJECT_QUERYSGPERSONNELMSG)) {
                    // 查询施工人员
                    Type type = new TypeToken<List<ProjectTeamEntity>>() {
                    }.getType();
                    List<ProjectTeamEntity> homeData = ToolsJson.parseObjecta(data, type);
                    ProjectProtocol.this.OnMessageResponse(url, homeData, status);
                }
                if (url.endsWith(ProtocolUrl.PROJECT_QUERYDECLARATIONMSG)) {
                    // 查询技术交底
                    ProjectProtocol.this.OnMessageResponse(url, mBaseEntity, status);
                }
                if (url.endsWith(ProtocolUrl.PROJECT_QUERYEQUIPMENTMSG)) {
                    // 查询设备信息
                    Type type = new TypeToken<List<ProjectSBDataEntity>>() {
                    }.getType();
                    List<ProjectSBDataEntity> homeData = ToolsJson.parseObjecta(data, type);
                    ProjectProtocol.this.OnMessageResponse(url, homeData, status);
                }
                if (url.endsWith(ProtocolUrl.PROJECT_QUERYCHECKOUTMSG)) {
                    // 查询工序报验
                    ProjectProtocol.this.OnMessageResponse(url, mBaseEntity, status);
                }
                if (url.endsWith(ProtocolUrl.PROJECT_QUERYALTERATIONMSG)) {
                    // 查询变更管理
                    ProjectProtocol.this.OnMessageResponse(url, mBaseEntity, status);
                }
                if (url.endsWith(ProtocolUrl.PROJECT_QUERYSUPPLIERMSG)) {
                    // 查询供货方信息
                    ProjectProtocol.this.OnMessageResponse(url, mBaseEntity, status);
                }
                if (url.endsWith(ProtocolUrl.PROJECT_QUERYSETTLEMENTMSG)) {
                    // 查询项目结算
                    ProjectProtocol.this.OnMessageResponse(url, mBaseEntity, status);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}
