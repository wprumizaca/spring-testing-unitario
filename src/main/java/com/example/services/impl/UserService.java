package com.example.services.impl;

import com.example.expose.dto.UserRegisterDTO;
import com.example.persistence.entities.User;
import com.example.persistence.entities.UserAuthority;
import com.example.persistence.repositories.UserRepository;
import com.example.services.contract.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    /**
     Buscar usuario en BBDD
     */
    public Optional<User> findByUserName(String username){
        return this.userRepository.findByUsername(username);
    }
    //.userRepository.findByUsername(username) es el que tenemos en UserRepository->
    // findByUsername Tiene que coincidir con el nombre del atributo en la entidad User
    /**
      Guardar usuario en BBDD
     */
    public User save(UserRegisterDTO userRegisterDTO){
        User user = new User(null,
                userRegisterDTO.email(),
                passwordEncoder.encode(userRegisterDTO.password()),userRegisterDTO.username(),
                List.of(UserAuthority.READ)); //Todos los usuarios que se guardan SOLO tiene permisos de
                // lectura. Después del login deberían OBTENER otro perimos

        return this.userRepository.save(user);
    }
}
