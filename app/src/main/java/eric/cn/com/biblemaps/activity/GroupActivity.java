package eric.cn.com.biblemaps.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroupManager;
import com.hyphenate.chat.EMGroupOptions;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.exceptions.HyphenateException;

import eric.cn.com.biblemaps.R;

/**
 * Created by Eric on 2017/11/10.
 */

public class GroupActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout ll_back;
    private TextView tv_title;
    private TextView tv_right;
    private ListView lv_group;
    private Button btn_submit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        initView();
    }

    private void initView() {
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_right = (TextView) findViewById(R.id.tv_right);
        lv_group = (ListView) findViewById(R.id.lv_group);
        btn_submit = (Button) findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(this);
        ll_back.setOnClickListener(this);
        tv_right.setVisibility(View.INVISIBLE);
        tv_title.setText("群聊");


    }

    /**
     * 创建公开群
     */
    private void createGroup() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMGroupOptions option = new EMGroupOptions();
                    option.maxUsers = 200;
                    option.style = EMGroupManager.EMGroupStyle.EMGroupStylePublicOpenJoin;
                    String[] name = {};
                    EMClient.getInstance().groupManager().createGroup("教会测试群1", "介绍", name, "", option);
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.btn_submit:
//                createGroup();
                Intent intent = new Intent(GroupActivity.this, ChatActivity.class);
                intent.putExtra(EaseConstant.EXTRA_USER_ID, "32673751957505");
                intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE,EaseConstant.CHATTYPE_GROUP);
                startActivity(intent);
                break;
        }
    }
}
