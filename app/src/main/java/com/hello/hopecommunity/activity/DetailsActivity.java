package com.hello.hopecommunity.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hello.hopecommunity.App;
import com.hello.hopecommunity.R;
import com.hello.hopecommunity.bean.Active;
import com.hello.hopecommunity.bean.HopeMap;
import com.hello.hopecommunity.bean.Image;
import com.hello.hopecommunity.model.ActiveModel;
import com.hello.hopecommunity.ui.MyListener;

import java.util.List;

public class DetailsActivity extends AppCompatActivity implements MyListener {

    private ActiveModel activeModel;
    private Context context;

    private ImageView image_title;
    private TextView text_title;
    private ImageView image_user;
    private TextView text_describe;
    private ImageView image_picture;
    private TextView text_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        context = this;
        activeModel = new ActiveModel();
        activeModel.look(getIntent().getIntExtra("actId", 0), this);
        initView();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        image_title = (ImageView) findViewById(R.id.image_title);
        text_title = (TextView) findViewById(R.id.text_title);
        image_user = (ImageView) findViewById(R.id.image_user);
        text_describe = (TextView) findViewById(R.id.text_describe);
        image_picture = (ImageView) findViewById(R.id.image_picture);
        text_location = (TextView) findViewById(R.id.text_location);
    }

    @Override
    public void onSuccess(Object object) {
        initView();
        HopeMap hopeMap = new Gson().fromJson(object.toString(), HopeMap.class);
        Active active = hopeMap.getActive();
        Log.v("active", active.toString());
        List<Image> images = hopeMap.getImages();
        Log.v("images", images.toString());
        text_title.setText(active.getActName());
        text_location.setText(active.getActAddress());
        text_describe.setText("\u3000\u3000" + active.getActDescribe());
        for (Image image : images) {
            if (image.getImgType() == 1) {
                String url = App.HOPE_URL + image.getImgPath();
                Glide.with(context)
                        .load(url)
                        .thumbnail(0.2f)
                        .placeholder(R.drawable.ic_image_loading)
                        .error(R.drawable.ic_image_failed)
                        .into(image_title);
            } else if (image.getImgType() == 2) {
                String url = App.HOPE_URL + image.getImgPath();
                Glide.with(context)
                        .load(url)
                        .thumbnail(0.2f)
                        .placeholder(R.drawable.ic_image_loading)
                        .error(R.drawable.ic_image_failed)
                        .into(image_picture);
            }
        }
    }

    @Override
    public void onFaile(Object o) {
        Toast.makeText(getApplicationContext(), o.toString(), Toast.LENGTH_LONG).show();
    }
}
