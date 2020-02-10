package com.hello.hopecommunity.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.hello.hopecommunity.R;
import com.hello.hopecommunity.activity.LoginActivity;
import com.hello.hopecommunity.activity.PersonalActivity;
import com.hello.hopecommunity.activity.UpdateInfoActivity;
import com.hello.hopecommunity.layout.ClickPoint;

public class NotificationsFragment extends Fragment implements View.OnClickListener {
    private ImageView image_user;
    private TextView text_user;
    private TextView text_signature;
    private ClickPoint click_one;
    private ClickPoint click_two;
    private ClickPoint click_three;
    private ClickPoint click_four;
    private ClickPoint click_loginout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        init(root);
        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.click_one:
                startActivity(new Intent(getContext(), PersonalActivity.class));
                break;
            case R.id.click_two:
                startActivity(new Intent(getContext(), UpdateInfoActivity.class));
                break;
            case R.id.text_user:
                startActivity(new Intent(getContext(), LoginActivity.class));
                break;
            case R.id.click_loginout:
                text_user.setText("登录/注册");
                text_user.setClickable(true);
                text_signature.setVisibility(View.GONE);
                click_loginout.setVisibility(View.GONE);
                break;
        }
    }

    private void init(View root) {
        image_user = root.findViewById(R.id.image_user);
        text_user = root.findViewById(R.id.text_user);
        text_user.setOnClickListener(this);
        text_user.setClickable(false);
        text_signature = root.findViewById(R.id.text_signature);

        click_one = root.findViewById(R.id.click_one);
        click_one.setClickPoint("个人主页", 1);
        click_one.setOnClickListener(this);
        click_two = root.findViewById(R.id.click_two);
        click_two.setClickPoint("信息修改", 1);
        click_two.setOnClickListener(this);
        click_three = root.findViewById(R.id.click_three);
        click_three.setClickPoint("手机修改", 1);
        click_three.setOnClickListener(this);
        click_four = root.findViewById(R.id.click_four);
        click_four.setClickPoint("密码修改", 1);
        click_four.setOnClickListener(this);
        click_loginout = root.findViewById(R.id.click_loginout);
        click_loginout.setClickPoint("退出登录", 0);
        click_loginout.setOnClickListener(this);
    }
}
