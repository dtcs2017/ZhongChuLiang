package com.zcl.hxqh.zhongchuliang.model;

import com.instagram.common.json.annotation.JsonField;
import com.instagram.common.json.annotation.JsonType;

/**
 * Created by apple on 15/6/3.
 * 出库WorkOrder
 */
@JsonType
public class WorkOrder extends Entity {
    private static final String TAG = "WorkOrder";

    @JsonField(fieldName = "wonum")
    public String wonum; //工单编号

    @JsonField(fieldName = "description")
    public String description; //描述

    @JsonField(fieldName = "onbehalfof")
    public String onbehalfof; //领用人

    @JsonField(fieldName = "status")
    public String status; //状态


    public String getWonum() {
        return wonum;
    }

    public void setWonum(String wonum) {
        this.wonum = wonum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOnbehalfof() {
        return onbehalfof;
    }

    public void setOnbehalfof(String onbehalfof) {
        this.onbehalfof = onbehalfof;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
