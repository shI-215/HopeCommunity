package com.hello.hopecommunity.callback;

import android.widget.Toast;

import com.google.gson.Gson;
import com.hello.hopecommunity.App;
import com.hello.hopecommunity.bean.Msg;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;

public abstract class MyCallBack extends Callback<Msg> {
    @Override
    public Msg parseNetworkResponse(okhttp3.Response response, int id) throws Exception {
        return new Gson().fromJson(response.body().string(), Msg.class);
    }

    @Override
    public void onError(Call call, Exception e, int id) {
        Toast.makeText(App.context, "错误", Toast.LENGTH_SHORT).show();
    }
}
