package eric.cn.com.biblemaps.net;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import eric.cn.com.biblemaps.MyApplication;
import eric.cn.com.biblemaps.bean.PoiCreateBean;
import eric.cn.com.biblemaps.utils.HttpCallBack;
import eric.cn.com.biblemaps.utils.interfaces.IAsyncHttpComplete;

/**
 * Created by Administrator on 2017/11/23.
 */

public class PoiCreateNet {
    /**
     * 百度地图POI 创建 数据点
     *
     * @param latitude
     * @param longitude
     * @param shop_type
     * @param title
     */
    private boolean isReturn;

    public boolean Create(String latitude, String longitude, String shop_type, String title) {

        RequestParams params = new RequestParams("http://api.map.baidu.com/geodata/v3/poi/create");
        params.addBodyParameter("latitude", latitude);//用户上传的纬度
        params.addBodyParameter("longitude", longitude);//用户上传的经度
        params.addBodyParameter("coord_type", "1");//GPS经纬度坐标
        params.addBodyParameter("geotable_id", MyApplication.BAIDU_GEOTABLE_ID);//创建数据的对应数据表id
        params.addBodyParameter("ak", MyApplication.BAIDU_AK);//用户的访问权限key
        params.addBodyParameter("shop_type", shop_type);//状态类型 1为祷告 3为教会
        params.addBodyParameter("title", title);//标题
        x.http().post(params, new HttpCallBack<PoiCreateBean>(new IAsyncHttpComplete<PoiCreateBean>() {
            @Override
            public void onSuccess(PoiCreateBean result) {
                if (result.getStatus() == 0) {
                    isReturn = true;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public boolean onCache(String result) {
                return false;
            }
        }));
        return isReturn;
    }
}
