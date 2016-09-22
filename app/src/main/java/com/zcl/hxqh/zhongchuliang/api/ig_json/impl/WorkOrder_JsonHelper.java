package com.zcl.hxqh.zhongchuliang.api.ig_json.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.instagram.common.json.JsonFactoryHolder;
import com.zcl.hxqh.zhongchuliang.api.ig_json.JsonHelper;
import com.zcl.hxqh.zhongchuliang.model.WorkOrder;

import java.io.IOException;
import java.util.ArrayList;

/**
 * WorkOrder解析*
 */
public final class WorkOrder_JsonHelper
        implements JsonHelper<WorkOrder> {
    private static final String TAG = "WorkOrder_JsonHelper";

    /**
     * 解析List*
     */
    public static ArrayList<WorkOrder> parseFromJsonList(JsonParser jp)
            throws IOException {

        ArrayList<WorkOrder> results = null;

        // validate that we're on the right token
        if (jp.getCurrentToken() == JsonToken.START_ARRAY) {
            results = new ArrayList<WorkOrder>();
            while (jp.nextToken() != JsonToken.END_ARRAY) {
                WorkOrder parsed = parseFromJson(jp);
                if (parsed != null) {
                    results.add(parsed);
                }
            }
        }


        return results;
    }


    /**
     * 解析WorkOrder
     */
    public static WorkOrder parseFromJson(JsonParser jp)
            throws IOException {
        WorkOrder instance = new WorkOrder();

        // validate that we're on the right token
        if (jp.getCurrentToken() != JsonToken.START_OBJECT) {
            jp.skipChildren();
            return null;
        }

        while (jp.nextToken() != JsonToken.END_OBJECT) {
            String fieldName = jp.getCurrentName();
            jp.nextToken();
            processSingleField(instance, fieldName, jp);
            jp.skipChildren();
        }

        return instance;
    }

    public static boolean processSingleField(WorkOrder instance, String fieldName, JsonParser jp)
            throws IOException {
        if ("WONUM".equals(fieldName)) {
            instance.wonum = jp.getValueAsString();
            return true;
        } else if ("DESCRIPTION".equals(fieldName)) {
            instance.description = jp.getValueAsString();
            return true;
        } else if ("ONBEHALFOF".equals(fieldName)) {
            instance.onbehalfof = jp.getValueAsString();
        } else if ("STATUS".equals(fieldName)) {
            instance.status = jp.getValueAsString();
            return true;
        }

        return false;
    }

    /**
     * 解析Item*
     */
    public static ArrayList<WorkOrder> parseFromJsonList(String inputString)
            throws IOException {
        JsonParser jp = JsonFactoryHolder.APP_FACTORY.createParser(inputString);
        jp.nextToken();
        return parseFromJsonList(jp);
    }

    /**
     * 解析WorkOrder
     */
    public static WorkOrder parseFromJson(String inputString)
            throws IOException {
        JsonParser jp = JsonFactoryHolder.APP_FACTORY.createParser(inputString);
        jp.nextToken();
        return parseFromJson(jp);
    }


}
