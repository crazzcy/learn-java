package org.chenyang.study.concurrency.threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ChenYang
 * @date 2019-03-21 14:58
 **/

public class ThreadLocalTest {

    //ThreadLocal<Person> threadLocal = ThreadLocal.withInitial();


    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM/ddHHmmss");
        String ymd = sdf.format(new Date());
        System.out.println(ymd);
    }

}
