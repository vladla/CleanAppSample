package com.cleanappsample.di.modules;

import com.cleanappsample.entity.mapper.UserEntityMapper;

import javax.inject.Singleton;

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
}
