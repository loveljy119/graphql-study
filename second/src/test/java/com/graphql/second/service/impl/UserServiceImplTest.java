package com.graphql.second.service.impl;

import com.graphql.second.pojo.UserPo;
import com.graphql.second.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @Resource
    private UserService userService;

    @Test
    public void saveUser() {
        UserPo userPo=new UserPo();
        userPo.setUsername("zhangsan");
        userPo.setPassword("123456a");
        userService.saveUser(userPo);
    }

    @Test
    public void findByUsername() {
        List<UserPo> list = userService.findByUsername("zhangsan");
        System.out.println(list);
    }

    @Test
    public void findByExample() {
        Page<UserPo> page = userService.findByExample("ZHANGSAN");
        System.out.println(page.getTotalPages());
        List<UserPo> list = page.getContent();
        System.out.println(list.get(0).getUsername());

    }
}