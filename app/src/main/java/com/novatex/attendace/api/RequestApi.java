package com.novatex.attendace.api;

import com.novatex.attendace.responses.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.POST;
public interface RequestApi {


    @POST("login")
    Call<LoginResponse> requestLogin(@Field("username") String username,
                                     @Field("password") String password);


}


