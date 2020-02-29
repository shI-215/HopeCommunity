package com.hello.hopecommunity.model;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.hello.hopecommunity.App;
import com.hello.hopecommunity.bean.Active;
import com.hello.hopecommunity.bean.Msg;
import com.hello.hopecommunity.bean.User;
import com.hello.hopecommunity.callback.MyCallBack;
import com.hello.hopecommunity.ui.FileListener;
import com.hello.hopecommunity.ui.MyListener;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;

public class ActiveModel {
    private SharedPreferences preferences = App.context
            .getSharedPreferences(App.SHARED_PREFERENCES_NAME, App.context.MODE_PRIVATE);
    private int userId = preferences.getInt("userId", 0);

    public void upLoadImage(ArrayList<String> path, final FileListener fileListener) {
        OkHttpUtils.post()
                .addFile("file", path.get(0), new File(path.get(0)))//图片
                .url(App.FILE_UPLOAD)
                .build()
                .execute(new MyCallBack() {
                    @Override
                    public void onResponse(Msg response, int id) {
                        if (response.getCode() == 200) {
                            fileListener.onUpload(response.getData());
                        } else {
                            fileListener.onError(response.getData());
                        }
                    }
                });
    }

    public void upLoadImageMore(ArrayList<String> path, final FileListener fileListener) {
        Map<String, File> fileMap = new HashMap<>();
        for (int i = 0; i < path.size(); i++) {
            fileMap.put(path.get(i), new File(path.get(i)));
        }
        OkHttpUtils.post()
                .files("file", fileMap)//证明
                .url(App.FILE_UPLOADMORE)
                .build()
                .execute(new MyCallBack() {
                    @Override
                    public void onResponse(Msg response, int id) {
                        if (response.getCode() == 200) {
                            fileListener.onUpload(response.getData());
                        } else {
                            fileListener.onError(response.getData());
                        }
                    }
                });
    }

    public void release(Active active, final MyListener myListener) {
        User user = new User();
        user.setUserId(userId);
        active.setUser(user);
        OkHttpUtils.postString().url(App.ACTIVE_RELEASE).content(new Gson().toJson(active))
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

    public void look(int actId, final MyListener myListener) {
        OkHttpUtils.get()
                .url(App.ACTIVE_LOOK)
                .addParams("actId", actId + "")
                .build()
                .execute(new MyCallBack() {
                    @Override
                    public void onResponse(Msg response, int id) {
                        if (response.getCode() == 200) {
                            myListener.onSuccess(response.getData());
                        } else {
                            myListener.onFaile(response.getData());
                        }
                    }
                });
    }
}
