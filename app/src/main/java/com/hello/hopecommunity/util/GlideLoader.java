package com.hello.hopecommunity.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hello.hopecommunity.R;
import com.jaiky.imagespickers.ImageLoader;

public class GlideLoader implements ImageLoader {
    @Override
    public void displayImage(Context context, String path, ImageView imageView) {
        Glide.with(context)
                .load(path)
                .placeholder(R.drawable.ic_image_add)
                .centerCrop()
                .into(imageView);
    }
}
