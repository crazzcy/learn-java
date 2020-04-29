package org.chenyang.study.redis;

import redis.clients.jedis.*;

import java.util.Arrays;

/**
 * @author : ChenYang
 * @date : 2020-04-16 8:59 下午
 * @since :
 */
public class JedisPubSubApi {
    /**
     * 测试jedis 发布、订阅API
     * 官方共6个命令
     * psubscribe / punsubscribe: 订阅/退订与给定模式相匹配(pattern)的所有频道，如果没给退订格式，则退订全部
     * subscribe / unsubscribe : 订阅/退订一个或多个频道
     * publish: 向给定频道发送消息
     * pubsub: 一个内省命令，允许检查发布/订阅子系统的状态。
     * @param jedisPool jedis客户端连接池
     */
    public static void testJedisPubSubApi(JedisPool jedisPool) {
        Jedis subJedisClient = jedisPool.getResource();
        Jedis subJedisClient2 = jedisPool.getResource();
        Jedis subJedisClient3 = jedisPool.getResource();
        Jedis subJedisClient4 = jedisPool.getResource();
        Jedis subJedisClient5 = jedisPool.getResource();
        Jedis pubJedisClient = jedisPool.getResource();
        MyJedisPubSub myJedisPubSub = new MyJedisPubSub();
        // 第一个参数，指定pubsub，后面可变参数指定channels

        String channel = "CCTV1";
        String channel2 = "CCTV2";
        String channel3 = "CCTV3";

        Thread subThread1 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            subJedisClient.subscribe(myJedisPubSub, channel);
            // subJedisClient.subscribe(myJedisPubSub, channel2);
            // subJedisClient.subscribe(myJedisPubSub, channel3);

        });
        Thread subThread2 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            subJedisClient2.subscribe(myJedisPubSub, channel);
            // subJedisClient.subscribe(myJedisPubSub, channel2);
            // subJedisClient.subscribe(myJedisPubSub, channel3);

        });
        Thread subThread3 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            subJedisClient3.subscribe(myJedisPubSub, channel);
            // subJedisClient.subscribe(myJedisPubSub, channel2);
            // subJedisClient.subscribe(myJedisPubSub, channel3);

        });

        Thread subThread4 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            subJedisClient4.pubsubChannels(channel);
            //subJedisClient4.psubscribe(myJedisPubSub, "CCTV*");
            // subJedisClient.subscribe(myJedisPubSub, channel2);
            // subJedisClient.subscribe(myJedisPubSub, channel3);

        });

        Thread pubThread = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            pubJedisClient.publish(channel, "hello world, welcome to " + channel);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            pubJedisClient.publish(channel, "let's start. this is " + channel);

            // pubJedisClient.publish(channel2, "hello world, welcome to " + channel2);
            // pubJedisClient.publish(channel3, "hello world, welcome to " + channel3);

        });

        subThread1.start();
        //subThread2.start();
        //subThread3.start();
        //subThread4.start();
        pubThread.start();

        try {
            subThread1.join();
            pubThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Main Thread Over");
        pubJedisClient.close();
        subJedisClient.close();
        subJedisClient2.close();
        subJedisClient3.close();
        subJedisClient4.close();
        subJedisClient5.close();


    }

    public static class MyJedisPubSub extends JedisPubSub {
        @Override
        public void onMessage(String channel, String message) {
            System.out.println("into onMessage: channel=" + channel + ",message=" + message);
            super.onMessage(channel, message);
        }

        @Override
        public void onPMessage(String pattern, String channel, String message) {
            super.onPMessage(pattern, channel, message);
        }

        @Override
        public void onSubscribe(String channel, int subscribedChannels) {
            System.out.println("into onSubscribe, channel: " + channel + ",subscribedChannels: " + subscribedChannels);
            // super.onSubscribe(channel, subscribedChannels);
        }

        @Override
        public void onUnsubscribe(String channel, int subscribedChannels) {
            super.onUnsubscribe(channel, subscribedChannels);
        }

        @Override
        public void onPUnsubscribe(String pattern, int subscribedChannels) {
            super.onPUnsubscribe(pattern, subscribedChannels);
        }

        @Override
        public void onPSubscribe(String pattern, int subscribedChannels) {
            super.onPSubscribe(pattern, subscribedChannels);
        }

        @Override
        public void onPong(String pattern) {
            super.onPong(pattern);
        }

        @Override
        public void unsubscribe() {
            super.unsubscribe();
        }

        @Override
        public void unsubscribe(String... channels) {
            super.unsubscribe(channels);
        }

        @Override
        public void subscribe(String... channels) {
            System.out.println("into subscribe, channels: " + Arrays.toString(channels));
            super.subscribe(channels);
        }

        @Override
        public void psubscribe(String... patterns) {
            super.psubscribe(patterns);
        }

        @Override
        public void punsubscribe() {
            super.punsubscribe();
        }

        @Override
        public void punsubscribe(String... patterns) {
            super.punsubscribe(patterns);
        }

        @Override
        public void ping() {
            super.ping();
        }

        @Override
        public boolean isSubscribed() {
            return super.isSubscribed();
        }

        @Override
        public void proceedWithPatterns(Client client, String... patterns) {
            super.proceedWithPatterns(client, patterns);
        }

        @Override
        public void proceed(Client client, String... channels) {
            super.proceed(client, channels);
        }

        @Override
        public int getSubscribedChannels() {
            return super.getSubscribedChannels();
        }
    }
}
