package com.hello.hopecommunity.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import java.util.List;
import java.util.Map;

public class PersonalActivity extends AppCompatActivity implements View.OnClickListener {
    private SharedPreferences preferences = App.context.getSharedPreferences(App.SHARED_PREFERENCES_NAME, App.context.MODE_PRIVATE);
    private int userId = preferences.getInt("userId", 0);
    private String userName = preferences.getString("userName", "Hope");
    private RecycleAdapter recycleAdapter;
    private Context context;

    private List<Map<String, Object>> list;
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
        initView();
        initData();
    }

    private void initView() {
        image_background = (ImageView) findViewById(R.id.image_background);
        btn_back = (ImageView) findViewById(R.id.btn_back);
        text_user = (TextView) findViewById(R.id.text_user);
        text_user.setText(userName);
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        image_user = (ImageView) findViewById(R.id.image_user);

        btn_back.setOnClickListener(this);
    }

    private void initData() {
//        list = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            list.add(i + "人");
//        }
        recycleAdapter = new RecycleAdapter(list, getApplicationContext());
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
                        startActivity(new Intent(context, DetailsActivity.class));
                        break;
                }
            }

            @Override
            public void onItemLongClick(View v) {

            }
        });
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
