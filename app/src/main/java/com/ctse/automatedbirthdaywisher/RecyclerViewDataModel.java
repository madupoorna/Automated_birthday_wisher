package com.ctse.automatedbirthdaywisher;

/**
 * Created by Madupoorna on 3/6/2018.
 */

public class RecyclerViewDataModel {

    private String name;
    private int photo;
    public String color;

    public RecyclerViewDataModel(String name, int photo, String color) {
        this.name = name;
        this.photo = photo;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
