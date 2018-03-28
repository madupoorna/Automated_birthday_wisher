package com.ctse.automatedbirthdaywisher;

import android.graphics.Bitmap;

/**
 * Created by Madupoorna on 3/20/2018.
 */

public class Contact {

    private Bitmap image;
    private String name;
    private String number;

    public Contact(Bitmap image, String name, String number) {
        this.image = image;
        this.name = name;
        this.number = number;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
