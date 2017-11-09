package eric.cn.com.biblemaps;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMChatManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import eric.cn.com.biblemaps.activity.PrayerDetails;
import eric.cn.com.biblemaps.bean.PoiMessageEvent;
import eric.cn.com.biblemaps.bean.PoiScanBean;
import eric.cn.com.biblemaps.net.PoiScanNet;
import eric.cn.com.biblemaps.utils.BaiDuMapCustomFile;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";
    MapView mMapView = null;
    BaiduMap mBaiduMap;

    // 定位相关
    LocationClient mLocClient;
    boolean isFirstLoc = true; // 是否首次定位
    private MyLocationData locData;
    private Double lastX = 0.0;
    private int mCurrentDirection = 0;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private float mCurrentAccracy;
    public MyLocationListenner myListener = new MyLocationListenner();
    private MyLocationConfiguration.LocationMode mCurrentMode;
    BitmapDescriptor mCurrentMarker;
    private List<PoiScanBean> poi_data = new ArrayList<>();
    private InfoWindow mInfoWindow;
    private ProgressDialog mDialog;

    // 提供三种样式模板："custom_config_blue.txt"，"custom_config_dark.txt"，"custom_config_midnightblue.txt"
    private static String PATH = "custom_config.txt";
    private int position=0;//获取当前位置
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        initView();
        //百度地图poi检索
        PoiScanNet.PoiScanNet("41.822981", "123.442725", "1000");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<String> usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();

                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }).start();



    }

    private void initView() {
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();

        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
//        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);//获取传感器管理服务
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        mMapView.showZoomControls(false);
        //旋转 调整俯仰角 overlook
        float overlook = mBaiduMap.getMapStatus().overlook;
        MapStatus overStatus = new MapStatus.Builder().overlook(overlook - 40).build();
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(overStatus));
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
        // 设置个性化地图config文件路径
        BaiDuMapCustomFile.setMapCustomFile(MainActivity.this,PATH);
        mMapView = new MapView(this, new BaiduMapOptions());
        MapView.setMapCustomEnable(true);

    }


    /**
     * 按返回按键退出左边侧滑
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 侧滑 控件
     *
     * @param item
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Toast.makeText(MainActivity.this, "求祷", Toast.LENGTH_SHORT).show();
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Toast.makeText(MainActivity.this, "代祷", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_slideshow) {
            Toast.makeText(MainActivity.this, "好友", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_manage) {
            Toast.makeText(MainActivity.this, "群", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_share) {
            Toast.makeText(MainActivity.this, "注销", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_send) {
            Toast.makeText(MainActivity.this, "退出", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        MapView.setMapCustomEnable(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();

    }

    @Override
    protected void onStart() {
        super.onStart();
        // 适配android M，检查权限
        List<String> permissions = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && isNeedRequestPermissions(permissions)) {
            requestPermissions(permissions.toArray(new String[permissions.size()]), 0);
        }
    }
    private void addPermission(List<String> permissionsList, String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
        }
    }
    private boolean isNeedRequestPermissions(List<String> permissions) {
        // 定位精确位置
        addPermission(permissions, Manifest.permission.ACCESS_FINE_LOCATION);
        addPermission(permissions, Manifest.permission.ACCESS_COARSE_LOCATION);
        // 存储权限
        addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        // 读取手机状态
        addPermission(permissions, Manifest.permission.READ_PHONE_STATE);
        //相机
        addPermission(permissions,Manifest.permission.CAMERA);
        //录音
        addPermission(permissions,Manifest.permission.RECORD_AUDIO);

        return permissions.size() > 0;
    }
    @Override
    protected void onStop() {
        //取消注册传感器监听
//        mSensorManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }


    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            mCurrentLat = location.getLatitude();
            mCurrentLon = location.getLongitude();
            mCurrentAccracy = location.getRadius();
            locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }

        }


        public void onConnectHotSpotMessage(String s, int i) {

        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void PoiScan(PoiMessageEvent messageEvent) {

        if (messageEvent.getBean().getSize() > 0) {
            poi_data.add(messageEvent.getBean());
        }

        for (int i = 0; i < poi_data.get(0).getSize(); i++) {
//        int i=0;
            final LatLng point = new LatLng(poi_data.get(0).getContents().get(i).getLocation().get(1), poi_data.get(0).getContents().get(i).getLocation().get(0));
            Log.i(TAG, "输出坐标：》》》》》》》》" + poi_data.get(0).getContents().get(i).getLocation().get(1) + poi_data.get(0).getContents().get(i).getLocation().get(0));
            BitmapDescriptor bitmap = null;
            if (poi_data.get(0).getContents().get(i).getShop_type().equals("3")){
                //类型为3 是教会
                //构建Marker图标
                bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.icon_jiaotang);
                Log.i(TAG,"绘制图标：教会");
            }if (poi_data.get(0).getContents().get(i).getShop_type().equals("1")){
                //类型为1  是祈祷
                //构建Marker图标
                bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.icon_qidao);
                Log.i(TAG,"绘制图标：祷告");
            }
            
            //构建MarkerOption，用于在地图上添加Marker
            OverlayOptions option = new MarkerOptions()
                    .position(point)
                    .icon(bitmap);

            mBaiduMap.addOverlay(option);
            mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {

                    Button button = new Button(getApplicationContext());
                    button.setBackgroundResource(R.drawable.popup);
                    button.setTextColor(Color.BLACK);
                    button.setWidth(300);

                    LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                    View view = layoutInflater.inflate(R.layout.view_dialog_map, null);
                    TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
                    LatLng ll = marker.getPosition();
                    for (int i = 0; i <= poi_data.size(); i++) {
                        Log.i("MainActivity", "判断Location：" + marker.getPosition().latitude + "数据中的Location：" + poi_data.get(0).getContents().get(i).getLocation().get(1));
                        if (marker.getPosition().latitude == poi_data.get(0).getContents().get(i).getLocation().get(1) && marker.getPosition().longitude == poi_data.get(0).getContents().get(i).getLocation().get(0)) {
//                            button.setText(poi_data.get(0).getContents().get(i).getLocation()+"");
//                            Log.i("MainActivity", poi_data.get(0).getContents().get(i).getLocation()+"设置参数");
//                            tv_title.setText(poi_data.get(0).getContents().get(i).getLocation() + "");
//                            Toast.makeText(MainActivity.this,"单击坐标点："+poi_data.get(0).getContents().get(i).getLocation(),Toast.LENGTH_SHORT).show();
                            position=i;//赋值给当前坐标值
                        }
                    }

//                    linearLayout.setBackgroundResource(R.drawable.popup);
                    mInfoWindow = new InfoWindow(view, ll, -100);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            Toast.makeText(MainActivity.this,"页面跳转："+poi_data.get(0).getContents().get(position).getLocation(),Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this, PrayerDetails.class));
                        }
                    });
                    mBaiduMap.showInfoWindow(mInfoWindow);
                    return false;
                }
            });
        }

    }





}
