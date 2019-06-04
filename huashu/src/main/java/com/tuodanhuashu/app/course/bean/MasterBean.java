package com.tuodanhuashu.app.course.bean;

import com.tuodanhuashu.app.home.bean.HomeCourseBean;

import java.util.List;

public class MasterBean {

    /**
     * id : 4
     * name : 导师4
     * avatar_url : http://huashu.zhongpin.me/uploads/master/header/201905231659264667.png
     * p_signature : 我还是很喜欢你，把相思藏在床底，梦里是你，醒来也是你。
     * intro : <p><span class="bjh-p" style="color: rgb(153, 153, 153); font-family: arial; font-size: 18px; text-align: justify; background-color: rgb(255, 255, 255);">如果没有遇见他，<span class="bjh-br"></span></span><span class="bjh-p" style="color: rgb(153, 153, 153); font-family: arial; font-size: 18px; text-align: justify; background-color: rgb(255, 255, 255);">这漫漫又匆匆的人生，</span><span class="bjh-p" style="color: rgb(153, 153, 153); font-family: arial; font-size: 18px; text-align: justify; background-color: rgb(255, 255, 255);">拿什么去感受刻骨铭心的回忆</span></p>
     * banner_url : http://huashu.zhongpin.me/uploads/master/banner/201905231659302414.png
     * source : 1
     * create_time : null
     * is_enable : 1
     * is_record : 0
     * share_url : http://huashu.zhongpin.me/api/course/master_info/master_id/4.html
     * course : [{"id":"5","course_name":"课程7","image_url":"http://huashu.zhongpin.me/uploads/course/201905231713111702.png","image_small_url":"http://huashu.zhongpin.me/uploads/course/201905231713152582.png","price":"500.00","sale_price":"300.00","join_count":"0","master_name":"导师4","activity_price":"0.00"},{"id":"6","course_name":"社群课","image_url":"http://huashu.zhongpin.me/uploads/course/201905231715042942.png","image_small_url":"http://huashu.zhongpin.me/uploads/course/201905231715097514.png","price":"500.00","sale_price":"300.00","join_count":"0","master_name":"导师4","activity_price":"0.00"}]
     */

    private String id;
    private String name;
    private String avatar_url;
    private String p_signature;
    private String intro;
    private String banner_url;
    private String source;
    private Object create_time;
    private String is_enable;
    private int is_record;
    private String share_url;
    private List<HomeCourseBean> course;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getP_signature() {
        return p_signature;
    }

    public void setP_signature(String p_signature) {
        this.p_signature = p_signature;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getBanner_url() {
        return banner_url;
    }

    public void setBanner_url(String banner_url) {
        this.banner_url = banner_url;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Object getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Object create_time) {
        this.create_time = create_time;
    }

    public String getIs_enable() {
        return is_enable;
    }

    public void setIs_enable(String is_enable) {
        this.is_enable = is_enable;
    }

    public int getIs_record() {
        return is_record;
    }

    public void setIs_record(int is_record) {
        this.is_record = is_record;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public List<HomeCourseBean> getCourse() {
        return course;
    }

    public void setCourse(List<HomeCourseBean> course) {
        this.course = course;
    }

}
