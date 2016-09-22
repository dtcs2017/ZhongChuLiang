package com.zcl.hxqh.zhongchuliang.api.ig_json.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.instagram.common.json.JsonFactoryHolder;
import com.zcl.hxqh.zhongchuliang.api.ig_json.JsonHelper;
import com.zcl.hxqh.zhongchuliang.model.Invreserve;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Invreserve解析*
 */
public final class Invreserve_JsonHelper
        implements JsonHelper<Invreserve> {
    private static final String TAG = "Invreserve";

    /**
     * 解析List*
     */
    public static ArrayList<Invreserve> parseFromJsonList(JsonParser jp)
            throws IOException {

        ArrayList<Invreserve> results = null;

        // validate that we're on the right token
        if (jp.getCurrentToken() == JsonToken.START_ARRAY) {
            results = new ArrayList<Invreserve>();
            while (jp.nextToken() != JsonToken.END_ARRAY) {
                Invreserve parsed = parseFromJson(jp);
                if (parsed != null) {
                    results.add(parsed);
                }
            }
        }


        return results;
    }


    /**
     * 解析Invreserve
     */
    public static Invreserve parseFromJson(JsonParser jp)
            throws IOException {
        Invreserve instance = new Invreserve();

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

    public static boolean processSingleField(Invreserve instance, String fieldName, JsonParser jp)
            throws IOException {
        if ("LOCATION".equals(fieldName)) {
            instance.location = jp.getValueAsString();
            return true;
        } else if ("DESCRIPTION".equals(fieldName)) {
            instance.description = jp.getValueAsString();
            return true;
        } else if ("ITEMNUM".equals(fieldName)) {
            instance.itemnum = jp.getValueAsString();
        } else if ("RESERVEDQTY".equals(fieldName)) {
            instance.reservedqty = jp.getValueAsString();
            return true;
        }else if ("BINNUM".equals(fieldName)) {
            instance.binnum = jp.getValueAsString();
            return true;
        }

        return false;
    }

    /**
     * 解析Invreserve
     */
    public static ArrayList<Invreserve> parseFromJsonList(String inputString)
            throws IOException {
        JsonParser jp = JsonFactoryHolder.APP_FACTORY.createParser(inputString);
        jp.nextToken();
        return parseFromJsonList(jp);
    }

    /**
     * 解析Invreserve
     */
    public static Invreserve parseFromJson(String inputString)
            throws IOException {
        JsonParser jp = JsonFactoryHolder.APP_FACTORY.createParser(inputString);
        jp.nextToken();
        return parseFromJson(jp);
    }


}
