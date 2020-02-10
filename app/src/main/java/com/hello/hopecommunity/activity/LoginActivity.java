package com.hello.hopecommunity.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.hello.hopecommunity.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;

    private EditText edt_phone;
    private EditText edt_password;
    private Button btn_login;
    private TextView text_register;
    private TextView text_find;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setContentView(R.layout.activity_login);
        initView();
        context = this;
    }

    private void initView() {
        edt_phone = (EditText) findViewById(R.id.edt_phone);
        edt_password = (EditText) findViewById(R.id.edt_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        text_register = (TextView) findViewById(R.id.text_register);
        text_find = (TextView) findViewById(R.id.text_find);

        text_register.setOnClickListener(this);
        text_find.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.text_register://注册
                intent.putExtra("hopeType", 1);
                intent.setClass(context, MyActivity.class);
                startActivity(intent);
                break;
            case R.id.text_find://找回密码
                intent.putExtra("hopeType", 2);
                intent.setClass(context, MyActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_login:
                finish();
                break;
        }
    }

    private void submit() {
        // validate
        String phone = edt_phone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "手机号码", Toast.LENGTH_SHORT).show();
            return;
        }

        String password = edt_password.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "密码", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something


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
}
