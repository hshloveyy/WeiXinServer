/*
 * @(#)DateUtils.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-9-9
 *  描　述：整理
 */
package com.infolion.platform.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
/**
 * 
 * <pre>日期处理（整理）.</pre>
 *
 * <br>
 * JDK版本:1.5
 *
 * @author 张崇镇
 * @version 1.0
 * @see <a href="http://java.sun.com/docs/books/tutorial/i18n/format/simpleDateFormat.html">Java simpleDateFormat Tutorial</a>
 * @since 1.0
 */

public class DateUtils
{

    /**数据库存储的时间格式串，如yyyymmdd 或yyyymmddHHMiSS*/
    public static final int DB_STORE_DATE = 1;

    /**用连字符-分隔的时间时间格式串，如yyyy-mm-dd 或yyyy-mm-dd HH:Mi:SS*/
    public static final int HYPHEN_DISPLAY_DATE = 2;

    /**用连字符.分隔的时间时间格式串，如yyyy.mm.dd 或yyyy.mm.dd HH:Mi:SS*/
    public static final int DOT_DISPLAY_DATE = 3;

    /**用中文字符分隔的时间格式串，如yyyy年mm月dd 或yyyy年mm月dd HH:Mi:SS*/
    public static final int CN_DISPLAY_DATE = 4;

    /**
     * 得到精确到秒的格式化当前时间串
     * @param formatType 时间格式的类型{@link #DB_STORE_DATE},{@link #EN_HTML_DISPLAY_DATE},{@link #CN_HTML_DISPLAY_DATE}
     * @return 当前时间格式化时间串
     */
    public static String getCurrTimeStr(int formatType)
    {
        return getTimeStr(new Date(), formatType);
    }


    /**
     * 得到精确到秒的格式化时间串
     * @param date 指定时间
     * @param formatType 时间格式的类型{@link #DB_STORE_DATE},{@link #EN_HTML_DISPLAY_DATE},{@link #CN_HTML_DISPLAY_DATE}
     * @return 指定时间的格式化时间串
     */
    public static String getTimeStr(Date date, int formatType)
    {
        if (formatType < DB_STORE_DATE || formatType > CN_DISPLAY_DATE)
        {
            throw new IllegalArgumentException("时间格式化类型不是合法的值。");
        }
        else
        {
            String formatStr = null;
            switch (formatType)
            {
                case DB_STORE_DATE:
                    formatStr = "yyyyMMddHHmmss";
                    break;
                case HYPHEN_DISPLAY_DATE:
                    formatStr = "yyyy'-'MM'-'dd HH:mm:ss";
                    break;
                case DOT_DISPLAY_DATE:
                    formatStr = "yyyy.MM.dd HH:mm:ss";
                    break;
                case CN_DISPLAY_DATE:
                    formatStr = "yyyy'年'MM'月'dd HH:mm:ss";
                    break;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
            return sdf.format(date);
        }
    }

    /**
     * 得到精确到天的当前格式化日期串
     *
     * @param formatType 时间格式的类型{@link #DB_STORE_DATE},{@link
     *   #EN_HTML_DISPLAY_DATE},{@link #CN_HTML_DISPLAY_DATE}
     * @return java.lang.String
     */
    public static String getCurrDateStr(int formatType)
    {
        return getDateStr(new Date(), formatType);
    }

    /**
     * 得到精确到天的指定时间格式化日期串
     * @param date 指定时间
     * @param formatType 时间格式的类型{@link #DB_STORE_DATE},{@link #EN_HTML_DISPLAY_DATE},{@link #CN_HTML_DISPLAY_DATE}
     * @return 指定时间格式化日期串
     */
    public static String getDateStr(Date date, int formatType)
    {
        if (formatType < DB_STORE_DATE || formatType > CN_DISPLAY_DATE)
        {
            throw new IllegalArgumentException("时间格式化类型不是合法的值。");
        }
        else
        {
            String formatStr = null;
            switch (formatType)
            {
                case DB_STORE_DATE:
                    formatStr = "yyyyMMdd";
                    break;
                case HYPHEN_DISPLAY_DATE:
                    formatStr = "yyyy-MM-dd";
                    break;
                case DOT_DISPLAY_DATE:
                    formatStr = "yyyy.MM.dd";
                    break;
                case CN_DISPLAY_DATE:
                    formatStr = "yyyy'年'MM'月'dd";
                    break;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
            return sdf.format(date);
        }
    }

    /**
     * 得到精确到月的当前时间格式化年月串
     * @param formatType 时间格式的类型{@link #DB_STORE_DATE},{@link #EN_HTML_DISPLAY_DATE},{@link #CN_HTML_DISPLAY_DATE}
     * @return 精确到月当前时间格式化年月串
     */
    public static String getYearMonthStr(int formatType)
    {
        return getYearMonthStr(new Date(), formatType);
    }

    /**
     * 得到精确到月的指定时间格式化年月串
     * @param date 指定的时间
     * @param formatType 时间格式的类型{@link #DB_STORE_DATE},{@link #EN_HTML_DISPLAY_DATE},{@link #CN_HTML_DISPLAY_DATE}
     * @return 精确到月当前时间格式化年月串
     */
    public static String getYearMonthStr(Date date, int formatType)
    {
        if (formatType < DB_STORE_DATE || formatType > CN_DISPLAY_DATE)
        {
            throw new IllegalArgumentException("时间格式化类型不是合法的值。");
        }
        else
        {
            String formatStr = null;
            switch (formatType)
            {
                case DB_STORE_DATE:
                    formatStr = "yyyyMM";
                    break;
                case HYPHEN_DISPLAY_DATE:
                    formatStr = "yyyy-MM";
                    break;
                case DOT_DISPLAY_DATE:
                    formatStr = "yyyy.MM";
                    break;
                case CN_DISPLAY_DATE:
                    formatStr = "yyyy'年'MM'月'";
                    break;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
            return sdf.format(date);
        }
    }

    /**
     * 得到14位的当前格式化时间
     *
     * @param formatType 时间格式的类型{@link #DB_STORE_DATE},{@link
     *   #EN_HTML_DISPLAY_DATE},{@link #CN_HTML_DISPLAY_DATE}
     * @return java.lang.String
     */
    public static String getCurrTime(int formatType)
    {
        if(formatType < DB_STORE_DATE || formatType > CN_DISPLAY_DATE){
            throw new IllegalArgumentException("时间格式化类型不是合法的值。");
        }
        else{
            String formatStr = null;
            switch(formatType){
                case DB_STORE_DATE:
                    formatStr = "yyyyMMddHHmmss";
                    break;
                case HYPHEN_DISPLAY_DATE:
                    formatStr = "yyyy'-'MM'-'dd HH:mm:ss";
                    break;
                case DOT_DISPLAY_DATE:
                    formatStr = "yyyy.MM.dd HH:mm:ss";
                    break;
                case CN_DISPLAY_DATE:
                    formatStr = "yyyy'年'MM'月'dd HH:mm:ss";
                    break;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
            return sdf.format(new Date());
        }
    }
    
    public static String getCurrMin(int formatType)
    {
        if(formatType < DB_STORE_DATE || formatType > CN_DISPLAY_DATE){
            throw new IllegalArgumentException("时间格式化类型不是合法的值。");
        }
        else{
            String formatStr = null;
            switch(formatType){
                case DB_STORE_DATE:
                    formatStr = "yyyyMMddHHmm";
                    break;
                case HYPHEN_DISPLAY_DATE:
                    formatStr = "yyyy'-'MM'-'dd HH:mm";
                    break;
                case DOT_DISPLAY_DATE:
                    formatStr = "yyyy.MM.dd HH:mm";
                    break;
                case CN_DISPLAY_DATE:
                    formatStr = "yyyy'年'MM'月'dd'日' HH'时'mm'分'";
                    break;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
            return sdf.format(new Date());
        }
    }

    /**
     * 得到8位的当前格式化日期
     *
     * @param formatType 时间格式的类型{@link #DB_STORE_DATE},{@link
     *   #EN_HTML_DISPLAY_DATE},{@link #CN_HTML_DISPLAY_DATE}
     * @return java.lang.String
     */
    public static String getCurrDate(int formatType)
    {
        if(formatType < DB_STORE_DATE || formatType > CN_DISPLAY_DATE){
            throw new IllegalArgumentException("时间格式化类型不是合法的值。");
        }
        else{
            String formatStr = null;
            switch(formatType){
                case DB_STORE_DATE:
                    formatStr = "yyyyMMddHHmmss";
                    break;
                case HYPHEN_DISPLAY_DATE:
                    formatStr = "yyyy-MM-dd";
                    break;
                case DOT_DISPLAY_DATE:
                    formatStr = "yyyy.MM.dd";
                    break;
                case CN_DISPLAY_DATE:
                    formatStr = "yyyy'年'MM'月'dd";
                    break;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
            return sdf.format(new Date());
        }
    }

    /**
     * 得到6位的当前格式化年月
     *
     * @param formatType 时间格式的类型{@link #DB_STORE_DATE},{@link
     *   #EN_HTML_DISPLAY_DATE},{@link #CN_HTML_DISPLAY_DATE}
     * @return java.lang.String
     */
    public static String getYearMonth(int formatType)
    {
        if(formatType < DB_STORE_DATE || formatType > CN_DISPLAY_DATE){
            throw new IllegalArgumentException("时间格式化类型不是合法的值。");
        }
        else{
            String formatStr = null;
            switch(formatType){
                case DB_STORE_DATE:
                    formatStr = "yyyyMM";
                    break;
                case HYPHEN_DISPLAY_DATE:
                    formatStr = "yyyy-MM";
                    break;
                case DOT_DISPLAY_DATE:
                    formatStr = "yyyy.MM";
                    break;
                case CN_DISPLAY_DATE:
                    formatStr = "yyyy'年'MM'月'";
                    break;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
            return sdf.format(new Date());
        }
    }

    /**
     * 将数据库存储的日期格式串转换为各种显示的格式
     * @param dateStr 最小6位，最大14位的数据库存储格式时间串如:20041212
     * @param formatType 时间格式的类型{@link #DB_STORE_DATE},{@link #EN_HTML_DISPLAY_DATE},{@link #CN_HTML_DISPLAY_DATE}
     * @return 格式化的时间串
     */
    public static String toDisplayStr(String dateStr,int formatType)
    {
        if(formatType < DB_STORE_DATE || formatType > CN_DISPLAY_DATE){
            throw new IllegalArgumentException("时间格式化类型不是合法的值。");
        }
        if(dateStr == null || dateStr.length() < 6 || dateStr.length() > 14 ||
           formatType == DB_STORE_DATE){
            return StringUtils.toVisualString(dateStr);
        }
        else{
            char[] charArr = null;
            switch(formatType){
                case HYPHEN_DISPLAY_DATE:
                    charArr = new char[]{
                        '-','-',' ',':',':'};
                    break;
                case DOT_DISPLAY_DATE:
                    charArr = new char[]{
                        '.','.',' ',':',':'};
                    break;
                case CN_DISPLAY_DATE:
                    charArr = new char[]{
                        '年','月','日',':',':'};
                    break;
                default:
                    charArr = new char[]{
                        '-','-',' ',':',':'};
            }
            try{
                SimpleDateFormat sdf = null;
                SimpleDateFormat _sdf = null;
                switch(dateStr.length()){
                    case 6:
                        sdf = new SimpleDateFormat("yyyyMM");
                        _sdf = new SimpleDateFormat("yyyy'" + charArr[0] +
                            "'MM'" + charArr[1] + "'");
                        break;
                    case 8:
                        sdf = new SimpleDateFormat("yyyyMMdd");
                        _sdf = new SimpleDateFormat("yyyy'" + charArr[0] +
                            "'MM'" + charArr[1] + "'dd'" + charArr[2] + "'");
                        break;
                    case 10:
                        sdf = new SimpleDateFormat("yyyyMMddHH");
                        _sdf = new SimpleDateFormat("yyyy'" + charArr[0] +
                            "'MM'" + charArr[1] + "'dd'" + charArr[2] +
                            "'HH");
                        break;
                    case 12:
                        sdf = new SimpleDateFormat("yyyyMMddHHmm");
                        _sdf = new SimpleDateFormat("yyyy'" + charArr[0] +
                            "'MM'" + charArr[1] + "'dd'" + charArr[2] +
                            "'HH'" + charArr[3] + "'mm");
                        break;
                    case 14:
                        sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                        _sdf = new SimpleDateFormat(
                            "yyyy'" + charArr[0] + "'MM'" + charArr[1] +
                            "'dd'" + charArr[2] + "'HH'" + charArr[3] +
                            "'mm'" + charArr[4] + "'ss");
                        break;
                    default:
                        return dateStr;
                }
                return _sdf.format(sdf.parse(dateStr));
            }
            catch(ParseException ex){
                return dateStr;
            }
        }
    }

    /**
     * 将显示格式的时间字符串转换为数据库存储的类型
     * @param dateStr 最小4位，最大19位。显示的时间格式时间串如:2004-12-12
     * @return 数据库存储的时间字符串
     */
    public static String toStoreStr(String dateStr)
    {
        if(dateStr == null || dateStr.trim().equals("")){
            return "";
        }
        StringBuffer strBuf = new StringBuffer();
        for(int i = 0;i < dateStr.length();i++){
            if(dateStr.charAt(i) >= '0' && dateStr.charAt(i) <= '9'){
                strBuf.append(dateStr.charAt(i));
            }
        }
        return strBuf.toString();
    }

    /**
     * 将生日存储的时间格式转化为年龄（周岁，小数点后不计）
     *
     * @param birthdayStr 生日字段 "yyyymmdd"
     * @return 年龄
     */
    public static String birthdayToAge(String birthdayStr)
    {
        if(birthdayStr == null || birthdayStr.length() < 6){
            return "";
        }
        else{
            int birthYear = Integer.parseInt(birthdayStr.substring(0,4));
            int birthMonth = Integer.parseInt(birthdayStr.substring(4,6));
            Calendar cal = new GregorianCalendar();
            int currYear = cal.get(Calendar.YEAR);
            int currMonth = cal.get(Calendar.MONTH);
            int age = currYear - birthYear;
            age -= currMonth < birthMonth ? 1 : 0;
            return "" + age;
        }
    }

    /**
     * 日期、时间格式化
     * @param date Date
     * 将要被格式化的日期对象
     * @param outFmt String
     * 返回样式，参照类说明，如：yyyy年MM月dd日
     * @return String
     * 格式化后的日期、时间字符串，data为null时返回null，outFmt非法时返回yyyyMMdd格式
     */
    public static String getDateFormat(Date date,String outFmt)
    {
        if(null == date){
            return null;
        }
        if(null == outFmt || "".equals(outFmt.trim())){ //outFmt非法
            outFmt = "yyyyMMdd";
        }

        String retu = null;
        SimpleDateFormat dateFormat = null;
        try{
            dateFormat = new SimpleDateFormat(outFmt);
        }
        catch(IllegalArgumentException iaex){ //outFmt非法
            dateFormat = new SimpleDateFormat("yyyyMMdd");
        }
        retu = dateFormat.format(date);

        dateFormat = null;

        return retu;
    }

    /**
     * 把日期时间对象格式化为yyyyMMdd样式
     * @param date Date
     * 将要被格式化的日期对象
     * @return String
     * 格式化后的日期、时间字符串，如：20041001
     */
    public static String getDateFormat(Date date)
    {
        return getDateFormat(date,"yyyyMMdd");
    }

    /**
     * 把系统当前日期时间格式化为指定的样式
     * @param outFmt String
     * 返回样式，参照类说明，如：yyyy年MM月dd日
     * @return String
     * 格式化后的日期、时间字符串，如：2004年10月01日
     */
    public static String getDateFormat(String outFmt)
    {
        return getDateFormat(new Date(),outFmt);
    }

    /**
     * 把系统当前日期时间格式化为默认样式yyyyMMdd
     * @return String
     * 格式化后的日期、时间字符串，如：20041001
     */
    public static String getDateFormat()
    {
        return getDateFormat(new Date(),"yyyyMMdd");
    }

    /**
     * 日期、时间格式化
     * @param millis long
     * the number of milliseconds（毫秒） since January 1, 1970, 00:00:00 GMT.
     * @param outFmt String
     * 返回样式，参照类说明，如：yyyy年MM月dd日
     * @return String
     * 格式化后的日期、时间字符串
     */
    public static String getDateFormat(long millis,String outFmt)
    {
        Calendar calendar = Calendar.getInstance();
        //calendar.setTimeInMillis(millis);
        calendar.setTime(new Date(millis));

        String retu = getDateFormat(calendar.getTime(),outFmt);
        calendar = null;
        return retu;
    }

    /**
     * 日期、时间格式化
     * @param datestr String
     * 存在一定格式的日期、时间字符串，如：20041001、200410011503
     * @param inFmt String
     * 对datestr参数格式说明，参照类说明，如：yyyyMMdd、yyyyMMddHHmm
     * @param outFmt String
     * 返回样式，参照类说明，如：yyyy年MM月dd日
     * @throws ParseException 当datestr不能格式化为inFmt格式时抛出此异常
     * @return String
     * 格式化后的日期、时间字符串，如：2004年10月01日、2004年10月01日
     * <BR>输出样式outFmt非法时，使用yyyyMMdd格式输出
     */
    public static String getDateFormat(String datestr,String inFmt,
                                       String outFmt)
        throws ParseException
    {
        if(null == datestr || "".equals(datestr.trim())){
            return datestr;
        }

        if(null == inFmt || "".equals(inFmt.trim())){
            return datestr;
        }

        if(null == outFmt || "".equals(outFmt.trim())){ //输出样式非法
            outFmt = "yyyyMMdd";
        }

        java.util.Date inDate = getDate(datestr,inFmt);

        if(null == inDate){ //根据inFmt分析datestr时抛出异常
            return datestr;
        }

        String retu = getDateFormat(inDate,outFmt);
        inDate = null;
        return retu;
    }

    /**
     * 把日期时间字符串，按inFmt样式转化为日期对象，然后格式化为默认样式yyyyMMdd
     * @param datestr String
     * 存在一定格式的日期、时间字符串，如：20041001、200410011503
     * @param inFmt String
     * 对datestr参数格式说明，参照类说明，如：yyyyMMdd、yyyyMMddHHmm
     * @throws ParseException 当datestr不能格式化为inFmt格式时抛出此异常
     * @return String
     * 格式化后的日期、时间字符串，如：20041001、20041001
     */
    public static String getDateFormat(String datestr,String inFmt)
        throws ParseException
    {
        return getDateFormat(datestr,inFmt,"yyyyMMdd");
    }

    /**
     * 根据inFmt的样式，日期时间字符串转化为日期时间对象
     * @param datestr String
     * 日期时间字符串，如：20041001、2004年10月01日 15:03
     * @param inFmt String
     * 对datestr参数格式说明，参照类说明，如yyyyMMdd、yyyy年MM月dd日 HH:mm
     * @throws ParseException 当datestr不能格式化为inFmt格式时抛出此异常
     * @return Date
     * 日期时间对象，格式inFmt非法时，使用yyyyMMdd格式
     */
    public static Date getDate(String datestr,String inFmt)
        throws ParseException
    {
        if(null == datestr || "".equals(datestr.trim())){
            return null;
        }

        if(null == inFmt || "".equals(inFmt.trim())){ //inFmt非法
            inFmt = "yyyyMMdd";
        }

        java.util.Date inDate = null;

        //依据inFmt格式把日期字符串转化为日期对象
        SimpleDateFormat inDateFormat = new SimpleDateFormat(inFmt);
        inDateFormat.setLenient(true);
        inDate = inDateFormat.parse(datestr);

        inDateFormat = null;
        return inDate;
    }

    /**
     * 对日期时间对象进行调整，实现如昨天是几号，去年的今天星期几等.
     * <BR>例子：
     * <pre>
     * <blockquote>
     * 计算去年今天星期几
     * Date date = DateUtils.addDate(new Date(),Calendar.YEAR,-1);
     * System.out.println(DateUtils.getDateFormat(date,"E"));
     * 打印60天后是什么日期，并显示为 yyyy-MM-dd 星期
     * Date date = DateUtils.addDate(new Date(),Calendar.DATE,60);
     * System.out.println(DateUtils.getDateFormat(date,"yyyy-MM-dd E"));
     * </blockquote>
     * </pre>
     * @param date Date
     * 需要调整的日期时间对象
     * @param CALENDARFIELD int
     * 对日期时间对象以什么单位进行调整：
     * <pre>
     * <blockquote>
     * 年 Calendar.YEAR
     * 月 Calendar.MONTH
     * 日 Calendar.DATE
     * 时 Calendar.HOUR
     * 分 Calendar.MINUTE
     * 秒 Calendar.SECOND
     * </blockquote>
     * </pre>
     * @param amount int
     * 调整数量，>0表向后调整（明天，明年），<0表向前调整（昨天，去年）
     * @return Date
     * 调整后的日期时间对象
     */
    public static Date addDate(Date date,int CALENDARFIELD,int amount)
    {
        if(null == date){
            return date;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(CALENDARFIELD,amount);
        return calendar.getTime();
    }

    /**
     * 对日期时间对象进行调整.
     * @param datestr String
     * 需要调整的日期时间字符串，它的格式为yyyyMMdd
     * @param CALENDARFIELD int
     * 对日期时间对象以什么单位进行调整
     * @param amount int
     * 调整数量
     * @throws ParseException 当datestr不能格式化为yyyyMMdd格式时抛出此异常
     * @return Date
     * 调整后的日期时间对象
     * @see #addDate(java.util.Date, int, int)
     */
    public static Date addDate(String datestr,int CALENDARFIELD,int amount)
        throws ParseException
    {
        return addDate(getDate(datestr,"yyyyMMdd"),CALENDARFIELD,amount);
    }

    /**
     *
     * @param dateTimeStr String 格式化的时间串
     * @param formatType 数据格式类型 {@link #DB_STORE_DATE},{@link #EN_HTML_DISPLAY_DATE},{@link #CN_HTML_DISPLAY_DATE}
     * @param detal int  增加或减少的时间
     * @param field int 参见Calendar中关于时间字段属性的定义
     * @return String 返回的
     */
    public static String add(String dateTimeStr, int formatType, int detal,
                             int field)
    {
        if (dateTimeStr == null || dateTimeStr.length() < 6)
        {
            return dateTimeStr;
        }
        else
        {
            try
            {
                String formatStr = null;
                switch (formatType)
                {
                    case DB_STORE_DATE:
                        formatStr = "yyyyMMddHHmmss";
                        break;
                    case HYPHEN_DISPLAY_DATE:
                        formatStr = "yyyy-MM-dd HH:mm:ss";
                        break;
                    case DOT_DISPLAY_DATE:
                        formatStr = "yyyy.MM.dd HH:mm:ss";
                        break;
                    case CN_DISPLAY_DATE:
                        formatStr = "yyyy'年'MM'月' HH：mm：ss";
                        break;
                    default:
                        formatStr = "yyyyMMddHHmmss";
                        break;
                }

                formatStr = formatStr.substring(0, dateTimeStr.length());
                SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
                Date d = sdf.parse(dateTimeStr);
                GregorianCalendar g = new GregorianCalendar();
                g.setTime(d);
                g.add(field, detal);
                d = g.getTime();
                return sdf.format(d);
            }
            catch (ParseException ex)
            {
                ex.printStackTrace();
                return dateTimeStr;
            }
        }
    }

    /**
     * 根据出生日期，计算出在某一个日期的年龄
     * @param birthday Date
     * 出生日期时间对象
     * @param date2 Date
     * 计算日期对象
     * @return int
     * 返回date2那一天出生日期为birthday的年龄，如果birthday大于date2则返回-1
     */
    public static int getAge(Date birthday,Date date2)
    {
        if(null == birthday || null == date2){
            return -1;
        }

        if(birthday.after(date2)){ //birthday大于date2
            return -1;
        }

        int ibdYear = StringUtils.getInt(getDateFormat(birthday,"yyyy"), -1);
        int idate2Year = StringUtils.getInt(getDateFormat(date2,"yyyy"), -1);

        if(ibdYear < 0 || idate2Year < 0){
            return -1;
        }
        if(ibdYear > idate2Year){
            return -1;
        }

        return idate2Year - ibdYear + 1;
    }

    /**
     * 根据出生日期，计算出当前的年龄
     * @param birthday Date
     * 出生日期时间对象
     * @return int
     * 返回出生日期为birthday的年龄，如果birthday大于当前系统日期则返回-1
     */
    public static int getAge(Date birthday)
    {
        return getAge(birthday,new Date());
    }

    /**
     * 根据出生日期，计算出当前的年龄
     * @param birthdaystr String
     * 出生日期时间字符串，其格式一定为yyyyMMdd
     * @throws ParseException 当datestr不能格式化为yyyyMMdd格式时抛出此异常
     * @return int
     * 返回出生日期为birthday的年龄，如果birthday大于当前系统日期则返回-1
     */
    public static int getAge(String birthdaystr)
        throws ParseException
    {
        return getAge(getDate(birthdaystr,"yyyyMMdd"));
    }
    /**
     * 日期相减，得到天数据。
     * @param enddate
     * @param begindate
     * @return
     */
    public static int getIntervalDays(Date  enddate,Date  begindate)   {
        long   millisecond   =   enddate.getTime()   -   begindate.getTime();
        int   day   =   (int)   (millisecond /   24L   /   60L   /   60L   /   1000L);
        return   day;
    } 
    /**
     *  日期相减，得到天数据。
     * @param enddate,不能为空，日期格式：yyyymmdd,yyyy-mm-dd
     * @param begindate不能为空，日期格式：yyyymmdd,yyyy-mm-dd
     * @return
     */
    public static int getIntervalDays(String  enddate,String  begindate)   {
    	 
		try {
			enddate = enddate.replace("-", "");
			begindate = begindate.replace("-", "");
			String enddate2="";
			if(null != enddate && enddate.length()==8){
				enddate2 = enddate + "000000";
			}
			String begindate2="";
			if(null != begindate && begindate.length()==8){
				begindate2 = begindate + "000000";
			}
			 Date d1 = getDate(enddate2,"yyyyMMddHHmmss");
			 Date d2=getDate(begindate2,"yyyyMMddHHmmss");
	          int d=getIntervalDays(d1, d2);
	          return d;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}         
    }
    public static void main(String[] args)
        throws ParseException
    {
        //System.out.println(DateUtils.getAge("200401"));
//        Date d1=DateUtils.getDate("20101211000000","yyyyMMddHHmmss");
//        Date d2=DateUtils.getDate("20101111000000","yyyyMMddHHmmss");
//        int d=DateUtils.getIntervalDays(d1, d2);
    	 int d=DateUtils.getIntervalDays("20101111", "2010-12-11");
        System.out.println(d);
        String s ="1,4";
        s=s.replaceAll(",", "','");		
        System.out.println(!com.infolion.platform.dpframework.util.DateUtils.isDateBefore("2012-02-27 23:59:59"));
    }

}
