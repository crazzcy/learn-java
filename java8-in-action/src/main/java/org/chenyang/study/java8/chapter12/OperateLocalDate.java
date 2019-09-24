package org.chenyang.study.java8.chapter12;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;

/**
 * 12.2 操作时间
 * @author ChenYang
 * @date 2019-06-05 21:22
 **/

public class OperateLocalDate {

    private static void testWith() {
        LocalDate localDate = LocalDate.of(2019,6,5);

        localDate = localDate.withYear(2011);
        localDate = localDate.withDayOfMonth(18);
        localDate = localDate.withMonth(3);

        System.out.println(localDate);

        localDate = localDate.plusDays(10L);
        System.out.println(localDate);

        localDate = localDate.minusDays(10L);
        System.out.println(localDate);


        localDate = localDate.plusWeeks(1L);
        System.out.println(localDate);

        localDate = localDate.plusDays(1L).plusWeeks(1L).withMonth(3).withYear(2018);
        System.out.println(localDate);

    }

    private static void testTemporalAdjuster() {
        LocalDate localDate = LocalDate.of(2019,6,5);
        localDate = localDate.with(TemporalAdjusters.lastDayOfMonth());
        System.out.println(localDate);

        // 下个周五
        localDate = localDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY));
        System.out.println(localDate);

        localDate = localDate.with(temporal -> {
            DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
            int addDays = 1;
            if(dow == DayOfWeek.FRIDAY) {
                addDays = 3;
            } else if(dow == DayOfWeek.SATURDAY) {
                addDays = 2;
            }
            return temporal.plus(addDays, ChronoUnit.DAYS);
        });


        System.out.println(localDate);
    }


    private static void testDateFormatter() {
        LocalDate localDate = LocalDate.of(2019,6,5);

        String s1 = localDate.format(DateTimeFormatter.BASIC_ISO_DATE);
        String s2 = localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String s3 = localDate.format(formatter);

        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);

        DateTimeFormatter italianFormatter = new DateTimeFormatterBuilder()
                .appendText(ChronoField.DAY_OF_MONTH)
                .appendLiteral(". ")
                .appendText(ChronoField.MONTH_OF_YEAR)
                .appendLiteral(" ")
                .appendText(ChronoField.YEAR)
                .parseCaseInsensitive()
                .toFormatter(Locale.ITALIAN);
        String s4 = localDate.format(italianFormatter);

        System.out.println(s4);
    }

    public static void main(String[] args) {
        //testWith();
        //testTemporalAdjuster();
        testDateFormatter();
    }
}
