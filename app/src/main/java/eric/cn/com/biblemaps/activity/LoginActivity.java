package eric.cn.com.biblemaps.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import eric.cn.com.biblemaps.R;

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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

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

        //创建账号


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_right:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
        }
    }
}
