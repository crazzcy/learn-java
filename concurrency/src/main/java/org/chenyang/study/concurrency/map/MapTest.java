package org.chenyang.study.concurrency.map;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ChenYang
 * @date 2019-03-12 15:01
 **/

public class MapTest {
    private Hashtable hashTable = new Hashtable();
    private HashMap hashMap = new HashMap();
    private ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
}
