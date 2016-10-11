package com.cleanappsample.domain;

import org.immutables.value.Value;

@Value.Immutable
public interface User extends WithUser {
    int getUserId();
    String getCoverUrl();
    String getFullName();
    String getEmail();
    String getDescription();
    int getFollowers();
}
