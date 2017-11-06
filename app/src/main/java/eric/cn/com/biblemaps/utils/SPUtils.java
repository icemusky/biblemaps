package eric.cn.com.biblemaps.utils;

import android.content.Context;
import android.content.SharedPreferences;

import eric.cn.com.biblemaps.MyApplication;

/**
 * Created by Administrator on 2017/11/6.
 */

public class SPUtils {
    //写入SP 文件存储
    public void SetSharedPreferences(String username){
        SharedPreferences sp = MyApplication.context.getSharedPreferences("username", MyApplication.context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("UserName",username);
        editor.commit();
    }
    //读取SP 文件存储
    public void GetSharedPreferences(){
        SharedPreferences preferences=MyApplication.context.getSharedPreferences("username", MyApplication.context.MODE_PRIVATE);
        MyApplication.USER_NAME=preferences.getString("UserName", "");
    }
    //清空SP 文件存储
    public void ClearSharedPreferences(){
        SharedPreferences sp = MyApplication.context.getSharedPreferences("username", MyApplication.context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("UserName","");
        editor.commit();
        MyApplication.USER_NAME="";
    }
}
