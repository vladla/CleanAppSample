package com.cleanappsample.di.modules;

import com.cleanappsample.mapper.UserDataMapper;
import com.cleanappsample.entity.mapper.UserEntityMapper;

import dagger.Module;
import dagger.Provides;
import io.techery.presenta.di.ApplicationScope;

@Module
public class UtilsModule {

    @Provides
    @ApplicationScope
    UserEntityMapper provideUserEntityMapper() {
        return new UserEntityMapper();
    }

    @Provides
    @ApplicationScope
    UserDataMapper provideUserDataMapper() {
        return new UserDataMapper();
    }
}
