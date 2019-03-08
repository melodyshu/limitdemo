package com.guava;

import com.google.common.util.concurrent.RateLimiter;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 漏桶
 */
public class APP2 {
    RateLimiter rateLimiter = RateLimiter.create(10.0,1, TimeUnit.SECONDS);

    public void doPay(String name) {
        if (rateLimiter.tryAcquire()) {
            System.out.println(name + "支付成功"+rateLimiter.getRate());
        } else {
            System.out.println(name + "支付失败请求繁忙，请稍后重试"+rateLimiter.getRate());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final APP2 app = new APP2();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final Random random = new Random(10);
        for (int i = 0; i < 20; i++) {
            final int fi = i;
            Thread t = new Thread(){
                @Override
                public void run() {
                    try {
                        countDownLatch.await();
                        Thread.sleep(random.nextInt(1000));
                        app.doPay("Thread-" + fi + "");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            t.start();
        }
        countDownLatch.countDown();
    }
}
