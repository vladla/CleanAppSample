package com.cleanappsample.network;

import com.google.gson.Gson;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.techery.janet.HttpActionService;
import io.techery.janet.Janet;
import io.techery.janet.gson.GsonConverter;
import io.techery.janet.okhttp3.OkClient;
import okhttp3.OkHttpClient;
import okhttp3.Request;

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
        return "https://api.github.com"; // ToDo replace with base url
    }

    @Provides
    @Singleton
    OkClient provideOkClient() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder().addInterceptor(chain -> {
            Request.Builder builderRequest = chain.request().newBuilder();  // ToDo configure request
            Request request = builderRequest.build();
            return chain.proceed(request);
        });
        return new OkClient(clientBuilder.build());
    }

    @Provides
    @Singleton
    GsonConverter provideGsonConverter(Gson gson) {
        return new GsonConverter(gson);
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new Gson();
    }

}
