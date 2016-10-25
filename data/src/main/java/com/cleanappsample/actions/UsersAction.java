package com.cleanappsample.actions;

import com.cleanappsample.entity.UserEntity;

import java.util.List;

import io.techery.janet.http.annotations.HttpAction;
import io.techery.janet.http.annotations.Response;

/**
 * Created by Anton Khorunzhyi on 10/25/2016.
 */
@HttpAction(value = "/users.json")
public class UsersAction {

    @Response
    List<UserEntity> response;

    public List<UserEntity> getResponse() {
        return response;
    }
}
