package com.example.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


//Configuramos la Autenticación Manager
@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    /**
     * @param http
     * @param passwordEncoder    Para cifrar la contraseña. No se debe almacenar en texto plano
     * @param userDetailsService
     * @return
     */
    @Bean
    //Este metodo hace que se nos genere una peticion de autenticación
    public AuthenticationManager getAuthManager(HttpSecurity http,
                                                PasswordEncoder passwordEncoder,
                                                UserDetailsService userDetailsService) throws Exception {

        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and().build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);//deshabilitamos estados. Esta APi es sin estado y sin sesión

        //Aqui se configuran todas las urls de los controladores
        http.authorizeHttpRequests()
                .mvcMatchers("/auth/**")
                .permitAll()
                .anyRequest()
                .authenticated();//Autorizamos Controladores. En este caso controlador (/auth/**).
                // Esto sirve para que se pueda loguear cualquiera (EVIDENTE). Pero el resto de peticiones
                //las bloqueamos ( .permitAll().anyRequest().authenticated() ) porque ya sería para ver el contenido
                // de nuestra api.


        //Filtros: intercepta peticiones y las analiza para ver si cumplen las normas/requisitos.
        //Si cumple las normas pasan, si no cumple se bloquea el paso.
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        //jwtFilter (nuestro filtro): va a evaluar la cabecera Authoritation, sacar el token jwt y validarlo

        return  http.build(); //una vez tratado el objeto lo devolvemos
    }



}
