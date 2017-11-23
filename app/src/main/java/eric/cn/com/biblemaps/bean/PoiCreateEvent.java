package eric.cn.com.biblemaps.bean;

/**
 * Created by Administrator on 2017/11/23.
 */

public class PoiCreateEvent {
    PoiCreateBean bean;

    public PoiCreateEvent(PoiCreateBean bean) {
        this.bean = bean;
    }

    public PoiCreateBean getBean() {
        return bean;
    }

    public void setBean(PoiCreateBean bean) {
        this.bean = bean;
    }
}
