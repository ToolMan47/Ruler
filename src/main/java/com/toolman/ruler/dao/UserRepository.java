package com.toolman.ruler.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.toolman.ruler.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    public User findByUsernameAndEnabled(String username, Boolean enabled);
    
}
