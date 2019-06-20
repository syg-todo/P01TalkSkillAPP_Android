package com.tuodanhuashu.app.Constants;

import android.Manifest;

public class Constants {

    public static String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE};


    public static class URL {

        public static final String BASE_URL = "http://huashu.zhongpin.me";

        public static final String LOGIN_URL = "/api/login/login";

        public static final String SEND_SMS_URL = "/api/index/send_sms";

        public static final String USER_REGISTER_URL = "/api/reg/reg";

        public static final String USER_FORGET_PWD_URL = "/api/reg/forget_passwd";

        public static final String HOME_PAGE_URL = "/api/index/index";

        public static final String HOME_TALK_SKILL_URL = "/api/talk/index";

        public static final String HOME_ZHUANLAN_URL = "/api/news/index";

        public static final String HOME_COLLEGE_URL = "/api/course/index";

        public static final String HOME_COLLEGE_CATEGORY_URL = "/api/course/category";

        public static final String COLLEGE_COURSE_CAT_LIST_URL = "/api/course/list_by_catid";

        public static final String COLLEGE_COURSE_COMMUNITY_URL = "/api/course/sqk_list";

        public static final String COLLEGE_COURSE_CHOICENESS_LIST = "/api/course/choiceness_list";

        public static final String COLLEGE_COURSE_ACTIVITY_LIST = "/api/cactivity/newlist";

        public static final String COLLEGE_COURSE_MASTER_LIST_URL = "/api/master/list";

        public static final String COLLEGE_COURSE_SJK_LIST_URL = "/api/course/sjk_list";

        public static final String COLLEGE_COURSE_DETAIL_URL = "/api/courseuser/course_info";

        public static final String COLLEGE_COURSE_SECTION_INFO_URL = "/api/Courseuser/sectioninfo";

        public static final String COLLEGE_COURSE_RECORD_MASTER_URL = "/api/Courseuser/record_master";

        public static final String COLLEGE_COURSE_UNRECORD_MASTER_URL = "/api/Courseuser/unrecord_master";

        public static final String COLLEGE_COURSE_MY_MASTER_URL = "/api/Courseuser/my_master";

        public static final String COLLEGE_COURSE_MY_COURSE_URL = "/api/Courseuser/my_course";

        public static final String COLLEGE_COURSE_SECTION_SEND_COMMENT = "/api/Courseuser/add_comment";

        public static final String COLLEGE_COURSE_LIKE_COMMENT = "/api/Courseuser/like_comment";

        public static final String COLLEGE_COURSE_UNLIKE_COMMENT = "/api/Courseuser/unlike_comment";

        public static final String COLLEGE_MASTER_DETAIL_URL = "/api/Courseuser/master_info";


        public static final String TALK_SKILL_LIST_URL = "/api/user/get_list_by_keywords";

        public static final String ARTICLE_DETAIL_URL = "/api/user/news_info";

        public static final String ARTICLE_KEYWORDS_URL = "/api/news/get_newsList_by_keywords";

        public static final String ARTICLE_CATID_URL = "/api/news/get_newsList_by_catid";

        public static final String ARTICLE_CAT_LIST_URL = "/api/news/get_cat_list";

        public static final String TEACHER_LIST_URL = "/api/index/expert_list";

        public static final String ARTICLE_NEW_URL = "/api/news/new_news_list";

        public static final String ARTICLE_CHOICE_URL = "/api/news/get_choiceness_news";

        public static final String PERSON_INFO_URL = "/api/user/personal";

        public static final String GOODS_LIST_URL = "/api/user/goods";

        public static final String ADD_ORDER_URL = "/api/order/add";

        public static final String WX_PAY_URL = "/api/order/wxpay";

        public static final String ALI_PAY_URL = "/api/order/alpay";

        public static final String EDIT_HEAD_URL = "/api/user/edit_heade_image";

        public static final String EDIT_INFO_URL = "/api/user/edit_userinfo";

        public static final String RESET_PWD_URL = "/api/user/edit_passwd";

        public static final String RESET_MOBILE_URL = "/api/user/edit_mobile";

        public static final String MY_ARTICLE_URL = "/api/user/c_news_list";

        public static final String MY_TALKSKILL_URL = "/api/user/c_talk_list";

        public static final String COLLECT_ARTICLE_URL = "/api/user/collection_news";

        public static final String UNCOLLECT_ARTICLE_URL = "/api/user/un_collection_news";

        public static final String COMMENT_ARTICLE_URL = "/api/user/add_comment";

        public static final String GET_MIJI_URL = "/api/index/mijiImg";

        public static final String WX_LOGIN_URL = "/api/login/wxLogin";

        public static final String FEED_BACK_URL = "/api/user/add_feedback";

        public static final String ABOUT_US_URL = "/api/index/aboutus";

        public static final String REGISTER_MESSAGE_URL = "/api/index/registerMessage";

        public static final String CHECK_VERSION_URL = "/api/index/AndroidVer";

        public static final String ADD_LGOOLDS_URL = "/api/order/add_lgoods";

        public static final String COLLEGE_BUY_COURSE_URL = "/api/courseuser/buy_course";

        public static final String GET_LOVE_GOODS_LIST_URL = "/api/courseuser/lovegoods";
    }

    public static class PREFERENCE {
        public static final String KEY_PERMISSION_GRANTED = "persmission_granted";
    }

    public static class IM {
        public static final String IM_APP_KEY = "1471190314068674#kefuchannelapp67196";

        public static final String IM_TENANT_ID = "67196";

        public static final String IM_SERVICE_ID = "kefuchannelimid_647553";
    }

    public static class WEIXIN {
        public static final String WX_APP_ID = "wxd3563efd9c2f7ec0";

        public static final String WX_APP_SECRET = "a039d07f990c4a15987d34752de79876";

        public static final String WX_TOEKN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";

        public static final String WX_INFO_URL = "https://api.weixin.qq.com/sns/userinfo";
    }


    public static class FILE_PATH {

        public static final String FILE_PROVIDER_AUTHORITIES = "com.tuodanhuashu.app.file_provider";

        public static final String PHOTO_PATH = "huashu/photo";

        public static final String APK_PATH = "huashu/apk";


    }

    public static class EVENT_TAG {
        public static final int TAG_FINISH = 999;

        public static final int TAG_WX_AUTH_SUCCESS = 998;

        public static final int TAG_WX_AUTH_FAIL = 997;

        public static final int TAG_WX_SHARE_SUCCESS = 996;

        public static final int TAG_WX_SHARE_FAIL = 995;

        public static final int TAG_WX_PAY_SUCCESS = 994;

        public static final int TAG_WX_PAY_FAIL = 993;

        public static final int TAG_FLOAT_PLAY = 992;

        public static final int TAG_PLAYER_DURATION = 991;

        public static final int TAG_PLAYER_CURRENT = 990;

        public static final int TAG_PLAYER_CURRENT_SECTION = 989;

        public static final int TAG_SECTION_CHOSEN = 988;

        public static final int TAG_SECTION_STATE_CHANGED = 987;

        public static final int TAG_SECTION_STATE_CHANGING = 986;

        public static final int TAG_STOP_SERVICE = 985;

        public static final int TAG_COMMENT_LIKE_SUCCESS = 984;

        public static final String TAG_SECTION_ID = "section_id";
        public static final String TAG_SECTION_NAME = "section_name";
        public static final String TAG_SECTION_DURATION = "section_duration";
        public static final String TAG_SECTION_STATE = "section_state";

//        public static  final String TAG_COMMENT_LIKE_SUCCESS = "comment_like_success";

        public static final String TAG_SECTION_BANNER_URL = "section_banner_url";
    }

    public static class REG {
        public static final String REG_PWD = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,12}$";
    }
}
