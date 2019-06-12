package com.tuodanhuashu.app.course.bean;

import java.util.List;

public  class CommentBean {
    /**
     * mobile : 13815030973
     * nickname : 东东
     * heade_img : https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83erqTAeZqj2xX0M803XtyibGkaxJnRweYk86h0tFXiaTHWY04Mj9sf9GhGDlNmaB0PhUU1ibrKxpaRP3w/132
     * content : 很好的啊
     * like_count : 1
     * is_like : 2
     * create_date : 1970-01-01
     * reply : {"mobile":"13815030973","nickname":"东东","heade_img":"https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83erqTAeZqj2xX0M803XtyibGkaxJnRweYk86h0tFXiaTHWY04Mj9sf9GhGDlNmaB0PhUU1ibrKxpaRP3w/132","content":"时的很好的啊","create_date":"1970-01-01"}
     */
    private String id;
    private String mobile;
    private String nickname;
    private String heade_img;
    private String content;
    private String like_count;
    private String is_like;
    private String create_date;
    private List<ReplyBean> reply;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getLike_count() {
        return like_count;
    }

    public void setLike_count(String like_count) {
        this.like_count = like_count;
    }

    public String getIs_like() {
        return is_like;
    }

    public void setIs_like(String is_like) {
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
         * mobile : 13815030973
         * nickname : 东东
         * heade_img : https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83erqTAeZqj2xX0M803XtyibGkaxJnRweYk86h0tFXiaTHWY04Mj9sf9GhGDlNmaB0PhUU1ibrKxpaRP3w/132
         * content : 时的很好的啊
         * create_date : 1970-01-01
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

