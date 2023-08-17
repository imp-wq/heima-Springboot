package com.itniuma;

import com.itniuma.dao.UserDao;
import com.itniuma.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Springboot05MybatisApplicationTests {
    @Autowired
    private UserDao userDao;

    @Test
    void contextLoads() {
        User user = this.userDao.getById(1);
        System.out.println(user);
    }

}
