package com.yumf.jince.library;

/**
 * Created by jince on 31/10/2018.
 */

public class Data {

    private String label;
    private double num;

    public Data(String label, double num) {
        this.label = label;
        this.num = num;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getNum() {
        return num;
    }

    public void setNum(double num) {
        this.num = num;
    }
}
