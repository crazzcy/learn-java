package org.chenyang.study.java8.chapter12;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;

/**
 * chapter12:测试java8中的时间类
 * @author ChenYang
 * @date 2019-06-05 20:54
 **/
public class TestLocalDateTime {


    /**
     * 测试LocalDate和LocalTime
     */
    private static void testLocalDateTime() {
        LocalDate localDate = LocalDate.of(2019,6,5);

        System.out.println(localDate.getYear());
        System.out.println(localDate.getDayOfYear());
        System.out.println(localDate.getMonth());
        System.out.println(localDate.getMonthValue());
        System.out.println(localDate.getDayOfMonth());

        int year = localDate.get(ChronoField.YEAR);
        int month = localDate.get(ChronoField.MONTH_OF_YEAR);
        int day = localDate.get(ChronoField.DAY_OF_MONTH);

        System.out.println(year + "-" + month + "-" + day);


        LocalTime localtime = LocalTime.of(21,2,33);

        System.out.println(localtime.getHour());
        System.out.println(localtime.getMinute());
        System.out.println(localtime.getSecond());


        LocalDate bth = LocalDate.parse("2018-06-18");
        System.out.println(bth.getDayOfMonth());
    }

    /**
     * 合并时间和日期
     */
    private static void combineDateAndTime() {
        LocalDate localDate = LocalDate.of(2019,6,5);
        LocalTime localtime = LocalTime.of(21,2,33);

        LocalDateTime localDateTime = LocalDateTime.of(localDate, localtime);
        System.out.println(localDateTime);

        LocalDateTime localDateTime2 = localDate.atTime(localtime);
        LocalDateTime localDateTime3 = localtime.atDate(localDate);

        //2019-06-05T21:02:33
        System.out.println(localDateTime2);
        System.out.println(localDateTime3);

        // 提取
        LocalDate localDate2 = localDateTime.toLocalDate();
        LocalTime localtime2 = localDateTime.toLocalTime();
        System.out.println(localDate2);
        System.out.println(localtime2);
    }


    /**
     * 创建周期
     */
    private static void testDurationAndPeriod() {
        LocalDate localDate1 = LocalDate.of(2018,6,5);

        LocalDate localDate2 = LocalDate.of(2019,6,5);

        Period period = Period.between(localDate1, localDate2);

        Period twoYearsThreeMonthsFourDays = Period.of(2,3,4);
    }

    private static void printNow() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String s3 = LocalDateTime.now().format(formatter);
        System.out.println(s3);
    }

    public static void main(String[] args) {
        printNow();
    }
}
