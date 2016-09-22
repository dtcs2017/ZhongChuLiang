package com.zcl.hxqh.zhongchuliang.constants;

import android.content.Context;

/**
 * Created by apple on 15/10/8.
 * 静态常量类
 */
public class Constants {

    /**
     * 基础接口*
     */
    public static final String HTTP_API_URL = "http://182.92.8.94:7002/maximo/mobile/";
//    public static final String HTTP_API_URL = "http://10.192.170.23:7004/maximo/mobile/";

    /**
     * 本地接口*
     */
//    public static final String HTTP_API_URL = "http://192.168.1.125:7008/maximo/mobile/";

    //登陆URL
    public static final String SIGN_IN_URL = HTTP_API_URL + "system/login";

    //webservice上传接口
    //远程
    public static final String webserviceURL = "http://182.92.8.94:7002/meaweb/services/MOBILESERVICE";
//    public static final String webserviceURL = "http://10.192.170.23:7004/meaweb/services/MOBILESERVICE";
    //本地
//    public static final String webserviceURL = "http://182.92.8.94:7002/meaweb/wsdl/MOBILESERVICE.wsdl";

    public static String getWsUrl(Context context) {
        return webserviceURL;
    }

    //通用接口查询
    public static final String BASE_URL = HTTP_API_URL + "common/api";

    //主项目详情修改
    public static final String ITEM_UPDATE_URL = HTTP_API_URL + "item/update";


    //生成物资编码接口
    public static final String ITEM_GENERATE_URL = HTTP_API_URL + "itemreq/createCode";
    //发送工作流接口

    public static final String START_FLOW_URL = HTTP_API_URL + "wf/start";
    //审批工作流接口
    public static final String APPROVAL_FLOW_URL = HTTP_API_URL + "wf/approve";

    /**
     * ------------------数据库表名配置－－开始*
     */

    //主项目的appid
    public static final String ITEM_APPID = "ITEM";

    //主项目的表名
    public static final String ITEM_NAME = "ITEM";


    /**
     * 出库管理*
     */
    //出库管理情况appid
    public static final String WORKORDER_APPID = "ISSUE";

    //出库管理的表名
    public static final String WORKORDER_NAME = "WORKORDER";
    //Invreserve的表名
    public static final String INVRESERVE_NAME = "INVRESERVE";


    /**
     * 库存盘点*
     */
    //入库的appid
    public static final String RECEIPT_APPID = "RECEIPT";
    //入库的表名
    public static final String PO_NAME = "PO";
    //入库物料的表名
    public static final String POLINE_NAME = "POLINE";
    /**
     * 库存盘点*
     */
    //库存盘点情况appid
    public static final String INVBALANCES_APPID = "TRANSFER";

    //库存盘点的表名
    public static final String INVBALANCESS_NAME = "INVBALANCES";
    /**
     * 库存使用情况*
     */

    //库存使用情况appid
    public static final String INV_APPID = "INV";

    //库存使用情况的表名
    public static final String INVENTORY_NAME = "INVENTORY";

    /**
     * 物资编码申请*
     */
    //物资编码申请appid
    public static final String ITEMREQ_APPID = "ITEMREQ";
    //物资编码申请的表名
    public static final String ITEMREQ_NAME = "ITEMREQ";
    //物资编码申请line
    public static final String ITEMREQLINE_NAME = "ITEMREQLINE";


    /**
     * 库存转移*
     */
    //LOCATIONS的appid
    public static final String LOCATIONS_APPID = "TRANSFER";
    //LOCATIONS的表名
    public static final String LOCATIONS_NAME = "LOCATIONS";
    //INVBALANCES的表名
    public static final String INVBALANCES_NAME = "INVBALANCES";


    /**
     * 用户登录表识--开始*
     */
    public static final String LOGINSUCCESS = "USER-S-101"; //登录成功

    public static final String CHANGEIMEI = "USER-S-104"; //登录成功,检测到用户更换手机登录

    public static final String USERNAMEERROR = "USER-E-100";//用户名密码错误

    public static final String GETDATASUCCESS = "GLOBAL-S-0";//获取数据成功


    /**
     * 入库管理的发放与接收*
     */
    public static final String RECEIPT = "RECEIPT";//接收
    public static final String RETURN = "RETURN";//退货
}
