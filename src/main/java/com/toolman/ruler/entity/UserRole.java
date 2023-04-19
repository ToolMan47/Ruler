package com.toolman.ruler.entity;

import java.io.Serializable;

import com.toolman.ruler.entity.emb.UserRoleId;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "users_roles")
public class UserRole implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private UserRoleId id;

}
