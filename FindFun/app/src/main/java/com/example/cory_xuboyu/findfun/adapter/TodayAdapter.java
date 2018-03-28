package com.example.cory_xuboyu.findfun.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.cory_xuboyu.findfun.R;
import com.example.cory_xuboyu.findfun.bean.TodayOfHistoryBean;

/**
 * 作用：历史上的今天适配器
 * Created by xuboyu on 2018/3/27.
 * e-mail:boyu274572505@qq.com
 */

public class TodayAdapter extends BaseQuickAdapter<TodayOfHistoryBean.ResultBean, BaseViewHolder> {


    public TodayAdapter() {
        super(R.layout.item_today);
    }

    @Override
    protected void convert(BaseViewHolder helper, TodayOfHistoryBean.ResultBean item) {
        helper.setText(R.id.tv_today_title, item.getTitle());
        helper.setText(R.id.tv_today_date, item.getDate());
        helper.addOnClickListener(R.id.ll_today_detail);
    }
}
