package com.siamsoft.covid_tracker.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIUtilities {

    private static Retrofit retrofit = null;

    public static ApiInt getApint() {

        if (retrofit == null) {

            retrofit = new Retrofit.Builder().baseUrl(ApiInt.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
return retrofit.create(ApiInt.class);

    }
}
