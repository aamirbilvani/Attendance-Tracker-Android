package com.novatex.attendace.api;

import android.content.Context;

import com.novatex.attendace.models.User;
import com.novatex.attendace.responses.LoginResponse;
import com.novatex.attendace.responses.LogoutResponse;
import com.novatex.attendace.responses.OfficesResponse;
import com.novatex.attendace.responses.SignUpResponse;
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
import static com.novatex.attendace.utilities.Utility.getToken;

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

    public void requestGetOffices() {

        Call<OfficesResponse> call = client.requestGetOffices();

        call.enqueue(new Callback<OfficesResponse>() {
            @Override
            public void onResponse(Call<OfficesResponse> call, Response<OfficesResponse> response) {

                onResponseSuccess(response, Constant.REQUEST_GET_OFFICES);
            }

            @Override
            public void onFailure(Call<OfficesResponse> call, Throwable t) {
                onResponseFail(t);
            }
        });

    }


    public void requestRegister(String username,String password, String fullname, String phone, String office) {

        Call<SignUpResponse> call = client.requestRegister(username, password, fullname, phone, office);

        call.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                onResponseSuccess(response, Constant.REQUEST_SIGNUP);
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                onResponseFail(t);
            }
        });

    }



    public void requestLogout() {

        Call<LogoutResponse> call = client.requestLogout("Token "+Utility.getToken(context));

        call.enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                onResponseSuccess(response, Constant.REQUEST_LOGOUT);
            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {
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
