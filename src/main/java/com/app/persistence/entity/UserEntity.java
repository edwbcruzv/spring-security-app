package com.app.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    @Column
    private String password;
    @Column
    private boolean enabled;
    @Column
    private boolean accountNonExpired;
    @Column
    private boolean accountNonLocked;
    @Column
    private boolean credentialsNonExpired;
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name= "user_roles" )
    private Set<RoleEntity> roles = new HashSet<>();
}
