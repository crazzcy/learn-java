package org.chenyang.study.java8.chapter12;

import java.time.*;
import java.util.TimeZone;

/**
 * @author ChenYang
 * @date 2019-06-05 22:11
 **/
public class DateTimeZone {

    private static void testZoneId() {
        ZoneId zoneId = TimeZone.getDefault().toZoneId();
        // Asia/Shanghai
        System.out.println(zoneId);

        LocalDateTime dateTime = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45);
        // 罗马时区
        ZoneId romeZone = ZoneId.of("Europe/Rome");
        ZonedDateTime zonedDateTime = dateTime.atZone(romeZone);
        System.out.println(zonedDateTime);

    }

    /**
     * 使用格林威治时间固定时区
     */
    private static void getZoneTime() {
        LocalDateTime dateTime = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45);
        ZoneOffset newYorkOffset = ZoneOffset.of("-05:00");
        OffsetDateTime dateTimeInNewYork = OffsetDateTime.of(dateTime, newYorkOffset);
        System.out.println(dateTimeInNewYork);

    }

    public static void main(String[] args) {
        testZoneId();
        getZoneTime();
    }
}
