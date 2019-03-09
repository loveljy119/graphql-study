package com.graphql.second.jpa;

import com.graphql.second.pojo.UserPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<UserPo,Long> {
    List<UserPo> findByUsername(String username);

    @Query("select u from UserPo u where id=:id and username=:username")
    UserPo findUser(@Param("id") Long id, @Param("username") String username);
}
