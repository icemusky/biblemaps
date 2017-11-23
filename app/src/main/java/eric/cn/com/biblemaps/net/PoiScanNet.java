package eric.cn.com.biblemaps.net;

import org.greenrobot.eventbus.EventBus;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import eric.cn.com.biblemaps.bean.PoiMessageEvent;
import eric.cn.com.biblemaps.bean.PoiScanBean;
import eric.cn.com.biblemaps.MyApplication;
import eric.cn.com.biblemaps.utils.HttpCallBack;
import eric.cn.com.biblemaps.utils.interfaces.IAsyncHttpComplete;

/**
 * Created by Administrator on 2017/8/4.
 */

public class PoiScanNet {

    /**
     * POI 周边检索
     */
    public  List PoiScanNet(String latitude,String longitude,String radius){
        final List<PoiScanBean> data=new ArrayList<>();
        RequestParams params=new RequestParams("http://api.map.baidu.com/geosearch/v3/nearby?ak="+ MyApplication.BAIDU_AK+"&geotable_id="+MyApplication.BAIDU_GEOTABLE_ID+"&location="+longitude+","+latitude+"&radius="+radius);
        x.http().get(params,new HttpCallBack<>(new IAsyncHttpComplete<PoiScanBean>() {
            @Override
            public void onSuccess(PoiScanBean result) {
                if (result.getStatus()==0){
                    //传递周边数据
                    EventBus.getDefault().post(new PoiMessageEvent(result));
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
        return data;
    }
}
