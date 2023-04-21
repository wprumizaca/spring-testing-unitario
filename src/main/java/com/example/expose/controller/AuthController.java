package com.example.expose.controller;


//Este controlador se va encargar de toda la Autenticación

import com.example.expose.dto.LoginRequest;
import com.example.expose.dto.LoginResponse;
import com.example.expose.dto.UserRegisterDTO;
import com.example.persistence.entities.User;
import com.example.security.JwtTokenProvider;
import com.example.services.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authManager;
    private final  JwtTokenProvider jwtTokenProvider;

    /**
     * Registrar/Crear un Usuario
     */
    @PostMapping("/auth/register")
    public User save(@RequestBody UserRegisterDTO userRegisterDTO){
        return userService.save(userRegisterDTO);
    }


    /**
     Loguearse
     */
    @PostMapping("/auth/login")
    public LoginResponse loginResponse(@RequestBody LoginRequest loginRequest){

        //Añadimos el usuario y contraseña del usuario que se va a loguear para, con estos datos, posteriormente crear el token
        Authentication authenticationDTO = new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password());

        //Para hacer la autenticación
        Authentication authentication = authManager.authenticate(authenticationDTO);
        User user = (User) authentication.getPrincipal();

        //y aquí GENERAMOS el token
        String token = jwtTokenProvider.generateToken(authentication);

        //Ese token generado es el que enviamos al Cliente
        return new LoginResponse(user.getUsername(),
                        user.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority).toList(), token);

    }

}
