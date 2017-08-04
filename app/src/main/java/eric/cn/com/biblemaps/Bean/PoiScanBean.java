package eric.cn.com.biblemaps.Bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/4.
 */

public class PoiScanBean {

    /**
     * status : 0
     * total : 2
     * size : 1
     * contents : [{"shop_type":"1","uid":2236726635,"province":"辽宁省","geotable_id":172421,"district":"沈河区","create_time":1500543054,"city":"沈阳市","location":[123.442725,41.822981],"title":"教会","coord_type":3,"direction":"附近","type":0,"distance":0,"weight":0}]
     */

    private int status;
    private int total;
    private int size;
    private List<ContentsBean> contents;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<ContentsBean> getContents() {
        return contents;
    }

    public void setContents(List<ContentsBean> contents) {
        this.contents = contents;
    }

    public static class ContentsBean {
        /**
         * shop_type : 1
         * uid : 2236726635
         * province : 辽宁省
         * geotable_id : 172421
         * district : 沈河区
         * create_time : 1500543054
         * city : 沈阳市
         * location : [123.442725,41.822981]
         * title : 教会
         * coord_type : 3
         * direction : 附近
         * type : 0
         * distance : 0
         * weight : 0
         */

        private String shop_type;
        private long uid;
        private String province;
        private int geotable_id;
        private String district;
        private int create_time;
        private String city;
        private String title;
        private int coord_type;
        private String direction;
        private int type;
        private int distance;
        private int weight;
        private List<Double> location;

        public String getShop_type() {
            return shop_type;
        }

        public void setShop_type(String shop_type) {
            this.shop_type = shop_type;
        }

        public long getUid() {
            return uid;
        }

        public void setUid(long uid) {
            this.uid = uid;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public int getGeotable_id() {
            return geotable_id;
        }

        public void setGeotable_id(int geotable_id) {
            this.geotable_id = geotable_id;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getCoord_type() {
            return coord_type;
        }

        public void setCoord_type(int coord_type) {
            this.coord_type = coord_type;
        }

        public String getDirection() {
            return direction;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public List<Double> getLocation() {
            return location;
        }

        public void setLocation(List<Double> location) {
            this.location = location;
        }
    }
}
