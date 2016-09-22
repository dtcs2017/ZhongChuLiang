package com.zcl.hxqh.zhongchuliang.model;

import com.instagram.common.json.annotation.JsonField;
import com.instagram.common.json.annotation.JsonType;

/**
 * Created by apple on 15/6/3.
 * 出库Invreserve
 */
@JsonType
public class Invreserve extends Entity {

    @JsonField(fieldName = "location")
    public String location; //库房

    @JsonField(fieldName = "description")
    public String description; //描述

    @JsonField(fieldName = "itemnum")
    public String itemnum; //物资编号

    @JsonField(fieldName = "reservedqty")
    public String reservedqty; //数量
    @JsonField(fieldName = "binnum")
    public String binnum; //货柜

    public String getBinnum() {
        return binnum;
    }

    public void setBinnum(String binnum) {
        this.binnum = binnum;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getItemnum() {
        return itemnum;
    }

    public void setItemnum(String itemnum) {
        this.itemnum = itemnum;
    }

    public String getReservedqty() {
        return reservedqty;
    }

    public void setReservedqty(String reservedqty) {
        this.reservedqty = reservedqty;
    }
}
