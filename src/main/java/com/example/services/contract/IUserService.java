package com.example.services.contract;

import com.example.expose.dto.UserRegisterDTO;
import com.example.persistence.entities.User;

import java.util.Optional;

public interface IUserService {
    Optional<User> findByUserName(String username);

    User save(UserRegisterDTO userRegisterDTO);
}
