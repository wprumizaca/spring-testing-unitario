package com.example.persistence.entities;



import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter @Setter
@Entity
public class User implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password; //Se almacenará cifrada

    @Column(unique = true) //imail único
    private String email;



    //Este constructor lo necestio para USERSERVICE
    public User(Long id, String username, String password, String email, List<UserAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.authorities = authorities;
    }

    @ElementCollection(fetch = FetchType.EAGER) //la inicialización de datos (authorities) se produce en el acto. El otro Lazy. inicializa cuando puede
    @Enumerated(EnumType.STRING)//Se almacena en la base de datos como tipo String
    private List<UserAuthority> authorities = new ArrayList<UserAuthority>(); //Authorities. Los permisos que tendrá un usuario. Viene del ENUM UserAuthority



    //Métodos de UserDetails. Para comprobación de cuenta expirada, bloqueada, etc
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //Nos transforma la lista de tipo UserAuthority a una coleccion de tipo GrandedAuthority
        return this.authorities
                .stream()
                .map(authority-> new SimpleGrantedAuthority(authority.toString()))
                .toList();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

