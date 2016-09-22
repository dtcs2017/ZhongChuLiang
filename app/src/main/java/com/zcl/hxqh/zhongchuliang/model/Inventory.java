package com.zcl.hxqh.zhongchuliang.model;

import com.instagram.common.json.annotation.JsonField;
import com.instagram.common.json.annotation.JsonType;

/**
 * Created by apple on 15/6/3.
 * 库存使用情况信息
 */
@JsonType
public class Inventory extends Entity  {
    private static final String TAG = "Inventory";

    @JsonField(fieldName = "binnum")
    public String binnum; //货柜编号

    @JsonField(fieldName = "curbaltotal")
    public String curbaltotal; //当前余量

    @JsonField(fieldName = "issueunit")
    public String issueunit; //发放单位

    @JsonField(fieldName = "itemdesc")
    public String itemdesc; //项目描述

    @JsonField(fieldName = "itemnum")
    public String itemnum; //项目编号
    @JsonField(fieldName = "location")
    public String location; //仓库
    @JsonField(fieldName = "locationdesc")
    public String locationdesc; //仓库描述
    @JsonField(fieldName = "lotnum")
    public String lotnum; //批次


    public String getBinnum() {
        return binnum;
    }

    public void setBinnum(String binnum) {
        this.binnum = binnum;
    }

    public String getCurbaltotal() {
        return curbaltotal;
    }

    public void setCurbaltotal(String curbaltotal) {
        this.curbaltotal = curbaltotal;
    }

    public String getIssueunit() {
        return issueunit;
    }

    public void setIssueunit(String issueunit) {
        this.issueunit = issueunit;
    }

    public String getItemdesc() {
        return itemdesc;
    }

    public void setItemdesc(String itemdesc) {
        this.itemdesc = itemdesc;
    }

    public String getItemnum() {
        return itemnum;
    }

    public void setItemnum(String itemnum) {
        this.itemnum = itemnum;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocationdesc() {
        return locationdesc;
    }

    public void setLocationdesc(String locationdesc) {
        this.locationdesc = locationdesc;
    }

    public String getLotnum() {
        return lotnum;
    }

    public void setLotnum(String lotnum) {
        this.lotnum = lotnum;
    }
}
