package com.example.cory_xuboyu.findfun.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.cory_xuboyu.findfun.R;
import com.example.cory_xuboyu.findfun.bean.NewsDataBean;

/**
 * 作用：新闻适配器
 * Created by xuboyu on 2018/3/26.
 * e-mail:boyu274572505@qq.com
 */

public class NewsDataAdapter
        extends BaseQuickAdapter<NewsDataBean.ResultBean.DataBean, BaseViewHolder> {

    public NewsDataAdapter() {
        super(R.layout.item_news_detail);
    }

    @Override
    protected void convert(BaseViewHolder holder, NewsDataBean.ResultBean.DataBean dataBean) {
        holder.setText(R.id.tv_news_detail_title, dataBean.getTitle());
        holder.setText(R.id.tv_news_detail_author_name, dataBean.getAuthor_name());
        holder.setText(R.id.tv_news_detail_date, dataBean.getDate());
        holder.addOnClickListener(R.id.ll_news_detail);
        Glide.with(mContext)
             .load(dataBean.getThumbnail_pic_s())
             .placeholder(R.mipmap.ic_error)
             .error(R.mipmap.ic_error)
             .crossFade()
             .centerCrop()
             .into((ImageView) holder.getView(R.id.iv_news_detail_pic));
    }
}
