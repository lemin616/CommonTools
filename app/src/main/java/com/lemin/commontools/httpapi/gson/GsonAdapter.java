package com.lemin.commontools.httpapi.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;
import java.util.List;



public class GsonAdapter {
    private static Gson gson;
    public static Gson buildGson() {
        if (gson == null) {
            gson = new GsonBuilder()
                    .registerTypeAdapter(Integer.class, new IntegerDefault0Adapter())
                    .registerTypeAdapter(int.class, new IntegerDefault0Adapter())
                    .registerTypeAdapter(Double.class, new DoubleDefault0Adapter())
                    .registerTypeAdapter(double.class, new DoubleDefault0Adapter())
                    .registerTypeAdapter(Long.class, new LongDefault0Adapter())
                    .registerTypeAdapter(long.class, new LongDefault0Adapter())
                    .registerTypeAdapter(List.class,new ListDefaultAdapter())
                    .registerTypeAdapter(Date.class,new ImprovedDateTypeAdapter())
                    .create();
        }
        return gson;
    }
}
