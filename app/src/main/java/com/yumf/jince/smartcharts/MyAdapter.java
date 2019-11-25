package com.yumf.jince.smartcharts;

import android.support.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends BaseQuickAdapter<Action, BaseViewHolder> {

    public static List<Action> list = getDatas();

    public MyAdapter() {
        super(R.layout.item_list_view, list);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Action item) {
        helper.setText(R.id.item, item.getItem());
        helper.setText(R.id.item1, item.getItem1());
    }

    public static List<Action> getDatas() {
        List<Action> list = new ArrayList<>();
        list.add(new Action("第一个item", "sub item", ""));
        list.add(new Action("litter sub item", "", ""));

        list.add(new Action("第二个item", "sub item", ""));
        list.add(new Action("第三个item", "sub item", ""));
        return list;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}
