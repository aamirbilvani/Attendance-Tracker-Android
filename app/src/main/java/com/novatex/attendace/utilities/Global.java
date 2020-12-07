package com.novatex.attendace.utilities;


import java.io.Serializable;

public class Global implements Serializable {


    public static int currentMillCount;
    public static int currentFavCount;
    public static int currentYarnRequestListCount;

    public static int isRefreshingToken = 0;

    public static boolean isYarnSearchSortEnabled = false;
    public static boolean isYarnRequestMenuEnabled = false;

    public static float screenSize = 0;


    public static String lastScreenBeforeLogin = "";
    public static String productIdSelectedBeforeLogin = "";

    public static byte[] CIPHER_IV = {64, 7, 16, -51, -39, -32, -2, -29, 29, 12, 20, -66, -28, 107, -18, -2};


    public static String tokenForRefresh = "";

    //public static int requests=0;
    //public static int requestsRefreshToken=0;
    //public static int requestsAnonymous=0;


    public static int locationPermission = 1;

    public static String version = "";
}
