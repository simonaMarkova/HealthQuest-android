package com.example.simona.healthquest.helper;

import com.example.simona.healthquest.model.BaseEntity;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by Simona on 8/3/2017.
 */

public class Serializer implements JsonSerializer<BaseEntity> {
    @Override
    public JsonElement serialize(BaseEntity src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jo = context.serialize(src).getAsJsonObject();
        if(src.id <= 0){
            jo.remove("id");
        }
        return jo;
    }
}
