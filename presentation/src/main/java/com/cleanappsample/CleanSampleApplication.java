package com.cleanappsample;

import android.app.Application;

import com.cleanappsample.di.components.ApplicationComponent;
import com.cleanappsample.di.components.DaggerApplicationComponent;
import com.cleanappsample.di.modules.ApplicationModule;
import com.cleanappsample.di.modules.NetworkModule;

public class CleanSampleApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initComponents();
    }

    private void initComponents() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .networkModule(new NetworkModule())
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
