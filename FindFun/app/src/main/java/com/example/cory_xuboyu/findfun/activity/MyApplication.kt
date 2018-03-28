package com.example.cory_xuboyu.findfun.activity

import android.app.Application
import com.blankj.utilcode.utils.Utils
import com.zhy.http.okhttp.OkHttpUtils
import com.zhy.http.okhttp.log.LoggerInterceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/**
 * 作用：初始化 okhttp
 * Created by xuboyu on 2018/3/27.
 * e-mail:boyu274572505@qq.com
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Utils.init(applicationContext)

        //JPushInterface.setDebugMode(false)
        //JPushInterface.init(this)

        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS).build()

        OkHttpUtils.initClient(okHttpClient)
    }
}