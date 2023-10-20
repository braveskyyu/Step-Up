package com.joey.step.project.io;

import lombok.SneakyThrows;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileTest {
    @SneakyThrows
    public static void main(String[] args) {
        String path = "D:\\mytest.txt";
        writeFile(path, "你好，明天!");

        System.out.println(readFile(path));
    }

    static void writeFile(String path, String content) throws IOException {
        try (RandomAccessFile rf = new RandomAccessFile(path, "rw")) {
            rf.seek(rf.length());
            rf.writeUTF(content);
        }
    }

    static String readFile(String path) throws IOException{
        StringBuilder sb = new StringBuilder();
        try (RandomAccessFile rf = new RandomAccessFile(path, "r")) {
            rf.seek(0);
            System.out.println(rf.getFilePointer() +" 起始位置;" +rf.length()+" 总大小");
            while (rf.getFilePointer()<rf.length()){
                sb.append(rf.readUTF());
            }
        }
        return sb.toString();
    }
}
