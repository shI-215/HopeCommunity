package com.hello.hopecommunity.ui;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Log.v("GlideImageLoader", path.toString());
        Glide.with(context).load(path).centerCrop().into(imageView);
    }
}
