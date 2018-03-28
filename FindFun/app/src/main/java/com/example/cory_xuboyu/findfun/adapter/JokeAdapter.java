package com.example.cory_xuboyu.findfun.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.cory_xuboyu.findfun.R;
import com.example.cory_xuboyu.findfun.bean.JokeBean;


/**
 * 作用：段子适配器
 * Created by xuboyu on 2018/3/27.
 * e-mail:boyu274572505@qq.com
 */

public class JokeAdapter extends BaseQuickAdapter<JokeBean.ResultBean.DataBean, BaseViewHolder> {

    public JokeAdapter() {
        super(R.layout.item_joke);
    }

    @Override
    protected void convert(BaseViewHolder helper, JokeBean.ResultBean.DataBean item) {
        helper.setText(R.id.tv_joke_content, item.getContent());
        helper.setText(R.id.tv_joke_date, item.getUpdatetime());
    }

}
