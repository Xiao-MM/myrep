package com.ming;

import com.ming.pojo.User;
import com.ming.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
public class TestRedis {

    @Autowired
    RedisUtil redisUtil;

    @Test
    void testRedisUtil(){
        Object name = redisUtil.get("name");
        System.out.println(name.toString());
        redisUtil.set("util","zs",10000);
        System.out.println(redisUtil.get("util").toString());

    }
    @Test
    void testGenericJackson2JsonRedisSerializer(){
        redisUtil.set("aaa","aaa");
        System.out.println(redisUtil.get("aaa").toString());
    }

    @Test
    void testJdkSerializationRedisSerializer(){
        redisUtil.set("bbb","bbb");
        System.out.println(redisUtil.get("bbb").toString());
    }

    @Test
    void testJackson2JsonRedisSerializer(){
//        redisUtil.set("ccc","ccc");
//        System.out.println(redisUtil.get("ccc").toString());
        User user = new User(1,"zs","123456");
//        redisUtil.set("user1",user);
//        System.out.println(redisUtil.get("user1").toString());
        Map<String,Object> map = new HashMap<>();

        map.put("id",user.getId());
        map.put("username",user.getUsername());
        map.put("password",user.getPassword());
        redisUtil.hashMoreSet("user2",map);
        System.out.println(redisUtil.hashMoreGet("user2"));
    }

    @Test
    void testStringRedisSerializer(){
//        redisUtil.set("ddd","ddd");
//        System.out.println(redisUtil.get("ddd").toString());
        User user = new User(1,"zs","123456");
        redisUtil.set("user3",user);
        System.out.println(redisUtil.get("user3").toString());
    }
    @Test
    void testList(){
        //redisUtil.lSet("lll","a1");
//        System.out.println(redisUtil.lRemove("lll", 2, "a1"));
//        List<Object> list = new ArrayList<>();
//        Collections.addAll(list, "a2", "a3", 4);
//        //redisUtil.lSet("lll", list);
//        System.out.println(list);
//        System.out.println(redisUtil.lGet("lll", 0, 20));
//        System.out.println(redisUtil.lGetIndex("lll", 0));
//        System.out.println(redisUtil.lGetSize("lll"));
//        redisUtil.lSet("lll","a5",10);
    }

    @Test
    void testSet(){
//        System.out.println(redisUtil.sSet("sss", "s1", "s2",  4));
//        System.out.println(redisUtil.sGet("sss"));
//        System.out.println(redisUtil.sGetSize("sss"));
//        System.out.println(redisUtil.sHasKey("sss", "s2"));
        redisUtil.sRemove("sss","s1",4);
        System.out.println(redisUtil.sGet("sss"));
    }

    @Test
    void testIncrAndDecr(){
        redisUtil.set("no",1);
        System.out.println(redisUtil.get("no"));
        redisUtil.incr("no",10);
        System.out.println(redisUtil.get("no"));
        redisUtil.decr("no",5);
        System.out.println(redisUtil.get("no"));
    }
    @Test
    void testExpire(){
        //redisUtil.expire("name",200);
        System.out.println(redisUtil.getExpire("name"));
    }
}
