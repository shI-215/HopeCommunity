package com.hello.hopecommunity;

import android.app.Application;
import android.content.Context;

public class App extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static String MOB_COUNTRY = "+86";

    public static String SHARED_PREFERENCES_NAME = "Hope";

    private static String HOPE_URL = "http://192.168.43.196:8080/Hope";
    //    user
    public static String USER_LOGIN = HOPE_URL + "/user/login";
    public static String USER_REGISTER = HOPE_URL + "/user/register";
    public static String USER_FIND = HOPE_URL + "/user/find";
    public static String USER_ALTERPHONE = HOPE_URL + "/user/alterPhone";
    public static String USER_ALTERUSER = HOPE_URL + "/user/alterUser";

    //file
    public static String FILE_UPLOAD = HOPE_URL + "/file/upload";
    public static String FILE_UPLOADMORE = HOPE_URL + "/file/uploadMore";

    //active
    public static String ACTIVE_RELEASE = HOPE_URL + "/active/release";
}
