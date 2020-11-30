package com.novatex.attendace.api;

import android.content.Context;

import com.novatex.attendace.BuildConfig;
import com.novatex.attendace.utilities.Constant;

import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.novatex.attendace.utilities.Constant.HOST_NAME;
import static com.novatex.attendace.utilities.Constant.PUBLIC_KEY;


public class ApiClient {

    private static RequestApi requestCall = null;


    public static RequestApi getInstance(Context context) {

        if (requestCall == null) {



            OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpBuilder.build())
                    .build();

            requestCall = retrofit.create(RequestApi.class);

        }
        return requestCall;
    }





}

