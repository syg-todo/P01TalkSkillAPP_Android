package com.tuodanhuashu.app.course.bean;

public class VideoBean {
    private String name;
    private String image;
    private String title;
    private int volume;
    private String avatar;

    public VideoBean(String name, String image, String title, int volume, String avatar) {
        this.name = name;
        this.image = image;
        this.title = title;
        this.volume = volume;
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
