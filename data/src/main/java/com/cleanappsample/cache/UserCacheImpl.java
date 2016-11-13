package com.cleanappsample.cache;

import android.app.Application;
import android.content.Context;

import com.cleanappsample.entity.UserEntity;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Anton Khorunzhyi on 11/9/2016.
 */

public class UserCacheImpl extends BaseCache implements UserCache {

    private static final long EXPIRATION_TIME = 60 * 10 * 1000;
    private static final String USER_KEY = "user";


    private PreferenceManager preferenceManager;

    @Inject
    public UserCacheImpl(PreferenceManager preferenceHelper, Context context) {
        super(context);
        this.preferenceManager = preferenceHelper;
    }

    @Override
    public Observable<UserEntity> get(final int userId) {
        return Observable.create(subscriber -> {
            UserEntity userEntity = retrieveObject(USER_KEY + userId, UserEntity.class);
            if(userEntity != null){
                subscriber.onNext(userEntity);
                subscriber.onCompleted();
            } else {
                subscriber.onError(new NullPointerException("user not found"));
            }
        });
    }

    @Override
    public void put(UserEntity userEntity) {
        if(userEntity != null){
            insert(USER_KEY + userEntity.id(), userEntity);
        }
    }

    @Override
    public void put(List<UserEntity> userEntities) {
        if(userEntities != null && !userEntities.isEmpty()){
            for (UserEntity userEntity : userEntities) {
                insert(USER_KEY + userEntity.id(), userEntity);
            }
            preferenceManager.setLastUpdateUsers(System.currentTimeMillis());
        }
    }

    @Override
    public boolean isCached(int userId) {
        return isExist(USER_KEY + userId);
    }

    @Override
    public boolean isExpired() {
        long currentTime = System.currentTimeMillis();
        long lastUpdateTime = this.getLastCacheUpdateTimeMillis();

        boolean expired = ((currentTime - lastUpdateTime) > EXPIRATION_TIME);

        if (expired) {
            this.evictAll();
        }

        return expired;
    }

    private long getLastCacheUpdateTimeMillis() {
        return preferenceManager.getLastUpdateUsers();
    }

    @Override
    public void evictAll() {

    }


}
