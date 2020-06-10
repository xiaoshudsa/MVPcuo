package com.zxp.frame;

public class FengUrl {

    public   static  String  ADVERT_URL;
    public   static  String SUBJECTLIST_URL;
    public   static  String LOGINVERIFY_URL;
    public   static  String LOGINBYVERIFY_URL1;
    public   static  String HEADERINFO_URL;
    public  static  String  HOMEBEAN_URL;
    public  static  String  HOMEBANNERBEAN_URL;
    public static  String GETLESSONLISTFORAPI ="";
    public static  String GETVIPLIVE_URL ="";
    public static  String GETVIPLIST_URL ="";
    public static final String GETGROUPLIST = "group/getGroupList";
    public static final String REMOVEGROUP = "removeGroup";
    public static final String JOINGROUP = "joingroup";

    /**
     * 静态代码块，优先于对象的创建而执行，且只执行一次
     */
    static {
         ADVERT_URL="ad/getAd";
        SUBJECTLIST_URL="lesson/getAllspecialty";
        LOGINVERIFY_URL="loginByMobileCode";
        LOGINBYVERIFY_URL1=  "loginByMobileCode";
        HEADERINFO_URL= "getUserHeaderForMobile";
        HOMEBEAN_URL="lesson/getIndexCommend";
        HOMEBANNERBEAN_URL="lesson/getCarouselphoto";
        GETLESSONLISTFORAPI = "lesson/getLessonListForApi";
        GETVIPLIVE_URL="lesson/get_new_vip";
        GETVIPLIST_URL="lesson/getVipSmallLessonList";

    }
}
