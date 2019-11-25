package com.yumf.jince.smartcharts;

public class Action {

    private String item;
    private String item1;
    private String item2;

    public Action(String item, String item1, String item2) {
        this.item = item;
        this.item1 = item1;
        this.item2 = item2;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getItem1() {
        return item1;
    }

    public void setItem1(String item1) {
        this.item1 = item1;
    }

    public String getItem2() {
        return item2;
    }

    public void setItem2(String item2) {
        this.item2 = item2;
    }
}
