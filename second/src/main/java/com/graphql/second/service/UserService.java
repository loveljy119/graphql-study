package com.graphql.second.service;

import com.graphql.second.pojo.UserPo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    public void saveUser(UserPo userPo);

    public List<UserPo> findAll();

    public List<UserPo> findByUsername(String username);

    public Page<UserPo> findByExample(String username);

    UserPo findById(Long id);

    UserPo findByIdAndUsername(Long id, String username);
}
