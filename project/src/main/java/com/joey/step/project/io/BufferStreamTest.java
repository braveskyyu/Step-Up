package com.joey.step.project.io;

import lombok.SneakyThrows;
import org.springframework.util.StopWatch;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class BufferStreamTest {
    static String path = "D:\\zhaoyu\\photo\\MVI_0013.AVI";
    @SneakyThrows
    public static void main(String[] args) {
        BufferStreamTest bt = new BufferStreamTest();
        bt.streamWriter();
    }

    void streamWriter() throws IOException {
        StopWatch sw = new StopWatch();
        sw.start();
        try (FileInputStream fi = new FileInputStream(path);
             FileOutputStream fo = new FileOutputStream("D:\\mv.avi")){
            int data;
            while ((data = fi.read())!=-1){
                fo.write(data);
            }
            fo.flush();
            sw.stop();
            System.out.println(sw.getTotalTimeSeconds()+"秒完成");
        }


    }
}
