package com.zcl.hxqh.zhongchuliang.api.ig_json.impl;

import android.util.Log;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.instagram.common.json.JsonFactoryHolder;
import com.zcl.hxqh.zhongchuliang.api.ig_json.JsonHelper;
import com.zcl.hxqh.zhongchuliang.model.Poline;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Item解析*
 */
public final class Poline_JsonHelper
        implements JsonHelper<Poline> {
    private static final String TAG = "Poline_JsonHelper";

    /**
     * 解析List*
     */
    public static ArrayList<Poline> parseFromJsonList(JsonParser jp)
            throws IOException {

        ArrayList<Poline> results = null;

        // validate that we're on the right token
        if (jp.getCurrentToken() == JsonToken.START_ARRAY) {
            results = new ArrayList<Poline>();
            while (jp.nextToken() != JsonToken.END_ARRAY) {
                Poline parsed = parseFromJson(jp);
                Log.i(TAG, "parsed=" + parsed);
                if (parsed != null) {
                    results.add(parsed);
                }
            }
        }


        return results;
    }


    /**
     * 解析Po
     */
    public static Poline parseFromJson(JsonParser jp)
            throws IOException {
        Poline instance = new Poline();

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

    public static boolean processSingleField(Poline instance, String fieldName, JsonParser jp)
            throws IOException {
        if ("ITEMNUM".equals(fieldName)) {
            instance.itemnum = jp.getValueAsString();
            return true;
        } else if ("POLINENUM".equals(fieldName)) {
            instance.polinenum = jp.getValueAsString();
            return true;
        } else if ("DESCRIPTION".equals(fieldName)) {
            instance.description = jp.getValueAsString();
            return true;
        } else if ("ORDERQTY".equals(fieldName)) {
            instance.orderqty = jp.getValueAsString();
        } else if ("ORDERUNIT".equals(fieldName)) {
            instance.orderunit = jp.getValueAsString();
            return true;
        }else if ("STORELOC".equals(fieldName)) {
            instance.storeloc = jp.getValueAsString();
            return true;
        }else if ("TOBIN".equals(fieldName)) {
            instance.tobin = jp.getValueAsString();
            return true;
        }
        else if ("TOLOT".equals(fieldName)) {
            instance.tolot = jp.getValueAsString();
            return true;
        }

        return false;
    }

    /**
     * 解析Po*
     */
    public static ArrayList<Poline> parseFromJsonList(String inputString)
            throws IOException {
        JsonParser jp = JsonFactoryHolder.APP_FACTORY.createParser(inputString);
        jp.nextToken();
        return parseFromJsonList(jp);
    }

    /**
     * 解析Item*
     */
    public static Poline parseFromJson(String inputString)
            throws IOException {
        JsonParser jp = JsonFactoryHolder.APP_FACTORY.createParser(inputString);
        jp.nextToken();
        return parseFromJson(jp);
    }


}
