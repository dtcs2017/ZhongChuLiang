package com.zcl.hxqh.zhongchuliang.model;

import com.instagram.common.json.annotation.JsonField;
import com.instagram.common.json.annotation.JsonType;

/**
 * Created by think on 15/12/16.
 * 入库管理物料
 */
@JsonType
public class Poline extends Entity {

    @JsonField(fieldName = "itemnum")
    public String itemnum; //项目编号
    @JsonField(fieldName = "polinenum")
    public String polinenum;//行号
    @JsonField(fieldName = "description")
    public String description; //描述
    @JsonField(fieldName = "orderqty")
    public String orderqty; //规格型号
    @JsonField(fieldName = "orderunit")
    public String orderunit; //订购单位
    @JsonField(fieldName = "storeloc")
    public String storeloc; //发放单位
    @JsonField(fieldName = "tobin")
    public String tobin; //库位号
    @JsonField(fieldName = "tolot")
    public String tolot; //批次

    public String getPolinenum() {
        return polinenum;
    }

    public void setPolinenum(String polinenum) {
        this.polinenum = polinenum;
    }

    public String getTobin() {
        return tobin;
    }

    public void setTobin(String tobin) {
        this.tobin = tobin;
    }

    public String getTolot() {
        return tolot;
    }

    public void setTolot(String tolot) {
        this.tolot = tolot;
    }

    public String getItemnum() {
        return itemnum;
    }

    public void setItemnum(String itemnum) {
        this.itemnum = itemnum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrderunit() {
        return orderunit;
    }

    public void setOrderunit(String orderunit) {
        this.orderunit = orderunit;
    }

    public String getOrderqty() {
        return orderqty;
    }

    public void setOrderqty(String orderqty) {
        this.orderqty = orderqty;
    }

    public String getStoreloc() {
        return storeloc;
    }

    public void setStoreloc(String storeloc) {
        this.storeloc = storeloc;
    }
}
