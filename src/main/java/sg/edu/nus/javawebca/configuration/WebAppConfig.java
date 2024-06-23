package sg.edu.nus.javawebca.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import sg.edu.nus.javawebca.interceptor.AuthenticationInterceptor;
import sg.edu.nus.javawebca.interceptor.SecurityInterceptor;

@Component
public class WebAppConfig implements WebMvcConfigurer {
    @Autowired
    AuthenticationInterceptor authenticationInterceptor;
    @Autowired
    SecurityInterceptor securityInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login", "/logout", "/resources/**", "/static/**", "/error");

        registry.addInterceptor(securityInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login", "/logout", "/resources/**", "/static/**", "/error");
    }

}