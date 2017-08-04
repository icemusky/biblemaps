package eric.cn.com.biblemaps.Bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/4.
 */

public class PoiMessageEvent {
    private PoiScanBean bean;

    public PoiMessageEvent(PoiScanBean bean) {
        this.bean = bean;
    }

    public PoiScanBean getBean() {
        return bean;
    }

    public void setBean(PoiScanBean bean) {
        this.bean = bean;
    }
}
