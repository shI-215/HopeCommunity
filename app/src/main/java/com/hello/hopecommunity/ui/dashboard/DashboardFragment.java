package com.hello.hopecommunity.ui.dashboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.hello.hopecommunity.App;
import com.hello.hopecommunity.R;
import com.hello.hopecommunity.activity.ReleaseActivity;

public class DashboardFragment extends Fragment implements View.OnClickListener {
    private SharedPreferences preferences = App.context.getSharedPreferences(App.SHARED_PREFERENCES_NAME, App.context.MODE_PRIVATE);
    private int userId = preferences.getInt("userId", 0);

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final ImageView image_add = root.findViewById(R.id.image_add);
        image_add.setOnClickListener(this);
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
}