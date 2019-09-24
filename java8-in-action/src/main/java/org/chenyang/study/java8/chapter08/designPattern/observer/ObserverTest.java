package org.chenyang.study.java8.chapter08.designPattern.observer;

/**
 * 观察者模式测试
 * @author ChenYang
 * @date 2019-04-25 22:29
 **/
public class ObserverTest {

    /**
     * 普通观察者模式
     */
    private static void normalObserver() {
        String news = "蕉娱乐旗下艺人王喆与老板王思聪一同出席某品牌发布会，有网友在发布会现场拍下一段王思聪为王喆拍照的视频，视频中的王思聪坐在台下。。。";
        RegisterCenter rc = new RegisterCenter();
        rc.registMedia(new BBCNews());
        rc.registMedia(new NYTimes());
        rc.publicNews(news);
    }


    private static void functionalObserver() {
        RegisterCenter rc = new RegisterCenter();
        rc.registMedia((news) -> System.out.println("from functional Media1:" + news));
        rc.registMedia((news) -> System.out.println("from functional Media2:" + news));

        String news = "蕉娱乐旗下艺人王喆与老板王思聪一同出席某品牌发布会，有网友在发布会现场拍下一段王思聪为王喆拍照的视频，视频中的王思聪坐在台下。。。";
        rc.publicNews(news);
    }

    public static void main(String[] args) {
        normalObserver();
        functionalObserver();
    }
}
