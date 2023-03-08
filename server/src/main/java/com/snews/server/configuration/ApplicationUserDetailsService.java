package com.snews.server.configuration;

import com.snews.server.entities.UserEntity;
import com.snews.server.services.user.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Set;
import java.util.stream.Collectors;

public class ApplicationUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public ApplicationUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = this.userService.getUserByEmail(email);

        if(user==null) {
            throw new UsernameNotFoundException("user not found");
        }

        Set<GrantedAuthority> authoritySet = user
                .getUserRoles()
                .stream()
                .map(e->new SimpleGrantedAuthority("ROLE_"+e.getRole().name()))
                .collect(Collectors.toSet());

         return new User(user.getUsername(), user.getPassword(),authoritySet );
    }
}
