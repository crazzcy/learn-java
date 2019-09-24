package org.chenyang.study.java8.chapter07;

import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * @author ChenYang
 * @date 2019-04-25 10:54
 **/

public class WordCounterSpliterator implements Spliterator<Character> {

    /**
     * 当前字符索引
     */
    private int currentCharIndex = 0;

    private String sentence;

    public WordCounterSpliterator(String sentence) {
        this.sentence = sentence;
    }

    /**
     * tryAdvance方法把String中当前位置的Character传给了Consumer，并让位置加一。
     * If a remaining element exists, performs the given action on it
     * @param action
     * @return
     */
    @Override
    public boolean tryAdvance(Consumer<? super Character> action) {
        action.accept(sentence.charAt(currentCharIndex++));
        return currentCharIndex < sentence.length();
    }

    /**
     * trySplit方法是Spliterator中最重要的一个方法，因为它定义了拆分要遍历的数据结构的逻辑。
     * 就像在实现的RecursiveTask的compute方法一样（分支 /合并框架的使用方式），首先要设定不再进一步拆分的下限。
     * 这里用了一个非常低的下限——10个Character，仅仅是为了保证程序会对那个比较短的String做几次拆分。
     * 在实际应用中，就像分支/合并的例子那样，你肯定要用更高的下限来避免生成太多的任务。
     * 如果剩余的Character数量低于下限，你就返回null表示无需进一步拆分。
     * 相反，如果你需要执行拆分，就把试探的拆分位置设在要解析的String块的中间。
     * 但我们没有直接使用这个拆分位置，因为要避免把词在中间断开，于是就往前找，直到找到一个空格。
     * 一旦找到了适当的拆分位置，就可以创建一个新的Spliterator来遍历从当前位置到拆分位置的子串；把当前位置this设为拆分位置，因为之前的部分将由新Spliterator来处理，最后返回。
     * @return
     */
    @Override
    public Spliterator<Character> trySplit() {
        int currentSize = sentence.length() - currentCharIndex;
        if (currentSize < 10) {
            return null;
        }
        for (int splitPos = currentSize / 2 + currentCharIndex; splitPos < sentence.length(); splitPos++) {
            if (Character.isWhitespace(sentence.charAt(splitPos))) {
                // 从有空格的地方再切割
                Spliterator<Character> spliterator = new WordCounterSpliterator(sentence.substring(currentCharIndex, splitPos));
                currentCharIndex = splitPos;
                return spliterator;
            }
        }
        return null;
    }

    /**
     * 需要遍历的元素的estimatedSize就是这个Spliterator解析的String的总长度和当前遍历的位置的差。
     * @return
     */
    @Override
    public long estimateSize() {
        return sentence.length() - currentCharIndex;
    }

    /**
     * characteristic方法告诉框架这个Spliterator是
     * ORDERED（顺序就是String 中各个Character的次序）
     * SIZED（estimatedSize方法的返回值是精确的）
     * SUBSIZED（trySplit方法创建的其他Spliterator也有确切大小）
     * NONNULL（String 中不能有为 null 的 Character ）
     * IMMUTABLE（在解析 String 时不能再添加 Character，因为String本身是一个不可变类）的。
     * @return
     */
    @Override
    public int characteristics() {
        return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
    }
}
