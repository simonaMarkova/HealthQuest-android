package com.example.simona.healthquest.helper;

import com.example.simona.healthquest.model.BaseEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;

/**
 * Created by Simona on 8/3/2017.
 */

public class JSON {

    private static Gson instance = null;

    private JSON(){
    }

    private static Gson getGson(){
        if(instance == null){
            GsonBuilder gb = new GsonBuilder();
            gb.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            gb.registerTypeAdapter(BaseEntity.class, new Serializer());
            instance = gb.create();
        }
        return instance;
    }

    public static String toJson(BaseEntity model){
        return getGson().toJson(model, BaseEntity.class);
    }

    public static String toJson(Object model, Type t){
        return getGson().toJson(model, t);
    }

    public static Object fromJson(String model, Type t){
        return getGson().fromJson(model, t);
    }

    public static Object fromJson(JsonObject model, Type t){
        return getGson().fromJson(model, t);
    }
}
