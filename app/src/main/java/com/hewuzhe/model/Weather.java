package com.hewuzhe.model;

import java.util.List;

/**
 * Created by xianguangjin on 16/1/13.
 */
public class Weather {


    /**
     * city : 临沂
     * cnty : 中国
     * id : CN101120901
     * lat : 35.057000
     * lon : 118.336000
     * update : {"loc":"2016-01-13 22:25","utc":"2016-01-13 14:25"}
     */

    public BasicEntity basic;
    /**
     * cond : {"code":"100","txt":"晴"}
     * fl : -5
     * hum : 71
     * pcpn : 0
     * pres : 1026
     * tmp : -1
     * vis : 10
     * wind : {"deg":"247","dir":"西南风","sc":"4-5","spd":"24"}
     */

    public NowEntity now;
    /**
     * basic : {"city":"临沂","cnty":"中国","id":"CN101120901","lat":"35.057000","lon":"118.336000","update":{"loc":"2016-01-13 22:25","utc":"2016-01-13 14:25"}}
     * daily_forecast : [{"astro":{"sr":"07:14","ss":"17:15"},"cond":{"code_d":"100","code_n":"100","txt_d":"晴","txt_n":"晴"},"date":"2016-01-13","hum":"22","pcpn":"0.0","pop":"0","pres":"1027","tmp":{"max":"5","min":"-6"},"vis":"10","wind":{"deg":"272","dir":"西风","sc":"微风","spd":"3"}},{"astro":{"sr":"07:14","ss":"17:16"},"cond":{"code_d":"101","code_n":"100","txt_d":"多云","txt_n":"晴"},"date":"2016-01-14","hum":"26","pcpn":"0.0","pop":"0","pres":"1023","tmp":{"max":"6","min":"-6"},"vis":"10","wind":{"deg":"253","dir":"西南风","sc":"微风","spd":"10"}},{"astro":{"sr":"07:14","ss":"17:17"},"cond":{"code_d":"101","code_n":"100","txt_d":"多云","txt_n":"晴"},"date":"2016-01-15","hum":"16","pcpn":"0.0","pop":"0","pres":"1020","tmp":{"max":"7","min":"-4"},"vis":"10","wind":{"deg":"182","dir":"南风","sc":"微风","spd":"0"}},{"astro":{"sr":"07:14","ss":"17:18"},"cond":{"code_d":"101","code_n":"400","txt_d":"多云","txt_n":"小雪"},"date":"2016-01-16","hum":"24","pcpn":"0.0","pop":"0","pres":"1021","tmp":{"max":"8","min":"-3"},"vis":"10","wind":{"deg":"225","dir":"北风","sc":"3-4","spd":"16"}},{"astro":{"sr":"07:13","ss":"17:19"},"cond":{"code_d":"400","code_n":"101","txt_d":"小雪","txt_n":"多云"},"date":"2016-01-17","hum":"45","pcpn":"0.0","pop":"3","pres":"1027","tmp":{"max":"3","min":"-7"},"vis":"10","wind":{"deg":"28","dir":"北风","sc":"3-4","spd":"11"}},{"astro":{"sr":"07:13","ss":"17:20"},"cond":{"code_d":"101","code_n":"101","txt_d":"多云","txt_n":"多云"},"date":"2016-01-18","hum":"29","pcpn":"0.0","pop":"0","pres":"1031","tmp":{"max":"2","min":"-8"},"vis":"10","wind":{"deg":"36","dir":"北风","sc":"3-4","spd":"13"}},{"astro":{"sr":"07:13","ss":"17:21"},"cond":{"code_d":"100","code_n":"100","txt_d":"晴","txt_n":"晴"},"date":"2016-01-19","hum":"34","pcpn":"0.0","pop":"0","pres":"1030","tmp":{"max":"1","min":"-7"},"vis":"10","wind":{"deg":"130","dir":"南风","sc":"3-4","spd":"15"}}]
     * hourly_forecast : [{"date":"2016-01-13 22:00","hum":"68","pop":"0","pres":"1026","tmp":"-5","wind":{"deg":"243","dir":"西南风","sc":"微风","spd":"10"}}]
     * now : {"cond":{"code":"100","txt":"晴"},"fl":"-5","hum":"71","pcpn":"0","pres":"1026","tmp":"-1","vis":"10","wind":{"deg":"247","dir":"西南风","sc":"4-5","spd":"24"}}
     * status : ok
     * suggestion : {"comf":{"brf":"较舒适","txt":"白天天气晴好，早晚会感觉偏凉，午后舒适、宜人。"},"cw":{"brf":"较适宜","txt":"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"},"drsg":{"brf":"较冷","txt":"建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。"},"flu":{"brf":"极易发","txt":"天气寒冷，且昼夜温差很大，极易发生感冒。请特别注意增加衣服保暖防寒。"},"sport":{"brf":"较适宜","txt":"天气较好，无雨水困扰，较适宜进行各种运动，但因气温较低，在户外运动请注意增减衣物。"},"trav":{"brf":"适宜","txt":"天气较好，同时又有微风伴您一路同行。虽会让人感觉有点凉，但仍适宜旅游，可不要错过机会呦！"},"uv":{"brf":"最弱","txt":"属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。"}}
     */

    public String status;
    /**
     * comf : {"brf":"较舒适","txt":"白天天气晴好，早晚会感觉偏凉，午后舒适、宜人。"}
     * cw : {"brf":"较适宜","txt":"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"}
     * drsg : {"brf":"较冷","txt":"建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。"}
     * flu : {"brf":"极易发","txt":"天气寒冷，且昼夜温差很大，极易发生感冒。请特别注意增加衣服保暖防寒。"}
     * sport : {"brf":"较适宜","txt":"天气较好，无雨水困扰，较适宜进行各种运动，但因气温较低，在户外运动请注意增减衣物。"}
     * trav : {"brf":"适宜","txt":"天气较好，同时又有微风伴您一路同行。虽会让人感觉有点凉，但仍适宜旅游，可不要错过机会呦！"}
     * uv : {"brf":"最弱","txt":"属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。"}
     */

    public SuggestionEntity suggestion;
    /**
     * astro : {"sr":"07:14","ss":"17:15"}
     * cond : {"code_d":"100","code_n":"100","txt_d":"晴","txt_n":"晴"}
     * date : 2016-01-13
     * hum : 22
     * pcpn : 0.0
     * pop : 0
     * pres : 1027
     * tmp : {"max":"5","min":"-6"}
     * vis : 10
     * wind : {"deg":"272","dir":"西风","sc":"微风","spd":"3"}
     */

    public List<DailyForecastEntity> daily_forecast;
    /**
     * date : 2016-01-13 22:00
     * hum : 68
     * pop : 0
     * pres : 1026
     * tmp : -5
     * wind : {"deg":"243","dir":"西南风","sc":"微风","spd":"10"}
     */

    public List<HourlyForecastEntity> hourly_forecast;

    public static class BasicEntity {
        public String city;
        public String cnty;
        public String id;
        public String lat;
        public String lon;
        /**
         * loc : 2016-01-13 22:25
         * utc : 2016-01-13 14:25
         */

        public UpdateEntity update;

        public static class UpdateEntity {
            public String loc;
            public String utc;
        }
    }

    public static class NowEntity {
        /**
         * code : 100
         * txt : 晴
         */

        public CondEntity cond;
        public String fl;
        public String hum;
        public String pcpn;
        public String pres;
        public String tmp;
        public String vis;
        /**
         * deg : 247
         * dir : 西南风
         * sc : 4-5
         * spd : 24
         */

        public WindEntity wind;

        public static class CondEntity {
            public String code;
            public String txt;
        }

        public static class WindEntity {
            public String deg;
            public String dir;
            public String sc;
            public String spd;
        }
    }

    public static class SuggestionEntity {
        /**
         * brf : 较舒适
         * txt : 白天天气晴好，早晚会感觉偏凉，午后舒适、宜人。
         */

        public ComfEntity comf;
        /**
         * brf : 较适宜
         * txt : 较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。
         */

        public CwEntity cw;
        /**
         * brf : 较冷
         * txt : 建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。
         */

        public DrsgEntity drsg;
        /**
         * brf : 极易发
         * txt : 天气寒冷，且昼夜温差很大，极易发生感冒。请特别注意增加衣服保暖防寒。
         */

        public FluEntity flu;
        /**
         * brf : 较适宜
         * txt : 天气较好，无雨水困扰，较适宜进行各种运动，但因气温较低，在户外运动请注意增减衣物。
         */

        public SportEntity sport;
        /**
         * brf : 适宜
         * txt : 天气较好，同时又有微风伴您一路同行。虽会让人感觉有点凉，但仍适宜旅游，可不要错过机会呦！
         */

        public TravEntity trav;
        /**
         * brf : 最弱
         * txt : 属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。
         */

        public UvEntity uv;

        public static class ComfEntity {
            public String brf;
            public String txt;
        }

        public static class CwEntity {
            public String brf;
            public String txt;
        }

        public static class DrsgEntity {
            public String brf;
            public String txt;
        }

        public static class FluEntity {
            public String brf;
            public String txt;
        }

        public static class SportEntity {
            public String brf;
            public String txt;
        }

        public static class TravEntity {
            public String brf;
            public String txt;
        }

        public static class UvEntity {
            public String brf;
            public String txt;
        }
    }

    public static class DailyForecastEntity {
        /**
         * sr : 07:14
         * ss : 17:15
         */

        public AstroEntity astro;
        /**
         * code_d : 100
         * code_n : 100
         * txt_d : 晴
         * txt_n : 晴
         */

        public CondEntity cond;
        public String date;
        public String hum;
        public String pcpn;
        public String pop;
        public String pres;
        /**
         * max : 5
         * min : -6
         */

        public TmpEntity tmp;
        public String vis;
        /**
         * deg : 272
         * dir : 西风
         * sc : 微风
         * spd : 3
         */

        public WindEntity wind;

        public static class AstroEntity {
            public String sr;
            public String ss;
        }

        public static class CondEntity {
            public String code_d;
            public String code_n;
            public String txt_d;
            public String txt_n;
        }

        public static class TmpEntity {
            public String max;
            public String min;
        }

        public static class WindEntity {
            public String deg;
            public String dir;
            public String sc;
            public String spd;
        }
    }

    public static class HourlyForecastEntity {
        public String date;
        public String hum;
        public String pop;
        public String pres;
        public String tmp;
        /**
         * deg : 243
         * dir : 西南风
         * sc : 微风
         * spd : 10
         */

        public WindEntity wind;

        public static class WindEntity {
            public String deg;
            public String dir;
            public String sc;
            public String spd;
        }
    }
}
