package eric.cn.com.biblemaps.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import eric.cn.com.biblemaps.MyApplication;
import eric.cn.com.biblemaps.R;
import eric.cn.com.biblemaps.bean.BmobUser;
import eric.cn.com.biblemaps.utils.MyProgressDialog;
import eric.cn.com.biblemaps.utils.MD5Util;

/**
 * Created by Eric on 2017/11/4.
 */

public class RegisterActivity extends Activity implements View.OnClickListener {
    private LinearLayout ll_back;//左边返回健
    private TextView tv_title;//标题
    private TextView tv_right;//右边按钮

    private EditText et_phone;
    private EditText et_password;
    private EditText et_sub_password;
    private Button btn_submit;
    private MyProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_right = (TextView) findViewById(R.id.tv_right);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_password = (EditText) findViewById(R.id.et_password);
        et_sub_password = (EditText) findViewById(R.id.et_sub_password);
        btn_submit = (Button) findViewById(R.id.btn_submit);

        tv_title.setText("注册");
        ll_back.setOnClickListener(this);
        tv_right.setVisibility(View.INVISIBLE);
        btn_submit.setOnClickListener(this);


    }

    /**
     * 判断账号 密码
     */
    private void isPassWord() {
        if (!TextUtils.isEmpty(et_phone.getText())) {
            if (!TextUtils.isEmpty(et_phone.getText()) && !TextUtils.isEmpty(et_sub_password.getText())) {
                if (et_password.getText().toString().equals(et_sub_password.getText().toString())) {

                    getQueryNet();

                } else {
                    Toast.makeText(RegisterActivity.this, "两次密码不相同！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(RegisterActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(RegisterActivity.this, "账号不能为空！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 查询账号是否已经被注册
     */
    private void getQueryNet() {
        BmobQuery<BmobUser> bmobUserBmobQuery = new BmobQuery<BmobUser>();
        bmobUserBmobQuery.addWhereEqualTo("username", et_phone.getText().toString());
        bmobUserBmobQuery.findObjects(new FindListener<BmobUser>() {
            @Override
            public void done(List<BmobUser> list, BmobException e) {
                if (e == null) {
                    if (list.size() == 0) {
                        setRegisterNet();
                    } else {
                        Toast.makeText(RegisterActivity.this, "此账号已经被注册！", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

    /**
     * 注册用户
     */
    private void setRegisterNet() {
        dialog=new MyProgressDialog();
        dialog.ShowDialog(RegisterActivity.this,"账号注册中！！！");
        BmobUser bmobUser = new BmobUser();
        bmobUser.setUsername(et_phone.getText().toString());
        bmobUser.setPassword(MD5Util.getMD5String(et_password.getText().toString()));
        bmobUser.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    //环信注册
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                EMClient.getInstance().createAccount(et_phone.getText().toString(), MD5Util.getMD5String(et_password.getText().toString()));//同步方法
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(RegisterActivity.this, "创建账号成功！", Toast.LENGTH_SHORT).show();
                                        MyApplication.spUtils.SetSharedPreferences(et_phone.getText().toString(),MD5Util.getMD5String(et_password.getText().toString()));
                                        finish();
                                    }
                                });

                            } catch (HyphenateException e1) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(RegisterActivity.this, "创建账号失败！", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                e1.printStackTrace();
                            }
                            dialog.CloseDialog();
                        }
                    }).start();
                } else {

                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.btn_submit:
                isPassWord();
                break;
        }
    }
}
