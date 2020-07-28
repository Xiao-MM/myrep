package com.ming;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

@SpringBootTest
public class TestBasic {
    @Test
    void testSubStr(){
        String father = "aaabbbcccdd";
        String son = "aabbbcx";
        int i = father.indexOf(son);
        System.out.println(i);
    }
}
