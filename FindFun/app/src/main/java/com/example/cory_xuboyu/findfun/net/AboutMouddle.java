package com.example.cory_xuboyu.findfun.net;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;

import com.example.cory_xuboyu.findfun.BR;

/**
 * 作用：关于界面
 * Created by xuboyu on 2018/3/27.
 * e-mail:boyu274572505@qq.com
 */

public class AboutMouddle extends BaseObservable {
    private String imgUrl;

    @Bindable
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        notifyPropertyChanged(BR.imgUrl);
    }

    @NonNull
    public String getString(String text) {
        String[] split = text.split(":");
        return split[1].trim() + ":" + split[2].trim();
    }
}
