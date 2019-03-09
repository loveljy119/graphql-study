package com.graphql.second.service.impl;

import com.graphql.second.jpa.UserDao;
import com.graphql.second.pojo.UserPo;
import com.graphql.second.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;


    @Override
    public void saveUser(UserPo userPo) {
        userDao.save(userPo);
    }

    @Override
    public List<UserPo> findAll() {
        return userDao.findAll();
    }

    @Override
    public List<UserPo> findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public Page<UserPo> findByExample(String username) {
        Sort sort=new Sort(Sort.Direction.DESC,"id");
        Pageable page=PageRequest.of(0,5,sort);
        UserPo userPo=new UserPo();
        userPo.setUsername(username);
        ExampleMatcher exampleMatcher=ExampleMatcher.matching()
                .withMatcher("username", ExampleMatcher.GenericPropertyMatchers.startsWith()).withIgnoreCase();
        Example<UserPo> userPoExample=Example.of(userPo,exampleMatcher);
        return userDao.findAll(userPoExample,page);
    }

    @Override
    public UserPo findById(Long id) {
        return userDao.findById(id).get();
    }

    @Override
    public UserPo findByIdAndUsername(Long id, String username) {
        return userDao.findUser(id,username);
    }
}
