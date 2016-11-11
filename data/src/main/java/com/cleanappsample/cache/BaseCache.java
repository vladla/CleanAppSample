package com.cleanappsample.cache;

import android.content.Context;

import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import java.io.Serializable;
import java.util.List;

public class BaseCache {

    private Context context;

    public BaseCache(Context context) {
        this.context = context;
    }

    protected <T> T retrieveObject(String key, Class<T> clazz){
        try {
            DB db = DBFactory.open(context);
            T result = db.getObject(key, clazz);
            db.close();
            return result;
        } catch (SnappydbException e) {
            throw new UnsupportedOperationException(e.getLocalizedMessage());
        }
    }

    protected <T extends Serializable> T retrieveSerializable(String key, Class<T> clazz){
        try {
            DB db = DBFactory.open(context);
            T result = db.get(key, clazz);
            db.close();
            return result;
        } catch (SnappydbException e) {
            throw new UnsupportedOperationException(e.getLocalizedMessage());
        }
    }

    protected <T> T[] retrieveListObject(String key, Class<T> clazz){
        try {
            DB db = DBFactory.open(context);
            T [] result = db.getObjectArray(key, clazz);
            db.close();
            return result;
        } catch (SnappydbException e) {
            throw new UnsupportedOperationException(e.getLocalizedMessage());
        }
    }

    protected <T> void insert(String key, T object){
        try {
            DB db = DBFactory.open(context);
            db.put(key, object);
            db.close();
        } catch (SnappydbException e) {
            throw new UnsupportedOperationException(e.getLocalizedMessage());
        }
    }

    protected <T> void insert(String key, T[] object){
        try {
            DB db = DBFactory.open(context);
            db.put(key, object);
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
