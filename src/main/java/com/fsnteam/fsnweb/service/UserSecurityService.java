package com.fsnteam.fsnweb.service;

import com.fsnteam.fsnweb.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserSecurityService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        User user= userService.selectUserById(id);
        String roles=userService.selectRoleById(id);
        if(!"".equals(roles)) {
            String[] rolesArray = roles.split(",");
            user.setRoles(rolesArray);
        }
        return new org.springframework.security.core.userdetails.User
                (user.getUsername(), user.getPassword(),user.isEnabled(),true,
                        true,true, user.getAuthorities());
    }
}
