package com.guava;

import com.google.common.util.concurrent.RateLimiter;

public class LimitDemo {
    public  void testNoRateLimiter() {
        Long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            System.out.println("call execute.." + i);
        }
        Long end = System.currentTimeMillis();
        System.out.println("耗时:"+(end - start));
    }

    public void testWithRateLimiter() {
        Long start = System.currentTimeMillis();
        RateLimiter limiter = RateLimiter.create(1000.0); // 每秒不超过10个任务被提交
        for (int i = 0; i < 100; i++) {
            limiter.acquire(10); // 请求RateLimiter, 超过permits会被阻塞
            System.out.println("call execute.." + i);
        }
        Long end = System.currentTimeMillis();
        System.out.println("耗时:"+(end - start));
    }
    public static void main(String[] args) {
        LimitDemo limitDemo=new LimitDemo();
        limitDemo.testNoRateLimiter();
        limitDemo.testWithRateLimiter();
    }
}
