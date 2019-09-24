package org.chenyang.study.java8.chapter11.discount;

/**
 * @author ChenYang
 * @date 2019-05-01 10:22
 **/
public class Discount {
    public enum Code {
        NONE(0), SLIVER(5), GOLD(10), PLATINUM(15), DIAMOND(20);

        private int percent;

        Code(int percent) {
            this.percent = percent;
        }
    }

    public static String applyDiscount(Quote quote) {
        return "shopName:[" + quote.getShopName() + "] [price:" + apply(quote.getPrice(), quote.getDiscountCode()) + "]";
    }


    private static double apply(double price, Code code) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return formatPrice(price * (100 - code.percent) / 100);
    }


    private static double formatPrice(double price) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
        return Double.valueOf(df.format(price));
    }
}
