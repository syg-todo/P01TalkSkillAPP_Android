package com.tuodanhuashu.app.course.bean;

import java.util.List;

public class CourseDetailBean {

    /**
     * course : {"id":10,"course_name":"100","image_url":"","image_small_url":"","price":10,"sale_price":9,"join_count":10000,"master_name":"张飞","course_intro":"一幅画千言万语，一棵树别样人生","activity_price":8,"media_type":1,"is_pay":1,"master_id":100,"is_checkout":1,"course_detail":"","share_image_url":"","share_url":""}
     * sections : [{"id":111,"course_id":10,"section_name":"01 发刊词：创伤自我疗愈","section_intro":"","duration":1000,"is_audition":1},{"id":111,"course_id":10,"section_name":"01 发刊词：创伤自我疗愈","section_intro":"","duration":1000,"is_audition":1},{"id":111,"course_id":10,"section_name":"01 发刊词：创伤自我疗愈","section_intro":"","duration":1000,"is_audition":1},{"id":111,"course_id":10,"section_name":"01 发刊词：创伤自我疗愈","section_intro":"","duration":1000,"is_audition":1},{"sectionId":111,"courseId":10,"sectionName":"01 发刊词：创伤自我疗愈","sectionIntro":"","duration":1000,"isAudition":1}]
     * comments : [{"mobile":"13776885597","nickname":"zhuzhu","heade_img":"","content":"","like_count":10000,"is_like":1,"create_date":"","reply":[{"mobile":"13776885597","nickname":"zhuzhu","heade_img":"","content":"","create_date":""}]},{"mobile":"13776885597","nickname":"zhuzhu","heade_img":"","content":"","like_count":10000,"is_like":1,"create_date":"","reply":[{"mobile":"13776885597","nickname":"zhuzhu","heade_img":"","content":"","create_date":""}]},{"mobile":"13776885597","nickname":"zhuzhu","heade_img":"","content":"","like_count":10000,"is_like":1,"create_date":"","reply":[{"mobile":"13776885597","nickname":"zhuzhu","heade_img":"","content":"","create_date":""}]}]
     * recommendCourses : [{"id":10,"course_name":"100","image_url":"","image_small_url":"","price":10,"sale_price":9,"join_count":10000,"master_name":"张飞"},{"id":10,"course_name":"100","image_url":"","image_small_url":"","price":10,"sale_price":9,"join_count":10000,"master_name":"张飞"},{"id":10,"course_name":"100","image_url":"","image_small_url":"","price":10,"sale_price":9,"join_count":10000,"master_name":"张飞"},{"id":10,"course_name":"100","image_url":"","image_small_url":"","price":10,"sale_price":9,"join_count":10000,"master_name":"张飞"}]
     */

    private CourseBean course;
    private List<SectionsBean> sections;
    private List<CommentsBean> comments;
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

    public List<CommentsBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentsBean> comments) {
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
         * id : 10
         * course_name : 100
         * image_url :
         * image_small_url :
         * price : 10
         * sale_price : 9
         * join_count : 10000
         * master_name : 张飞
         * course_intro : 一幅画千言万语，一棵树别样人生
         * activity_price : 8
         * media_type : 1
         * is_pay : 1
         * master_id : 100
         * is_checkout : 1
         * course_detail :
         * share_image_url :
         * share_url :
         */

        private int id;
        private String course_name;
        private String image_url;
        private String image_small_url;
        private int price;
        private int sale_price;
        private int join_count;
        private String master_name;
        private String course_intro;
        private int activity_price;
        private int media_type;
        private int is_pay;
        private int master_id;
        private int is_checkout;
        private String course_detail;
        private String share_image_url;
        private String share_url;

        public int getId() {
            return id;
        }

        public void setId(int id) {
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

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getSale_price() {
            return sale_price;
        }

        public void setSale_price(int sale_price) {
            this.sale_price = sale_price;
        }

        public int getJoin_count() {
            return join_count;
        }

        public void setJoin_count(int join_count) {
            this.join_count = join_count;
        }

        public String getMaster_name() {
            return master_name;
        }

        public void setMaster_name(String master_name) {
            this.master_name = master_name;
        }

        public String getCourse_intro() {
            return course_intro;
        }

        public void setCourse_intro(String course_intro) {
            this.course_intro = course_intro;
        }

        public int getActivity_price() {
            return activity_price;
        }

        public void setActivity_price(int activity_price) {
            this.activity_price = activity_price;
        }

        public int getMedia_type() {
            return media_type;
        }

        public void setMedia_type(int media_type) {
            this.media_type = media_type;
        }

        public int getIs_pay() {
            return is_pay;
        }

        public void setIs_pay(int is_pay) {
            this.is_pay = is_pay;
        }

        public int getMaster_id() {
            return master_id;
        }

        public void setMaster_id(int master_id) {
            this.master_id = master_id;
        }

        public int getIs_checkout() {
            return is_checkout;
        }

        public void setIs_checkout(int is_checkout) {
            this.is_checkout = is_checkout;
        }

        public String getCourse_detail() {
            return course_detail;
        }

        public void setCourse_detail(String course_detail) {
            this.course_detail = course_detail;
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
         * id : 111
         * course_id : 10
         * section_name : 01 发刊词：创伤自我疗愈
         * section_intro :
         * duration : 1000
         * is_audition : 1
         * sectionId : 111
         * courseId : 10
         * sectionName : 01 发刊词：创伤自我疗愈
         * sectionIntro :
         * isAudition : 1
         */

        private int id;
        private int course_id;
        private String section_name;
        private String section_intro;
        private int duration;
        private int is_audition;
        private int sectionId;
        private int courseId;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCourse_id() {
            return course_id;
        }

        public void setCourse_id(int course_id) {
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

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public int getIs_audition() {
            return is_audition;
        }

        public void setIs_audition(int is_audition) {
            this.is_audition = is_audition;
        }

        public int getSectionId() {
            return sectionId;
        }

        public void setSectionId(int sectionId) {
            this.sectionId = sectionId;
        }

        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

    }

    public static class CommentsBean {
        /**
         * mobile : 13776885597
         * nickname : zhuzhu
         * heade_img :
         * content :
         * like_count : 10000
         * is_like : 1
         * create_date :
         * reply : [{"mobile":"13776885597","nickname":"zhuzhu","heade_img":"","content":"","create_date":""}]
         */

        private String mobile;
        private String nickname;
        private String heade_img;
        private String content;
        private int like_count;
        private int is_like;
        private String create_date;
        private List<ReplyBean> reply;

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getHeade_img() {
            return heade_img;
        }

        public void setHeade_img(String heade_img) {
            this.heade_img = heade_img;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getLike_count() {
            return like_count;
        }

        public void setLike_count(int like_count) {
            this.like_count = like_count;
        }

        public int getIs_like() {
            return is_like;
        }

        public void setIs_like(int is_like) {
            this.is_like = is_like;
        }

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }

        public List<ReplyBean> getReply() {
            return reply;
        }

        public void setReply(List<ReplyBean> reply) {
            this.reply = reply;
        }

        public static class ReplyBean {
            /**
             * mobile : 13776885597
             * nickname : zhuzhu
             * heade_img :
             * content :
             * create_date :
             */

            private String mobile;
            private String nickname;
            private String heade_img;
            private String content;
            private String create_date;

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getHeade_img() {
                return heade_img;
            }

            public void setHeade_img(String heade_img) {
                this.heade_img = heade_img;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getCreate_date() {
                return create_date;
            }

            public void setCreate_date(String create_date) {
                this.create_date = create_date;
            }
        }
    }

    public static class RecommendCoursesBean {
        /**
         * id : 10
         * course_name : 100
         * image_url :
         * image_small_url :
         * price : 10
         * sale_price : 9
         * join_count : 10000
         * master_name : 张飞
         */

        private int id;
        private String course_name;
        private String image_url;
        private String image_small_url;
        private int price;
        private int sale_price;
        private int join_count;
        private String master_name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
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

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getSale_price() {
            return sale_price;
        }

        public void setSale_price(int sale_price) {
            this.sale_price = sale_price;
        }

        public int getJoin_count() {
            return join_count;
        }

        public void setJoin_count(int join_count) {
            this.join_count = join_count;
        }

        public String getMaster_name() {
            return master_name;
        }

        public void setMaster_name(String master_name) {
            this.master_name = master_name;
        }
    }
}