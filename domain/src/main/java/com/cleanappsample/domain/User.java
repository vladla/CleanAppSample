package com.cleanappsample.domain;

import android.support.annotation.Nullable;

import org.immutables.value.Value;

@Value.Immutable
public interface User extends WithUser {
    int getUserId();
    @Nullable
    String getCoverUrl();
    String getFullName();
    @Nullable
    String getEmail();
    @Nullable
    String getDescription();
    int getFollowers();
}
