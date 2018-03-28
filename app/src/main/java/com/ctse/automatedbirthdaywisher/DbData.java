package com.ctse.automatedbirthdaywisher;

/**
 * Created by Madupoorna on 3/28/2018.
 */

public class DbData {

    int id, flag;
    byte[] img;
    String phoneNumber, date, time, msg, name;

    public DbData() {
    }

    public DbData(int id, byte[] img, String phoneNumber, String date, String time, String msg, String name) {
        this.id = id;
        this.img = img;
        this.phoneNumber = phoneNumber;
        this.date = date;
        this.time = time;
        this.name = name;
        this.msg = msg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }
}
