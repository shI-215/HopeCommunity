package com.hello.hopecommunity.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.hello.hopecommunity.App;
import com.hello.hopecommunity.R;
import com.hello.hopecommunity.bean.User;
import com.hello.hopecommunity.model.UserModel;
import com.hello.hopecommunity.ui.MyListener;

public class UpdatePasswordActivity extends AppCompatActivity implements View.OnClickListener, MyListener {
    private SharedPreferences preferences = App.context.getSharedPreferences(App.SHARED_PREFERENCES_NAME, App.context.MODE_PRIVATE);
    private String phone = preferences.getString("userTel", "");
    private UserModel userModel;
    private Context context;

    private EditText edt_phone;
    private EditText edt_password;
    private EditText edt_password_new;
    private Button btn_update_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        userModel = new UserModel(this);
        context = this;
        initView();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        edt_phone = (EditText) findViewById(R.id.edt_phone);
        edt_phone.setText(phone);
        edt_password = (EditText) findViewById(R.id.edt_password);
        edt_password_new = (EditText) findViewById(R.id.edt_password_new);
        btn_update_password = (Button) findViewById(R.id.btn_update_password);

        btn_update_password.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update_password:
                submit();
                break;
        }
    }

    private void submit() {
        // validate
        String phone = edt_phone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "请输入手机号码", Toast.LENGTH_SHORT).show();
            return;
        }

        String password = edt_password.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        String newPassword = edt_password_new.getText().toString().trim();
        if (TextUtils.isEmpty(newPassword)) {
            Toast.makeText(this, "请输入新密码", Toast.LENGTH_SHORT).show();
            return;
        } else if (!TextUtils.equals(password, newPassword)) {
            Toast.makeText(this, "密码与新密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something
        User user = new User();
        user.setUserTel(phone);
        user.setUserPwd(password);
        userModel.find(user, this);
    }

    @Override
    public void onSuccess(Object object) {
        finish();
        Toast.makeText(context, object.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFaile(Object object) {
        Toast.makeText(context, object.toString(), Toast.LENGTH_SHORT).show();
    }
}
