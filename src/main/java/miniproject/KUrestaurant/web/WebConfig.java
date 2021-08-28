package miniproject.KUrestaurant.web;

import miniproject.KUrestaurant.web.interceptor.ConsumerAuthorizationCheckInterceptor;
import miniproject.KUrestaurant.web.interceptor.LogInterceptor;
import miniproject.KUrestaurant.web.interceptor.LoginCheckInterceptor;
import miniproject.KUrestaurant.web.interceptor.OwnerAuthorizationCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error");

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/members/add", "/login", "/logout",
                        "/css/**", "/*.ico", "/error");

        registry.addInterceptor(new ConsumerAuthorizationCheckInterceptor())
                .order(3)
                .addPathPatterns("/**/pick", "/**/unpick", "/**/reply");

        registry.addInterceptor(new OwnerAuthorizationCheckInterceptor())
                .order(4)
                .addPathPatterns("/restaurants/add");
    }

}
