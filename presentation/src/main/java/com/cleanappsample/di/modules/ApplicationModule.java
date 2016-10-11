package com.cleanappsample.di.modules;

import android.content.Context;

import com.cleanappsample.CleanSampleApplication;
import com.cleanappsample.preferences.PreferenceHelper;
import com.cleanappsample.preferences.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final CleanSampleApplication application;

    public ApplicationModule(CleanSampleApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Context provideAppContext(){
        return this.application;
    }

    @Provides
    @Singleton
    PreferenceHelper providePreferenceHelper(Context context){
        return new PreferenceManager(context);
    }
}
