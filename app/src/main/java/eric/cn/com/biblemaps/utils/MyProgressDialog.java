package eric.cn.com.biblemaps.utils;

import android.content.Context;

/**
 * Created by Administrator on 2017/11/6.
 */

public class MyProgressDialog {
    private android.app.ProgressDialog mDialog;
    public void ShowDialog(Context context,String text){
        mDialog = new android.app.ProgressDialog(context);
        mDialog.setMessage(text);
        mDialog.show();
    }
    public void CloseDialog(){
        mDialog.dismiss();
    }
}
