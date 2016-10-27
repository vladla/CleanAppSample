package com.cleanappsample.di.modules;

import com.google.gson.Gson;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.techery.janet.HttpActionService;
import io.techery.janet.Janet;
import io.techery.janet.gson.GsonConverter;
import io.techery.janet.okhttp3.OkClient;

@Module
public class NetworkModule {

    private static final String NAMED_BASE_URL = "base_url";

    @Provides
    @Singleton
    Janet provideBaseJanet(HttpActionService httpActionService){
        return new Janet.Builder().addService(httpActionService).build();
    }

    @Provides
    @Singleton
    HttpActionService provideHttpActionService(@Named(NAMED_BASE_URL) String url, OkClient okClient, GsonConverter gsonConverter){
        return new HttpActionService(url, okClient, gsonConverter);
    }

    @Provides
    @Named(NAMED_BASE_URL)
    String provideBaseUrl(){
        return "https://github.com/"; // ToDo replace with base url
    }

    @Provides
    @Singleton
    GsonConverter provideGsonConverter(Gson gson){
        return new GsonConverter(gson);
    }

    @Provides
    @Singleton
    Gson provideGson(){
        return new Gson();
    }

}
