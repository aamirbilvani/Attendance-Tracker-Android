package com.novatex.attendace.responses;

import com.novatex.attendace.models.Offices;
import com.novatex.attendace.models.User;

import java.util.ArrayList;

public class OfficesResponse {


    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public class Response {

        private String msg;

        private int status;

        private ArrayList<Offices> data;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public ArrayList<Offices> getData() {
            return data;
        }

        public void setData(ArrayList<Offices> data) {
            this.data = data;
        }
    }


}
