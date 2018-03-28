package com.example.cory_xuboyu.findfun.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * 作用：MessageBean
 * Created by xuboyu on 2018/3/27.
 * e-mail:boyu274572505@qq.com
 */

public class MessageBean implements Parcelable {

    public int type;// 消息类型
    public String content;// 消息内容



    protected MessageBean(Parcel in) {
        this.type = in.readInt();
        this.content = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(type);
        dest.writeString(content);
    }

    public static final Creator<MessageBean> CREATOR = new Creator<MessageBean>() {
        @Override
        public MessageBean createFromParcel(Parcel in) {
            return new MessageBean(in);
        }

        @Override
        public MessageBean[] newArray(int size) {
            return new MessageBean[size];
        }
    };
}