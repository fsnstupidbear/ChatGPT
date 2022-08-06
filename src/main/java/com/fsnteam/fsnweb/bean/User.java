package com.fsnteam.fsnweb.bean;

import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author 一只大笨熊
 */
@Data
public class User implements UserDetails {
    private String id;
    private String username;
    private String password;
    //分队/总队
    private String department;
    private String QQnumber;
    private String phoneNumber;
    private Date joinDate;
    private String[] roles;
    private boolean isEnabled;
    private String vocation;
    private Collection<? extends GrantedAuthority> authorities;

    @SneakyThrows
    @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if(roles==null) {

        }
        if(roles!=null) {
            for (int i = 0; i < roles.length; i++) {
                authorities.add(new SimpleGrantedAuthority(roles[i]));
            }
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
