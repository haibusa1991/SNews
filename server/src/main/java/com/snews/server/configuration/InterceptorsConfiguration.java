package com.snews.server.configuration;

import com.snews.server.interceptors.DisabledRegistrationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorsConfiguration implements WebMvcConfigurer {

    private final DisabledRegistrationInterceptor disabledRegistrationInterceptor;

    public InterceptorsConfiguration(DisabledRegistrationInterceptor disabledRegistrationInterceptor) {
        this.disabledRegistrationInterceptor = disabledRegistrationInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(disabledRegistrationInterceptor);
    }
}
