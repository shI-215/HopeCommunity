package com.hello.hopecommunity.model;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hello.hopecommunity.App;
import com.hello.hopecommunity.bean.Image;
import com.hello.hopecommunity.bean.Msg;
import com.hello.hopecommunity.callback.MyCallBack;
import com.hello.hopecommunity.ui.ListListener;
import com.zhy.http.okhttp.OkHttpUtils;

import java.lang.reflect.Type;
import java.util.List;

public class ImageModel {

    public void getAll(final ListListener listListener) {
        OkHttpUtils.get().url(App.IMAGE_LOOKALLIMAGE).build().execute(new MyCallBack() {
            @Override
            public void onResponse(Msg response, int id) {
                if (response.getCode() == 200) {
                    Type type = new TypeToken<List<Image>>() {
                    }.getType();
                    List<Image> list = new Gson().fromJson(response.getData(), type);
                    Log.v("List---->", list.toString());
                    listListener.onSuccess(list);
                } else {
                    listListener.onFaile(response.getData());
                }
            }
        });
    }

    public void getMyImage(int userId, final ListListener listListener) {
        OkHttpUtils.get().url(App.IMAGE_LOOKMYIMAGE)
                .addParams("uId", userId + "")
                .build()
                .execute(new MyCallBack() {
                    @Override
                    public void onResponse(Msg response, int id) {
                        if (response.getCode() == 200) {
                            Type type = new TypeToken<List<Image>>() {
                            }.getType();
                            List<Image> list = new Gson().fromJson(response.getData(), type);
                            Log.v("List---->", list.toString());
                            listListener.onSuccess(list);
                        } else {
                            listListener.onFaile(response.getData());
                        }
                    }
                });
    }

    public void getAllMyImage(int userId, final ListListener listListener) {
        OkHttpUtils.get().url(App.IMAGE_LOOKALLMYIMAGE)
                .addParams("uId", userId + "")
                .build()
                .execute(new MyCallBack() {
                    @Override
                    public void onResponse(Msg response, int id) {
                        if (response.getCode() == 200) {
                            Type type = new TypeToken<List<Image>>() {
                            }.getType();
                            List<Image> list = new Gson().fromJson(response.getData(), type);
                            Log.v("List---->", list.toString());
                            listListener.onSuccess(list);
                        } else {
                            listListener.onFaile(response.getData());
                        }
                    }
                });
    }
}
