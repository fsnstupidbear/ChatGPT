package com.fsnteam.fsnweb.bean;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

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
    private List<UserRole> roles;
    private String isEnabled;
    private String vocation;
    private Collection<? extends GrantedAuthority> authorities;

    @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        if(roles.size()>0) {
//            for (UserRole role : roles) {
//                authorities.add(new SimpleGrantedAuthority(role.getRole()));
//            }
//        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
        return true;
    }
}
