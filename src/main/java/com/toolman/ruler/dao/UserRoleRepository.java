package com.toolman.ruler.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.toolman.ruler.entity.UserRole;
import com.toolman.ruler.entity.emb.UserRoleId;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {

    @Query("SELECT ur FROM UserRole ur WHERE ur.id.userId = :userId")
    public List<UserRole> findByUserId(@Param("userId")Integer userId);
    
}
