package com.novatex.attendace.api;


import android.content.Context;
import android.os.Build;
import android.provider.Settings;

import com.novatex.attendace.responses.LoginResponse;
import com.novatex.attendace.utilities.Constant;
import com.novatex.attendace.utilities.Utility;

import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.novatex.attendace.utilities.Constant.GENERIC_ERROR;
import static com.novatex.attendace.utilities.Constant.GENERIC_ERROR_API;

public class ApiCallRequest {

    private Context context;
    private RequestApi client;
    private int a=0;


    public ApiCallRequest(Context context) {
        this.context = context;
        client = ApiClient.getInstance(context);

    }

    private CallBackListener listener;


    public void requestLogin(String username, String password) {

        Call<LoginResponse> call = client.requestLogin(username, password);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                onResponseSuccess(response, Constant.REQUEST_LOGIN);
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                onResponseFail(t);
            }
        });
    }

    private void onResponseSuccess(Response response, int requestCode) {
        //Global.requests=Global.requests+1;
        if (listener != null) {
            if (response.code() == 200) {
                listener.callBack(requestCode, response.body());
            } else if (response.code() == 500 || response.code() == 404) {
                String msg = "";
                try {
                    String res = response.errorBody().string();
                    JSONObject jsonRes = new JSONObject(res);
                    String msgAndStatus = jsonRes.getString("response");
                    JSONObject jsonMsgAndStatus = new JSONObject(msgAndStatus);
                    msg = jsonMsgAndStatus.getString("msg");
                } catch (Exception ex) {
                    msg = GENERIC_ERROR_API;
                }
                listener.callBack(Constant.REQUEST_FAILED, msg);
            } else if (response.code() == 401) {
                //logout(context);
/*
                Intent i = new Intent(context, LoginActivity.class);
                //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(i);

 */
            } else {
                listener.callBack(Constant.REQUEST_FAILED, GENERIC_ERROR);
            }
        }
    }

    private void onResponseFail(Throwable t) {
        if (listener != null) {
            if (t instanceof SocketTimeoutException) {
                listener.callBack(Constant.REQUEST_FAILED, Constant.SLOW_NETWORK_ERROR);
            } else if (t instanceof IOException) {

                if (Utility.isInternetAvailable(context)) {
                    if (t instanceof UnknownHostException) {
                        listener.callBack(Constant.REQUEST_FAILED, Constant.HOST_ERROR);
                    } else if (t instanceof UnknownServiceException) {
                        listener.callBack(Constant.REQUEST_FAILED, Constant.SERVICE__ERROR);
                    } else {
                        listener.callBack(Constant.REQUEST_FAILED, Constant.UNKNOWN_ERROR);
                    }
                } else {
                    listener.callBack(Constant.REQUEST_FAILED, Constant.NETWORK_ERROR);
                }
            } else {
                listener.callBack(Constant.REQUEST_FAILED, Constant.CONVERSION_ERROR);
            }
        }
    }


    public void setCallBackListener(CallBackListener listener) {
        this.listener = listener;
    }

    public interface CallBackListener {
        void callBack(int requestType, Object object);
    }
}
