package eric.cn.com.biblemaps.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.hyphenate.easeui.ui.EaseChatFragment;

import eric.cn.com.biblemaps.R;

/**
 * Created by Administrator on 2017/11/8.
 */

public class ChatActivity extends AppCompatActivity {
    private EaseChatFragment chatFragment;
    String toChatUsername;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
//        EaseChatFragment chatFragment=new EaseChatFragment();
//        chatFragment.setArguments(getIntent().getExtras());

        //use EaseChatFratFragment
        chatFragment = new EaseChatFragment();
        //pass parameters to chat fragment
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
    }
}
