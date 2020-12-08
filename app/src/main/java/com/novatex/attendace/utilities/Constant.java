package com.novatex.attendace.utilities;


public class Constant {


    public static final int REQUEST_FAILED = 0;
    public static final int REQUEST_LOGIN = 1;
    public static final int REQUEST_GET_BANNER = 2;
    public static final int REQUEST_GET_MILLS = 3;
    public static final int REQUEST_GET_YARN_LIST = 4;
    public static final int REQUEST_GET_YARN_DETAILS = 5;
    public static final int REQUEST_SIGNUP = 7;
    public static final int REQUEST_GET_YARN_FILTER = 8;
    public static final int REQUEST_GET_MILLS_PAGING = 9;
    public static final int REQUEST_EMAIL_CHECK = 10;
    public static final int REQUEST_SIGNUP_SOCIAL = 11;
    public static final int REQUEST_SAVE_SEARCH = 12;
    public static final int REQUEST_GET_SAVED_SEARCH = 13;
    public static final int REQUEST_GET_FIREBASE_DETAILS = 14;
    public static final int REQUEST_USER_SUBSCRIPTION = 15;
    public static final int REQUEST_USER_UNSUBSCRIPTION = 16;
    public static final int REQUEST_USER_SUBSCRIPTION_LIST = 17;
    public static final int REQUEST_SAVE_SEARCH_DELETE = 18;
    public static final int REQUEST_GET_RATE_HISTORY = 19;
    public static final int REQUEST_ADD_YARN_REQUEST = 20;
    public static final int REQUEST_GET_YARN_REQUEST_LIST = 21;
    public static final int REQUEST_YARN_REQUEST_DELETE = 22;
    public static final int REQUEST_LOGIN_ANONYMOUS = 23;
    public static final int REQUEST_USER_LOCATION = 24;
    public static final int REQUEST_REFRESH_TOKEN = 25;
    public static final int REQUEST_PRODUCT_ANALYTICS = 26;
    public static final int REQUEST_YARN_REQUEST_DETAILS = 27;
    public static final int REQUEST_UPDATE_PROFILE = 28;
    public static final int REQUEST_CHANGE_PASSWORD = 29;
    public static final int REQUEST_CRASH_REPORT = 30;
    public static final int REQUEST_RESET_PASS_EMAIL = 31;
    public static final int REQUEST_RESET_PASS= 32;
    public static final int REQUEST_GET_OFFICES = 33;
    public static final int REQUEST_LOGOUT = 34;
    public static final int ADD_ATTENDANCE= 35;

    public static final String PLATFORM= "Android";


    public static final String DEVICE_CONFIG_PREF = "DeviceConfig";
    public static final String LOGIN_PREF = "LoginPreferences";

    public static final String APPLICATION_CONFIG_PREF = "ApplicationConfigPreferences";

    public static final String PREFS_LOCATION_TRACKING_NAME = "LocationTrackingPreferences";

    public static final String DOESNT_EXIST_STRING = "-1";
    public static final int DOESNT_EXIST = -1;
    public static final String PREF_UUID = "UUID";


    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd hh:mm:ss";
    public static final String TIME_FORMAT = "hh:mm:ss";
    public static final String SERVER_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_FORMAT = "yyyy-MM-dd";


    public static final int RECENT_SEARCH_HISTORY_COUNT=10;

    public static final int RC_SIGN_IN = 1;

    public static final int PROVIDER_ID_FB = 1;
    public static final int PROVIDER_ID_GOOGLE = 2;

    public static final int REQUEST_CALL = 64023;

    public static final String SUBSCRIPTION_TYPE_PRODUCT = "product_id";

    public static final String RATE_CHANGE_CHANNEL_ID = "1";
    public static int RATE_CHANGE_NOTIFICATION_ID = 1;

    //Error Messages
    public static final String HOST_ERROR = "Sorry, server not responding at the moment. Please wait a few minutes and try again. Error Code : 1001";
    public static final String SERVICE__ERROR = "Sorry, an error occurred on the server. Please wait a few minutes and try again. Error Code : 1002";
    public static final String SLOW_NETWORK_ERROR = "Slow internet connection. Please check your connection speed and try again. Error Code : 1003";
    public static final String NETWORK_ERROR = "Please make sure you're connected to the internet and try again. Error Code : 1004";
    public static final String CONVERSION_ERROR = "An error occurred, please try again after few minutes. Error Code : 1005";
    public static final String UNKNOWN_ERROR = "An error occurred. Please try again after few minutes. Error Code : 1006";
    public static final String GENERIC_ERROR = "Something went wrong, Please try again later. Error Code : 1007";
    public static final String GENERIC_ERROR_API = "Something went wrong, Please try again later. Error Code : 1008";

    public static final String JSON_ERROR = "Unable to parse JSON. Error Code : 2001";
    public static final String YARN_SEARCH_SET_CATWISE_ERROR = "There are no products in this category. Error Code : 2002";
    public static final String WHATSAPP_NOT_FOUND_ERROR = "Whatsapp not installed in your phone. Error Code : 2003";
    public static final String PARSE_EXCEPTION_ERROR = "Unable to parse Date. Error Code : 2004";
    public static final String GENERIC_ERROR_APP_CRASH = "Something went wrong, Please try again later. Error Code : 2005";
    public static final String PHONE_VERIFICATION_ERROR = "Unable to verify code. Error Code : 2006";

    public static final String FB_ERROR = "Unable to login in to Facebook. Error Code : 3001";
    public static final String GOOGLE_ERROR = "Unable to login in to Google. Error Code : 3002";

    public static final String NO_USER_FOUND= "No user found on this email";


    public static final double NEAR_LOCATION_GEOFENCE_RADIUS = 0.0027;
    public static final double NEAR_LOCATION_GEOFENCE_RADIUS_GRACE = 0.0005;

    public static final String WORKER_THREAD_ERROR = "Something went wrong, Please try again later. Error Code : 3003";

    public static final int MENU_INDEX_YARN_SEARCH = 1;
    public static final int MENU_INDEX_MILL_LISTING = 2;
    public static final int MENU_INDEX_FAVOURITES = 3;
    public static final int MENU_INDEX_YARN_REQUEST = 1;

    public static final String MENU_TITLE_MILL_LISTING = "MillTb Listing";
    public static final String MENU_TITLE_YARN_SEARCH = "Yarn Search";
    public static final String MENU_TITLE_YARN_LISTING = "Yarn Listing";
    public static final String MENU_TITLE_YARN_DETAILS = "Yarn Details";
    public static final String MENU_TITLE_RATE_HISTORY = "Rate History";
    public static final String MENU_TITLE_YARN_REQUEST = "Yarn Request";


    public static final int SCALE_MULTIPLIER=10;


    public static String API_BASE_URL = "http://52.221.131.164/apis/";
    //public static String API_BASE_URL = "https://devapp.sanatkar.pk/api/";

    //Global.USER_TOKEN=mPrefs.getString("token","");
    //public static String FIREBASE_BASE_URL = "https://iid.googleapis.com/iid/info/"+getToken(context);
    //public static String FIREBASE_BASE_URL = "https://iid.googleapis.com/iid/info/";


    public static final int SPLASH_SCREEN_DELAY = 1000;


    public static final String RATE_HISTORY_DURATION_DATE = "dt";



    public static final String OTHER_TEXT = "Custom";
    public static final String INQUIRY_TEXT = "Inquired";
    public static final String CAT_ORDER = "cat_order";
    public static final String PARA_ORDER = "para_order";
    public static final String LIKE = "Subscribe";
    public static final String UNLIKE = "Unsubscribe";




    public static final String DENIER_FILAMENT_CATEGORY= "polyester filament yarn";
    public static final String BLENDED_CATEGORY= "blended";

    public static final int oneRowFilterListView=80;
    public static final int oneRowSavedSearchListView=80;


    public static final int DEFAULT_REQUIRED_RECORDS=10;

    public static final String PREF_IS_FIRST_RUN = "NearLocationAlarm";

    public static final long  SEND_LOCATIONS_TO_SERVER_ALARM_TIMER=1800000;
    //public static final long  SEND_LOCATIONS_TO_SERVER_ALARM_TIMER=1000;

    public static final int  ACCESS_TOKEN_EXPIRATION_TIME_HOURS=23;



    public static final int  ONE_MONTH_DAYS=30;
    public static final int  THREE_MONTH_DAYS=90;
    public static final int  SIX_MONTH_DAYS=180;
    public static final int  ONE_YEAR_DAYS=365;

    public static final int SEG_CTRL_ITEMS=2;

    public static final String NULL_VALUE="N/A";


    public static final String PREV_ACTIVITY_TEXT="prevActivity";
    public static final String PROFILE_TEXT="profile";
    public static final String FAV_TEXT="fav";
    public static final String RATE_HISTORY_TEXT="rate_history";
    public static final String YARN_REQUEST_TEXT="yarn_request";
    public static final String YARN_REQUEST_DETAILS_TEXT="yarn_request_details";
    public static final String RESET_PASSWORD="reset_password";

    public static final String YARN_REQUEST_LISTING_TEXT="yarn_request_listing";
    public static final String LOGIN_TEXT="login";
    public static final String LOGIN_SOCIAL_TEXT="login_social";
    public static final String HOME_TEXT="home";
    public static final String PHONE_TEXT="phone";


    public static final String HOST_NAME = "sanatkar.pk";
    //public static final String HOST_NAME = "*";

    //public static final String PUBLIC_KEY ="sha256/9Cb2OzBgXYk8Pl0INOzEAIYqqZ8ZI+vr0tXiawlY0U8=";
    //public static final String PUBLIC_KEY ="sha256/xrStn2CwLNr2ekXFDno3tWMn0HWZdG9duzcxCKzudSI=";
    public static final String PUBLIC_KEY ="sha256/H/ufbLsh68/vgKh1l8MjBMjUQ3wBMiHmreWaQ4DQoK8=";



    public static final String KEY_STORE="AndroidKeyStore";

    public static final String KEY_ALIAS="attendance_key_alias";

    //public static final String CRYPTO_TRANSFORMATION="AES";
    public static final String CRYPTO_TRANSFORMATION="AES/CBC/PKCS7Padding";


    public static final int KEY_END_YEARS=200;
}

