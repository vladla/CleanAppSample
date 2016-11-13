package com.cleanappsample.cache;

import com.cleanappsample.entity.UserEntity;

import java.util.List;

import rx.Observable;

public interface UserCache {
    /**
     * Gets an {@link rx.Observable} which will emit a {@link UserEntity}.
     *
     * @param userId The user id to retrieve data.
     */
    Observable<UserEntity> get(final int userId);

    /**
     * Puts and element into the cache.
     *
     * @param userEntity Element to insert in the cache.
     */
    void put(UserEntity userEntity);

    /**
     * Puts and elements into the cache.
     *
     * @param userEntities Elements to insert in the cache.
     */
    void put(List<UserEntity> userEntities);

    /**
     * Checks if an element (User) exists in the cache.
     *
     * @param userId The id used to look for inside the cache.
     * @return true if the element is cached, otherwise false.
     */
    boolean isCached(final int userId);

    /**
     * Checks if the cache is expired.
     *
     * @return true, the cache is expired, otherwise false.
     */
    boolean isExpired();

    /**
     * Evict all elements of the cache.
     */
    void evictAll();

}
