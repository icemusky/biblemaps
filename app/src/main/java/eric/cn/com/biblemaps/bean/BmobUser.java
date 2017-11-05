package eric.cn.com.biblemaps.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Eric on 2017/11/4.
 */

public class BmobUser extends BmobObject {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
