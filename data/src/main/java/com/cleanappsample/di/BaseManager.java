package com.cleanappsample.di;

import com.google.gson.Gson;

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
                    .addService(new HttpActionService(BASE_URL, new OkClient(), new GsonConverter(new Gson())))
                    .build();
        }
        return janet;
    }

}
