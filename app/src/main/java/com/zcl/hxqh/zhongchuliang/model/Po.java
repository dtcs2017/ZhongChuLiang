package com.zcl.hxqh.zhongchuliang.model;


import com.instagram.common.json.annotation.JsonField;
import com.instagram.common.json.annotation.JsonType;

/**
 * Created by apple on 15/6/9.
 */
@JsonType
public class Po extends Entity {
    @JsonField(fieldName = "poid")
    public String poid; //唯一表识

    @JsonField(fieldName = "ponum")
    public String ponum; //采购编号
    @JsonField(fieldName = "description")
    public String description; //描述
    @JsonField(fieldName = "vendor")
    public String vendor; //公司名称
    @JsonField(fieldName = "vendordesc")
    public String vendordesc; //公司名称
    @JsonField(fieldName = "recorder")
    public String recorder;//接收人
    @JsonField(fieldName = "status")
    public String status; //状态
    @JsonField(fieldName = "siteid")
    public String siteid; //站点
    @JsonField(fieldName = "pretaxtotal")
    public String pretaxtotal; //税前总计

    @JsonField(fieldName = "orderdate")
    public String orderdate; //订购日期
    @JsonField(fieldName = "shiptoattn")
    public String shiptoattn; //接收人

    public String getPoid() {
        return poid;
    }

    public void setPoid(String poid) {
        this.poid = poid;
    }

    public String getPonum() {
        return ponum;
    }

    public void setPonum(String ponum) {
        this.ponum = ponum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getVendordesc() {
        return vendordesc;
    }

    public void setVendordesc(String vendordesc) {
        this.vendordesc = vendordesc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSiteid() {
        return siteid;
    }

    public void setSiteid(String siteid) {
        this.siteid = siteid;
    }

    public String getPretaxtotal() {
        return pretaxtotal;
    }

    public void setPretaxtotal(String pretaxtotal) {
        this.pretaxtotal = pretaxtotal;
    }

    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }

    public String getShiptoattn() {
        return shiptoattn;
    }

    public void setShiptoattn(String shiptoattn) {
        this.shiptoattn = shiptoattn;
    }
}
