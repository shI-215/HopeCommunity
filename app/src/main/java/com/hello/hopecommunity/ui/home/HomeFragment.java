package com.hello.hopecommunity.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.hello.hopecommunity.bean.Image;
import com.hello.hopecommunity.model.ImageModel;
import com.hello.hopecommunity.ui.GlideImageLoader;
import com.hello.hopecommunity.ui.ListListener;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment implements ListListener {
    private ImageModel imageModel;

    private RecycleAdapter recycleAdapter;
    private List<Map<String, Object>> list = new ArrayList<>();

    private View root;
    private Banner banner_home;
    private RecyclerView recycler_view;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);
        banner_home = root.findViewById(R.id.banner_home);
        imageModel = new ImageModel();
        imageModel.getAll(this);
//        initData();
        List images = new ArrayList();
        images.add(R.drawable.one);
        images.add(R.drawable.two);
        banner_home.setImageLoader(new GlideImageLoader());
        banner_home.setImages(images);
        banner_home.start();
        return root;
    }

    private void initData() {
        recycleAdapter = new RecycleAdapter(list, getActivity());
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

    @Override
    public void onSuccess(List list) {
        this.list = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            Image image = (Image) list.get(i);
            map.put("title", image.getActive().getActName());
            map.put("image", image.getImgPath());
            this.list.add(map);
        }
        Log.v("List", this.list.toString());
        initData();
    }

    @Override
    public void onFaile(Object o) {

    }
}