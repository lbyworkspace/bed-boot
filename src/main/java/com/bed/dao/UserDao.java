package com.bed.dao;

import com.bed.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User,Long> {

    User findByAccount(@Param("account")String account);
}
