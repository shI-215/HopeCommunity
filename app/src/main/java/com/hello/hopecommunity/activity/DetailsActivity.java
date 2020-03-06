package com.hello.hopecommunity.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.hello.hopecommunity.ui.JoinListener;
import com.hello.hopecommunity.ui.MyListener;

import java.util.List;

public class DetailsActivity extends AppCompatActivity implements MyListener, JoinListener, View.OnClickListener {

    private SharedPreferences preferences = App.context.getSharedPreferences(App.SHARED_PREFERENCES_NAME, App.context.MODE_PRIVATE);
    private int userId = preferences.getInt("userId", 0);
    private ActiveModel activeModel;
    private int actId;
    private Context context;

    private ImageView image_title;
    private TextView text_title;
    private ImageView image_user;
    private TextView text_describe;
    private ImageView image_picture;
    private TextView text_location;
    private Button btn_join;

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
        actId = getIntent().getIntExtra("actId", 0);
        activeModel.look(userId, actId, this);
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
        btn_join = (Button) findViewById(R.id.btn_join);
        btn_join.setOnClickListener(this);
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
        if (active.getUser().getUserId() == userId) {
            btn_join.setVisibility(View.GONE);
        } else {
            btn_join.setVisibility(View.VISIBLE);
        }
        if (userId == 0) {
            btn_join.setClickable(false);
            btn_join.setText("请先登录");
        } else {
            if (hopeMap.isJoin()) {
                btn_join.setClickable(false);
                btn_join.setText("活动已报名");
            } else {
                btn_join.setClickable(true);
                btn_join.setText("报名活动");
            }
        }
    }

    @Override
    public void onFaile(Object o) {
        Toast.makeText(getApplicationContext(), o.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_join:
                activeModel.join(userId, actId, this);
                break;
        }
    }

    @Override
    public void onSuccess() {
        Intent intent = new Intent();
        intent.putExtra("actId", actId);
        intent.setClass(context, DetailsActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFaile() {
        Toast.makeText(context, "报名失败", Toast.LENGTH_LONG).show();
    }
}
