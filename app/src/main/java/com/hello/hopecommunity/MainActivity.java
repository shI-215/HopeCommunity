package com.hello.hopecommunity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hello.hopecommunity.bean.User;
import com.hello.hopecommunity.model.UserModel;
import com.hello.hopecommunity.ui.MyListener;

public class MainActivity extends AppCompatActivity implements MyListener {
    private SharedPreferences preferences = App.context.getSharedPreferences(App.SHARED_PREFERENCES_NAME, App.context.MODE_PRIVATE);
    private String userTel = preferences.getString("userTel", "");
    private String userPwd = preferences.getString("userPwd", "");
    private UserModel userModel;
    public static AppCompatActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userModel = new UserModel(this);
        Log.v("MianActivity", userTel + "<----->" + userPwd);
        if (!TextUtils.isEmpty(userTel) && !TextUtils.isEmpty(userPwd)) {
            User user = new User();
            user.setUserTel(userTel);
            user.setUserPwd(userPwd);
            userModel.login(user, this);
        }
        activity = this;
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public void onSuccess(Object object) {

    }

    @Override
    public void onFaile(Object object) {

    }
}
