package com.example.cory_xuboyu.findfun.net;

import com.example.cory_xuboyu.findfun.bean.GIFBean;
import com.example.cory_xuboyu.findfun.bean.JokeBean;
import com.example.cory_xuboyu.findfun.bean.NewsDataBean;
import com.example.cory_xuboyu.findfun.bean.TodayOfHistoryBean;
import com.example.cory_xuboyu.findfun.bean.TodayOfHistoryDetailBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 作用：服务端接口类
 * Created by xuboyu on 2018/3/27.
 * e-mail:boyu274572505@qq.com
 */

public interface QNewsService {

    public static final String DESC = "desc"; // 指定时间之前发布的
    public static final String ASC = "asc";   // 指定时间之后发布的


    /**
     * @param page      查询的页数
     * @param pagesize  一页数据显示的条数
     * @return          查询结束返回的被观察者
     */
    // http://japi.juhe.cn/joke/content/text.from?key=ae240f7fba620fc370b803566654949e
    @POST("text.from?key=ae240f7fba620fc370b803566654949e")
    Observable<JokeBean> getCurrentJokeData(
            @Query("page") int page,
            @Query("pagesize") int pagesize
    );


    /**
     * @param time          要指定查询的时间
     * @param page          查询的页数
     * @param pagesize      一页数据显示的条数
     * @param sort          判断是在指定时间之前还是之后
     *                          {@value DESC 指定之前},{@value ASC 指定之后}
     * @return              查询结束返回的被观察者
     */
    // http://japi.juhe.cn/joke/content/list.from?key=ae240f7fba620fc370b803566654949e&page=1&pagesize=5&sort=desc
    @GET("list.from?key=ae240f7fba620fc370b803566654949e")
    Observable<JokeBean> getAssignJokeData(
            @Query("time") long time,
            @Query("page") int page,
            @Query("pagesize") int pagesize,
            @Query("sort") String sort
    );


    /**
     * 根据传过来的 event id 来查询 历史上的今天详情数据
     *
     * @param e_id  事件的 id
     * @return      查询结束 返回历史上今天的 详情数据 被观察者
     */
    // http://v.juhe.cn/todayOnhistory/queryDetail.php?key=f5f7d655ef148f6bb777c80167f7f6de
    @GET("todayOnhistory/queryDetail.php?key=f5f7d655ef148f6bb777c80167f7f6de")
    Observable<TodayOfHistoryDetailBean> getTodayOfHistoryDetailData(
            @Query("e_id") String e_id
    );

    /**
     * 根据日期获取历史上的今天数据
     *
     * @param date  当前日期
     * @return      查询结束返回历史上今天数据的被观察者
     */
    // http://v.juhe.cn/todayOnhistory/queryEvent.php?key=f5f7d655ef148f6bb777c80167f7f6de
    @GET("todayOnhistory/queryEvent.php?key=f5f7d655ef148f6bb777c80167f7f6de")
    Observable<TodayOfHistoryBean> getTodayOfHistoryData(
            @Query("date") String date
    );

    /**
     * 根据 新闻类型 获取新闻数据
     *
     * @param type  新闻的类型
     * @return      查询结束返回数据的被观察者
     */
    // http://v.juhe.cn/toutiao/index?key=d78b502268f7456b79fbe7228cecdd46
    @GET("toutiao/index?key=d78b502268f7456b79fbe7228cecdd46")
    Observable<NewsDataBean> getNewsData(
            @Query("type") String type
    );

    /**
     * @return 返回随机的动态图的被观察者
     */
    // http://v.juhe.cn/joke/randJoke.php?key=ae240f7fba620fc370b803566654949e&type=pic
    @GET("joke/randJoke.php?key=ae240f7fba620fc370b803566654949e&type=pic")
    Observable<GIFBean> getGIFRandomData();
}

