package com.joey.step.project.thread;

/**
 * CountDownLatch 测试
 * Enum 测试
 * https://www.bilibili.com/video/BV18b411M7xz?p=32&vd_source=31cf5e360eb13ff91e5b7d2f53c08513
 */

import com.joey.step.project.collections.CountryEnum;
import lombok.SneakyThrows;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {
    private static CountDownLatch cdl = new CountDownLatch(6);

    @SneakyThrows
    public static void main(String[] args) {
        for (int i = 1; i <= 6; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"被灭");
                cdl.countDown();
            }, CountryEnum.getCountryEnum(i).getCountryName()).start();
        }

        cdl.await();
        System.out.println("天下一统!!!");
    }
}
