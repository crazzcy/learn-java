package org.chenyang.study.concurrency.prodcons;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ChenYang
 * @date 2019-02-23 10:55
 **/

public class ToastQueue {
    private static List<Toast> toastList = new ArrayList<>();
    public static final int MAX_LENGTH = 10;

    private ToastQueue() {

    }
    public static List<Toast> getInstance() {
        return toastList;
    }
}
