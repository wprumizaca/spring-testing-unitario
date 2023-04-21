package com.example.security;



import com.example.persistence.entities.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import java.util.Date;


/**
 * Esta clase se encarga de generar TOKENS JWT cuando un usuario inicia sesión satisfactoriamente
 */
@Component

public class JwtTokenProvider {

    Logger log = LoggerFactory.getLogger(this.getClass());

    //Estos datos tiene que ver con los datos que añadimos en Properties

    @Value("${app.security.jwt.secret}")
    private String jwtSecret;

    @Value("${app.security.jwt.expiration}")
    private Long jwtDurationSeconds;



    /**
     Generamos TOKEN
     */
    public String generateToken(Authentication authentication){
        User user = (User) authentication.getPrincipal(); //extraemos un usuario a partir del objeto authentication

        //OPCIÓN PARA CREAR TOKEN
        //A partir de un secreto ( jwtSecret.getBytes() ) que tenemos almacenado en el Properties
        // vamos a firmar el contenido que añadimos (  user.getUsername(),  user.getEmail(), user.getId(),y una serie de fechas )
        //Resumen: esto es el PAYLOAD
        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()), SignatureAlgorithm.HS512)
                .setHeaderParam("typ","JWT")
                .setSubject(Long.toString(user.getId()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (jwtDurationSeconds*1000)))
                .claim("username", user.getUsername())
                .claim("mail", user.getEmail())
                .compact();
    }

    /**
      Comprobas si el Token es valido
     */
    public boolean isValidToken(String token){
        if(!StringUtils.hasLength(token)) //Comprobamos que el token tenga longitud, no sea un string vacío
            return false;

        try {
            //OPCIÓN PARA VALIDAR TOKEN
            JwtParser validator = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                    .build(); //Se crea un validador usando el secreto que le hemos
            // dicho ( H512 - Keys.hmacShaKeyFor(jwtSecret.getBytes() )

            validator.parseClaimsJwt(token); //Aqui nos indica si ese token que nos pasa el usuario coincide coincide con el secreto(firma)

            return true;
        }catch(SignatureException e){
            log.info("Error en la firma del token", e);
        }catch(MalformedJwtException | UnsupportedJwtException e){
            log.info("Token incorrecto", e);
        }catch(ExpiredJwtException e){
            log.info("Token expirado", e);
        }

        return false;
    }

    public String getUserNameFromToken(String token){
        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .build();

        Claims claims = parser.parseClaimsJwt(token).getBody();
        return claims.get("username").toString();
    }

}
