package com.gome.wa.util;

import java.util.Date;

/**
 * 指定查询时间工具类
 * Created by jiangtao7 on 2017/6/29.
 */
public class SpQueryTimeUtil {
    public static String startTime;
    public static String lastStartTime = "1970-01-01";;
    public static String endTime;
    public static String lastEndTime = "1970-01-01";
    public static String duration;

    public static String getDuration() {
        return duration;
    }

    public static void setDuration(String duration) {
        SpQueryTimeUtil.duration = duration;
    }

    /**
     * 获取查询开始时间
     *
     * @param queryType 指定查询的类型
     * @return
     */
    public static String getStartTime(String queryType) {
        String startTime = null;
        if (queryType.equals(Constant.QUERY_7DAYS)) {
            startTime = DateUtils.getDaysTimeBeforeTargetTime(DateUtils.DEFAULT_DATE_FORMAT, getEndTime(queryType), Constant.DAYS_7);
        } else if (queryType.equals(Constant.QUERY_30DAYS)) {
            startTime = DateUtils.getDaysTimeBeforeTargetTime(DateUtils.DEFAULT_DATE_FORMAT, getEndTime(queryType), Constant.DAYS_30);
        } else if (queryType.equals(Constant.QUERY_SELFDAYS)) {
            startTime = SpQueryTimeUtil.startTime;
        } else {

        }
        return startTime;
    }

    /**
     * 获取查询结束时间
     *
     * @return
     */
    public static String getEndTime(String queryType) {
        String endTime = null;
        if (queryType.equals(Constant.QUERY_SELFDAYS)) {
            endTime = SpQueryTimeUtil.endTime;
        } else {
            String today = DateUtils.toDefDateString(new Date());
            endTime = DateUtils.getYesTodayStr(today);
        }
        return endTime;
    }

    public static String getEndTime() {
        String today = DateUtils.toDefDateString(new Date());
        String endTime = DateUtils.getYesTodayStr(today);
        return endTime;
    }

    public static String getLastStartTime() {
        return lastStartTime;
    }

    public static void setLastStartTime(String lastStartTime) {
        SpQueryTimeUtil.lastStartTime = lastStartTime;
    }

    public static String getLastEndTime() {
        return lastEndTime;
    }

    public static void setLastEndTime(String lastEndTime) {
        SpQueryTimeUtil.lastEndTime = lastEndTime;
    }
}
