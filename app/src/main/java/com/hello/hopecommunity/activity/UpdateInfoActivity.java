package com.hello.hopecommunity.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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
import com.hello.hopecommunity.R;
import com.lljjcoder.citypickerview.widget.CityPicker;

import java.util.Date;

public class UpdateInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private TimePickerView pvTime;
    private CityPicker cityPicker;
    private Context context;

    private EditText edt_name;
    private RadioButton radio_man;
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
        context = this;
        initView();
    }

    private void submit() {
        // validate
        String name = edt_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "name不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something


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
        edt_name.setSelection(edt_name.getText().length());
        radio_man = (RadioButton) findViewById(R.id.radio_man);
        birthday = (TextView) findViewById(R.id.birthday);
        city = (TextView) findViewById(R.id.city);

        birthday.setOnClickListener(this);
        city.setOnClickListener(this);

        //时间选择器
        pvTime = new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                String mDate = (date.getYear() + 1900) + "-" + (date.getMonth() + 1) + "-" + date.getDate();
                birthday.setText(mDate);
                Toast.makeText(context, mDate, Toast.LENGTH_SHORT).show();
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
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.submit_item:
                Toast.makeText(context, "提交", Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
