package com.cleanappsample.network.components;

import com.cleanappsample.CleanSampleApplication;
import com.cleanappsample.MainActivity;
import com.cleanappsample.di.RootModule;
import com.cleanappsample.network.modules.ApplicationModule;
import com.cleanappsample.network.NetworkModule;
import com.cleanappsample.utils.UtilsModule;

import javax.inject.Singleton;

import dagger.Component;
import io.techery.presenta.di.ApplicationScope;

@ApplicationScope
@Component(modules = {ApplicationModule.class, NetworkModule.class, RootModule.class, UtilsModule.class})
public interface ApplicationComponent {
    void inject(CleanSampleApplication application);
    void inject(MainActivity mainActivity);
}
