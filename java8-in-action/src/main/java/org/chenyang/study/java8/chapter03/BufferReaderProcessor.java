package org.chenyang.study.java8.chapter03;

import java.io.BufferedReader;
import java.io.IOException;

@FunctionalInterface
/**
 * @author ChenYang
 * @date 2019-04-16 19:34
 * 处理文件的函数式接口
 */
public interface BufferReaderProcessor {
    String process(BufferedReader br) throws IOException;
}
