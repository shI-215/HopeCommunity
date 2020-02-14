package com.hello.hopecommunity.ui.notifications;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.hello.hopecommunity.App;
import com.hello.hopecommunity.MainActivity;
import com.hello.hopecommunity.R;
import com.hello.hopecommunity.activity.LoginActivity;
import com.hello.hopecommunity.activity.PersonalActivity;
import com.hello.hopecommunity.activity.UpdateInfoActivity;
import com.hello.hopecommunity.activity.UpdatePasswordActivity;
import com.hello.hopecommunity.activity.UpdatePhoneActivity;
import com.hello.hopecommunity.layout.ClickPoint;
import com.hello.hopecommunity.model.UserModel;
import com.hello.hopecommunity.ui.MyListener;

public class NotificationsFragment extends Fragment implements View.OnClickListener, MyListener {
    private SharedPreferences preferences = App.context.getSharedPreferences(App.SHARED_PREFERENCES_NAME, App.context.MODE_PRIVATE);
    private int userId = preferences.getInt("userId", 0);
    private UserModel userModel;

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
        userModel = new UserModel(getContext());
        return root;
    }

    @Override
    public void onClick(View v) {
        if (userId != 0) {
            switch (v.getId()) {
                case R.id.click_one:
                    startActivity(new Intent(getContext(), PersonalActivity.class));
                    break;
                case R.id.click_two:
                    startActivity(new Intent(getContext(), UpdateInfoActivity.class));
                    break;
                case R.id.click_three:
                    startActivity(new Intent(getContext(), UpdatePhoneActivity.class));
                    break;
                case R.id.click_four:
                    startActivity(new Intent(getContext(), UpdatePasswordActivity.class));
                    break;
                case R.id.click_loginout:
                    userModel.loginOut(this);
                    break;
            }
        } else {
            Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
        }
    }

    private void init(View root) {
        image_user = root.findViewById(R.id.image_user);
        text_user = root.findViewById(R.id.text_user);
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

        String userName = preferences.getString("userName", "");
        String userAutograph = preferences.getString("userAutograph", "");
        if (!TextUtils.isEmpty(userName)) {
            text_user.setText(userName);
            text_user.setClickable(false);
            text_signature.setText(userAutograph);
            text_signature.setVisibility(View.VISIBLE);
            click_loginout.setVisibility(View.VISIBLE);
            click_loginout.setOnClickListener(this);
        } else {
            text_user.setText("登录/注册");
            text_user.setClickable(true);
            text_user.setOnClickListener(this);
            text_signature.setVisibility(View.GONE);
            click_loginout.setVisibility(View.GONE);
        }
        text_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
    }

    @Override
    public void onSuccess(Object object) {
//        text_user.setText("登录/注册");
//        text_user.setClickable(true);
//        text_signature.setVisibility(View.GONE);
//        click_loginout.setVisibility(View.GONE);
        MainActivity.activity.finish();
        startActivity(new Intent(getContext(), MainActivity.class));
        Toast.makeText(getContext(), object.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFaile(Object object) {

    }
}
