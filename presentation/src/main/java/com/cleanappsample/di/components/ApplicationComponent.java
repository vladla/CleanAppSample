package com.cleanappsample.di.components;

import com.cleanappsample.MainActivity;
import com.cleanappsample.di.modules.ApplicationModule;
import com.cleanappsample.di.modules.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface ApplicationComponent {

    void inject(MainActivity mainActivity);
}
