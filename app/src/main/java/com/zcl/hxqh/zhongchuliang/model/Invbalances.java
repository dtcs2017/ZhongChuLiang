package com.zcl.hxqh.zhongchuliang.model;

import com.instagram.common.json.annotation.JsonField;
import com.instagram.common.json.annotation.JsonType;

/**
 * Created by apple on 15/6/3.
 * 库存项目
 */
@JsonType
public class Invbalances extends Entity {

    @JsonField(fieldName = "binnum")
    public String binnum; //货柜
    @JsonField(fieldName = "itemnum")
    public String itemnum; //项目编号
    @JsonField(fieldName = "curbal")
    public String curbal; //当前余量
    @JsonField(fieldName = "itemdesc")
    public String itemdesc; //描述
    @JsonField(fieldName = "itemin20")
    public String itemin20; //规格型号
    @JsonField(fieldName = "itemorderunit")
    public String itemorderunit; //订购单位
    @JsonField(fieldName = "location")
    public String location; //库房
    @JsonField(fieldName = "lotnum")
    public String lotnum; //批次
    @JsonField(fieldName = "siteid")
    public String siteid; //地点
    @JsonField(fieldName = "invtype")
    public String invtype; //库存类别
    @JsonField(fieldName = "unitcost")
    public String unitcost; //单位成本

    public String getInvtype() {
        return invtype;
    }

    public void setInvtype(String invtype) {
        this.invtype = invtype;
    }

    public String getBinnum() {
        return binnum;
    }

    public void setBinnum(String binnum) {
        this.binnum = binnum;
    }

    public String getItemnum() {
        return itemnum;
    }

    public void setItemnum(String itemnum) {
        this.itemnum = itemnum;
    }

    public String getCurbal() {
        return curbal;
    }

    public void setCurbal(String curbal) {
        this.curbal = curbal;
    }

    public String getItemdesc() {
        return itemdesc;
    }

    public void setItemdesc(String itemdesc) {
        this.itemdesc = itemdesc;
    }

    public String getItemin20() {
        return itemin20;
    }

    public void setItemin20(String itemin20) {
        this.itemin20 = itemin20;
    }

    public String getItemorderunit() {
        return itemorderunit;
    }

    public void setItemorderunit(String itemorderunit) {
        this.itemorderunit = itemorderunit;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLotnum() {
        return lotnum;
    }

    public void setLotnum(String lotnum) {
        this.lotnum = lotnum;
    }

    public String getSiteid() {
        return siteid;
    }

    public void setSiteid(String siteid) {
        this.siteid = siteid;
    }
}
