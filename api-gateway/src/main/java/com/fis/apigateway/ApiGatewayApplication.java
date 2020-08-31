package com.fis.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import com.fis.apigateway.filter.PreFilter;
import com.fis.apigateway.filter.PostFilter;

@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
@EnableHystrix
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}
	
//	 @Bean
//	    public PreFilter preFilter() {
//	        return new PreFilter();
//	    }
//
//	 @Bean
//	    public PostFilter postFilter() {
//	        return new PostFilter();
//	    }

}
