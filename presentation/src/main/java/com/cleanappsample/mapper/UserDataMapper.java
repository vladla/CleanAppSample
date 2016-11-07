package com.cleanappsample.mapper;

import com.cleanappsample.domain.User;
import com.cleanappsample.entity.UserEntity;
import com.cleanappsample.model.UserModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Anton Khorunzhyi on 11/7/2016.
 */

public class UserDataMapper {

    @Inject
    public UserDataMapper() {
    }

    public UserModel convert(UserEntity userEntity){
        UserModel user = UserModel.builder()
                .userId(userEntity.id())
                .coverUrl(userEntity.coverUrl())
                .description(userEntity.description())
                .email(userEntity.email())
                .followers(userEntity.followers())
                .fullName(userEntity.fullName())
                .build();
        return user;
    }

    public List<UserModel> convert(List<UserEntity> userEntities){
        List<UserModel> userList = new ArrayList<>();
        if(userEntities != null && !userEntities.isEmpty()){
            for (UserEntity userEntity : userEntities) {
                UserModel user = convert(userEntity);
                if(user != null){
                    userList.add(user);
                }
            }
        }
        return userList;
    }
}
