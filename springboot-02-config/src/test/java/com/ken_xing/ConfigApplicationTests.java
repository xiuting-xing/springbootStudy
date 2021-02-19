package com.ken_xing;

import com.ken_xing.pojo.person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ConfigApplicationTests {

    @Autowired
    private person _person;

    @Test
    void contextLoads() {

        System.out.println(_person);
    }

}
