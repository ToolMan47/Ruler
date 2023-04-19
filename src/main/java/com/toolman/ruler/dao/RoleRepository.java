package com.toolman.ruler.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.toolman.ruler.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{
    
}
