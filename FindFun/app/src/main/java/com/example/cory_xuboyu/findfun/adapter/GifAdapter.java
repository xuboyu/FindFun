package com.example.cory_xuboyu.findfun.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.blankj.utilcode.utils.LogUtils;
import com.bumptech.glide.Glide;

import com.example.cory_xuboyu.findfun.R;
import com.example.cory_xuboyu.findfun.bean.GIFBean;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * 作用：CIF适配器
 * Created by xuboyu on 2018/3/26.
 * e-mail:boyu274572505@qq.com
 */

public class GifAdapter extends CommonAdapter<GIFBean.ResultBean> {
    private Context context;

    public GifAdapter(Context context, List<GIFBean.ResultBean> datas) {
        super(context, R.layout.item_gif, datas);
        this.context = context;
    }

    @Override
    protected void convert(ViewHolder holder, GIFBean.ResultBean gifBean, int position) {
        holder.setText(R.id.tv_gif_title, gifBean.getContent());
        String url = gifBean.getUrl();
        LogUtils.i("url?:" + url);
        if (url.endsWith("f")) {
            Glide.with(context)
                 .load(gifBean.getUrl())
                 .asGif()
                 .placeholder(R.mipmap.ic_error)
                 .into((ImageView) holder.getView(R.id.iv_gif));
        } else {
            Glide.with(context)
                 .load(gifBean.getUrl())
                 .placeholder(R.mipmap.ic_error)
                 .into((ImageView) holder.getView(R.id.iv_gif));
        }
    }
}
