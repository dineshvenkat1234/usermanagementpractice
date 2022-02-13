package com.usermanagement.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import com.usermanagement.demo.filter.JWTFilter;

@SpringBootApplication
@EnableEurekaClient
public class UsermanagementappApplication {

	@Bean
	public FilterRegistrationBean jwtFilter() {
		FilterRegistrationBean fb = new FilterRegistrationBean();
		fb.setFilter(new JWTFilter());
		fb.addUrlPatterns("/api/v1/*");
		return fb;
	}
	public static void main(String[] args) {
		SpringApplication.run(UsermanagementappApplication.class, args);
	}

}
