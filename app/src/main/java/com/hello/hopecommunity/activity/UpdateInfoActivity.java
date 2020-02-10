package com.hello.hopecommunity.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hello.hopecommunity.App;
import com.hello.hopecommunity.R;

import java.util.ArrayList;

import zuo.biao.library.ui.DatePickerWindow;
import zuo.biao.library.ui.PlacePickerWindow;
import zuo.biao.library.util.Log;

public class UpdateInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;

    private EditText edt_name;
    private RadioButton radio_man;
    private TextView birthday;
    private TextView city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);
        initView();
        context = this;
    }

    private void initView() {
        edt_name = (EditText) findViewById(R.id.edt_name);
        radio_man = (RadioButton) findViewById(R.id.radio_man);
        birthday = (TextView) findViewById(R.id.birthday);
        city = (TextView) findViewById(R.id.city);

        birthday.setOnClickListener(this);
        city.setOnClickListener(this);
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
        Intent intent;
        switch (v.getId()) {
            case R.id.birthday:
                intent = new Intent(context, DatePickerWindow.class);
                startActivityForResult(intent, App.DATE_PICKER_CODE);
                break;
            case R.id.city:
                intent = new Intent(context, PlacePickerWindow.class);
                startActivityForResult(intent, 2);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case App.DATE_PICKER_CODE:
                if (data != null) {
                    ArrayList<Integer> list = data.getIntegerArrayListExtra(DatePickerWindow.RESULT_DATE_DETAIL_LIST);
                    if (list != null && list.size() >= 3) {
                        birthday.setText(list.get(0) + "-" + (list.get(1) + 1) + "-" + list.get(2));
                    }
                }
                break;
            case 2:
                if (data != null) {
                    Log.v("city", data.getStringExtra(PlacePickerWindow.RESULT_PLACE_LIST));
                    ArrayList<Integer> list = data.getIntegerArrayListExtra(PlacePickerWindow.RESULT_PLACE_LIST);
                    if (list != null && list.size() >= 3) {
                        city.setText(list.toString());
                    }
                }
                break;
        }
    }
}
