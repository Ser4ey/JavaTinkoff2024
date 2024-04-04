package edu.java.scrapper.configuration;

import edu.java.scrapper.interceptor.RateLimitInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class RateLimitConfiguration implements WebMvcConfigurer {
    private final RateLimitInterceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor).addPathPatterns("/links").addPathPatterns("/tg-chat");
    }
}
