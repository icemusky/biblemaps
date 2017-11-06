package eric.cn.com.biblemaps.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import eric.cn.com.biblemaps.MainActivity;
import eric.cn.com.biblemaps.MyApplication;
import eric.cn.com.biblemaps.R;
import eric.cn.com.biblemaps.bean.BmobUser;
import eric.cn.com.biblemaps.utils.MD5Util;
import eric.cn.com.biblemaps.utils.MyProgressDialog;

/**
 * Created by Eric on 2017/11/4.
 */

public class LoginActivity extends Activity implements View.OnClickListener {

    private LinearLayout ll_back;//左边返回健
    private TextView tv_title;//标题
    private TextView tv_right;//右边按钮

    private EditText et_phone;
    private EditText et_password;
    private Button btn_submit;

    private MyProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        //自动登陆
        if (!MyApplication.USER_NAME.equals("")) {
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (!MyApplication.USER_NAME.equals("")) {
            et_phone.setText(MyApplication.USER_NAME);
        }
    }


    private void initView() {
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_right = (TextView) findViewById(R.id.tv_right);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_submit = (Button) findViewById(R.id.btn_submit);

        tv_title.setText("登陆");
        ll_back.setVisibility(View.INVISIBLE);
        tv_right.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
    }

    /**
     * 环信登陆
     */
    private void getLoginNet() {
        dialog = new MyProgressDialog();
        dialog.ShowDialog(LoginActivity.this, "账号登陆中！！！");
        EMClient.getInstance().login(et_phone.getText().toString(), MD5Util.getMD5String(et_password.getText().toString()), new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.CloseDialog();
                        EMClient.getInstance().groupManager().loadAllGroups();
                        EMClient.getInstance().chatManager().loadAllConversations();
                        Toast.makeText(LoginActivity.this, "账号登陆成功！", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        finish();
                    }
                });


            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this, "登陆服务器失败！", Toast.LENGTH_SHORT).show();
                        dialog.CloseDialog();
                    }
                });

            }
        });
    }

    /**
     * 查询账号和密码是否正确
     */
    private void getQueryNet() {

        BmobQuery<BmobUser> queryUsername = new BmobQuery<BmobUser>();
        queryUsername.addWhereEqualTo("username", et_phone.getText().toString());
        BmobQuery<BmobUser> queryPassword = new BmobQuery<BmobUser>();
        queryPassword.addWhereEqualTo("password", MD5Util.getMD5String(et_password.getText().toString()));
        List<BmobQuery<BmobUser>> andQuerys = new ArrayList<BmobQuery<BmobUser>>();
        andQuerys.add(queryUsername);
        andQuerys.add(queryPassword);
        BmobQuery<BmobUser> query = new BmobQuery<BmobUser>();
        query.and(andQuerys);
        query.findObjects(new FindListener<BmobUser>() {
            @Override
            public void done(List<BmobUser> list, BmobException e) {
                Log.i("LoginActivity", list.size() + "查看数量");
                if (e == null) {
                    if (list.size() == 1) {
                        getLoginNet();
                    } else {
                        Toast.makeText(LoginActivity.this, "账号密码错误！", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


    }

    /**
     * 判断账号 密码
     */
    private void isPassWord() {
        if (!TextUtils.isEmpty(et_phone.getText())) {
            if (!TextUtils.isEmpty(et_phone.getText())) {
                getQueryNet();
            } else {
                Toast.makeText(LoginActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(LoginActivity.this, "账号不能为空！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_right:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.btn_submit:
                isPassWord();
                break;
        }
    }
}
