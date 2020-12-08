package com.novatex.attendace.responses;

import com.novatex.attendace.models.Attendance;

public class AddAttendanceResponse {


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

        private Attendance data;

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

        public Attendance getData() {
            return data;
        }

        public void setData(Attendance data) {
            this.data = data;
        }
    }


}
