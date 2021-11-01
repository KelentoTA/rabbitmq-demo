package com.example.rabbitmqproject;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Sets.newHashSet;

@SpringBootTest
class RabbitMqProjectApplicationTests {

    @Test
    void contextLoads() {
        String s = "zhangsan=12,lishi=13";
        Map<String, String> map = Splitter.on(",").withKeyValueSeparator("=").split(s);
        System.out.println("s: " + map);

        String str = "1,2,3,4,5";
        List<String> list = Splitter.on(",").splitToList(str);
        System.out.println("list: " + list);

        String str2 = "1,2, 3,4, 5, 6";
        List<String> list2 = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(str2);
        System.out.println("list2: " + list2);

        String input = "aa.dd,,ff,,.";
        List<String> result = Splitter.onPattern("[.|,]").omitEmptyStrings().splitToList(input);
        System.out.println("result: " + result);
    }

    @Test
    void test2() {
//        String s2 = CharMatcher.inRange('0', '9').retainFrom("abc 123 efg");
//        boolean result = CharMatcher.inRange('a', 'z').or(CharMatcher.inRange('A', 'Z')).matches('K');
//        System.out.println("s2: " + result);

        HashSet setA = newHashSet(1, 2, 3, 4, 5);
        HashSet setB = newHashSet(4, 5, 6, 7, 8);

        Sets.SetView union = Sets.intersection(setA, setB);
        System.out.println("union:" + union);
    }

}
