package com.app.persistence.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@Entity
@Table(name="users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;



    private boolean isEnable;
    private boolean accountNoExpired;
    private boolean accountNotLocked;
    private boolean credentialNoExpired;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name= "user_roles" )
    private Set<RoleEntity> roles = new HashSet<>();
}
