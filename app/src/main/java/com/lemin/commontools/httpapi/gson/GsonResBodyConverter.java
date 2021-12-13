package com.lemin.commontools.httpapi.gson;

import com.lemin.commontools.utils.DD;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public final class GsonResBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final Type type;

    GsonResBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String json = value.string();
        DD.dd("GsonResBodyConverter", json);
        try {
            return gson.fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("解析数据出错");
        }
    }
}