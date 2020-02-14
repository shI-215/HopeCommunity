package com.hello.hopecommunity.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.hello.hopecommunity.App;
import com.hello.hopecommunity.bean.Msg;
import com.hello.hopecommunity.bean.User;
import com.hello.hopecommunity.callback.MyCallBack;
import com.hello.hopecommunity.ui.MyListener;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.MediaType;

public class UserModel {
    private Context context;
    private SharedPreferences preferences;

    public UserModel(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(App.SHARED_PREFERENCES_NAME, context.MODE_PRIVATE);
    }

    public void login(final User user, final MyListener myListener) {
        Log.v("Login", user.toString());
        OkHttpUtils.postString().url(App.USER_LOGIN).content(new Gson().toJson(user))
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build().execute(new MyCallBack() {
            @Override
            public void onResponse(Msg msg, int id) {
                if (msg.getCode() == 200) {
                    User user1 = new Gson().fromJson(msg.getData(), User.class);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("userId", user1.getUserId());
                    editor.putString("userName", user1.getUserName());
                    editor.putString("userTel", user1.getUserTel());
                    editor.putString("userSex", user1.getUserSex());
                    editor.putString("userBirthday", user1.getUserBirthday());
                    editor.putString("userAddress", user1.getUserAddress());
                    if (user1.getUserPicture() != null) {
                        editor.putString("userPicture", user1.getUserPicture());
                    } else {
                        editor.putString("userPicture", "");
                    }
                    editor.putString("userAutograph", user1.getUserAutograph());
                    editor.commit();
                    myListener.onSuccess("");
                } else {
                    myListener.onFaile(msg.getData());
                }
            }
        });
    }

    public void loginOut(final MyListener myListener) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
        myListener.onSuccess("退出登录");
    }

    public void alterUser(User user, final MyListener myListener) {
        OkHttpUtils.postString().url(App.USER_ALTERUSER).content(new Gson().toJson(user))
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build().execute(new MyCallBack() {
            @Override
            public void onResponse(Msg msg, int id) {
                if (msg.getCode() == 200) {
                    myListener.onSuccess(msg.getData());
                } else {
                    myListener.onFaile(msg.getData());
                }
            }
        });
    }

    public void alterPhone(String phone, String newPhone, final MyListener myListener) {
        OkHttpUtils.post().url(App.USER_ALTERPHONE)
                .addParams("phone", phone)
                .addParams("newPhone", newPhone)
                .build().execute(new MyCallBack() {
            @Override
            public void onResponse(Msg msg, int id) {
                if (msg.getCode() == 200) {
                    myListener.onSuccess(msg.getData());
                } else {
                    myListener.onFaile(msg.getData());
                }
            }
        });
    }

    public void find(User user, final MyListener myListener) {
        OkHttpUtils.postString().url(App.USER_FIND).content(new Gson().toJson(user))
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build().execute(new MyCallBack() {
            @Override
            public void onResponse(Msg msg, int id) {
                if (msg.getCode() == 200) {
                    myListener.onSuccess(msg.getData());
                } else {
                    myListener.onFaile(msg.getData());
                }
            }
        });
    }
}
