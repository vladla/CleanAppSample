package com.cleanappsample.cache;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

public class PreferenceManager extends PreferenceHelper {

    private static final String TAG = "clean_app_sample";
    private static final String LAST_UPDATE_USERS_KEY = "users_last_update";

    @Inject
    public PreferenceManager(Context context) {
        super(context, TAG);
    }

    public long getLastUpdateUsers() {
        return getLongValue(LAST_UPDATE_USERS_KEY, false);
    }

    public void setLastUpdateUsers(long date) {
        storeValue(LAST_UPDATE_USERS_KEY, date, false);
    }
}
