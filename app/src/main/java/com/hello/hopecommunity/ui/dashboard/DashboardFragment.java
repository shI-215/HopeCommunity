package com.hello.hopecommunity.ui.dashboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.hello.hopecommunity.App;
import com.hello.hopecommunity.R;
import com.hello.hopecommunity.activity.DetailsActivity;
import com.hello.hopecommunity.activity.ReleaseActivity;
import com.hello.hopecommunity.adapter.RecycleAdapter;
import com.hello.hopecommunity.bean.Image;
import com.hello.hopecommunity.model.ImageModel;
import com.hello.hopecommunity.ui.ListListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardFragment extends Fragment implements View.OnClickListener, ListListener {
    private SharedPreferences preferences = App.context.getSharedPreferences(App.SHARED_PREFERENCES_NAME, App.context.MODE_PRIVATE);
    private int userId = preferences.getInt("userId", 0);

    private ImageModel imageModel;
    private RecycleAdapter recycleAdapter;
    private List<Map<String, Object>> list = new ArrayList<>();

    private View root;
    private RecyclerView recycler_view;
    private ImageView image_add;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        if (userId != 0) {
            imageModel = new ImageModel();
            imageModel.getMyImage(userId, this);
        } else {
            initData();
        }
        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_add:
                if (userId == 0) {
                    Toast.makeText(getContext(), "请先登录之后再操作", Toast.LENGTH_LONG).show();
                } else {
                    startActivity(new Intent(getContext(), ReleaseActivity.class));
                }
                break;
        }
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
        Toast.makeText(getContext(), object.toString(), Toast.LENGTH_LONG).show();
    }

    private void initData() {
        recycleAdapter = new RecycleAdapter(list, getActivity());
        recycler_view = root.findViewById(R.id.recycler_view);
        image_add = root.findViewById(R.id.image_add);
        image_add.setOnClickListener(this);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        recycler_view.setLayoutManager(staggeredGridLayoutManager);
        recycler_view.setAdapter(recycleAdapter);

        recycleAdapter.setOnItemClickListener(new RecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, RecycleAdapter.ViewName viewName, int position) {
                switch (v.getId()) {
                    case R.id.card_image_item:
                        Toast.makeText(getContext(), list.get(position).get("id") + "", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent();
                        intent.putExtra("actId", (Integer) list.get(position).get("id"));
                        intent.setClass(getContext(), DetailsActivity.class);
                        startActivity(intent);
                        break;
                }
            }

            @Override
            public void onItemLongClick(View v) {

            }
        });
    }
}