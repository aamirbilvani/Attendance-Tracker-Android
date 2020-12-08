package com.novatex.attendace.models;

public class Attendance {


    private String date;

    private String time;

    private String lat;
    private String lng;
    private boolean ispresent;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public boolean isIspresent() {
        return ispresent;
    }

    public void setIspresent(boolean ispresent) {
        this.ispresent = ispresent;
    }
}
