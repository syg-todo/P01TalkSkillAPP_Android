package com.tuodanhuashu.app.course.bean;

import java.util.List;

public class CourseDetailBean {


    /**
     * course : {"id":"4","course_name":"课程3","image_url":"http://huashu.zhongpin.me/uploads/course/201905231709159241.png","image_small_url":"http://huashu.zhongpin.me/uploads/course/201905231709182818.png","price":"500.00","sale_price":"400.00","join_count":"0","course_intro":"愤怒往往能使对方丧胆而让步；流泪能够换得对方的同情；恐惧能将人们的心拴在一起；情感冷漠常使交往者打退堂鼓；。","media_type":"1","master_id":"3","class_id":"0","course_type":"3","course_detail":"<p class=\"one-p\" style=\"margin-top: 0px; margin-bottom: 2em; padding: 0px; line-height: 2.2; overflow-wrap: break-word; font-family: \" microsoft=\"\" segoe=\"\" hiragino=\"\" sans=\"\" wenquanyi=\"\" micro=\"\" font-size:=\"\" white-space:=\"\">习近平主席夫人彭丽媛，中共中央政治局委员、中央外事工作委员会办公室主任杨洁篪，全国人大常委会副委员长曹建明，国务委员王毅，全国政协副主席何立峰、刘新成等出席欢迎仪式。<\/p><p class=\"one-p\" style=\"margin-top: 0px; margin-bottom: 2em; padding: 0px; line-height: 2.2; overflow-wrap: break-word; font-family: \" microsoft=\"\" segoe=\"\" hiragino=\"\" sans=\"\" wenquanyi=\"\" micro=\"\" font-size:=\"\" white-space:=\"\"><img src=\"https://inews.gtimg.com/newsapp_bt/0/9146589761/1000\" class=\"content-picture\" style=\"margin: 0.6em auto; padding: 0px; border: 0px none; vertical-align: middle; max-width: 100%; display: block;\"/><em class=\"desc\" style=\"margin: 0px; padding: 0px; color: rgb(155, 158, 163); line-height: 20px; text-align: center; display: block; font-size: 14px;\">5月28日，国家主席习近平在北京人民大会堂同尼日尔总统伊素福举行会谈。这是会谈前，习近平在人民大会堂东门外广场为伊素福举行欢迎仪式。 新华社记者 刘卫兵 摄<\/em><\/p><p class=\"one-p\" style=\"margin-top: 0px; margin-bottom: 2em; padding: 0px; line-height: 2.2; overflow-wrap: break-word; font-family: \" microsoft=\"\" segoe=\"\" hiragino=\"\" sans=\"\" wenquanyi=\"\" micro=\"\" font-size:=\"\" white-space:=\"\">陪同伊素福访华的有总统夫人、总统办公厅主任、外交、合作、非洲一体化和尼日尔侨民部部长等。<\/p><p class=\"one-p\" style=\"margin-top: 0px; margin-bottom: 2em; padding: 0px; line-height: 2.2; overflow-wrap: break-word; font-family: \" microsoft=\"\" segoe=\"\" hiragino=\"\" sans=\"\" wenquanyi=\"\" micro=\"\" font-size:=\"\" white-space:=\"\">军乐团奏尼中两国国歌。鸣礼炮21响。<\/p><p><br/><\/p>","master_name":"导师3","master_avatar_url":"http://huashu.zhongpin.me/uploads/master/header/201905231659514894.png","activity_price":"0","is_pay":"2","is_checkout":"1","share_image_url":"http://huashu.zhongpin.me/uploads/course/201905231709182818.png","share_url":"http://huashu.zhongpin.me/api/course/course_info/course_id/4.html"}
     * sections : [{"id":"7","course_id":"4","section_name":"第2章","section_intro":"&lt;p&gt;33333&lt;/p&gt;","duration":"3","is_audition":"1"}]
     * comments : []
     * recommendCourses : [{"id":"4","course_name":"课程3","image_url":"http://huashu.zhongpin.me/uploads/course/201905231709159241.png","image_small_url":"http://huashu.zhongpin.me/uploads/course/201905231709182818.png","price":"500.00","sale_price":"400.00","join_count":"0","master_name":"导师3","activity_price":"0.00"},{"id":"5","course_name":"课程7","image_url":"http://huashu.zhongpin.me/uploads/course/201905231713111702.png","image_small_url":"http://huashu.zhongpin.me/uploads/course/201905231713152582.png","price":"500.00","sale_price":"300.00","join_count":"0","master_name":"导师4","activity_price":"0.00"}]
     */

    private CourseBean course;
    private List<SectionsBean> sections;
    private List<CommentBean> comments;
    private List<RecommendCoursesBean> recommendCourses;

    public CourseBean getCourse() {
        return course;
    }

    public void setCourse(CourseBean course) {
        this.course = course;
    }

    public List<SectionsBean> getSections() {
        return sections;
    }

    public void setSections(List<SectionsBean> sections) {
        this.sections = sections;
    }

    public List<CommentBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentBean> comments) {
        this.comments = comments;
    }

    public List<RecommendCoursesBean> getRecommendCourses() {
        return recommendCourses;
    }

    public void setRecommendCourses(List<RecommendCoursesBean> recommendCourses) {
        this.recommendCourses = recommendCourses;
    }

    public static class CourseBean {
        /**
         * id : 4
         * course_name : 课程3
         * image_url : http://huashu.zhongpin.me/uploads/course/201905231709159241.png
         * image_small_url : http://huashu.zhongpin.me/uploads/course/201905231709182818.png
         * price : 500.00
         * sale_price : 400.00
         * join_count : 0
         * course_intro : 愤怒往往能使对方丧胆而让步；流泪能够换得对方的同情；恐惧能将人们的心拴在一起；情感冷漠常使交往者打退堂鼓；。
         * media_type : 1
         * master_id : 3
         * class_id : 0
         * course_type : 3
         * course_detail : <p class="one-p" style="margin-top: 0px; margin-bottom: 2em; padding: 0px; line-height: 2.2; overflow-wrap: break-word; font-family: " microsoft="" segoe="" hiragino="" sans="" wenquanyi="" micro="" font-size:="" white-space:="">习近平主席夫人彭丽媛，中共中央政治局委员、中央外事工作委员会办公室主任杨洁篪，全国人大常委会副委员长曹建明，国务委员王毅，全国政协副主席何立峰、刘新成等出席欢迎仪式。</p><p class="one-p" style="margin-top: 0px; margin-bottom: 2em; padding: 0px; line-height: 2.2; overflow-wrap: break-word; font-family: " microsoft="" segoe="" hiragino="" sans="" wenquanyi="" micro="" font-size:="" white-space:=""><img src="https://inews.gtimg.com/newsapp_bt/0/9146589761/1000" class="content-picture" style="margin: 0.6em auto; padding: 0px; border: 0px none; vertical-align: middle; max-width: 100%; display: block;"/><em class="desc" style="margin: 0px; padding: 0px; color: rgb(155, 158, 163); line-height: 20px; text-align: center; display: block; font-size: 14px;">5月28日，国家主席习近平在北京人民大会堂同尼日尔总统伊素福举行会谈。这是会谈前，习近平在人民大会堂东门外广场为伊素福举行欢迎仪式。 新华社记者 刘卫兵 摄</em></p><p class="one-p" style="margin-top: 0px; margin-bottom: 2em; padding: 0px; line-height: 2.2; overflow-wrap: break-word; font-family: " microsoft="" segoe="" hiragino="" sans="" wenquanyi="" micro="" font-size:="" white-space:="">陪同伊素福访华的有总统夫人、总统办公厅主任、外交、合作、非洲一体化和尼日尔侨民部部长等。</p><p class="one-p" style="margin-top: 0px; margin-bottom: 2em; padding: 0px; line-height: 2.2; overflow-wrap: break-word; font-family: " microsoft="" segoe="" hiragino="" sans="" wenquanyi="" micro="" font-size:="" white-space:="">军乐团奏尼中两国国歌。鸣礼炮21响。</p><p><br/></p>
         * master_name : 导师3
         * master_avatar_url : http://huashu.zhongpin.me/uploads/master/header/201905231659514894.png
         * activity_price : 0
         * is_pay : 2
         * is_checkout : 1
         * share_image_url : http://huashu.zhongpin.me/uploads/course/201905231709182818.png
         * share_url : http://huashu.zhongpin.me/api/course/course_info/course_id/4.html
         */

        private String id;
        private String course_name;
        private String image_url;
        private String image_small_url;
        private String price;
        private String sale_price;
        private String join_count;
        private String course_intro;
        private String media_type;
        private String master_id;
        private String class_id;
        private String course_type;
        private String course_detail;
        private String master_name;
        private String master_avatar_url;
        private String activity_price;
        private String is_pay;
        private String is_checkout;
        private String share_image_url;
        private String share_url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCourse_name() {
            return course_name;
        }

        public void setCourse_name(String course_name) {
            this.course_name = course_name;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public String getImage_small_url() {
            return image_small_url;
        }

        public void setImage_small_url(String image_small_url) {
            this.image_small_url = image_small_url;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getSale_price() {
            return sale_price;
        }

        public void setSale_price(String sale_price) {
            this.sale_price = sale_price;
        }

        public String getJoin_count() {
            return join_count;
        }

        public void setJoin_count(String join_count) {
            this.join_count = join_count;
        }

        public String getCourse_intro() {
            return course_intro;
        }

        public void setCourse_intro(String course_intro) {
            this.course_intro = course_intro;
        }

        public String getMedia_type() {
            return media_type;
        }

        public void setMedia_type(String media_type) {
            this.media_type = media_type;
        }

        public String getMaster_id() {
            return master_id;
        }

        public void setMaster_id(String master_id) {
            this.master_id = master_id;
        }

        public String getClass_id() {
            return class_id;
        }

        public void setClass_id(String class_id) {
            this.class_id = class_id;
        }

        public String getCourse_type() {
            return course_type;
        }

        public void setCourse_type(String course_type) {
            this.course_type = course_type;
        }

        public String getCourse_detail() {
            return course_detail;
        }

        public void setCourse_detail(String course_detail) {
            this.course_detail = course_detail;
        }

        public String getMaster_name() {
            return master_name;
        }

        public void setMaster_name(String master_name) {
            this.master_name = master_name;
        }

        public String getMaster_avatar_url() {
            return master_avatar_url;
        }

        public void setMaster_avatar_url(String master_avatar_url) {
            this.master_avatar_url = master_avatar_url;
        }

        public String getActivity_price() {
            return activity_price;
        }

        public void setActivity_price(String activity_price) {
            this.activity_price = activity_price;
        }

        public String getIs_pay() {
            return is_pay;
        }

        public void setIs_pay(String is_pay) {
            this.is_pay = is_pay;
        }

        public String getIs_checkout() {
            return is_checkout;
        }

        public void setIs_checkout(String is_checkout) {
            this.is_checkout = is_checkout;
        }

        public String getShare_image_url() {
            return share_image_url;
        }

        public void setShare_image_url(String share_image_url) {
            this.share_image_url = share_image_url;
        }

        public String getShare_url() {
            return share_url;
        }

        public void setShare_url(String share_url) {
            this.share_url = share_url;
        }
    }

    public static class SectionsBean {
        /**
         * id : 7
         * course_id : 4
         * section_name : 第2章
         * section_intro : &lt;p&gt;33333&lt;/p&gt;
         * duration : 3
         * is_audition : 1
         */

        private String id;
        private String course_id;
        private String section_name;
        private String section_intro;
        private String duration;
        private String is_audition;
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCourse_id() {
            return course_id;
        }

        public void setCourse_id(String course_id) {
            this.course_id = course_id;
        }

        public String getSection_name() {
            return section_name;
        }

        public void setSection_name(String section_name) {
            this.section_name = section_name;
        }

        public String getSection_intro() {
            return section_intro;
        }

        public void setSection_intro(String section_intro) {
            this.section_intro = section_intro;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getIs_audition() {
            return is_audition;
        }

        public void setIs_audition(String is_audition) {
            this.is_audition = is_audition;
        }
    }

    public static class RecommendCoursesBean {
        /**
         * id : 4
         * course_name : 课程3
         * image_url : http://huashu.zhongpin.me/uploads/course/201905231709159241.png
         * image_small_url : http://huashu.zhongpin.me/uploads/course/201905231709182818.png
         * price : 500.00
         * sale_price : 400.00
         * join_count : 0
         * master_name : 导师3
         * activity_price : 0.00
         */

        private String id;
        private String course_name;
        private String image_url;
        private String image_small_url;
        private String price;
        private String sale_price;
        private String join_count;
        private String master_name;
        private String activity_price;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCourse_name() {
            return course_name;
        }

        public void setCourse_name(String course_name) {
            this.course_name = course_name;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public String getImage_small_url() {
            return image_small_url;
        }

        public void setImage_small_url(String image_small_url) {
            this.image_small_url = image_small_url;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getSale_price() {
            return sale_price;
        }

        public void setSale_price(String sale_price) {
            this.sale_price = sale_price;
        }

        public String getJoin_count() {
            return join_count;
        }

        public void setJoin_count(String join_count) {
            this.join_count = join_count;
        }

        public String getMaster_name() {
            return master_name;
        }

        public void setMaster_name(String master_name) {
            this.master_name = master_name;
        }

        public String getActivity_price() {
            return activity_price;
        }

        public void setActivity_price(String activity_price) {
            this.activity_price = activity_price;
        }
    }
}