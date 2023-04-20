package com.example.security;



import com.example.persistence.entities.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;


/**
 * Esta clase se encarga de generar TOKENS JWT cuando un usuario inicia sesión satisfactoriamente
 */
@Component
public class JwtTokenProvider {

    Logger log = LoggerFactory.getLogger(this.getClass());

    //Estos datos tiene que ver con los datos que añadimos en Properties

    //@Value("${app.security.jwt.secret}") DESCOMENTAR
    private String jwtSecret;

    //@Value("${app.security.jwt.expiration}") DESCOMENTAR
    private Long jwtDurationSeconds;


    /**
     Generamos TOKEN
     */
    public String generateToken(Authentication authentication){
        User user = (User) authentication.getPrincipal(); //extraemos un usuario a partir del objeto authentication

        //A partir de un secreto ( jwtSecret.getBytes() ) que tenemos almacenado en el Properties
        // vamos a firmar el contenido que añadimos (  user.getUsername(),  user.getEmail(), user.getId(),y una serie de fechas )
        //Resumen: esto es el PAYLOAD
        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()), SignatureAlgorithm.ES512)
                .setHeaderParam("typ","JWT")
                .setSubject(Long.toString(user.getId()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (jwtDurationSeconds*1000)))
                .claim("username", user.getUsername())
                .claim("mail", user.getEmail())
                .compact();
    }


}
