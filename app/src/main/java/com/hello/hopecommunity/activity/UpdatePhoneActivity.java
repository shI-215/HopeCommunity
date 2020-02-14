package com.hello.hopecommunity.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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

import com.hello.hopecommunity.App;
import com.hello.hopecommunity.R;
import com.hello.hopecommunity.model.UserModel;
import com.hello.hopecommunity.ui.MyListener;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class UpdatePhoneActivity extends AppCompatActivity implements View.OnClickListener, MyListener {
    private SharedPreferences preferences = App.context.getSharedPreferences(App.SHARED_PREFERENCES_NAME, App.context.MODE_PRIVATE);
    private String phone = preferences.getString("userTel", "");
    private UserModel userModel;

    private String newPhone;
    private Context context;
    private EditText edt_phone;
    private EditText edt_code;
    private TextView text_get;
    private Button btn_update_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_phone);
        SMSSDK.registerEventHandler(eventHandler);
        context = this;
        userModel = new UserModel(context);
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
        edt_code = (EditText) findViewById(R.id.edt_code);
        text_get = (TextView) findViewById(R.id.text_get);
        btn_update_phone = (Button) findViewById(R.id.btn_update_phone);

        text_get.setOnClickListener(this);
        btn_update_phone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_get:
                getCode();
                break;
            case R.id.btn_update_phone:
                submit();
                break;
        }
    }

    private void getCode() {
        // validate
        String phone = edt_phone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "手机号码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something
        SMSSDK.getVerificationCode(App.MOB_COUNTRY, phone);
    }

    private void submit() {
        // validate
        newPhone = edt_phone.getText().toString().trim();
        if (TextUtils.isEmpty(newPhone)) {
            Toast.makeText(this, "手机号码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String code = edt_code.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something
        SMSSDK.submitVerificationCode(App.MOB_COUNTRY, newPhone, code);
    }

    EventHandler eventHandler = new EventHandler() {
        public void afterEvent(int event, int result, Object data) {
            Message msg = new Message();
            msg.arg1 = event;
            msg.arg2 = result;
            msg.obj = data;
            new Handler(Looper.getMainLooper(), new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    int event = msg.arg1;
                    int result = msg.arg2;
                    Object data = msg.obj;
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            handler.sendEmptyMessage(1);
                        } else {
                            handler.sendEmptyMessage(3);
                            ((Throwable) data).printStackTrace();
                        }
                    } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            handler.sendEmptyMessage(4);
                        } else {
                            handler.sendEmptyMessage(3);
                            ((Throwable) data).printStackTrace();
                        }
                    }
                    return false;
                }
            }).sendMessage(msg);
        }
    };

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Toast.makeText(context, "发送验证码成功", Toast.LENGTH_SHORT).show();
                    edt_phone.clearFocus();//清除焦点，光标消失
                    edt_code.requestFocus();//获取焦点，光标出现
                    edt_code.setHint("请输入验证码");
                    countDownTimer.start();
                    break;
                case 2:
                    Toast.makeText(context, "操作频繁，请稍后再试", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(context, "验证码错误", Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    Toast.makeText(context, "验证码通过", Toast.LENGTH_SHORT).show();
                    userModel.alterPhone(phone, newPhone, (MyListener) context);
                    break;
            }
        }
    };

    //倒计时务
    private CountDownTimer countDownTimer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            text_get.setText(millisUntilFinished / 1000 + "");
            text_get.setClickable(false);
        }

        @Override
        public void onFinish() {
            text_get.setText("重新获取");
            text_get.setClickable(true);
        }
    };

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
