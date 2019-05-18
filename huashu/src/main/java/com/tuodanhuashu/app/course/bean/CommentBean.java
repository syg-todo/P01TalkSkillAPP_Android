package com.tuodanhuashu.app.course.bean;

public class CommentBean {
    private String avatar;
    private String name;
    private String time;
    private String comment;
    private int like_count;

    public CommentBean(String avatar, String name, String time, String comment, int like_count) {
        this.avatar = avatar;
        this.name = name;
        this.time = time;
        this.comment = comment;
        this.like_count = like_count;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }
}
