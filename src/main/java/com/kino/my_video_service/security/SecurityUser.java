package com.kino.my_video_service.security;

import lombok.Getter;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class SecurityUser implements UserDetails, CredentialsContainer {
    private final String login;
    private String passwordHash;
    @Getter
    private final Long id;

    public SecurityUser(String login, String passwordHash, Long id) {

        this.login = login;
        this.passwordHash = passwordHash;
        this.id = id;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public void eraseCredentials() {
        passwordHash = null;
    }
}
