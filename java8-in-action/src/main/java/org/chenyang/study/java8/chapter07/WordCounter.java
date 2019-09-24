package org.chenyang.study.java8.chapter07;

/**
 * @author ChenYang
 * @date 2019-04-24 19:52
 **/

public class WordCounter {

    private boolean blankSpaceFlag;
    private int count;

    public boolean isBlankSpaceFlag() {
        return blankSpaceFlag;
    }

    public void setBlankSpaceFlag(boolean blankSpaceFlag) {
        this.blankSpaceFlag = blankSpaceFlag;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public WordCounter() {
        this.count = 0;
        this.blankSpaceFlag = false;
    }

    public WordCounter(boolean blankSpaceFlag, int count) {
        this.blankSpaceFlag = blankSpaceFlag;
        this.count = count;
    }

    /**
     * 累加
     */
    public WordCounter accumulator(char c) {

        if(Character.isWhitespace(c)) {
            this.blankSpaceFlag = true;
        } else {
            if(this.blankSpaceFlag) {
                this.count++;
                this.blankSpaceFlag = false;
            }
        }
        return new WordCounter(this.blankSpaceFlag, this.count);


    }

    public WordCounter combine(WordCounter wordCounter) {
        return new WordCounter(false, this.count + wordCounter.getCount());
    }

}
