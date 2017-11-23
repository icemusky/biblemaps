package eric.cn.com.biblemaps.activity;

import android.content.Intent;
import android.nfc.Tag;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMGroupManager;
import com.hyphenate.chat.EMGroupOptions;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.List;

import eric.cn.com.biblemaps.R;

/**
 * Created by Eric on 2017/11/10.
 */

public class GroupActivity extends AppCompatActivity implements View.OnClickListener {
    private String Tag = "GroupActivity";

    private LinearLayout ll_back;
    private TextView tv_title;
    private TextView tv_right;
    private ListView lv_group;
    List<String> usernames;
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    lv_group.setAdapter(new ArrayAdapter<String>(GroupActivity.this, android.R.layout.simple_expandable_list_item_1, usernames));
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };
    List<EMGroup> grouplist;

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

        ll_back.setOnClickListener(this);
        tv_right.setVisibility(View.INVISIBLE);
        tv_title.setText("群聊");

        usernames=new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    grouplist = EMClient.getInstance().groupManager().getJoinedGroupsFromServer();//需异步处理
                    for (int i = 0; i < grouplist.size(); i++) {
                        usernames.add(grouplist.get(i).getGroupName());
                    }
                    Log.i(Tag, "群数量:" + grouplist.get(0).getGroupName());
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        }).start();
        lv_group.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(GroupActivity.this, ChatActivity.class);
                intent.putExtra(EaseConstant.EXTRA_USER_ID, grouplist.get(position).getGroupId());
                intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_GROUP);
                startActivity(intent);
            }
        });
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
        }
    }
}
