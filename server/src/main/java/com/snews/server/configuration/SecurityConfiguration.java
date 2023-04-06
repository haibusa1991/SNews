package com.snews.server.configuration;

import com.snews.server.enumeration.UserRoleEnum;
import com.snews.server.services.user.UserService;
import org.springframework.boot.web.server.Cookie;
import org.springframework.boot.web.servlet.server.CookieSameSiteSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
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

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.cors.CorsConfiguration;


@Configuration
//@EnableWebSecurity(debug = true)
@EnableWebSecurity(debug = false)
@EnableMethodSecurity()
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

//------------------original config----------------------
        http.authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/user/login", "/user/register", "/user/forgotten-password", "/user/reset-password", "/user/remove-avatar").permitAll()
//                .requestMatchers(HttpMethod.POST, "/article/new-article").hasRole(UserRoleEnum.ADMINISTRATOR.name())
                //todo update with proper rights
                .requestMatchers(HttpMethod.POST, "/article/new-article",
                        "/user/upload-avatar",
                        "/user/change-password",
                        "/user/change-email",
                        "/configuration/modify-setting",
                        "/configuration/set-state",
                        "/user/update-authority").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/testpost").hasRole(UserRoleEnum.ADMINISTRATOR.name())

                .and()
                .formLogin()
                .loginPage("/user/login")
                .failureHandler((request, response, exception) -> response.sendError(403))

                .and().cors().disable().csrf().disable()


//                .and()
//                .csrf()
//                .csrfTokenRepository(tokenRepository)
//                .csrfTokenRequestHandler(requestHandler)

//                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new Http403ForbiddenEntryPoint())

//                .and()
//                .cors()
//                .configurationSource(s -> new CorsConfiguration().applyPermitDefaultValues())

                .and()
                .logout()
                .clearAuthentication(true)
                .deleteCookies("XSRF-TOKEN")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .logoutUrl("/user/logout")
                .logoutSuccessUrl("/");
        return http.build();

        //------------------ end original config----------------------


//        ------------------------HTTPS configuration--------------------------------
//        http.requiresChannel()
//                .requestMatchers(HttpMethod.GET, "/**").requiresSecure()
//                .requestMatchers(HttpMethod.POST, "/user/login","/user/register","/user/forgotten-password","/user/reset-password").requiresSecure()
//                .requestMatchers(HttpMethod.POST, "/article/new-article").requiresSecure()
//                .requestMatchers(HttpMethod.POST, "/api/testpost").requiresSecure()
//
//                .and()
//                .formLogin()
//                .loginPage("/user/login")
//                .failureHandler((request, response, exception) -> response.sendError(403))
//
////                .and().cors().disable().csrf().disable()
//
//
//                .and()
//                .csrf()
//                .csrfTokenRepository(tokenRepository)
//                .csrfTokenRequestHandler(requestHandler)
//
//                .and()
//                .exceptionHandling()
//                .authenticationEntryPoint(new Http403ForbiddenEntryPoint())
//
//                .and()
//                .cors()
//                .configurationSource(s -> new CorsConfiguration().applyPermitDefaultValues())
//
//                .and()
//                .logout()
//                .clearAuthentication(true)
//                .deleteCookies("XSRF-TOKEN")
//                .deleteCookies("JSESSIONID")
//                .invalidateHttpSession(true)
//                .logoutUrl("/user/logout")
//                .logoutSuccessUrl("/");
//        return http.build();
        //        ------------------------end HTTPS configuration--------------------------------
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
