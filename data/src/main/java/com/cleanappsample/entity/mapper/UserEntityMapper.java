package com.cleanappsample.entity.mapper;

import com.cleanappsample.domain.ImmutableUser;
import com.cleanappsample.domain.User;
import com.cleanappsample.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Anton Khorunzhyi on 10/25/2016.
 */
@Singleton
public class UserEntityMapper {

    @Inject
    public UserEntityMapper() {
    }

    public User convert(UserEntity userEntity){
        ImmutableUser user = ImmutableUser.builder()
                .userId(userEntity.id())
                .coverUrl(userEntity.coverUrl())
                .description(userEntity.description())
                .email(userEntity.email())
                .followers(userEntity.followers())
                .fullName(userEntity.fullName())
                .build();
        return user;
    }

    public List<User> convert(List<UserEntity> userEntities){
        List<User> userList = new ArrayList<>();
        if(userEntities != null && !userEntities.isEmpty()){
            for (UserEntity userEntity : userEntities) {
                User user = convert(userEntity);
                if(user != null){
                    userList.add(user);
                }
            }
        }
        return userList;
    }
}
