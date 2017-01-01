package com.cleanappsample.di;

import com.cleanappsample.data.BuildConfig;
import com.cleanappsample.net.AutoValueAdapterFactory;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.techery.janet.HttpActionService;
import io.techery.janet.Janet;
import io.techery.janet.gson.GsonConverter;
import io.techery.janet.okhttp3.OkClient;
import okhttp3.OkHttpClient;

/**
 * Created by Lubenets Vladyslav on 11/6/16.
 */
class BaseManager {

    private static final String BASE_URL = "http://www.android10.org/myapi";
    private Janet janet;

    Janet provideJanet() {
        if (janet == null) {
            janet = new Janet.Builder()
                    .addService(new HttpActionService(BASE_URL, provideOkClient(), new GsonConverter(provideGson())))
                    .build();
        }
        return janet;
    }

    private OkClient provideOkClient(){
        if(BuildConfig.DEBUG){
            return new OkClient(new OkHttpClient.Builder()
                    .addNetworkInterceptor(new StethoInterceptor())
                    .build());
        } else
            return new OkClient();
    }

    private Gson provideGson(){
        GsonBuilder builder = new GsonBuilder();
        builder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        builder.registerTypeAdapterFactory(new AutoValueAdapterFactory());
        return builder.create();
    }

}
