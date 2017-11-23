package eric.cn.com.biblemaps;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.baidu.mapapi.SDKInitializer;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;

import org.xutils.x;

import java.util.Iterator;
import java.util.List;

import cn.bmob.v3.Bmob;
import eric.cn.com.biblemaps.utils.SPUtils;


/**
 * Created by Administrator on 2017/7/20.
 */

public class MyApplication extends Application {
    //百度签名ak  和id
    public static String BAIDU_AK = "LGyDaizTbkNShNatcilGABWbhKWbXNWY";
    public static String BAIDU_GEOTABLE_ID = "172421";
    public static Context context;

    //账号名称
    public static String USER_NAME;
    //账号密码
    public static String USER_PASSWORD;
    //读写账号工具
    public static SPUtils spUtils;

    public static double latitude=0.0;//纬度
    public static double longitude=0.0;//经度

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
//        SDKInitializer.setCoordType(CoordType.BD09LL);
        x.Ext.init(this);
        x.Ext.setDebug(false);
        spUtils = new SPUtils();
        spUtils.GetSharedPreferences();

        /**
         * 初始化 环信SKD
         */
//        EMOptions options = new EMOptions();
//        // 默认添加好友时，是不需要验证的，改成需要验证
//        options.setAcceptInvitationAlways(true);
//        // ture代表可以自动登陆 环信
//        options.setAutoLogin(true);
//        //初始化
//        EMClient.getInstance().init(context, options);
//        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
//        EMClient.getInstance().setDebugMode(true);
        EaseUI.getInstance().init(this,null);
//        EMClient.getInstance().setDebugMode(true);
        /**
         * 初始化 后端云 后面是 key
         */
        Bmob.initialize(context, "4cff7784a2709f4fed3fab3be88fac10");


    }


}
