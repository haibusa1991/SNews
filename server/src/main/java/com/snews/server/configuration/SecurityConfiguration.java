package com.snews.server.configuration;

import com.snews.server.enumeration.UserRoleEnum;
import com.snews.server.services.user.UserService;
import org.springframework.boot.web.server.Cookie;
import org.springframework.boot.web.servlet.server.CookieSameSiteSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestHandler;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;

import org.springframework.web.cors.CorsConfiguration;


@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        CookieCsrfTokenRepository tokenRepository = new CookieCsrfTokenRepository();
        tokenRepository.setSecure(true);
        tokenRepository.setCookieHttpOnly(false);

        XorCsrfTokenRequestAttributeHandler delegate = new XorCsrfTokenRequestAttributeHandler();
        delegate.setCsrfRequestAttributeName("_csrf");

        CsrfTokenRequestHandler requestHandler = delegate::handle;


        http.authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/user/login","/user/register","/user/forgotten-password","/user/reset-password").permitAll()
//                .requestMatchers(HttpMethod.POST, "/article/new-article").hasRole(UserRoleEnum.ADMINISTRATOR.name())
                .requestMatchers(HttpMethod.POST, "/article/new-article").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/testpost").hasRole(UserRoleEnum.ADMINISTRATOR.name())

                .and()
                .formLogin()
                .loginPage("/user/login")
                .failureHandler((request, response, exception) -> response.sendError(403))

//                .and().cors().disable().csrf().disable()


                .and()
                .csrf()
                .csrfTokenRepository(tokenRepository)
                .csrfTokenRequestHandler(requestHandler)

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new Http403ForbiddenEntryPoint())

                .and()
                .cors()
                .configurationSource(s -> new CorsConfiguration().applyPermitDefaultValues())

                .and()
                .logout()
                .clearAuthentication(true)
                .deleteCookies("XSRF-TOKEN")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .logoutUrl("/user/logout")
                .logoutSuccessUrl("/");
        return http.build();
    }

    @Bean
    public UserDetailsService getUserDetailsService(UserService userService) {
        return new ApplicationUserDetailsService(userService);
    }

    @Bean
    public CookieSameSiteSupplier applicationCookieSameSiteSupplier() {
        return CookieSameSiteSupplier.of(Cookie.SameSite.NONE);
    }
}
