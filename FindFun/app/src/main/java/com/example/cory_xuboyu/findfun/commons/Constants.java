package com.example.cory_xuboyu.findfun.commons;

/**
 * 作用：接口地址类
 * Created by xuboyu on 2018/3/27.
 * e-mail:boyu274572505@qq.com
 */

public class Constants {

    /**
     * 新闻数据接口
     */
    public static final String NEWS_DATA_URL
            = "http://v.juhe.cn/toutiao/index?key=d78b502268f7456b79fbe7228cecdd46";

    /**
     * 历史上今天接口
     */
    public static final String TODAY_OF_HISTORY_URP
            = "http://v.juhe.cn/todayOnhistory/queryEvent.php?key=f5f7d655ef148f6bb777c80167f7f6de";

    /**
     * 历史上今天详情接口
     */
    public static final String TODAY_OF_HISTORY_DETAIL_URL
            = "http://v.juhe.cn/todayOnhistory/queryDetail.php?key=f5f7d655ef148f6bb777c80167f7f6de";

    /**
     * 段子详情接口
     */
    public static final String JOKE_URL
            = "http://japi.juhe.cn/joke/content/text.from?key=ae240f7fba620fc370b803566654949e";

    public static final String JOKE_DESC_URL
            = "http://japi.juhe.cn/joke/content/list.from?key=ae240f7fba620fc370b803566654949e&page=1&pagesize=5&sort=desc";

    public static final String WEATHER_URL
            = "http://op.juhe.cn/onebox/weather/query?key=ae0fb87a8f219261470673f9a31a0a3b";

    public static final String JOKE_RANDOM_URL
            = "http://v.juhe.cn/joke/randJoke.php?key=ae240f7fba620fc370b803566654949e";

    public static final String GIG_RANDOM_URL
            = "http://v.juhe.cn/joke/randJoke.php?key=ae240f7fba620fc370b803566654949e&type=pic";

    /**
     * 段子 baseUrl
     */
    public static final String BASE_JOKE_URL = "http://japi.juhe.cn/joke/content/";

    /**
     * 聚合 baseUrl
     */
    public static final String BASE_JUHE_URL = "http://v.juhe.cn/";

}
