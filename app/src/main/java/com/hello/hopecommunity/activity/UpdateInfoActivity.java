package com.hello.hopecommunity.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.hello.hopecommunity.App;
import com.hello.hopecommunity.MainActivity;
import com.hello.hopecommunity.R;
import com.hello.hopecommunity.bean.User;
import com.hello.hopecommunity.model.UserModel;
import com.hello.hopecommunity.ui.MyListener;
import com.lljjcoder.citypickerview.widget.CityPicker;

import java.util.Date;

public class UpdateInfoActivity extends AppCompatActivity implements View.OnClickListener, MyListener {

    private SharedPreferences preferences = App.context.getSharedPreferences(App.SHARED_PREFERENCES_NAME, App.context.MODE_PRIVATE);
    private int userId = preferences.getInt("userId", 0);
    private String userName = preferences.getString("userName", "Hope");
    private String userSex = preferences.getString("userSex", "男");
    private String userAddress = preferences.getString("userAddress", "湖南省-长沙市-芙蓉区");
    private String userBirthday = preferences.getString("userBirthday", "2020-01-01");
    private UserModel userModel;
    private Context context;

    private TimePickerView pvTime;
    private CityPicker cityPicker;

    private EditText edt_name;
    private RadioButton radio_man;
    private RadioButton radio_woman;
    private TextView birthday;
    private TextView city;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.title_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setContentView(R.layout.activity_update_info);
        userModel = new UserModel(this);
        context = this;
        initView();
    }

    private void submit() {
        // validate
        String name = edt_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something
        String sex = "女";
        if (radio_man.isChecked()) {
            sex = "男";
        }
        String birth = birthday.getText().toString().trim();
        String address = city.getText().toString().trim();

        User user = new User();
        user.setUserId(userId);
        user.setUserName(name);
        user.setUserSex(sex);
        user.setUserBirthday(birth);
        user.setUserAddress(address);

        userModel.alterUser(user, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.birthday:
                pvTime.show();
                break;
            case R.id.city:
                cityPicker.show();
                break;
        }
    }

    private void initView() {
        edt_name = (EditText) findViewById(R.id.edt_name);
        edt_name.setText(userName);
        edt_name.setSelection(edt_name.getText().length());
        radio_man = (RadioButton) findViewById(R.id.radio_man);
        radio_woman = (RadioButton) findViewById(R.id.radio_woman);
        if (TextUtils.equals(userSex, "男")) {
            radio_man.setChecked(true);
        } else {
            radio_woman.setChecked(true);
        }
        birthday = (TextView) findViewById(R.id.birthday);
        birthday.setText(userBirthday);
        city = (TextView) findViewById(R.id.city);
        city.setText(userAddress);

        birthday.setOnClickListener(this);
        city.setOnClickListener(this);

        //时间选择器
        pvTime = new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                String mDate = (date.getYear() + 1900) + "-" + (date.getMonth() + 1) + "-" + date.getDate();
                birthday.setText(mDate);
            }
        }).setTitleText("日期选择").build();

        //城市选择
        cityPicker = new CityPicker.Builder(context)
                .title("地址选择")
                .titleBackgroundColor("#FFFFFF")
                .confirTextColor("#057DFF").cancelTextColor("#057DFF")
                .province("湖南省").city("长沙市").district("芙蓉区")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();

        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                String province = citySelected[0];    //省份
                String citys = citySelected[1];        //城市
                String district = citySelected[2];    //区县（如果设定了两级联动，那么该项返回空）
                //为TextView赋值
                city.setText(province.trim() + "-" + citys.trim() + "-" + district.trim());
            }
        });
        radio_woman = (RadioButton) findViewById(R.id.radio_woman);
        radio_woman.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.submit_item:
                submit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSuccess(Object object) {
        MainActivity.activity.finish();
        userModel.remove();
        finish();
        startActivity(new Intent(context, MainActivity.class));
        Toast.makeText(context, "修改成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFaile(Object object) {
        Toast.makeText(context, "修改失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                boolean res = hideKeyboard(v.getWindowToken());
                if (res) {
                    //隐藏了输入法，则不再分发事件
                    return true;
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    private boolean hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            return im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
        return false;
    }
}
