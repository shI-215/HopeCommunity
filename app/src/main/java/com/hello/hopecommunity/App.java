package com.hello.hopecommunity;

import android.app.Application;
import android.content.Context;

import cn.jpush.android.api.JPushInterface;

public class App extends Application {
    public static Context context;
    public static String registrationID;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        registrationID = JPushInterface.getRegistrationID(this);
    }

    public static final String MOB_COUNTRY = "+86";

    public static final String SHARED_PREFERENCES_NAME = "Hope";

    public static final String HOPE_URL = "http://192.168.43.196:8080/Hope";

    //user
    public static final String USER_LOGIN = HOPE_URL + "/user/login";
    public static final String USER_LOGIN_OUT = HOPE_URL + "/user/loginOut";
    public static final String USER_REGISTER = HOPE_URL + "/user/register";
    public static final String USER_FIND = HOPE_URL + "/user/find";
    public static final String USER_ALTERPHONE = HOPE_URL + "/user/alterPhone";
    public static final String USER_ALTERUSER = HOPE_URL + "/user/alterUser";

    //file
    public static final String FILE_UPLOAD = HOPE_URL + "/file/upload";
    public static final String FILE_UPLOADMORE = HOPE_URL + "/file/uploadMore";

    //active
    public static final String ACTIVE_RELEASE = HOPE_URL + "/active/release";
    public static final String ACTIVE_LOOK = HOPE_URL + "/active/look";
    public static final String ACTIVE_JOIN = HOPE_URL + "/help/join";

    //image
    public static final String IMAGE_LOOKALLIMAGE = HOPE_URL + "/image/lookAllImage";
    public static final String IMAGE_LOOKALLMYIMAGE = HOPE_URL + "/image/lookAllMyImage";
    public static final String IMAGE_LOOKMYIMAGE = HOPE_URL + "/image/lookMyImage";
}
