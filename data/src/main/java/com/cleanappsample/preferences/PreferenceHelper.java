package com.cleanappsample.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import com.cleanappsample.security.SecurityHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class PreferenceHelper {

    private SharedPreferences prefs;
    private final ExecutorService executor;
    private Gson gson;
    private Map<String, Object> storedObjects;

    protected PreferenceHelper(Context context, String tag) {
        prefs = context.getSharedPreferences(tag, Context.MODE_PRIVATE);
        gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        executor = Executors.newSingleThreadExecutor();
        storedObjects = new HashMap<>();
    }

    protected void storeObject(final Object value) {
        storeObject(value.getClass(), value);
    }

    protected void storeObject(final Class<?> clazz, final Object value) {
        storedObjects.put(clazz.getName(), value);
        executor.submit(() -> {
            String valueString = gson.toJson(value, clazz);
            storeValue(clazz.getName(), valueString, false);
        });
    }

    public <T> T getObject(Class<T> classType) {
        T result = (T) storedObjects.get(classType.getName());
        if (result == null) {
            String objectSource = getStringValue(classType.getName(), false);
            result = gson.fromJson(objectSource, classType);
        }
        return result;
    }

    public boolean hasStoredValue(String key) {
        return prefs.contains(key);
    }

    public String getStringValue(String key, boolean useDecryption) {
        String value = prefs.getString(key, null);
        if (useDecryption && value != null)
            return SecurityHelper.decodeString(value);
        else
            return value;
    }

    protected long getLongValue(String key, boolean useDecryption) {
        String stringValue = getStringValue(key, useDecryption);
        if (stringValue == null)
            return 0L;
        else
            return Long.valueOf(stringValue);
    }

    protected int getIntValue(String key, boolean useDecryption) {
        String stringValue = getStringValue(key, useDecryption);
        if (stringValue == null)
            return 0;
        else
            return Integer.valueOf(stringValue);
    }

    public boolean getBooleanValue(String key, boolean useDecryption) {
        String stringValue = getStringValue(key, useDecryption);
        if (stringValue == null)
            return false;
        else
            return Boolean.valueOf(stringValue);
    }

    public <U> void storeValue(String key, U value, boolean useEncryption) {
        String resultValue;
        if (useEncryption)
            resultValue = SecurityHelper.encodeString(String.valueOf(value));
        else
            resultValue = String.valueOf(value);
        prefs.edit().putString(key, resultValue).commit();
    }

    public void removeValues(String[] values) {
        for (String value : values) {
            removeValue(value);
        }
    }

    protected <U> void removeValue(String key) {
        prefs.edit().remove(key).commit();
    }

    protected void storeIntegerList(String key, List<Integer> list) {
        JSONArray array = new JSONArray();
        for (Integer item : list) {
            array.put(item);
        }
        storeValue(key, array.toString(), false);
    }

    protected void storeStringList(String key, List<String> list) {
        JSONArray array = new JSONArray();
        for (String item : list) {
            array.put(item);
        }
        storeValue(key, array.toString(), false);
    }

    protected ArrayList<Integer> getIntegerList(String key) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        String stringValue = getStringValue(key, false);
        JSONArray jsonArray;
        if (stringValue != null) {
            try {
                jsonArray = new JSONArray(stringValue);
                for (int i = 0; i < jsonArray.length(); i++) {
                    result.add(jsonArray.optInt(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    protected ArrayList<String> getStringList(String key) {
        ArrayList<String> result = new ArrayList<String>();
        String stringValue = getStringValue(key, false);
        JSONArray jsonArray;
        if (stringValue != null) {
            try {
                jsonArray = new JSONArray(stringValue);
                for (int i = 0; i < jsonArray.length(); i++) {
                    result.add(jsonArray.optString(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    protected void storeLongList(String key, ArrayList<Long> list) {
        JSONArray array = new JSONArray();
        for (long item : list) {
            array.put(item);
        }
        storeValue(key, array.toString(), false);
    }

    protected ArrayList<Long> getLongList(String key) {
        ArrayList<Long> result = new ArrayList<Long>();
        String stringValue = getStringValue(key, false);
        JSONArray jsonArray;
        if (stringValue != null) {
            try {
                jsonArray = new JSONArray(stringValue);
                for (int i = 0; i < jsonArray.length(); i++) {
                    result.add(jsonArray.optLong(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}

