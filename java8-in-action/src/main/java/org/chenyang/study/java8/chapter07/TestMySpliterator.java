package org.chenyang.study.java8.chapter07;

import java.util.Spliterator;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author ChenYang
 * @date 2019-04-24 16:08
 **/

public class TestMySpliterator {
    private static final String SENTENCE =
            " Nel mezzo del cammin di nostra vita mi ritrovai in una selva oscura ché la dritta via era smarrita ";


    private static int normalCompute(String str) {
        int count = 0;
        if(str == null) {
            return count;
        }
        // 先删除两边的空格
        str = str.trim();
        boolean whiteSpaceFlag = false;
        boolean wordFlag = false;
        for(int i=0; i<str.length(); i++) {
            if(Character.isWhitespace(str.charAt(i))) {
                whiteSpaceFlag = true;
            } else {
                if(whiteSpaceFlag){
                    count++;
                }
                whiteSpaceFlag = false;
                wordFlag = true;
            }
        }
        // 最后一位是字符，没有空格
        if(wordFlag && !whiteSpaceFlag) {
            count += 1;
        }
        return count;

    }

    private static int functionCompute(String str) {
        Stream<Character> characterStream  = IntStream.rangeClosed(0, str.length()-1).mapToObj(str::charAt);
        // reduce -> 3个参数，初始值，累加，合并，和Collector一样
        WordCounter wordCounter =
                characterStream.reduce(
                        new WordCounter(false, 0),
                        WordCounter::accumulator,
                        WordCounter::combine);
        return wordCounter.getCount();
    }

    private static int spliteratorComputer(String str) {
        Spliterator<Character> spliterator = new WordCounterSpliterator(str);
        // 创建并行流
        Stream<Character> stream = StreamSupport.stream(spliterator, true);
        WordCounter wordCounter = stream.reduce(new WordCounter(false, 0),
                WordCounter::accumulator,
                WordCounter::combine);
        return wordCounter.getCount();
    }

    public static void main(String[] args) {
        System.out.println(normalCompute(SENTENCE));
        System.out.println(functionCompute(SENTENCE));
        System.out.println(spliteratorComputer(SENTENCE));

    }
}
