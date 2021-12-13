package com.lemin.commontools.helper;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

/**
 * Created by liuzq on 2018/4/26.
 */

public class JsonString {

    public static JSONArray getJson(List list) {
        try {
            String json = new Gson().toJson(list);
            return new JSONArray(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONArray();
    }

    public static String getJson(Object obj) {
        return new Gson().toJson(obj);
    }
}
