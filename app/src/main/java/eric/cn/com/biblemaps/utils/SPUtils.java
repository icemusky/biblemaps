package eric.cn.com.biblemaps.utils;

import android.content.Context;
import android.content.SharedPreferences;

import eric.cn.com.biblemaps.MyApplication;

/**
 * Created by Administrator on 2017/11/6.
 */

public class SPUtils {
    //写入SP 文件存储
    public void SetSharedPreferences(String username, String password) {
        SharedPreferences sp = MyApplication.context.getSharedPreferences("username", MyApplication.context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("UserName", username);
        editor.putString("UserPassWord", password);
        editor.commit();
        MyApplication.USER_NAME = username;
        MyApplication.USER_PASSWORD = password;
    }

    //读取SP 文件存储
    public void GetSharedPreferences() {
        SharedPreferences preferences = MyApplication.context.getSharedPreferences("username", MyApplication.context.MODE_PRIVATE);
        MyApplication.USER_NAME = preferences.getString("UserName", "");
        MyApplication.USER_PASSWORD = preferences.getString("UserPassWord", "");
    }

    //清空SP 文件存储
    public void ClearSharedPreferences() {
        SharedPreferences sp = MyApplication.context.getSharedPreferences("username", MyApplication.context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("UserName", "");
        editor.putString("UserPassWord", "");
        editor.commit();
        MyApplication.USER_NAME = "";
        MyApplication.USER_PASSWORD = "";
    }
}
