package com.zcl.hxqh.zhongchuliang.api.ig_json.impl;

import android.util.Log;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.instagram.common.json.JsonFactoryHolder;
import com.zcl.hxqh.zhongchuliang.api.ig_json.JsonHelper;
import com.zcl.hxqh.zhongchuliang.model.Po;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Po解析*
 */
public final class Po_JsonHelper
        implements JsonHelper<Po> {
    private static final String TAG = "Po_JsonHelper";

    /**
     * 解析List*
     */
    public static ArrayList<Po> parseFromJsonList(JsonParser jp)
            throws IOException {

        ArrayList<Po> results = null;

        // validate that we're on the right token
        if (jp.getCurrentToken() == JsonToken.START_ARRAY) {
            results = new ArrayList<Po>();
            while (jp.nextToken() != JsonToken.END_ARRAY) {
                Po parsed = parseFromJson(jp);
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
    public static Po parseFromJson(JsonParser jp)
            throws IOException {
        Po instance = new Po();

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

    public static boolean processSingleField(Po instance, String fieldName, JsonParser jp)
            throws IOException {
        if ("PONUM".equals(fieldName)) {
            instance.ponum = jp.getValueAsString();
            return true;
        } else if ("DESCRIPTION".equals(fieldName)) {
            instance.description = jp.getValueAsString();
            return true;
        } else if ("RECORDER".equals(fieldName)) {
            instance.recorder = jp.getValueAsString();
        } else if ("VENDOR".equals(fieldName)) {
            instance.vendor = jp.getValueAsString();
            return true;
        }

        return false;
    }

    /**
     * 解析Po*
     */
    public static ArrayList<Po> parseFromJsonList(String inputString)
            throws IOException {
        JsonParser jp = JsonFactoryHolder.APP_FACTORY.createParser(inputString);
        jp.nextToken();
        return parseFromJsonList(jp);
    }

    /**
     * 解析Item*
     */
    public static Po parseFromJson(String inputString)
            throws IOException {
        JsonParser jp = JsonFactoryHolder.APP_FACTORY.createParser(inputString);
        jp.nextToken();
        return parseFromJson(jp);
    }


}
