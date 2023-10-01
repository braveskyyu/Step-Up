package com.suyi.mytest.study;

import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class MyCache<K,V> {
    public MyCache(){}
    private Map map = new HashMap();
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock() ;
    @SneakyThrows
    public void put(K key, V value){
        try{
            lock.writeLock().lock();
            System.out.println(key+"写入");
            map.put(key, value);
            //TimeUnit.SECONDS.sleep((long) 0.5);
            System.out.println(key+"写完");
        }catch(Exception ex){
        }
        finally{
            lock.writeLock().unlock();
        }
    }

    @SneakyThrows
    public V get(K key){
        try{
            lock.readLock().lock();
            System.out.println("开始读"+key);
            V value = (V)map.get(key);
            //TimeUnit.SECONDS.sleep((long) 0.5);
            System.out.println("读完"+key+"\t"+value);
            return value;
        }catch(Exception ex){
            return null;
        }
        finally{
            lock.readLock().unlock();
        }

    }

    public void clear(){
        map.clear();
    }
}

public class ReadWriteLockTest {
    @SneakyThrows
    public static void main(String[] args) {
        MyCache cache = new MyCache();

        for (int i = 1; i <= 5; i++) {
            int finalI = i;
            new Thread(()->{
                cache.put(finalI +"", finalI +"");
            },"t1").start();
        }

        TimeUnit.SECONDS.sleep(3);

        for (int i = 1; i <= 5; i++) {
            int finalI = i;
            new Thread(()->{
                cache.get(finalI+"");
            },"t1").start();
        }
    }
}
