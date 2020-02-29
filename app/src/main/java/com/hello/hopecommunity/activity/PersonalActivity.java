package com.hello.hopecommunity.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.appbar.AppBarLayout;
import com.hello.hopecommunity.App;
import com.hello.hopecommunity.R;
import com.hello.hopecommunity.adapter.RecycleAdapter;
import com.hello.hopecommunity.bean.Image;
import com.hello.hopecommunity.model.ImageModel;
import com.hello.hopecommunity.ui.ListListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonalActivity extends AppCompatActivity implements View.OnClickListener, ListListener {
    private SharedPreferences preferences = App.context.getSharedPreferences(App.SHARED_PREFERENCES_NAME, App.context.MODE_PRIVATE);
    private int userId = preferences.getInt("userId", 0);
    private String userName = preferences.getString("userName", "Hope");

    private ImageModel imageModel;
    private RecycleAdapter recycleAdapter;
    private List<Map<String, Object>> list = new ArrayList<>();
    private Context context;

    private ImageView image_background;
    private ImageView btn_back;
    private TextView text_user;
    private RecyclerView recycler_view;
    private ImageView image_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        AppBarLayout appBarLayout = findViewById(R.id.appBar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                ImageView imageView = findViewById(R.id.image_user);
                if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) { // 折叠状态
                    imageView.setVisibility(View.GONE);
                } else { // 非折叠状态
                    imageView.setVisibility(View.VISIBLE);
                }
            }
        });
        context = this;
        if (userId != 0) {
            imageModel = new ImageModel();
            imageModel.getAllMyImage(userId, this);
        } else {
            initData();
        }
    }

    private void initData() {
        image_background = (ImageView) findViewById(R.id.image_background);
        btn_back = (ImageView) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
        text_user = (TextView) findViewById(R.id.text_user);
        text_user.setText(userName);
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        image_user = (ImageView) findViewById(R.id.image_user);

        recycleAdapter = new RecycleAdapter(list, context);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        recycler_view.setLayoutManager(staggeredGridLayoutManager);
        recycler_view.setAdapter(recycleAdapter);

        recycleAdapter.setOnItemClickListener(new RecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, RecycleAdapter.ViewName viewName, int position) {
                switch (v.getId()) {
                    case R.id.card_image_item:
                        Toast.makeText(context, (position + 1) + "", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent();
                        intent.putExtra("actId", (Integer) list.get(position).get("id"));
                        intent.setClass(context, DetailsActivity.class);
                        startActivity(intent);
                        break;
                }
            }

            @Override
            public void onItemLongClick(View v) {

            }
        });
    }

    @Override
    public void onSuccess(List list) {
        this.list = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            Image image = (Image) list.get(i);
            map.put("id", image.getActive().getActId());
            map.put("title", image.getActive().getActName());
            map.put("image", image.getImgPath());
            this.list.add(map);
        }
        Log.v("List", this.list.toString());
        initData();
    }

    @Override
    public void onFaile(Object object) {
        Toast.makeText(context, object.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }
}
