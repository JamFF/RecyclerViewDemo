package com.jam.ff.recyclerview.bean;

/**
 * Created by jamff on 2018/2/5 14:21.
 */

public class DataBean {

    public DataBean(String text, int height) {
        this.text = text;
        this.height = height;
    }

    private String text;

    private int height;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "DataBean{" +
                "text='" + text + '\'' +
                ", height=" + height +
                '}';
    }
}
