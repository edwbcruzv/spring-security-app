package com.app.service;

import com.app.persistence.entity.UserEntity;
import com.app.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // si existe el usuario lo traemos de la base de datos.
        UserEntity user = userRepository.findUserEntityByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("El usuario "+username+" no existe."));


        // tenemos que transfrormar el rol de la forma que spring lo reconosca
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(rol ->
                authorities.add(new SimpleGrantedAuthority("ROLE_".concat(rol.getRoleEnum().name()))));

        // tenemos que transformar los permisos de forma que spring lo reconosca
        user.getRoles().stream()
                .flatMap(rol -> rol.getPermissions().stream())
                .forEach(permission ->authorities.add(new SimpleGrantedAuthority(permission.getName())));
        
        // debemos de transformar el nuestro UserEntity para que spring lo reconosca
        return new User(user.getUsername(),user.getPassword(),user.isEnable(),user.isAccountNoExpired(),
                user.isCredentialNoExpired(),user.isAccountNotLocked(),authorities);

    }
}
