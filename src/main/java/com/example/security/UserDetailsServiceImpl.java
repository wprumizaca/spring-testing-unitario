package com.example.security;

import com.example.services.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Esta clase va a estraer el usuario de BBDD. Esta clase usa el servicio UserService para
 * poder buscar por nombre
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);//Para indentificar sitodo va bien o no

    private final UserService userService;


    /**
     Busca en BBDD y lo devuelve y en caso de que no lo encuentre lanza una excepción.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("loadUserByUsername {}", username);
        return this.userService.findByUserName(username)
                .orElseThrow(()->new UsernameNotFoundException(username + "no encontrado"));
    }
}
