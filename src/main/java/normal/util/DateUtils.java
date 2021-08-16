package normal.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * DateUtils
 *
 * @author hetao
 * @date 2018-09-12
 */
public class DateUtils extends cn.hutool.core.date.DateUtil {

    /**
     * 期限天
     */
    public static final String TERM_UNIT_DAY = "010";
    /**
     * 期限月
     */
    public static final String TERM_UNIT_MONTH = "020";
    /**
     * 期限年
     */
    public static final String TERM_UNIT_YEAR = "030";

    public static final String PATTERN = "yyyy-MM-dd";

    public static final String PATTERN_2 = "yyyy-MM-dd HH:mm:ss";

    public static final String SINGLE_PATTERN = "yyyy-M-d";

    /**
     * 无分隔符时间格式化
     */
    public static final String TIME_FORMAT_NO_SEPARATOR = "yyyyMMddHHmmss";

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd）
     */
    public static String getDate() {
        return getDate("yyyy-MM-dd");
    }

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String getDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }

    /**
     * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String formatDate(Date date, Object... pattern) {
        String formatDate = null;
        if (pattern != null && pattern.length > 0) {
            formatDate = DateFormatUtils.format(date, pattern[0].toString());
        } else {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        }
        return formatDate;
    }

    /**
     * 将yyyy-MM-dd格式转换为yyyyMMdd, 或者将yyyyMMdd格式转换为yyyy-MM-dd
     */
    public static String formatDateStr(String dateStr) {
        if (StringUtils.isBlank(dateStr)) {
            return dateStr;
        }
        int index = dateStr.indexOf("-");
        if (index != -1) {
            return formatDate(parse(dateStr, "yyyy-MM-dd"), "yyyyMMdd");
        } else {
            return formatDate(parse(dateStr, "yyyyMMdd"), "yyyy-MM-dd");
        }
    }

    /**
     * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String formatDateTime(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前时间字符串 格式（HH:mm:ss）
     */
    public static String getTime() {
        return formatDate(new Date(), "HH:mm:ss");
    }

    /**
     * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String getDateTime() {
        return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前年份字符串 格式（yyyy）
     */
    public static String getYear() {
        return formatDate(new Date(), "yyyy");
    }

    public static String getYear(Date date) {
        return formatDate(date, "yyyy");
    }

    /**
     * 得到当前月份字符串 格式（MM）
     */
    public static String getMonth() {
        return formatDate(new Date(), "MM");
    }

    public static String getMonth(Date date) {
        return formatDate(date, "MM");
    }

    /**
     * 得到当天字符串 格式（dd）
     */
    public static String getDay() {
        return formatDate(new Date(), "dd");
    }

    public static String getDay(Date date) {
        return formatDate(date, "dd");
    }

    /**
     * 得到当前星期字符串 格式（E）星期几
     */
    public static String getWeek() {
        return formatDate(new Date(), "E");
    }

    /**
     * 获取过去的天数
     *
     * @param date
     * @return
     */
    public static long pastDays(Date date) {
        long t = System.currentTimeMillis() - date.getTime();
        return t / (24 * 60 * 60 * 1000);
    }

    /**
     * 获取过去的小时
     *
     * @param date
     * @return
     */
    public static long pastHour(Date date) {
        long t = System.currentTimeMillis() - date.getTime();
        return t / (60 * 60 * 1000);
    }

    /**
     * 获取过去的分钟
     *
     * @param date
     * @return
     */
    public static long pastMinutes(Date date) {
        long t = System.currentTimeMillis() - date.getTime();
        return t / (60 * 1000);
    }

    /**
     * 转换为时间（天,时:分:秒.毫秒）
     *
     * @param timeMillis
     * @return
     */
    public static String formatDateTime(long timeMillis) {
        long day = timeMillis / (24 * 60 * 60 * 1000);
        long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
        long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
        return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "." + sss;
    }

    /**
     * 获取两个日期之间的天数，大于0部分算1
     *
     * @param before
     * @param after
     * @return
     */
    public static double getDistanceOfTwoDate(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        double days = new BigDecimal((afterTime - beforeTime) / (1000.0 * 60.0 * 60.0 * 24.0)).setScale(0, BigDecimal.ROUND_UP).doubleValue();
        return days;
    }

    /**
     * 获取两个日期之间的天数，大于0部分算1
     *
     * @param before
     * @param after
     * @return
     */
    public static double getDistanceOfTwoDateDown(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        double days = new BigDecimal((afterTime - beforeTime) / (1000.0 * 60.0 * 60.0 * 24.0)).setScale(0, BigDecimal.ROUND_DOWN).doubleValue();
        return days;
    }

    /**
     * 获取两个日期之间的天数，，大于0但小于1部分算0
     *
     * @param before
     * @param after
     * @return
     */
    public static int getDistanceOfTwoDateDown2(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return BigDecimal.valueOf((afterTime - beforeTime) / (1000.0 * 60.0 * 60.0 * 24.0)).setScale(0, BigDecimal.ROUND_DOWN).intValue();
    }

    /**
     * 日期加几年
     *
     * @param date
     * @param num
     * @return
     */
    public static Date getDateByAddYear(Date date, int num) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        // 日期加1年
        rightNow.add(Calendar.YEAR, num);
        Date dt1 = rightNow.getTime();
        return dt1;
    }

    /**
     * 日期加几月
     *
     * @param date
     * @param num
     * @return
     */
    public static Date getDateByAddMonth(Date date, int num) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        // 日期加1年
        rightNow.add(Calendar.MONTH, num);
        Date dt1 = rightNow.getTime();
        return dt1;
    }

    /**
     * 日期加几天
     *
     * @param date
     * @param num
     * @return
     */
    public static Date getDateByAddDay(Date date, int num) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        // 日期加1年
        rightNow.add(Calendar.DATE, num);
        Date dt1 = rightNow.getTime();
        return dt1;
    }

    /**
     * 日期加几毫秒
     *
     * @param date
     * @param num
     * @return
     */
    public static Date getDateByAddMinute(Date date, int num) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.MINUTE, num);
        Date dt1 = rightNow.getTime();
        return dt1;
    }

    /**
     * 日期加几毫秒
     *
     * @param date
     * @param num
     * @return
     */
    public static Date getDateByAddSecond(Date date, int num) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.SECOND, num);
        Date dt1 = rightNow.getTime();
        return dt1;
    }

    /**
     * 日期加几毫秒
     *
     * @param date
     * @param num
     * @return
     */
    public static Date getDateByAddMillisecond(Date date, int num) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.MILLISECOND, num);
        Date dt1 = rightNow.getTime();
        return dt1;
    }

    /**
     * 日期加几天
     *
     * @param date
     * @param num
     * @return
     */
    public static Date getDateByAdd(Date date, int num, String unit) {
        Date result = new Date();
        switch (unit) {
            case "YEAR":
                result = getDateByAddYear(date, num);
                break;
            case "MONTH":
                result = getDateByAddMonth(date, num);
                break;
            case "DAY":
                result = getDateByAddDay(date, num);
                break;
            case "MINUTE":
                result = getDateByAddMinute(date, num);
                break;
            case "MILLISECOND":
                result = getDateByAddMillisecond(date, num);
                break;
            default:
                result = getDateByAddMonth(date, num);
                break;
        }
        return result;
    }

    /**
     * 获取两个日期字符串之间的日期集合
     *
     * @param startTime:String
     * @param endTime:String
     * @return list:yyyy-MM-dd
     */
    public static List<String> getBetweenDate(String startTime, String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 声明保存日期集合
        List<String> list = new ArrayList<>();
        try {
            // 转化成日期类型
            Date startDate = sdf.parse(startTime);
            Date endDate = sdf.parse(endTime);

            //用Calendar 进行日期比较判断
            Calendar calendar = Calendar.getInstance();
            while (startDate.getTime() <= endDate.getTime()) {
                // 把日期添加到集合
                list.add(sdf.format(startDate));
                // 设置日期
                calendar.setTime(startDate);
                //把日期增加一天
                calendar.add(Calendar.DATE, 1);
                // 获取增加后的日期
                startDate = calendar.getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * @param startDate
     * @param endDate
     * @return
     * @throws ParseException 获取两个日期之间的天数
     */
    public static int getDays(Date startDate, Date endDate) {
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.setTime(startDate);
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        start.set(Calendar.MILLISECOND, 0);
        end.setTime(endDate);
        end.set(Calendar.HOUR_OF_DAY, 0);
        end.set(Calendar.MINUTE, 0);
        end.set(Calendar.SECOND, 0);
        end.set(Calendar.MILLISECOND, 0);
        int iDays = (int) ((end.getTimeInMillis() - start.getTimeInMillis()) / 86400000L);
        return iDays;
    }

    /**
     * 获取2个日期相隔的月数（不要余数）
     *
     * @param date1 yyyy-mm-dd
     * @param date2 yyyy-mm-dd
     * @return
     */
    public static int getMonthsNoRemainder(String date1, String date2) throws Exception {
        String beginDate;
        String endDate;
        int monthsBetween = 0;
        if (date1.equals(date2)) {
            return monthsBetween;
        } else if (date1.compareTo(date2) > 0) {
            beginDate = date2;
            endDate = date1;
        } else {
            beginDate = date1;
            endDate = date2;
        }
        //格式化为年月
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

        Calendar beginCalendar = Calendar.getInstance();
        beginCalendar.setTime((sdf.parse(beginDate)));

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime((sdf.parse(endDate)));

        while (beginCalendar.get(Calendar.YEAR) != endCalendar.get(Calendar.YEAR)
                || beginCalendar.get(Calendar.MONTH) != endCalendar.get(Calendar.MONTH)) {
            beginCalendar.add(Calendar.MONTH, 1);
            monthsBetween++;
        }

        if (beginCalendar.get(Calendar.DAY_OF_MONTH) > endCalendar.get(Calendar.DAY_OF_MONTH)) {
            monthsBetween--;
        }
        return monthsBetween;
    }


    public static Date getDateFromStr(String dateStr, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 校验String类型日期是否合法,默认为yyyy-MM-dd
     *
     * @param strDate
     * @return
     */
    public static boolean isValidDate(String strDate) {
        return isValidDate(strDate, null);
    }

    /**
     * 校验String类型日期是否合法,默认为yyyy-MM-dd
     *
     * @param strDate
     * @param pattern
     * @return
     */
    public static boolean isValidDate(String strDate, String pattern) {
        boolean convertSuccess = true;
        SimpleDateFormat format = StringUtils.isNotBlank(pattern) ?
                new SimpleDateFormat(pattern) : new SimpleDateFormat("yyyy-MM-dd");
        try {
            format.setLenient(false);
            format.parse(strDate);
        } catch (ParseException e) {
            convertSuccess = false;
        }
        return convertSuccess;
    }

    /**
     * 判断某一时间是否在一个区间内
     *
     * @param sourceTime 时间区间,半闭合,如[10:00-20:00)
     * @param curTime    需要判断的时间 如10:00
     * @return
     * @throws IllegalArgumentException
     */
    public static boolean isInTime(String sourceTime, String curTime) {
        if (sourceTime == null || !sourceTime.contains("-") || !sourceTime.contains(":")) {
            throw new IllegalArgumentException("Illegal Argument arg:" + sourceTime);
        }
        if (curTime == null || !curTime.contains(":")) {
            throw new IllegalArgumentException("Illegal Argument arg:" + curTime);
        }
        String[] args = sourceTime.split("-");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            long now = sdf.parse(curTime).getTime();
            long start = sdf.parse(args[0]).getTime();
            long end = sdf.parse(args[1]).getTime();
            if (args[1].equals("00:00")) {
                args[1] = "24:00";
            }
            if (end < start) {
                if (now >= end && now <= start) {
                    return false;
                } else {
                    return true;
                }
            } else {
                if (now >= start && now <= end) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Illegal Argument arg:" + sourceTime);
        }

    }

    public static String stampToDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 获取一天的最早时刻,凌晨
     *
     * @param date
     * @return
     */
    public static Date getFirstTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取一天的最后时刻,23:59:59 999
     *
     * @param date
     * @return
     */
    public static Date getLastTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * 获取当天整点
     *
     * @return
     */
    public static Date getTodayZero() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }


    /**
     * 比较日期  输入的日期与当前日期比较 小于当前日期返回 TRUE
     *
     * @param startDate
     * @return
     */
    public static Boolean compare(Date startDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String formatDate = getDate();
            Date endDate = sdf.parse(formatDate);

            if (startDate.getTime() < endDate.getTime()) {
                return Boolean.TRUE;
            }
        } catch (Exception e) {

        }
        return Boolean.FALSE;
    }

    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }

    public static LocalDateTime date2LocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDateTime();
    }

    public static Date localDate2Date(LocalDate localDate) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * 获取当天整点
     *
     * @return
     */
    public static Date getDateZero(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTime();
    }


    public static int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static int getCurrentDays(Date date) {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }


    /**
     * @param args      /**
     *                  比较两个日期大小 endDate大于 startDate 返回true
     * @param startDate
     * @param endDate
     * @return
     */
    public static Boolean compare(Date startDate, Date endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (startDate.getTime() < endDate.getTime()) {
                return Boolean.TRUE;
            }
            if (startDate.getTime() == endDate.getTime()) {
                return Boolean.TRUE;
            }
        } catch (Exception e) {

        }
        return Boolean.FALSE;
    }

    /**
     * 比较两个日期大小 endDate大于 startDate 返回true
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static Boolean compareGe(Date startDate, Date endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (startDate.getTime() < endDate.getTime()) {
                return Boolean.TRUE;
            }
        } catch (Exception e) {

        }
        return Boolean.FALSE;
    }


    /**
     * 比较两个日期大小 endDate 小于等于startDate 返回true
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static Boolean compareEqualsLe(Date startDate, Date endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (endDate.getTime() == startDate.getTime()) {
                return Boolean.TRUE;
            }
            if (endDate.getTime() < startDate.getTime()) {
                return Boolean.TRUE;
            }
        } catch (Exception e) {

        }
        return Boolean.FALSE;
    }

    public static Date formatStringToDate(String dateString, String pattern) {
        Date date = null;
        if (StringUtils.isNotEmpty(dateString)) {
            //创建SimpleDateFormat对象实例并定义好转换格式
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            try {
                // 注意格式需要与上面一致，不然会出现异常
                date = sdf.parse(dateString);
            } catch (ParseException e) {

            }
        }
        return date;
    }

    /***
     * 判断字符串是否是yyyyMMdd格式
     * @param dateStr 字符串
     * @return boolean 是否是日期格式
     */
    public static boolean isYMDFormat(String dateStr) {
        String format = "([0-9]{4})(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])";
        Pattern pattern = Pattern.compile(format);
        Matcher matcher = pattern.matcher(dateStr);
        if (matcher.matches()) {
            pattern = Pattern.compile("(\\d{4})(\\d{2})(\\d{2})");
            matcher = pattern.matcher(dateStr);
            if (matcher.matches()) {
                int y = Integer.valueOf(matcher.group(1));
                int m = Integer.valueOf(matcher.group(2));
                int d = Integer.valueOf(matcher.group(3));
                if (d > 28) {
                    Calendar c = Calendar.getInstance();
                    c.set(y, m - 1, 1);
                    //每个月的最大天数
                    int lastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
                    return (lastDay >= d);
                }
            }
            return true;
        }
        return false;

    }

    /**
     * 将字符串转成日期。失败返回null
     *
     * @param dateStr
     * @param pattern
     * @return
     */
    public static Date StringToDate(String dateStr, String pattern) {
        DateFormat format = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = format.parse(dateStr);
        } catch (Exception e) {
        }
        return date;
    }

    public static Date getTodayMaxTime() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * 判断结束时间是否小于开始时间，结束时间小于开始时间返回 true
     */
    public static boolean verifyDate(Date start,Date end){
        if (ObjectUtils.isNull(start) || ObjectUtils.isNull(end) || end.compareTo(start) < 0){
            return true;
        }
        return false;
    }

    /**
     * 将类似‘2012-3-9’转换为‘2012-03-09’
     */
    public static String formatDate(String startDate) {
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern(SINGLE_PATTERN);
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern(PATTERN);
        return LocalDate.parse(startDate, inputFormat).format(outputFormat);
    }
}
