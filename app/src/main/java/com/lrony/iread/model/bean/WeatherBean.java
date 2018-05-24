package com.lrony.iread.model.bean;

import java.util.List;

/**
 * Created by Lrony on 18-5-24.
 */
public class WeatherBean {
    /**
     * date : 20180524
     * message : Success !
     * status : 200
     * city : 上海
     * count : 1022
     * data : {"shidu":"68%","pm25":43,"pm10":156,"quality":"轻度污染","wendu":"20","ganmao":"儿童、老年人及心脏、呼吸系统疾病患者人群应减少长时间或高强度户外锻炼","yesterday":{"date":"23日星期三","sunrise":"04:55","high":"高温 26.0℃","low":"低温 17.0℃","sunset":"18:47","aqi":100,"fx":"北风","fl":"<3级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},"forecast":[{"date":"24日星期四","sunrise":"04:54","high":"高温 27.0℃","low":"低温 20.0℃","sunset":"18:48","aqi":84,"fx":"东南风","fl":"<3级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"25日星期五","sunrise":"04:54","high":"高温 28.0℃","low":"低温 20.0℃","sunset":"18:49","aqi":93,"fx":"东南风","fl":"<3级","type":"中雨","notice":"记得随身携带雨伞哦"},{"date":"26日星期六","sunrise":"04:53","high":"高温 26.0℃","low":"低温 20.0℃","sunset":"18:49","aqi":84,"fx":"东北风","fl":"<3级","type":"小雨","notice":"雨虽小，注意保暖别感冒"},{"date":"27日星期日","sunrise":"04:53","high":"高温 26.0℃","low":"低温 19.0℃","sunset":"18:50","aqi":101,"fx":"东南风","fl":"<3级","type":"阴","notice":"不要被阴云遮挡住好心情"},{"date":"28日星期一","sunrise":"04:53","high":"高温 28.0℃","low":"低温 20.0℃","sunset":"18:50","aqi":95,"fx":"东风","fl":"<3级","type":"小雨","notice":"雨虽小，注意保暖别感冒"}]}
     */

    private String date;
    private String message;
    private int status;
    private String city;
    private int count;
    private DataBean data;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * shidu : 68%
         * pm25 : 43.0
         * pm10 : 156.0
         * quality : 轻度污染
         * wendu : 20
         * ganmao : 儿童、老年人及心脏、呼吸系统疾病患者人群应减少长时间或高强度户外锻炼
         * yesterday : {"date":"23日星期三","sunrise":"04:55","high":"高温 26.0℃","low":"低温 17.0℃","sunset":"18:47","aqi":100,"fx":"北风","fl":"<3级","type":"晴","notice":"愿你拥有比阳光明媚的心情"}
         * forecast : [{"date":"24日星期四","sunrise":"04:54","high":"高温 27.0℃","low":"低温 20.0℃","sunset":"18:48","aqi":84,"fx":"东南风","fl":"<3级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"25日星期五","sunrise":"04:54","high":"高温 28.0℃","low":"低温 20.0℃","sunset":"18:49","aqi":93,"fx":"东南风","fl":"<3级","type":"中雨","notice":"记得随身携带雨伞哦"},{"date":"26日星期六","sunrise":"04:53","high":"高温 26.0℃","low":"低温 20.0℃","sunset":"18:49","aqi":84,"fx":"东北风","fl":"<3级","type":"小雨","notice":"雨虽小，注意保暖别感冒"},{"date":"27日星期日","sunrise":"04:53","high":"高温 26.0℃","low":"低温 19.0℃","sunset":"18:50","aqi":101,"fx":"东南风","fl":"<3级","type":"阴","notice":"不要被阴云遮挡住好心情"},{"date":"28日星期一","sunrise":"04:53","high":"高温 28.0℃","low":"低温 20.0℃","sunset":"18:50","aqi":95,"fx":"东风","fl":"<3级","type":"小雨","notice":"雨虽小，注意保暖别感冒"}]
         */

        private String shidu;
        private double pm25;
        private double pm10;
        private String quality;
        private String wendu;
        private String ganmao;
        private YesterdayBean yesterday;
        private List<ForecastBean> forecast;

        public String getShidu() {
            return shidu;
        }

        public void setShidu(String shidu) {
            this.shidu = shidu;
        }

        public double getPm25() {
            return pm25;
        }

        public void setPm25(double pm25) {
            this.pm25 = pm25;
        }

        public double getPm10() {
            return pm10;
        }

        public void setPm10(double pm10) {
            this.pm10 = pm10;
        }

        public String getQuality() {
            return quality;
        }

        public void setQuality(String quality) {
            this.quality = quality;
        }

        public String getWendu() {
            return wendu;
        }

        public void setWendu(String wendu) {
            this.wendu = wendu;
        }

        public String getGanmao() {
            return ganmao;
        }

        public void setGanmao(String ganmao) {
            this.ganmao = ganmao;
        }

        public YesterdayBean getYesterday() {
            return yesterday;
        }

        public void setYesterday(YesterdayBean yesterday) {
            this.yesterday = yesterday;
        }

        public List<ForecastBean> getForecast() {
            return forecast;
        }

        public void setForecast(List<ForecastBean> forecast) {
            this.forecast = forecast;
        }

        public static class YesterdayBean {
            /**
             * date : 23日星期三
             * sunrise : 04:55
             * high : 高温 26.0℃
             * low : 低温 17.0℃
             * sunset : 18:47
             * aqi : 100.0
             * fx : 北风
             * fl : <3级
             * type : 晴
             * notice : 愿你拥有比阳光明媚的心情
             */

            private String date;
            private String sunrise;
            private String high;
            private String low;
            private String sunset;
            private double aqi;
            private String fx;
            private String fl;
            private String type;
            private String notice;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getSunrise() {
                return sunrise;
            }

            public void setSunrise(String sunrise) {
                this.sunrise = sunrise;
            }

            public String getHigh() {
                return high;
            }

            public void setHigh(String high) {
                this.high = high;
            }

            public String getLow() {
                return low;
            }

            public void setLow(String low) {
                this.low = low;
            }

            public String getSunset() {
                return sunset;
            }

            public void setSunset(String sunset) {
                this.sunset = sunset;
            }

            public double getAqi() {
                return aqi;
            }

            public void setAqi(double aqi) {
                this.aqi = aqi;
            }

            public String getFx() {
                return fx;
            }

            public void setFx(String fx) {
                this.fx = fx;
            }

            public String getFl() {
                return fl;
            }

            public void setFl(String fl) {
                this.fl = fl;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getNotice() {
                return notice;
            }

            public void setNotice(String notice) {
                this.notice = notice;
            }
        }

        public static class ForecastBean {
            /**
             * date : 24日星期四
             * sunrise : 04:54
             * high : 高温 27.0℃
             * low : 低温 20.0℃
             * sunset : 18:48
             * aqi : 84.0
             * fx : 东南风
             * fl : <3级
             * type : 多云
             * notice : 阴晴之间，谨防紫外线侵扰
             */

            private String date;
            private String sunrise;
            private String high;
            private String low;
            private String sunset;
            private double aqi;
            private String fx;
            private String fl;
            private String type;
            private String notice;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getSunrise() {
                return sunrise;
            }

            public void setSunrise(String sunrise) {
                this.sunrise = sunrise;
            }

            public String getHigh() {
                return high;
            }

            public void setHigh(String high) {
                this.high = high;
            }

            public String getLow() {
                return low;
            }

            public void setLow(String low) {
                this.low = low;
            }

            public String getSunset() {
                return sunset;
            }

            public void setSunset(String sunset) {
                this.sunset = sunset;
            }

            public double getAqi() {
                return aqi;
            }

            public void setAqi(double aqi) {
                this.aqi = aqi;
            }

            public String getFx() {
                return fx;
            }

            public void setFx(String fx) {
                this.fx = fx;
            }

            public String getFl() {
                return fl;
            }

            public void setFl(String fl) {
                this.fl = fl;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getNotice() {
                return notice;
            }

            public void setNotice(String notice) {
                this.notice = notice;
            }
        }
    }
}
