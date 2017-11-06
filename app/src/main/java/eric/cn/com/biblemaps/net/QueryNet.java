package eric.cn.com.biblemaps.net;

import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import eric.cn.com.biblemaps.activity.RegisterActivity;
import eric.cn.com.biblemaps.bean.BmobUser;

/**
 * Created by Administrator on 2017/11/6.
 */

public class QueryNet {
    /**
     * 返回为true 是查询到了，false 是没有查询到
     */
    private boolean isType;

    public boolean getQueryNet(String username) {
        BmobQuery<BmobUser> bmobUserBmobQuery = new BmobQuery<BmobUser>();
        bmobUserBmobQuery.addWhereEqualTo("username", username);
        bmobUserBmobQuery.findObjects(new FindListener<BmobUser>() {
            @Override
            public void done(List<BmobUser> list, BmobException e) {
                if (e == null) {
                    if (list.size() == 0) {
                        isType = false;
                    } else {
                        isType = true;
                    }
                }
            }
        });

        return isType;
    }
}
