package com.cleanappsample.network;

import com.cleanappsample.entity.GsonAdaptersEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
    Janet provideBaseJanet(HttpActionService httpActionService) {
        return new Janet.Builder().addService(httpActionService).build();
    }

    @Provides
    @Singleton
    HttpActionService provideHttpActionService(@Named(NAMED_BASE_URL) String url, OkClient okClient, GsonConverter gsonConverter) {
        return new HttpActionService(url, okClient, gsonConverter);
    }

    @Provides
    @Named(NAMED_BASE_URL)
    String provideBaseUrl() {
        return "http://www.android10.org/myapi"; // ToDo replace with base url
    }

    @Provides
    @Singleton
    OkClient provideOkClient() {
        return new OkClient();
    }

    @Provides
    @Singleton
    GsonConverter provideGsonConverter(Gson gson) {
        return new GsonConverter(gson);
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapterFactory(new GsonAdaptersEntity());
        builder.serializeNulls();
        return builder.create();
    }

}
