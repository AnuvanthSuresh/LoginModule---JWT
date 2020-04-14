package com.stock.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.stock.interceptors.AuthenticationInterceptor;



@Configuration
public class LoginConfiguration implements WebMvcConfigurer {

	  @Bean
	    AuthenticationInterceptor getAuthenticationInterceptor() {
	        return new AuthenticationInterceptor();
	    }
	 
	
	@Override
	    public void addInterceptors(InterceptorRegistry registry)
	    {
	        //registry.addInterceptor(new AuthenticationInterceptor());
	        registry.addInterceptor(new AuthenticationInterceptor()).excludePathPatterns("/signin", "/signout", "/signup");

	    }

}
