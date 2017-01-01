package com.cleanappsample.entity;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.cleanappsample.net.AutoGson;
import com.google.auto.value.AutoValue;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Anton Khorunzhyi on 10/25/2016.
 */

@AutoValue @AutoGson
public abstract class UserEntity implements Parcelable{

    public abstract int id();
    @SerializedName("cover_url")
    @Nullable
    public abstract String coverUrl();
    @SerializedName("full_name")
    @Nullable
    public abstract String fullName();
    @Nullable
    public abstract String description();
    public abstract int followers();
    @Nullable
    public abstract String email();

    public static Builder builder() {
        return new AutoValue_UserEntity.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder id(int id);
        public abstract Builder coverUrl(String coverUrl);
        public abstract Builder fullName(String fullName);
        public abstract Builder description(String description);
        public abstract Builder followers(int followers);
        public abstract Builder email(String email);
        public abstract UserEntity build();
    }


}
