package com.yumf.jince.smartcharts;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yumf.jince.smartcharts.view.LineChartView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String TAG = "yumf";
    private TabLayout tabLayout;

    private LineChartView lineChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lineChartView = findViewById(R.id.linechart);
        tabLayout = findViewById(R.id.tab_layout);
        /**
         *  step 1: add new tab
         */
        tabLayout.addTab(tabLayout.newTab().setText("tab1"), false);
        tabLayout.addTab(tabLayout.newTab().setText("tab2"), false);
        tabLayout.addTab(tabLayout.newTab().setText("tab3"), false);
        tabLayout.addTab(tabLayout.newTab().setText("tab4"), false);
        tabLayout.addTab(tabLayout.newTab().setText("tab5"), false);

        /**
         *  step 2: addOnTabSelectedListener
         */
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.e(TAG, "onTabSelected: " + tab.getText() + " ,count:" + tabLayout.getTabCount());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.e(TAG, "onTabUnselected: " + tab.getText() + " ,count:" + tabLayout.getTabCount());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.e(TAG, "onTabReselected: " + tab.getText() + " ,count:" + tabLayout.getTabCount());
            }
        });

        /**
         * step 3: select
         */
        tabLayout.getTabAt(0).select();
        List<Option> list = getFilterList(this);
        lineChartView.setDataList(list);
    }

    public List<Option> getFilterList(Context context) {
        List<Option> list = null;
        try {
            String json = getStringFromAssets(context, "data/demo.json");
            Gson gson = new Gson();
            Type type = new TypeToken<List<Option>>() {
            }.getType();
            list = gson.fromJson(json, type);
            Log.e(TAG, "jsonArray: " + list.toString());
        } catch (Exception e) {
            Log.e(TAG, "getMarketTabList", e);
        }
        return list;
    }

    public String getStringFromAssets(Context context, String filename) {
        InputStream in = null;
        try {
            in = context.getAssets().open(filename);
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[4096];
            int count = -1;
            while ((count = in.read(data, 0, 4096)) != -1)
                outStream.write(data, 0, count);
            return new String(outStream.toByteArray(), "utf-8");
        } catch (Exception e) {
            Log.e(TAG, "getStringFromAssets", e);
            return null;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                Log.e(TAG, "IOException", e);
            }
        }
    }
}
