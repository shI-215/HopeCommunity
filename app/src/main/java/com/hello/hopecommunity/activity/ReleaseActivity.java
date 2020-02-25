package com.hello.hopecommunity.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.hello.hopecommunity.R;
import com.hello.hopecommunity.bean.Active;
import com.hello.hopecommunity.model.ActiveModel;
import com.hello.hopecommunity.ui.FileListener;
import com.hello.hopecommunity.ui.MyListener;
import com.hello.hopecommunity.util.GlideLoader;
import com.jaiky.imagespickers.ImageConfig;
import com.jaiky.imagespickers.ImageSelector;
import com.jaiky.imagespickers.ImageSelectorActivity;

import java.util.ArrayList;
import java.util.List;

public class ReleaseActivity extends AppCompatActivity implements View.OnClickListener, MyListener, FileListener {
    private LocationClient mLocationClient;

    private ArrayList<String> path = new ArrayList<>();
    public static final int REQUEST_CODE = 123;
    private ImageConfig imageConfig;

    private ActiveModel activeModel;
    private int FILE_TYPE = 0;
    private boolean COVER_IMAGE = false;
    private boolean PROVE_IMAGE = false;

    private LinearLayout ll_cover;
    private EditText edt_title;
    private EditText edt_describe;
    private TextView text_GPS;
    private LinearLayout ll_prove;
    private ImageView image_prove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_release);
        initView();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        activeModel = new ActiveModel();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.title_menu, menu);
        return true;
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


    private void submit() {
        // validate
        String title = edt_title.getText().toString().trim();
        if (TextUtils.isEmpty(title)) {
            Toast.makeText(this, "标题不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String describe = edt_describe.getText().toString().trim();
        if (TextUtils.isEmpty(describe)) {
            Toast.makeText(this, "描述不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String str = text_GPS.getText().toString().trim();
        int index = str.indexOf("：");
        String address = str.substring(index + 1);
        Log.v("------------> ", address);
        if (TextUtils.isEmpty(address)) {
            Toast.makeText(this, "位置为空", Toast.LENGTH_SHORT).show();
            return;
        }

        if (COVER_IMAGE == false) {
            Toast.makeText(this, "请先添加图片", Toast.LENGTH_SHORT).show();
            return;
        } else if (PROVE_IMAGE == false) {
            Toast.makeText(this, "请先添加证明图片", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something
        Active active = new Active();
        active.setActName(title);
        active.setActDescribe(describe);
        active.setActAddress(address);
        activeModel.release(active, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_cover:
                getCover();
                break;
            case R.id.ll_prove:
                getProve();
                break;
        }
    }

    private void getCover() {
        imageConfig = new ImageConfig.Builder(new GlideLoader())
                .steepToolBarColor(getResources().getColor(R.color.red))
                .titleBgColor(getResources().getColor(R.color.red))
                .titleSubmitTextColor(getResources().getColor(R.color.white))
                .titleTextColor(getResources().getColor(R.color.white))
                .singleSelect()      // 开启单选   （默认为多选）
//                .crop(1, 1, 500, 500)        // 裁剪 (只有单选可裁剪)
                .showCamera()        // 开启拍照功能 （默认关闭）
                .setContainer(ll_cover, 1, true)    // 设置显示容器和删除按钮
                .requestCode(REQUEST_CODE)
                .build();
        ImageSelector.open(ReleaseActivity.this, imageConfig);
        FILE_TYPE = 1;
    }

    private void getProve() {
        imageConfig = new ImageConfig.Builder(new GlideLoader())
                .steepToolBarColor(getResources().getColor(R.color.red))
                .titleBgColor(getResources().getColor(R.color.red))
                .titleSubmitTextColor(getResources().getColor(R.color.white))
                .titleTextColor(getResources().getColor(R.color.white))
                .showCamera()        // 开启拍照功能 （默认关闭）
                .setContainer(ll_prove, 2, true)    // 设置显示容器和删除按钮
                .requestCode(REQUEST_CODE)
                .build();
        ImageSelector.open(ReleaseActivity.this, imageConfig);
        FILE_TYPE = 2;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
            Log.v("picyure", pathList.toString());
            path.clear();
            path.addAll(pathList);
            if (FILE_TYPE == 1) {
                activeModel.upLoadImage(path, this);
            } else if (FILE_TYPE == 2) {
                activeModel.upLoadImageMore(path, this);
            }
        }
    }

    @Override
    public void onSuccess(Object object) {
        Toast.makeText(getApplicationContext(), object.toString(), Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onFaile(Object object) {
        Toast.makeText(getApplicationContext(), object.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpload(Object o) {
        if (FILE_TYPE == 1) {
            COVER_IMAGE = true;
        } else if (FILE_TYPE == 2) {
            PROVE_IMAGE = true;
        }
        Toast.makeText(getApplicationContext(), o.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(Object o) {
        Toast.makeText(getApplicationContext(), o.toString(), Toast.LENGTH_SHORT).show();
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(final BDLocation bdLocation) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    text_GPS.setText("地址：" + bdLocation.getAddrStr());
                }
            });
        }
    }

    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(5000);
        option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);//GPS定位
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        // 判断GPS模块是否开启，如果没有则开启
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(ReleaseActivity.this, "请打开GPS", Toast.LENGTH_SHORT).show();
            final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("请打开GPS连接");
            dialog.setMessage("为方便获取您的位置信息，请先打开GPS");
            dialog.setPositiveButton("设置", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    // 转到手机设置界面，用户设置GPS
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    Toast.makeText(ReleaseActivity.this, "打开后直接点击返回键即可，若不打开返回下次将再次出现", Toast.LENGTH_SHORT).show();
                    startActivityForResult(intent, 0); // 设置完成后返回到原来的界面
                }
            });
            dialog.setNeutralButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    arg0.dismiss();
                }
            });
            dialog.show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                } else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }

    private void initView() {
        ll_cover = (LinearLayout) findViewById(R.id.ll_cover);
        ll_cover.setOnClickListener(this);
        edt_title = (EditText) findViewById(R.id.edt_title);
        edt_describe = (EditText) findViewById(R.id.edt_describe);
        text_GPS = (TextView) findViewById(R.id.text_GPS);
        ll_prove = (LinearLayout) findViewById(R.id.ll_prove);
        ll_prove.setOnClickListener(this);
        image_prove = (ImageView) findViewById(R.id.image_prove);

        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(ReleaseActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(ReleaseActivity.this,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(ReleaseActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(ReleaseActivity.this, permissions, 1);
        } else {
            requestLocation();
        }
    }
}
