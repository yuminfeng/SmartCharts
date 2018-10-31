package com.yumf.jince.smartcharts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yumf.jince.library.Data;
import com.yumf.jince.library.SimpleChart;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Data> cat = new ArrayList<>();

        cat.add(new Data("今日",-100));
        cat.add(new Data("近5日",80));
        cat.add(new Data("近20日",50));
        cat.add(new Data("近60日",-10));

        SimpleChart chart = findViewById(R.id.chart);
        chart.setCategoryData(cat);
    }
}
