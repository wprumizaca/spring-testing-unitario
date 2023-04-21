package com.example.expose.dto;

/*
Estos DTOs actuaran como @Bean. Actuaran como record ( para mover datos de front a back-end
y tenerlos en forma de objetos java)
 */
public record LoginRequest (String username, String password){

}
