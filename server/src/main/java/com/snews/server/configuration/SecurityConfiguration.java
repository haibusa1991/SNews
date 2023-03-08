package com.snews.server.configuration;

import com.snews.server.enumeration.UserRoleEnum;
import com.snews.server.security.UserDetailsServiceImpl;
import com.snews.server.services.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {


    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests()
                .requestMatchers("/*", "/assets/**", "/user/login","/user/logins").permitAll()
                .requestMatchers("/user/register").denyAll()
                .requestMatchers("/user/users-only").hasRole(UserRoleEnum.USER.name())
                .requestMatchers("/user/mods-only").hasRole(UserRoleEnum.MODERATOR.name())
                .requestMatchers("/user/admins-only").hasRole(UserRoleEnum.ADMINISTRATOR.name())

                .and()
                .formLogin()
                .loginPage("/user/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .successForwardUrl("/")
                .failureForwardUrl("/")

                .and()
                .logout()
                .logoutUrl("/user/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .and()
                .csrf().disable();

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        return new UserDetailsServiceImpl(userService) {
        };
    }
}
