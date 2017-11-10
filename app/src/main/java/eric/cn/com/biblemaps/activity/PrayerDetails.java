package eric.cn.com.biblemaps.activity;

import android.content.Intent;
import android.view.ViewGroup.LayoutParams;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;

import eric.cn.com.biblemaps.R;

/**
 * Created by Eric on 2017/11/3.
 */

public class PrayerDetails extends Activity {
    private LinearLayout ll_back;
    private LinearLayout ll_chat;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prayer_details);
        initView();
    }

    private void initView() {
        ll_back= (LinearLayout) findViewById(R.id.ll_back);
        ll_chat= (LinearLayout) findViewById(R.id.ll_chat);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ll_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrayerDetails.this, ChatActivity.class);
                intent.putExtra(EaseConstant.EXTRA_USER_ID, "15904006088");
                intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EMMessage.ChatType.Chat);
                startActivity(intent);
            }
        });
    }
}
