package com.hello.hopecommunity.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hello.hopecommunity.R;

public class ClickPoint extends RelativeLayout {
    private TextView text_title;
    private ImageView image_click;

    public ClickPoint(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.click_point, this);
    }

    public void setClickPoint(String title, int isShow) {
        init();
        text_title.setText(title);
        if (isShow == 0) {
            image_click.setVisibility(GONE);
        } else {
            image_click.setVisibility(VISIBLE);
        }
    }

    private void init() {
        text_title = findViewById(R.id.text_title);
        image_click = findViewById(R.id.image_click);
    }
}
