package com.cleanappsample.di.components;

import com.cleanappsample.CleanSampleApplication;
import com.cleanappsample.MainActivity;
import com.cleanappsample.di.NetworkModule;
import com.cleanappsample.di.RootModule;
import com.cleanappsample.di.modules.ApplicationModule;
import com.cleanappsample.di.modules.UtilsModule;

import dagger.Component;
import io.techery.presenta.addition.ActionBarOwner;
import io.techery.presenta.di.ApplicationScope;

@ApplicationScope
@Component(modules = {ApplicationModule.class, NetworkModule.class, RootModule.class, UtilsModule.class})
public interface ApplicationComponent {
    void inject(CleanSampleApplication application);

    void inject(MainActivity mainActivity);
    ActionBarOwner actionBarOwner();
}
