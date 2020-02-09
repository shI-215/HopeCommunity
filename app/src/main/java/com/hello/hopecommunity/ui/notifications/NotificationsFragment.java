package com.hello.hopecommunity.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.hello.hopecommunity.R;
import com.hello.hopecommunity.layout.ClickPoint;

public class NotificationsFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        final ClickPoint click_one = root.findViewById(R.id.click_one);
        click_one.setClickPoint("个人主页", 1);
        final ClickPoint click_two = root.findViewById(R.id.click_two);
        click_two.setClickPoint("信息修改", 1);
        final ClickPoint click_three = root.findViewById(R.id.click_three);
        click_three.setClickPoint("手机修改", 1);
        final ClickPoint click_four = root.findViewById(R.id.click_four);
        click_four.setClickPoint("密码修改", 1);

        return root;
    }
}