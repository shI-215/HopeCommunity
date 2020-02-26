package com.hello.hopecommunity.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.hello.hopecommunity.R;

public class CardLayoutItem extends CardView {
    private ImageView card_image_item;
    private TextView text_people;

    public CardLayoutItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.card_layout_item, this);
    }

    public void setCard(int image, String people) {
        init();
        card_image_item.setImageResource(image);
        text_people.setText(people);
    }

    private void init() {
        card_image_item = findViewById(R.id.card_image_item);
        text_people = findViewById(R.id.text_title_item);
    }
}
