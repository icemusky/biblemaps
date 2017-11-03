package eric.cn.com.biblemaps.activity;

import android.view.ViewGroup.LayoutParams;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import eric.cn.com.biblemaps.R;

/**
 * Created by Administrator on 2017/11/3.
 */

public class PrayerDetails extends Activity {
    private LinearLayout ll_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prayer_details);
        initView();
    }

    private void initView() {
        ll_back= (LinearLayout) findViewById(R.id.ll_back);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
