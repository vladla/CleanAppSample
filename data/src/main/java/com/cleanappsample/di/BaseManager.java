package com.cleanappsample.di;

import com.cleanappsample.net.AutoValueAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.techery.janet.HttpActionService;
import io.techery.janet.Janet;
import io.techery.janet.gson.GsonConverter;
import io.techery.janet.okhttp3.OkClient;

/**
 * Created by Lubenets Vladyslav on 11/6/16.
 */
class BaseManager {

    private static final String BASE_URL = "http://www.android10.org/myapi";
    private Janet janet;

    Janet provideJanet() {
        if (janet == null) {
            janet = new Janet.Builder()
                    .addService(new HttpActionService(BASE_URL, new OkClient(), new GsonConverter(provideGson())))
                    .build();
        }
        return janet;
    }

    private Gson provideGson(){
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapterFactory(new AutoValueAdapterFactory());
        return builder.create();
    }

}
