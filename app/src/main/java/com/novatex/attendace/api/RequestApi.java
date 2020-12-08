package com.novatex.attendace.api;

import com.novatex.attendace.responses.LoginResponse;
import com.novatex.attendace.responses.LogoutResponse;
import com.novatex.attendace.responses.OfficesResponse;
import com.novatex.attendace.responses.SignUpResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface RequestApi {

    @FormUrlEncoded
    @POST("login/")
    Call<LoginResponse> requestLogin(@Field("username") String username,
                                     @Field("password") String password);



    @FormUrlEncoded
    @POST("register/")
    Call<SignUpResponse> requestRegister(@Field("username") String username,
                                         @Field("password") String password,
                                         @Field("fullname") String fullname,
                                         @Field("phone") String phone,
                                         @Field("office") String office);

    @POST("logout/")
    Call<LogoutResponse> requestLogout(@Header("Authorization") String auth);

    @GET("getoffices/")
    Call<OfficesResponse> requestGetOffices();
}


