package com.cleanappsample.model;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class UserModel implements Parcelable{

    public abstract int getUserId();
    @Nullable public abstract String getCoverUrl();
    @Nullable public abstract String getFullName();
    @Nullable public abstract String getEmail();
    @Nullable public abstract String getDescription();
    public abstract int getFollowers();

    public static Builder builder() {
        return new AutoValue_UserModel.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder userId(int id);
        public abstract Builder coverUrl(String coverUrl);
        public abstract Builder fullName(String fullName);
        public abstract Builder email(String email);
        public abstract Builder followers(int followers);
        public abstract Builder description(String description);
        public abstract UserModel build();
    }
}
