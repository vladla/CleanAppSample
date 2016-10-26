package com.cleanappsample.di.modules;

import com.cleanappsample.entity.mapper.UserEntityMapper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Anton Khorunzhyi on 10/25/2016.
 */
@Module
public class UtilsModule {

    @Provides
    @Singleton
    UserEntityMapper provideUserEntityMapper() {
        return new UserEntityMapper();
    }
}
