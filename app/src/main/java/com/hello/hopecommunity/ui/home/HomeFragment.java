package com.hello.hopecommunity.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.hello.hopecommunity.R;
import com.hello.hopecommunity.activity.DetailsActivity;
import com.hello.hopecommunity.adapter.RecycleAdapter;
import com.hello.hopecommunity.ui.GlideImageLoader;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecycleAdapter recycleAdapter;

    private List<String> list;

    private Banner banner_home;
    private RecyclerView recycler_view;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        banner_home = root.findViewById(R.id.banner_home);
        initData(root);
        List images = new ArrayList();
        images.add(R.drawable.one);
        images.add(R.drawable.two);
        banner_home.setImageLoader(new GlideImageLoader());
        banner_home.setImages(images);
        banner_home.start();
        return root;
    }

    private void initData(View root) {
        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i + "人");
        }
        recycleAdapter = new RecycleAdapter(list);
        recycler_view = root.findViewById(R.id.recycler_view);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        recycler_view.setLayoutManager(staggeredGridLayoutManager);
        recycler_view.setAdapter(recycleAdapter);

        recycleAdapter.setOnItemClickListener(new RecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, RecycleAdapter.ViewName viewName, int position) {
                switch (v.getId()) {
                    case R.id.card_image_item:
                        Toast.makeText(getContext(), (position + 1) + "", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getContext(), DetailsActivity.class));
                        break;
                }
            }

            @Override
            public void onItemLongClick(View v) {

            }
        });
    }

}