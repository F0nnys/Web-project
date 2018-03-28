package com.roy.wenda.wendaUtil;

import redis.clients.jedis.Jedis;

public class JedisAdapter {
    public static void print(int index,Object object){
        System.out.println(String.format("%d,%s",index, object.toString()));
    }

    public static void main(String[] args){
        Jedis jedis = new Jedis("redis://127.0.0.1:6379/9");
        jedis.flushDB();
        jedis.set("hello","world");
        jedis.set("roy123","handsome");
        print(1,jedis.get("roy123"));
    }
}
