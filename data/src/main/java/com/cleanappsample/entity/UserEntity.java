package com.cleanappsample.entity;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import org.immutables.value.Value;

/**
 * Created by Anton Khorunzhyi on 10/25/2016.
 */

@Value.Immutable
public abstract class UserEntity {

    public abstract int id();

    @SerializedName("cover_url")
    @Nullable
    public abstract String coverUrl();

    @SerializedName("full_name")
    public abstract String fullName();

    @Nullable
    public abstract String description();

    public abstract int followers();

    @Nullable
    public abstract String email();
}
