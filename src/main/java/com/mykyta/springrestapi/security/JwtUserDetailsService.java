package com.mykyta.springrestapi.security;

import com.mykyta.springrestapi.model.User;
import com.mykyta.springrestapi.security.jwt.JwtUser;
import com.mykyta.springrestapi.security.jwt.JwtUserFactory;
import com.mykyta.springrestapi.service.UserService;
import com.mykyta.springrestapi.service.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user= userService.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("User not found");
            }

        JwtUser jwtUser = JwtUserFactory.create(user);
        return jwtUser;
    }
}
