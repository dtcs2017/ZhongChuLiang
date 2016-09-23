package com.zcl.hxqh.zhongchuliang.api.ig_json.impl;

import android.util.Log;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.instagram.common.json.JsonFactoryHolder;
import com.zcl.hxqh.zhongchuliang.api.ig_json.JsonHelper;
import com.zcl.hxqh.zhongchuliang.model.Invbalances;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Invbalances解析*
 */
public final class Invbalances_JsonHelper
        implements JsonHelper<Invbalances> {
    private static final String TAG = "Invbalances_JsonHelper";

    /**
     * 解析List*
     */
    public static ArrayList<Invbalances> parseFromJsonList(JsonParser jp)
            throws IOException {

        ArrayList<Invbalances> results = null;

        // validate that we're on the right token
        if (jp.getCurrentToken() == JsonToken.START_ARRAY) {
            results = new ArrayList<Invbalances>();
            while (jp.nextToken() != JsonToken.END_ARRAY) {
                Invbalances parsed = parseFromJson(jp);
                Log.i(TAG, "parsed=" + parsed);
                if (parsed != null) {
                    results.add(parsed);
                }
            }
        }


        return results;
    }


    /**
     * 解析Invbalances
     */
    public static Invbalances parseFromJson(JsonParser jp)
            throws IOException {
        Invbalances instance = new Invbalances();

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

    public static boolean processSingleField(Invbalances instance, String fieldName, JsonParser jp)
            throws IOException {
        if ("BINNUM".equals(fieldName)) {
            instance.binnum = jp.getValueAsString();
            return true;
        } else if ("ITEMNUM".equals(fieldName)) {
            instance.itemnum = jp.getValueAsString();
            return true;
        } else if ("CURBAL".equals(fieldName)) {
            instance.curbal = jp.getValueAsString();
        } else if ("ITEMDESC".equals(fieldName)) {
            instance.itemdesc = jp.getValueAsString();
            return true;
        } else if ("ITEMIN20".equals(fieldName)) {
            instance.itemin20 = jp.getValueAsString();
            return true;
        } else if ("ITEMORDERUNIT".equals(fieldName)) {
            instance.itemorderunit = jp.getValueAsString();
            return true;
        } else if ("LOCATION".equals(fieldName)) {
            instance.location = jp.getValueAsString();
            return true;
        } else if ("LOTNUM".equals(fieldName)) {
            instance.lotnum = jp.getValueAsString();
            return true;
        } else if ("SITEID".equals(fieldName)) {
            instance.siteid = jp.getValueAsString();
            return true;
        } else if ("INVTYPE".equals(fieldName)) {
            instance.invtype = jp.getValueAsString();
            return true;
        } else if ("unitcost".equals(fieldName)) {
            instance.unitcost = jp.getValueAsString();
            return true;
        }

        return false;
    }

    /**
     * 解析Invbalances*
     */
    public static ArrayList<Invbalances> parseFromJsonList(String inputString)
            throws IOException {
        JsonParser jp = JsonFactoryHolder.APP_FACTORY.createParser(inputString);
        jp.nextToken();
        return parseFromJsonList(jp);
    }

    /**
     * 解析Invbalances*
     */
    public static Invbalances parseFromJson(String inputString)
            throws IOException {
        JsonParser jp = JsonFactoryHolder.APP_FACTORY.createParser(inputString);
        jp.nextToken();
        return parseFromJson(jp);
    }


}
