package com.cleanappsample;

import android.app.Application;

import com.cleanappsample.di.components.ApplicationComponent;
import com.cleanappsample.di.modules.ApplicationModule;
import com.facebook.stetho.Stetho;


import io.techery.presenta.mortar.DaggerService;
import mortar.MortarScope;

public class CleanSampleApplication extends Application {

    private ApplicationComponent component;
    private MortarScope rootScope;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerService.createComponent(ApplicationComponent.class, new ApplicationModule(this));
        component.inject(this);
        if(BuildConfig.DEBUG){
            Stetho.initializeWithDefaults(this);
        }
    }

    @Override
    public Object getSystemService(String name) {
        if (rootScope == null) {
            rootScope = MortarScope.buildRootScope()
                    .withService(DaggerService.SERVICE_NAME, component)
                    .build("root");
        }
        if (rootScope.hasService(name)) return rootScope.getService(name);

        return super.getSystemService(name);
    }

    public ApplicationComponent getApplicationComponent() {
        return component;
    }
}
