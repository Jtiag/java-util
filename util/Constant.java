
public interface Constant {
	public String SHOP_USER_ID="SHOP_USER_ID";
	public String LEADER_LOGGED_ON="LEADER_LOGGED_ON";//leader登录标识
	
	public String LEADER_LEVEL="LEADER_LEVEL";
	public String LEADER_CODE="LEADER_CODE";

	/**
	 * druid 查询 粒度
	 */
	String DRUID_SECOND="second";
	String DRUID_MINUTE="minute";
	String DRUID_FIFTEEN_MINUTE="fifteen_minute";
	String DRUID_THIRTY_MINUTE="thirty_minute";
	String DRUID_HOUR="hour";
	String DRUID_DAY="day";
	String DRUID_WEEK="week";
    String DRUID_MONTH="month";
    String DRUID_QUARTER="quarter";// 季度
    String DRUID_YEAR="year";
    String DRUID_ALL="all";
    String DRUID_NONE="none";

    int DAY_HOUR = 24;

	/**
	 * 平台查询类型
	 */
	String ALL_DOWNLOAD = "all";
	String ANDROID_DOWNLOAD = "android";
	String IOS_DOWNLOAD = "ios";
	/**
	 * 查询时间指定
	 */
	int DAYS_30 = 30;
	int DAYS_7 = 7;
    /**
     * 查询类型
     */
    String QUERY_7DAYS = "7days";
    String QUERY_30DAYS = "30days";
    String QUERY_SELFDAYS = "selfdays";

    /**
     * 时间段 ms
     */
    Long DURATION_2HOURS = 7200000L;
    Long DURATION_3HOURS = 10800000L;
    Long DURATION_4HOURS = 14400000L;
    /**
     * druid metric name
     */
    String METRIC_NAME_COUNT = "count";
    /**
     * 时间查询返回结果的显示name
     */
    String DISPLAY_UV = "uv";
    String DISPLAY_PV = "pv";
    String DISPLAY_COUNT = "count";
    String DISPLAY_USER_TIME = "usetime";
    String DISPLAY_EVT_ID_NAME = "evt_id_name_count";
    // 浏览，点赞，收藏，分享
    String DISPLAY_VIEW = "view";
    String DISPLAY_LIKE = "like";
	String DISPLAY_COLLECT = "collect";
	String DISPLAY_SHARE = "share";
    /**
     * eventName value
     */
    String EVTNAME_VIEW_VALUE = "view";
    String EVTNAME_LIKE_VALUE = "like";
    String EVTNAME_COLLECT_VALUE = "collect";
    String EVTNAME_SHARE_VALUE = "share";

    String EVENT_NAME = "evtName";
    /**
     * article相关
     */
    String EVENT= "event";
    String MALL = "MALL";
    String EVENT_ARTICLE = "ARTICLE";
    String ARTICLE_ID = "articleID";
    Integer ARTICLE_STATUS_OFF = 0;
    Integer ARTICLE_STATUS_ON = 1;
    Integer AUTO_EXPIRE_MINUTE_IDLIST= 5;//过期时间
    Integer AUTO_EXPIRE_MINUTE_PAGE = 5;//过期时间
    Integer AUTO_EXPIRE_EXPORT_MINUTE= 5;//过期时间
    Integer AUTO_EXPIRE_EXPORT_SECONDS= 300;//过期时间
    String REDIS_ARTICLE = "article_redis_1";
    String REDIS_ARTICLE_EXPORT = "article_redis_export";
    String REDIS_ARTICLE_PAGE = "article_redis_1_page";//cache the page num
    String ARTICLETITLE_EVTNAME = "articleTitleEvtName";//compose key for query  id+evtName
    String ARTICLE_TOTAL_LIST= "articleDataList";//返回前端展示的文章统计详情
    String ARTICLE_TOTAL_COUNT= "totalItems";//总的统计数
    String ARTICLE_EXPORT_REDIS_KEY= "article_exprot_redis_1";//导出rediskey

   /** mall 相关
     */
    String MALL_EVTNAME = "evtName"; // evtName:'分类-1,Banner-1', //事件id,事件名
	/**
	 * topn查询的 threshold
	 */
	int THRESHOLD_10000 = 10000;
	int THRESHOLD_1000 = 1000;
	int THRESHOLD_500 = 500;
	int THRESHOLD_100 = 100;
	int THRESHOLD_50 = 50;
	/**
	 * ms转化为小时的除数因子
	 */
//	int MS_TO_HOUR_FACTOR_DAY = 86400000;//1000*60*60*24
	int MS_TO_HOUR_FACTOR = 3600000;//1000*60*60
    /**
     * time duration
     */
    int DURATION_2H = 2;
    int DURATION_3H = 3;
    int DURATION_4H = 4;
    int BOUNDARY_2H = 8;
    int BOUNDARY_3H = 9;
    int BOUNDARY_4H = 8;

    String TOTAL_ITEMS = "totalItems";
    String PV_UV_AVGCLICK_LIST = "pvUvAvgClickList";
}
