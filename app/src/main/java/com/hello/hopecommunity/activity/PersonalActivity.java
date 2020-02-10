package com.hello.hopecommunity.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.appbar.AppBarLayout;
import com.hello.hopecommunity.R;
import com.hello.hopecommunity.adapter.RecycleAdapter;

import java.util.ArrayList;
import java.util.List;

public class PersonalActivity extends AppCompatActivity implements View.OnClickListener {
    private RecycleAdapter recycleAdapter;

    private List<String> list;
    private ImageView image_background;
    private ImageView btn_back;
    private TextView text_user;
    private RecyclerView recycler_view;
    private ImageView image_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        setStatusBarTransparent();
        initView();
        initData();
        recycleAdapter = new RecycleAdapter(list);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        recycler_view.setLayoutManager(staggeredGridLayoutManager);
        recycler_view.setAdapter(recycleAdapter);
    }

    /**
     * 设置透明状态栏
     */
    private void setStatusBarTransparent() {
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
    }

    private void initView() {
        image_background = (ImageView) findViewById(R.id.image_background);
        btn_back = (ImageView) findViewById(R.id.btn_back);
        text_user = (TextView) findViewById(R.id.text_user);
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        image_user = (ImageView) findViewById(R.id.image_user);

        btn_back.setOnClickListener(this);
    }

    private void initData() {
        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i + "人");
        }
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
