package com.snews.server.interceptors;

import com.snews.server.services.configuration.ConfigurationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class DisabledRegistrationInterceptor implements HandlerInterceptor {
    private final ConfigurationService configurationService;

    public DisabledRegistrationInterceptor(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean isRegistrationEnabled = (boolean) this.configurationService.getSetting("enableNewUserRegistration");


        if(request.getRequestURL().indexOf("/user/register")!=-1 && !isRegistrationEnabled){
            response.setStatus(400);
            response.getWriter().write("User registration is not allowed at this time.");
            return false;
        }
        return true;
    }
}
