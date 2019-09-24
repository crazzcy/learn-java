package org.chenyang.study.java8.chapter03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * 环绕执行
 * @author ChenYang
 * @date 2019-04-16 19:34
 **/

public class ExecuteAround {
    /**
     * 处理文件，这段代码，只有readLine有效，因此要拆解该方法，将readLine动作传递
     * @return
     * @throws IOException
     */
    public static String processFile() throws IOException {
        try(BufferedReader br = new BufferedReader(new FileReader(ExecuteAround.class.getResource("/").getPath()+ "test.txt"))) {
            return br.readLine();
        }
    }

    /**
     * 1、行为参数化，将readLine方法抽象出来
     * 2、使用函数式接口，来传递行为，创建BufferReaderProcessor
     * 3、执行行为（接口方法），执行BufferReaderProcessor接口的process方法
     * 4、传递行为， 创建Lambda表达式定义
     * @return
     * @throws IOException
     */
    public static String processFile2(BufferReaderProcessor bufferReaderProcessor) throws IOException {
        try(BufferedReader br = new BufferedReader(new FileReader(ExecuteAround.class.getResource("/").getPath()+ "test.txt"))) {
            return bufferReaderProcessor.process(br);
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println(processFile());
        System.out.println(processFile2((BufferedReader br) -> br.readLine() + br.readLine()));
    }
}
