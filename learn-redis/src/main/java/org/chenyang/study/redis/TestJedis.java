package org.chenyang.study.redis;

/**
 * @author : ChenYang
 * @date : 2019-09-22 21:30
 * @since :
 */
public class TestJedis {

    public static void main(String[] args) {

        try {
            System.out.println("方法开始咯");
            throw new Exception("测试异常");
        }catch(Exception e) {
            System.out.println("catch块:" + e.toString());
        }
    }
}
