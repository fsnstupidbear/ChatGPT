package com.fsnteam.fsnweb.bean;

import com.fsnteam.fsnweb.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserRole {
    private String userID;
    private String name;
    private String role;
}
