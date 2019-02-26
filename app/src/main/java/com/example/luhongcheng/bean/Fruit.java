package com.example.luhongcheng.bean;

public class Fruit {

    private String name;

    private int imageId;

    public Fruit(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public Fruit(String name, int imageId,String subtitle) {
        this.name = name;
        this.imageId = imageId;
        this.subtitle = subtitle;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    String subtitle;

}
