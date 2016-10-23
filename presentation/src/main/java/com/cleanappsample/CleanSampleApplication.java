package com.cleanappsample;

import android.app.Application;

import com.cleanappsample.di.RootModule;
import com.cleanappsample.di.components.ApplicationComponent;
import com.cleanappsample.di.components.DaggerApplicationComponent;
import com.cleanappsample.di.modules.ApplicationModule;
import com.cleanappsample.di.modules.NetworkModule;
import com.cleanappsample.model.Chats;

import dagger.Component;
import io.techery.presenta.addition.ActionBarOwner;
import io.techery.presenta.di.ApplicationScope;

public class CleanSampleApplication extends Application {

    @ApplicationScope
    @Component(modules =  RootModule.class)
    public interface AppComponent {
        void inject(CleanSampleApplication application);
        Chats chats();
    }

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
