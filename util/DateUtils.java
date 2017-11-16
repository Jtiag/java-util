import jdk.nashorn.internal.ir.ReturnNode;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.text.*;
import java.util.*;

public class DateUtils {
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DRILL_DATE_FORMAT = "yyyyMMdd";
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";
    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String SHORT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String YYYY_MM_DD_T_HH_MM_SS = "yyyy-MM-dd'T'HH:mm:ss";

    private static final double[] LIMITS = {0, 1, 2};

    private static final String[] MINUTES_PART =
            {"", "1 minute ", "{0,number} minutes "};

    private static final String[] SECONDS_PART =
            {"0 seconds", "1 second", "{1,number} seconds"};

    private static final ChoiceFormat MINUTES_FORMAT =
            new ChoiceFormat(LIMITS, MINUTES_PART);

    private static final ChoiceFormat SECONDS_FORMAT =
            new ChoiceFormat(LIMITS, SECONDS_PART);

    private static final MessageFormat MINUTE_SECONDS =
            new MessageFormat("{0}{1}");

    static {
        MINUTE_SECONDS.setFormat(0, MINUTES_FORMAT);
        MINUTE_SECONDS.setFormat(1, SECONDS_FORMAT);
    }

    public static final long ONE_SECOND = 1000;
    public static final long ONE_MINUTE = 60 * ONE_SECOND;
    public static final long ONE_HOUR = 60 * ONE_MINUTE;
    public static final long ONE_DAY = 24 * ONE_HOUR;
    public static final long ONE_WEEK = 7 * ONE_DAY;


    public static final SimpleDateFormat _defDateTimeFmt =
            new SimpleDateFormat(DEFAULT_DATETIME_FORMAT);

    public static final SimpleDateFormat _defDateFmt =
            new SimpleDateFormat(DEFAULT_DATE_FORMAT);

    public static String toString(Date date, String format) {

        SimpleDateFormat formatter;

        if ((date == null) || (format == null) || (format.length() == 0)) {
            return null;
        }
        formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }

    public static Date toDate(String str, String format) {
        if ((str == null)
                || (str.length() == 0)
                || (format == null)
                || (format.length() == 0)) {
            return null;
        }

        SimpleDateFormat formatter = new SimpleDateFormat(format);
        formatter.setLenient(false);
        ParsePosition pos = new ParsePosition(0);
        return formatter.parse(str, pos);
    }

    public static boolean compare(Date date1, Date date2) {
        if (date1 == null && date2 == null) {
            return true;
        }
        if (date1 == null || date2 == null) {
            return false;
        } else {
            return date1.getTime() == date2.getTime();
        }
    }

    /**
     * 返回字符串日期的Date类型的日期
     *
     * @param str
     * @return
     */
    public static Date toDate(String str) {
        try {
            if (str.indexOf(':') > 0) {
                return toDate(str, DEFAULT_DATETIME_FORMAT);
            } else {
                return toDate(str, DEFAULT_DATE_FORMAT);
            }
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 转化为指定格式的Date时间
     *
     * @param str    string格式的时间
     * @param format 时间格式
     * @return
     */
    public static Date toDateFormat(String str, String format) {
        try {
            if (str.indexOf(':') > 0) {
                return toDate(str, format);
            } else {
                return toDate(str, format);
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public static Date toDateTimes(String str) {
        try {
            if (str.indexOf(':') > 0) {
                return toDate(str, SHORT_DATETIME_FORMAT);
            } else {
                return toDate(str, DEFAULT_DATE_FORMAT);
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public static String currentDateToString(String format) {
        Date date = new Date();
        return toString(date, format);
    }

    public static String curDateStr() {
        return _defDateFmt.format(new Date());
    }

    public static String curDateTimeStr() {
        return _defDateTimeFmt.format(new Date());
    }

    public static String formatElapsedTime(long millis) {
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        Object[] args = {new Long(minutes), new Long(seconds % 60)};
        return MINUTE_SECONDS.format(args);
    }

    /**
     * 获取现在的时间 (已转化为默认格式的字符串形式)
     *
     * @param date
     * @return yyyy-MM-dd
     */
    public static String toDefDateString(Date date) {
        return toString(date, DEFAULT_DATE_FORMAT);
    }

    public static String toDefDateTimeString(Date date) {
        return toString(date, SHORT_DATETIME_FORMAT);
    }

    public static String toDrillDateString(Date date) {
        return toString(date, DRILL_DATE_FORMAT);
    }

    public static String toDefDatetimeString(Date date) {
        return toString(date, DEFAULT_DATETIME_FORMAT);
    }

    public static String toDefTimeString(Date date) {
        return toString(date, DEFAULT_TIME_FORMAT);
    }

    public static String convertSecondsToTime(Long seconds) {
        long s;//秒
        long h;//小时
        long m;//分钟
        if (0 == seconds) {
            return "00:00:00";
        }
        seconds = seconds * 1000;
        h = seconds / 1000 / 60 / 60;
        m = (seconds - h * 60 * 60 * 1000) / 1000 / 60;
        s = seconds / 1000 - h * 60 * 60 - m * 60;
        return (h < 10 ? ("0" + h) : h) + ":" + (m < 10 ? ("0" + m) : m) + ":" + (s < 10 ? ("0" + s) : s);
    }

    public static Date getTodayStartTime() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    public static Date getYearStartTime() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.DAY_OF_YEAR, 0);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    public static String getSmartDateString(Date date, String formatString) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
        SimpleDateFormat sdf1 = new SimpleDateFormat(formatString);
        Long dayLong = 24 * 3600 * 1000L;
        Long todayStart = DateUtils.getTodayStartTime().getTime();
        Long last = todayStart - date.getTime();
        String time = sdf1.format(date);
        if (last <= 0) {
            return "今天" + time;
        } else if (last / dayLong >= 1) {
            return sdf.format(date) + " " + time;
        } else if (last / dayLong == 0) {
            return "昨天" + time;
        } else {
            return "";
        }
    }

    public static String getSmartDateString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
        Long dayLong = 24 * 3600 * 1000L;
        Long todayStart = DateUtils.getTodayStartTime().getTime();
        Long last = todayStart - date.getTime();
        if (last <= 0) {
            Long time = System.currentTimeMillis() - date.getTime();
            int s = (int) (time / 1000);
            if (s >= 3600) {
                return s / 3600 + "小时前";
            } else if (s >= 60) {
                return s / 60 + "分钟前";
            } else {
                return "刚刚";
            }
        } else if (last / dayLong > 10) {
            return sdf.format(date);
        } else if (last / dayLong >= 1) {
            return last / dayLong + 1 + "天前";
        } else if (last / dayLong == 0) {
            return "昨天";
        } else {
            return "";
        }
    }

    public static int getDays(Date startTime, Date endTime) {
        Long time = endTime.getTime() - startTime.getTime();
        int days = (int) (time / (3600000 * 24));
        if (endTime.getTime() == toDate(toDefDateString(endTime)).getTime()) {
            return days;
        }
        return days + 1;
    }

    public static String getSmartLeftTime(Long leftTime) {
        if (leftTime > ONE_DAY) {
            return leftTime / ONE_DAY + "天以上";
        } else if (leftTime >= ONE_HOUR) {
            return leftTime / ONE_HOUR + "小时以上";
        } else if (leftTime >= ONE_MINUTE) {
            return leftTime / ONE_HOUR + 1 + "分钟";
        } else if (leftTime > 0) {
            return "一分钟";
        } else {
            return "";
        }
    }

    /**
     * 距离目标日期相应天数的日期
     *
     * @param date 目标日期
     * @param to   距离天数
     * @return
     */
    public static String getDateToToday(String date, int to) {
        String str = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date myDate = formatter.parse(date);
            Calendar c = Calendar.getInstance();
            c.setTime(myDate);
            c.add(Calendar.DATE, to);
            myDate = c.getTime();
            str = formatter.format(myDate);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return str;
    }

    /**
     * 获取一个月之前的日期
     *
     * @param fortmat
     * @return
     */
    public static String getDayOfLastMonth(String fortmat) {
        SimpleDateFormat sdf = new SimpleDateFormat(fortmat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        // 取得上一个时间
        calendar.set(Calendar.MONDAY, calendar.get(Calendar.MONDAY) - 1);
        return sdf.format(calendar.getTime());
    }

    /**
     * 获取指定日期一个月之前的日期
     *
     * @param fortmat
     * @param targetTime
     * @return
     */
    public static String getDayOfLastMonth(String fortmat, String targetTime) {
        SimpleDateFormat sdf = new SimpleDateFormat(fortmat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(toDate(targetTime));
        // 取得上一个月的时间
        calendar.set(Calendar.MONDAY, calendar.get(Calendar.MONDAY) - 1);
        return sdf.format(calendar.getTime());
    }

    /**
     * 获取一周之前的日期
     *
     * @param fortmat
     * @return
     */
    public static String getDayOfLastWeek(String fortmat) {
        SimpleDateFormat sdf = new SimpleDateFormat(fortmat);
        Long lastWeekTime = System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000;
        String lastWeekStr = sdf.format(lastWeekTime);
        return DateUtils.getTomorrowStr(lastWeekStr);
    }

    /**
     * 获取指定日期一周之前的日期
     *
     * @param fortmat
     * @param targetDay
     * @return
     */
    public static String getDayOfLastWeek(String fortmat, String targetDay) {
        SimpleDateFormat sdf = new SimpleDateFormat(fortmat);
        Long lastWeekTime = toDate(targetDay).getTime() - 7 * 24 * 60 * 60 * 1000;
        String lastWeekStr = sdf.format(lastWeekTime);
        return DateUtils.getTomorrowStr(lastWeekStr);
    }

    /**
     * 获取指定日期之前 给定的多少天 的日期
     *
     * @param fortmat
     * @param targetTime
     * @param days
     * @return
     */
    public static String getDaysTimeBeforeTargetTime(String fortmat, String targetTime, int days) {
        SimpleDateFormat sdf = new SimpleDateFormat(fortmat);
        Long lastdaysTime = toDate(targetTime).getTime() - days * 24L * 60L * 60L * 1000L;
        String lastdaysStr = sdf.format(lastdaysTime);
        return DateUtils.getTomorrowStr(lastdaysStr);
    }

    public static Object getTomorrowMorning() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.add(Calendar.DAY_OF_YEAR, 1);
        return c.getTime();
    }

    /**
     * 获取昨天的日期
     *
     * @return
     */
    public static String getYesTodayStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return sdf.format(cal.getTime());
    }

    /**
     * 获取某一天的昨天的日期
     *
     * @return
     */
    public static String getYesTodayStr(String str) {
        Long time = toDate(str).getTime() - 24 * 60 * 60 * 1000;
        return toDefDateString(new Date(time));
    }

    /**
     * 获取某一天的昨天的日期 for Drill
     *
     * @return
     */
    public static String getYesTodayStr4Drill(String str) {
        Long time = toDate(str).getTime() - 24 * 60 * 60 * 1000;
        return toDrillDateString(new Date(time));
    }

    /**
     * 获取某一天的明天的日期
     *
     * @return
     */
    public static String getTomorrowStr(String str) {
        Long time = toDate(str).getTime() + 24 * 60 * 60 * 1000;
        return toDefDateString(new Date(time));
    }

    public static String getTomorrowTimesStr(String str) {
        Long time = toDateTimes(str).getTime() + 24 * 60 * 60 * 1000;
        return toDefDateTimeString(new Date(time));
    }

    /**
     * 获取某一天的明天的日期  for Drill
     *
     * @return
     */
    public static String getTomorrowStr4Drill(String str) {
        Long time = toDate(str).getTime() + 24 * 60 * 60 * 1000;
        return toDrillDateString(new Date(time));
    }

    /**
     * 获取某一天的今天的日期  for Drill
     *
     * @return
     */
    public static String getTodayStr4Drill(String str) {
        Long time = toDate(str).getTime();
        return toDrillDateString(new Date(time));
    }


    /**
     * 获取某一天的后天的日期
     *
     * @return
     */
    public static String getAfterTomorrowStr(String str) {
        Long time = toDate(str).getTime() + 2 * 24 * 60 * 60 * 1000;
        return toDefDateString(new Date(time));
    }

    /**
     * GMT时间转化为本地时间
     *
     * @param formate
     * @return
     */
    public static String getlocalTime(String str, String formate) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        DateFormat format = new SimpleDateFormat(formate);
        try {
            return format.format(df.parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getlocalTime2(String str, String formate) {
        SimpleDateFormat df = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        DateFormat format = new SimpleDateFormat(formate);
        try {
            return format.format(df.parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * utc格式字符串转换为yyyy-MM-dd HH:mm:ss格式字符串
     *
     * @param str
     * @return
     */
    public static String utc2local(String str) {
        return getlocalTime(str, DEFAULT_DATETIME_FORMAT);
    }

    /**
     * 获取当前的UTC时间
     *
     * @return
     */
    public static String localToUTC(String dateStr) {
        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");
        //解析
        DateTime srcDateTime = DateTime.parse(dateStr, format);
        //DateTime srcDateTime = new  DateTime(dateStr);
        DateTime dstDateTime = srcDateTime.toDateTime(DateTimeZone.UTC);
        return dstDateTime.toString(ISODateTimeFormat.dateTime());
    }

    public static String localToUTCTimes(String dateStr) {
        DateTimeFormatter format = DateTimeFormat.forPattern(SHORT_DATETIME_FORMAT);
        //解析
        DateTime srcDateTime = DateTime.parse(dateStr, format);
        //DateTime srcDateTime = new  DateTime(dateStr);
        DateTime dstDateTime = srcDateTime.toDateTime(DateTimeZone.UTC);
        return dstDateTime.toString(ISODateTimeFormat.dateTime());
    }

    /**
     * 计算2个日期之间相差的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int daysBetween(Date date1, Date date2) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days)) + 1;
    }


    /**
     * 返回2个日期之间的天数组成的list
     *
     * @return(yyyy-MM-dd)
     */
    public static List<String> list2daysBetween(String startTime, String endTime) {
        List<String> list = new ArrayList<String>();
        int days = daysBetween(DateUtils.toDate(startTime), DateUtils.toDate(endTime));
        for (int i = 0; i < days; i++) {
            Long L_time = DateUtils.toDate(startTime).getTime() + new Long(i) * 24 * 60 * 60 * 1000;
            String time = DateUtils.toDefDateString(new Date(L_time));
            list.add(time);
        }
        return list;
    }

    public static List<String> list2daysBetweenFormat(String startTime, String endTime, String format) {
        List<String> list = new ArrayList<>();
        int days = daysBetween(DateUtils.toDate(startTime), DateUtils.toDate(endTime));
        for (int i = 0; i < days; i++) {
            Long lTime = DateUtils.toDate(startTime).getTime() + (long) i * 24 * 60 * 60 * 1000;
            String time = DateUtils.toString(new Date(lTime), format);
            list.add(time);
        }
        return list;
    }

    /**
     * 返回-天中的24小时list
     *
     * @return
     */
    public static List<String> listHourOfDay() {
        List<String> timeList = new ArrayList<String>();
        for (int i = 0; i < 24; i++) {
            timeList.add(i + "");
        }
        return timeList;
    }

    /**
     * 返回-天中的24小时list 是01 02 03 04 05的
     *
     * @return
     */
    public static List<String> listHourOfDay2() {
        List<String> timeList = new ArrayList<String>();
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                timeList.add("0" + i);
            } else {
                timeList.add(i + "");
            }
        }
        return timeList;
    }

    /**
     * 获取当月的第一天
     *
     * @return
     */
    public static String getFirstDayOfCurrentMonth() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, 1);
        return toDefDateString(cal.getTime());
    }

    /**
     * 获取过去一年的第一个月的第一天
     *
     * @return
     */
    public static String getFirstMonthDayOfLastYear() {
        Calendar cal = Calendar.getInstance();
        /**
         * 算上当天所在的月份，才为12个月
         */
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 11);
        cal.set(Calendar.DATE, 1);
        return toDefDateString(cal.getTime());
    }


    public static List<String> listMonthBetweenTwoDays(String startTime, String endTime) {
        List<String> list = new ArrayList<String>();
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(toDate(startTime));
        cal2.setTime(toDate(endTime));
        int num = cal2.get(Calendar.MONTH) - cal1.get(Calendar.MONTH);
        if (num < 0) {
            num = num + 12;
        }
        for (int i = 0; i <= num; i++) {
            list.add(toString(cal1.getTime(), "yyyy-MM"));
            cal1.set(Calendar.MONTH, cal1.get(Calendar.MONTH) + 1);
        }
        return list;
    }

    /**
     * 比较两个字符串形式的时间
     *
     * @param t1
     * @param t2
     * @return
     */
    public static boolean compareStringTime(String t1, String t2) {
        if (toDate(t1).getTime() > toDate(t2).getTime()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取以timeInterval 为时间间隔的24H时间段
     *
     * @param duration 时间间隔(可以是2 3 4)
     * @return 返回以timeInterval为时间间隔的24小时时间间隔列表(00:00-02:00, 02:00-04:00, 04:00-06:00)
     */
    public static List<String> get24hTimeInterval(int duration) {
        List<String> list = null;
        if (duration == Constant.DURATION_2H) {
            list = getDurationFormat(duration, Constant.BOUNDARY_2H);
        } else if (duration == Constant.DURATION_3H) {
            list = getDurationFormat(duration, Constant.BOUNDARY_3H);
        } else if (duration == Constant.DURATION_4H) {
            list = getDurationFormat(duration, Constant.BOUNDARY_4H);
        }
        return list;
    }

    /**
     * 获取以timeInterval 为时间间隔的24H时间段
     *
     * @param duration 时间间隔(可以是2 3 4)
     * @return 返回以timeInterval为时间间隔的24小时时间间隔列表(00:00, 02:00, 04:00, 06:00)
     */
    public static List get24hTimeDuration(int duration) {
        List<String> list = new ArrayList<>();
        list.add("00:00");
        for (int i = duration; i <= Constant.DAY_HOUR; i += duration) {
            if (i <= 9) {
                list.add("0" + i + ":00");
            } else {
                list.add(i + ":00");
            }
        }
        return list;
    }

    private static List<String> getDurationFormat(int duration, int boundary) {
        List<String> list = new ArrayList<>();
        list.add("\"00:00-0" + duration + ":00\"");
        for (int i = duration; i < Constant.DAY_HOUR; i += duration) {
            /**
             * 时间为个位数02:00-04:00
             */
            if ((i < boundary)) {
                list.add("\"0" + i + ":00" + "-" + "0" + (i + duration) + ":00\"");
            } else {
                if (i == boundary) {
                    list.add("\"0" + i + ":00" + "-" + (i + duration) + ":00\"");
                } else {
                    list.add("\"" + i + ":00" + "-" + (i + duration) + ":00\"");
                }
            }

        }
        return list;
    }

    /**
     * 比较两个时间大小
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean compareStringDate(String date1, String date2) {
        DateFormat df = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() >= dt2.getTime()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return true;
    }

    /**
     * 按指定格式比较两个字符串格式的时间
     * @param date1
     * @param date2
     * @param format
     * @return
     */
    public static boolean compareStringDate(String date1, String date2, String format) {
        DateFormat df = new SimpleDateFormat(format);
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            /**
             * 不能有等于
             */
            if (dt1.getTime() > dt2.getTime()) {
                return true;
            } else if (dt1.getTime() < dt2.getTime()) {
                return false;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    /**
     * 获取一年中某周的开始时间
     *
     * @param date 2017-10 2017第10个周末
     * @return 这个周 周一的时间
     */
    public static String getFirstDayOfWeeks(String date) {

        String targetYear = date.split("-")[0];
        String targetWeek = date.split("-")[1];
        int week = Integer.parseInt(targetWeek);
        int year = Integer.parseInt(targetYear);

        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, 1);

        Calendar cal = (GregorianCalendar) c.clone();
        cal.add(Calendar.DATE, week * 7);

        Date firstDayOfWeek = getFirstDayOfWeek(cal.getTime());

        return toString(firstDayOfWeek, DEFAULT_DATE_FORMAT);
    }


    /**
     * 获取当前时间所在周的结束日期
     *
     * @param date
     * @return
     */
    public static String getLastDayOfWeeks(String date) {

        String targetYear = date.split("-")[0];
        String targetWeek = date.split("-")[1];
        int week = Integer.parseInt(targetWeek);
        int year = Integer.parseInt(targetYear);

        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, 1);

        Calendar cal = (GregorianCalendar) c.clone();
        cal.add(Calendar.DATE, week * 7);

        Date firstDayOfWeek = getLastDayOfWeek(cal.getTime());

        return toString(firstDayOfWeek, DEFAULT_DATE_FORMAT);
    }

    /**
     * 获取当前时间所在周的开始日期
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        /**
         * Monday
         */
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
        return c.getTime();
    }

    /**
     * 获取当前时间所在周的结束日期
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        /**
         * Sunday
         */
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6);
        return c.getTime();
    }

    /**
     * 获取某个月的第一天(str 为  YYYY-MM)
     *
     * @param str
     * @return
     */
    public static String getFirstDayOfMonth(String str) {
        return toString(toDate(str, "yyyy-MM"), "yyyy-MM-dd");
    }

    /**
     * 获取某个月的最后一天
     *
     * @param str
     * @return
     */
    public static String getLastDayOfMonth(String str) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(toDate(getFirstDayOfMonth(str)));
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 1);
        return toDefDateString(cal.getTime());
    }

    /**
     * 获取某年的第一天
     *
     * @param date 必须为 yy格式
     * @return
     */
    public static String getFirstDayOfYear(String date) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        int year = Integer.parseInt(date);
        calendar.set(Calendar.YEAR, year);

        Date time = calendar.getTime();
        return toString(time, DEFAULT_DATE_FORMAT);
    }

    /**
     * 获取某年的最后一天
     *
     * @param date 必须为 yy格式
     * @return
     */
    public static String getLastDayOfYear(String date) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.clear();
        int year = Integer.parseInt(date);
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date time = calendar.getTime();
        return toString(time, DEFAULT_DATE_FORMAT);
    }

    /**
     * 获取某天按指定格式的小时列表
     *
     * @param date   yy-MM-dd格式
     * @param format
     * @return
     */
    public static List<String> getOneDayHourList(String date, String format) {
        final int maxLenth = 24;

        List<String> timeList = new ArrayList<>();
        long currentTimeMs = 0L;
        for (int i = 0; i < maxLenth; i++) {

            Date dateTime = toDate(date);
            if (dateTime != null) {
                currentTimeMs = dateTime.getTime();
            }
            if (i != 0) {
                currentTimeMs += 3600000L;
            }
            date = toString(new Date(currentTimeMs), format);
            timeList.add(date);
        }
        return timeList;
    }

    /**
     * 获取某年某月有好多天
     *
     * @param year  年份
     * @param month 月份
     * @return
     */
    public static int getDaysCountOfMonth(int year, int month) {
        Calendar calendar = GregorianCalendar.getInstance();

        calendar.set(year, month, 0);

        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取一年中 按照2017-01-01 格式的12个月的列表
     *
     * @param time yyyy
     * @return
     */
    public static List<String> getMonthsOfYear(String time) {
        List<String> monthList = new ArrayList<>();
        final int maxSize = 13;
        for (int month = 1; month < maxSize; month++) {

            String monthDay = time + "-" + month + "-01";
            monthList.add(monthDay);
        }
        return monthList;
    }

    /**
     * 获取当前月份的上个月时间
     *
     * @param date yyyy-MM
     * @return date yyyy-MM
     */
    public static String getLastMonth(String date) {
        Calendar calendar = GregorianCalendar.getInstance();
        int year = Integer.parseInt(date.split("-")[0]);
        int month = Integer.parseInt(date.split("-")[1]);
        calendar.set(year, month, 0);

        calendar.add(Calendar.MONTH, -1);

        Date calendarTime = calendar.getTime();

        return toString(calendarTime, "yyyy-MM");
    }

    /**
     * 获取某个时间在一年中为第几周
     * @param date
     * @return
     */
    public static int getWeeksOfYear(String date)
    {
        Calendar calendar = GregorianCalendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(DEFAULT_DATE_FORMAT);

        try {
            calendar.setTime(df.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        return week;
    }

    /**
     * 获取指定格式的时间 时区为 Asia/Chongqing
     * @param date
     * @param format
     * @return
     */
    public static String getFomatedStringDate(String date,String format)
    {
        SimpleDateFormat df = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        df.setTimeZone(TimeZone.getTimeZone("Asia/Chongqing"));
        DateFormat formats = new SimpleDateFormat(format);
        try {
            return formats.format(df.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}


