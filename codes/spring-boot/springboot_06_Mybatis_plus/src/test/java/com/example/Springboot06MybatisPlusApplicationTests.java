package com.example;

import com.example.dao.UserDao;
import com.example.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Springboot06MybatisPlusApplicationTests {


    @Autowired
    private UserDao userDao;

    @Test
    void contextLoads() {
        User user = this.userDao.selectById(1);
        System.out.println(user);
    }

}
