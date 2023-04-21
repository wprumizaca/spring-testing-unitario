package com.example.expose.dto;

import java.util.List;

/*
Estos DTOs actuaran como @Bean. Actuaran como record ( para mover datos de front a back-end
y tenerlos en forma de objetos java).

 List<String> authorities -> puede o no tenerlos.
 */
public record LoginResponse(String username, List<String> authorities, String token) {
    /**
     * El String token es lo que vamos a tener que generar y se lo vamos a devolver al navegador/cliente/postman
     * y el navegador CAPTURA ese token y lo utiliza a partir de ese momento en todas las peticiones ( en las cabeceras de las peticiones )
     * que haga
     */
}
