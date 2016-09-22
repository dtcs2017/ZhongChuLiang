package com.zcl.hxqh.zhongchuliang.api.ig_json.impl;

import android.util.Log;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.instagram.common.json.JsonFactoryHolder;
import com.zcl.hxqh.zhongchuliang.api.ig_json.JsonHelper;
import com.zcl.hxqh.zhongchuliang.model.Inventory;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Inventory解析*
 */
public final class Inventory_JsonHelper
        implements JsonHelper<Inventory> {
    private static final String TAG = "Inventory_JsonHelper";

    /**
     * 解析List*
     */
    public static ArrayList<Inventory> parseFromJsonList(JsonParser jp)
            throws IOException {

        ArrayList<Inventory> results = null;

        // validate that we're on the right token
        if (jp.getCurrentToken() == JsonToken.START_ARRAY) {
            results = new ArrayList<Inventory>();
            while (jp.nextToken() != JsonToken.END_ARRAY) {
                Inventory parsed = parseFromJson(jp);
                Log.i(TAG, "parsed=" + parsed);
                if (parsed != null) {
                    results.add(parsed);
                }
            }
        }


        return results;
    }


    /**
     * 解析Inventory
     */
    public static Inventory parseFromJson(JsonParser jp)
            throws IOException {
        Inventory instance = new Inventory();

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

    public static boolean processSingleField(Inventory instance, String fieldName, JsonParser jp)
            throws IOException {
        if ("BINNUM".equals(fieldName)) {
            instance.binnum = jp.getValueAsString();
            return true;
        } else if ("CURBALTOTAL".equals(fieldName)) {
            instance.curbaltotal = jp.getValueAsString();
            return true;
        } else if ("ISSUEUNIT".equals(fieldName)) {
            instance.issueunit = jp.getValueAsString();
        } else if ("ITEMDESC".equals(fieldName)) {
            instance.itemdesc = jp.getValueAsString();
            return true;
        } else if ("ITEMNUM".equals(fieldName)) {
            instance.itemnum = jp.getValueAsString();
            return true;
        } else if ("LOCATION".equals(fieldName)) {
            instance.location = jp.getValueAsString();
            return true;
        } else if ("LOCATIONDESC".equals(fieldName)) {
            instance.locationdesc = jp.getValueAsString();
            return true;
        } else if ("LOTNUM".equals(fieldName)) {
            instance.lotnum = jp.getValueAsString();
            return true;
        }

        return false;
    }

    /**
     * 解析Item*
     */
    public static ArrayList<Inventory> parseFromJsonList(String inputString)
            throws IOException {
        JsonParser jp = JsonFactoryHolder.APP_FACTORY.createParser(inputString);
        jp.nextToken();
        return parseFromJsonList(jp);
    }

    /**
     * 解析Item*
     */
    public static Inventory parseFromJson(String inputString)
            throws IOException {
        JsonParser jp = JsonFactoryHolder.APP_FACTORY.createParser(inputString);
        jp.nextToken();
        return parseFromJson(jp);
    }


}
