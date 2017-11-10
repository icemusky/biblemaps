package eric.cn.com.biblemaps.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

import eric.cn.com.biblemaps.R;

/**
 * Created by Eric on 2017/11/10.
 */

public class FriendActivity extends AppCompatActivity implements View.OnClickListener {
    private String Tag = "FriendActivity";
    private LinearLayout ll_back;
    private TextView tv_title;
    private TextView tv_right;
    private EditText et_id;
    private Button btn_submit;
    private ListView lv_data;

    List<String> usernames;

    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    lv_data.setAdapter(new ArrayAdapter<String>(FriendActivity.this, android.R.layout.simple_expandable_list_item_1, usernames));
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        initView();
    }

    private void initView() {
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_right = (TextView) findViewById(R.id.tv_right);
        et_id = (EditText) findViewById(R.id.et_id);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        lv_data = (ListView) findViewById(R.id.lv_data);

        ll_back.setOnClickListener(this);
        tv_right.setVisibility(View.INVISIBLE);
        tv_title.setText("好友");


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();
                    Log.i(Tag, "好友数量:" + usernames.size());
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
                Message message = new Message();
                message.what = 1;
                mHandler.sendMessage(message);
            }
        });
        thread.start();
        et_id.setOnClickListener(this);
        btn_submit.setOnClickListener(this);

        lv_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FriendActivity.this, ChatActivity.class);
                intent.putExtra(EaseConstant.EXTRA_USER_ID, usernames.get(position));
                intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EMMessage.ChatType.Chat);
                startActivity(intent);
            }
        });

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    EMClient.getInstance().contactManager().acceptInvitation("15904006087");
//                } catch (HyphenateException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.btn_submit:
                addFriend();
                break;
        }
    }

    //添加好友
    private void addFriend() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().contactManager().addContact(et_id.getText().toString(), "添加好友");
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
