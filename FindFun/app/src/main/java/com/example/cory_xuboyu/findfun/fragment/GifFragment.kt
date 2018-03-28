package com.example.cory_xuboyu.findfun.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.cory_xuboyu.findfun.R
import com.example.cory_xuboyu.findfun.adapter.GifAdapter
import com.example.cory_xuboyu.findfun.commons.Constants
import com.example.cory_xuboyu.findfun.net.QClitent
import com.example.cory_xuboyu.findfun.net.QNewsService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_gif.view.*

/**
 * 作用：趣图界面
 * Created by xuboyu on 2018/3/26.
 * e-mail:boyu274572505@qq.com
 */
class GifFragment : Fragment() {

    private var adapter: GifAdapter? = null

    override fun onCreateView(
            inflater: LayoutInflater?, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_gif, null)
        view.rv_gif!!.layoutManager = LinearLayoutManager(activity)
        QClitent.getInstance()
                .create(QNewsService::class.java, Constants.BASE_JUHE_URL)
                .gifRandomData
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ gifBean ->
                    val result = gifBean.result
                    adapter = GifAdapter(activity, result)
                    view.rv_gif!!.adapter = adapter
                    adapter!!.notifyDataSetChanged()
                }) { throwable ->
                    Toast.makeText(activity, "获取数据失败", Toast.LENGTH_SHORT).show()
                    Log.e("----->", "error accept:${throwable.message}")
                }
        return view
    }


}