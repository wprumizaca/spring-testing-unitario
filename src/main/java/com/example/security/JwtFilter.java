package com.example.security;

import com.example.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Extrae el token jwt de la cabecera Authotitation de la petición HTTP que llega al back-end.
 * Esta clase se puede usar porque la hemos añadido en la seguridad( HTTPSecurity ) de la clase SecurityConfig
 */

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired //mirar la funcionalidad de @Autowired y @Override
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = this.extractToken(request);

        if(this.tokenProvider.isValidToken(token)){ //tokenProvider.isValidToken(token) -> Generamos el token

            //1. Extraemos el usuario de BBDD
            String username= this.tokenProvider.getUserNameFromToken(token);
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);//Nuestra clase User hereda de UserDetails

            //2. Comprobamos que ese usuario existe y tiene permiso de acceso
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userDetails.getUsername(),
                    userDetails.getPassword(),
                    userDetails.getAuthorities()); //authentication nos dice si el usuario se a logueado correctamente

            //3. Si el usuario existe se guarda aquí la informacion y se puede acceder a ella en toda la aplicación
            SecurityContextHolder.getContext().setAuthentication(authentication); //El contexto es accesible en todas partes
            // de la aplicación y nos permitira acceder al usuario que ha enviado una determinada petición

            //Cualquier controlador podria acceder a SecurityContextHolder y ver si existe un usuario logueado y
            // en función de eso hacer una u otra cosa.
        }

        filterChain.doFilter(request, response); //Para que la petición continue dentro de la cadena de filtros
    }

    private String extractToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");

        if(StringUtils.hasLength(bearerToken) && bearerToken.startsWith("Bearer")){
            return bearerToken.substring("Bearer".length()); //Quitamos Bearer y nos quedamos con lo que es el TOKEN
        }

        return null;
    }

}
