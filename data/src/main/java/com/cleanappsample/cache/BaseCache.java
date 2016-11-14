package com.cleanappsample.cache;

import android.content.Context;

import com.google.gson.Gson;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import java.io.Serializable;
import java.util.List;

public class BaseCache {

    private Context context;
    private Gson gson;

    public BaseCache(Context context, Gson gson) {
        this.context = context;
        this.gson = gson;
    }

    protected <T> T retrieveObject(String key, Class<T> clazz){
        try {
            DB db = DBFactory.open(context);
            String json = db.get(key);
            db.close();
            return gson.fromJson(json, clazz);
        } catch (SnappydbException e) {
            throw new UnsupportedOperationException(e.getLocalizedMessage());
        }
    }

    protected <T> void insert(String key, T object){
        try {
            DB db = DBFactory.open(context);
            String value = gson.toJson(object);
            db.put(key, value);
            db.close();
        } catch (SnappydbException e) {
            throw new UnsupportedOperationException(e.getLocalizedMessage());
        }
    }

    protected boolean isExist(String key){
        try {
            DB db = DBFactory.open(context);
            boolean exist = db.exists(key);
            db.close();
            return exist;
        } catch (SnappydbException e) {
            throw new UnsupportedOperationException(e.getLocalizedMessage());
        }
    }
}
