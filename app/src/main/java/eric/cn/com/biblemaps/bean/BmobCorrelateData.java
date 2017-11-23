package eric.cn.com.biblemaps.bean;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;

/**   关联百度POI 和 环信的 重要数据
 * Created by Administrator on 2017/11/23.
 */

public class BmobCorrelateData extends BmobObject{
    private String baidu_poi_id;//百度点ID
    private String huanxin_id;//环信ID
    private String username;//用户名称
    private String context;//祷告内容

    public String getBaidu_poi_id() {
        return baidu_poi_id;
    }

    public void setBaidu_poi_id(String baidu_poi_id) {
        this.baidu_poi_id = baidu_poi_id;
    }

    public String getHuanxin_id() {
        return huanxin_id;
    }

    public void setHuanxin_id(String huanxin_id) {
        this.huanxin_id = huanxin_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
