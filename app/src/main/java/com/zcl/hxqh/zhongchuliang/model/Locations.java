package com.zcl.hxqh.zhongchuliang.model;

import com.instagram.common.json.annotation.JsonField;
import com.instagram.common.json.annotation.JsonType;

/**
 * Created by apple on 15/6/3.
 * 位置表
 */
@JsonType
public class Locations extends Entity {

    @JsonField(fieldName = "locationsid")
    public String locationsid; //id
    @JsonField(fieldName = "location")
    public String location; //编号
    @JsonField(fieldName = "description")
    public String description; //描述
    @JsonField(fieldName = "siteid")
    public String siteid; //地点


    public String getLocationsid() {
        return locationsid;
    }

    public void setLocationsid(String locationsid) {
        this.locationsid = locationsid;
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

    public String getSiteid() {
        return siteid;
    }

    public void setSiteid(String siteid) {
        this.siteid = siteid;
    }
}
