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
import io.techery.presenta.mortar.DaggerService;
import mortar.MortarScope;

public class CleanSampleApplication extends Application {

    private ApplicationComponent component;
    private MortarScope rootScope;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerService.createComponent(ApplicationComponent.class);
        rootScope = MortarScope.buildRootScope()
                .withService(DaggerService.SERVICE_NAME, component)
                .build("root");
        component.inject(this);
    }


    @Override public Object getSystemService(String name) {
        Object mortarService = rootScope.getService(name);
        if (mortarService != null) return mortarService;

        return super.getSystemService(name);
    }

    public ApplicationComponent getApplicationComponent() {
        return component;
    }
}
