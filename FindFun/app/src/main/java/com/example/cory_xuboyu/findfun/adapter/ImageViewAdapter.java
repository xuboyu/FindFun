package com.example.cory_xuboyu.findfun.adapter;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * 作用：图片适配器
 * Created by xuboyu on 2018/3/27.
 * e-mail:boyu274572505@qq.com
 */

public class ImageViewAdapter {

    @BindingAdapter({"img_url"})
    public static void loadImageView(ImageView iv, String url) {
        Glide.with(iv.getContext()).load(url).centerCrop().into(iv);
    }
}
