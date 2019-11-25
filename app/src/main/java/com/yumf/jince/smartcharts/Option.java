package com.yumf.jince.smartcharts;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jince on 2017/4/12.
 */

public class Option {

    private String date;
    private String total_equity;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotal_equity() {
        return total_equity;
    }

    public void setTotal_equity(String total_equity) {
        this.total_equity = total_equity;
    }

    @Override
    public String toString() {
        return "Option{" +
                "date='" + date + '\'' +
                ", total_equity='" + total_equity + '\'' +
                '}';
    }
}
