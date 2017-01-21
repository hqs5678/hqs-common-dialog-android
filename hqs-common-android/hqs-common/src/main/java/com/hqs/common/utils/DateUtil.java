package com.hqs.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by apple on 16/9/30.
 */

public class DateUtil {

    public static long calculateTimeInterval(String time1, String time2){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            java.util.Date d1 = format.parse(time1);
            java.util.Date d2 = format.parse(time2);
            return calculateTimeInterval(d1, d2);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static long calculateTimeInterval(java.util.Date time1, java.util.Date time2){
        long d = time1.getTime() - time2.getTime();
        return d > 0 ? d : d * -1;
    }

    public static Date currentDate(){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        java.util.Date d = new java.util.Date();
        String dateString = format.format(d);

        String[] values = dateString.split("-");

        date.year = Integer.parseInt(values[0]);
        date.month = Integer.parseInt(values[1]);
        date.day = Integer.parseInt(values[2]);
        date.hour = Integer.parseInt(values[3]);
        date.minute = Integer.parseInt(values[4]);
        date.second = Integer.parseInt(values[5]);

        return date;
    }

    public static String currentDateString(){
        java.util.Date date = new java.util.Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = format.format(date);

        return dateString;
    }

    public static Date dateWithDateString(String dateString, String formatString){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat(formatString);
        java.util.Date d = null;
        try {
            d = format.parse(dateString);

            format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            dateString = format.format(d);

            String[] values = dateString.split("-");

            date.year = Integer.parseInt(values[0]);
            date.month = Integer.parseInt(values[1]);
            date.day = Integer.parseInt(values[2]);
            date.hour = Integer.parseInt(values[3]);
            date.minute = Integer.parseInt(values[4]);
            date.second = Integer.parseInt(values[5]);

            return date;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static int daysOfMonth(int mon, int year){
        switch (mon){
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12: return 31;
            case 4:
            case 6:
            case 9:
            case 11: return 30;
            default:
                if (isLeapYear(year)){
                    return 29;
                }
                else {
                    return 28;
                }
        }
    }

    public static boolean isLeapYear(int year){
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0){
            return true;
        }
        return false;
    }

    public static class Date{
        public int year;
        public int month;
        public int day;
        public int hour;
        public int minute;
        public int second;

        public String getDatetime(){
            return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
        }
    }
}
