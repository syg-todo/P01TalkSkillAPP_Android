package com.tuodanhuashu.app.course.bean;

import java.util.List;

public class SectionBean {


    /**
     * media_type : 1
     * url :
     * section_name : 第一章
     * section_intro : &lt;p&gt;情感：生活现象与人心的相互作用下，产生的感受。&lt;/p&gt;&lt;p&gt;情感客观的方面：生活现象中蕴含了情感，人的大脑可以感受到这种情感。&lt;/p&gt;&lt;p&gt;情感主观的方面：第一，即使过同样的生活，美好的人和丑恶的人对生活的感觉不同，第二，受到负面情感刺激而心情不好时，感受情感的能力就下降了。&lt;/p&gt;&lt;p&gt;因此，情感是生活现象与人心共同决定的，而不能单独由某一方来定。&lt;/p&gt;&lt;p&gt;情感是人对现实的一种比较固定的态度，它表现为与人的个性、道德经验等有关的各种体验之中。&lt;span class=&quot;sup--normal&quot; data-sup=&quot;3&quot; data-ctrmap=&quot;:3,&quot; style=&quot;font-size: 10.5px; line-height: 0; position: relative; vertical-align: baseline; top: -0.5em; margin-left: 2px; color: rgb(51, 102, 204); cursor: pointer; padding: 0px 2px;&quot;&gt;&amp;nbsp;[3]&lt;/span&gt;&lt;a style=&quot;color: rgb(19, 110, 194); position: relative; top: -50px; font-size: 0px; line-height: 0;&quot; name=&quot;ref_[3]_19222975&quot;&gt;&lt;/a&gt;&amp;nbsp;&lt;/p&gt;&lt;p&gt;我们常常说“感觉”这个词，例如：一对恋人分手了，理由是彼此对彼此没有感觉了，再例如：活的麻木了，已经对生活没感觉了。感觉就是从生活现象里感受到的情感，这是由生活现象和人心两方面共同决定的情感。&lt;/p&gt;&lt;p&gt;&lt;br/&gt;&lt;/p&gt;
     * course_id : 3
     * comments : [{"mobile":"13815030973","nickname":"东东","heade_img":"https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83erqTAeZqj2xX0M803XtyibGkaxJnRweYk86h0tFXiaTHWY04Mj9sf9GhGDlNmaB0PhUU1ibrKxpaRP3w/132","content":"很好的啊","like_count":"1","is_like":2,"create_date":"1970-01-01","reply":{"mobile":"13815030973","nickname":"东东","heade_img":"https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83erqTAeZqj2xX0M803XtyibGkaxJnRweYk86h0tFXiaTHWY04Mj9sf9GhGDlNmaB0PhUU1ibrKxpaRP3w/132","content":"时的很好的啊","create_date":"1970-01-01"}}]
     * price : 400.00
     * sale_price : 300.00
     * activity_price : 100.00
     */

    private String media_type;
    private String url;
    private String section_name;
    private String section_intro;
    private String course_id;
    private String price;
    private String sale_price;
    private String activity_price;
    private List<CommentBean> comments;

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
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

    public String getActivity_price() {
        return activity_price;
    }

    public void setActivity_price(String activity_price) {
        this.activity_price = activity_price;
    }

    public List<CommentBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentBean> comments) {
        this.comments = comments;
    }

}
