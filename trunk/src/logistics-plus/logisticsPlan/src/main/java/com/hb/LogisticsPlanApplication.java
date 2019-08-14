package com.hb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;

/**
 * @author lirc
 */
@SpringBootApplication
@MapperScan("com.hb")
@EnableTransactionManagement
@Configuration
public class LogisticsPlanApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(LogisticsPlanApplication.class, args);
	}

	@Bean
	public PaginationInterceptor paginationInterceptor() {
		return new PaginationInterceptor();
	}

	// 实现WebMvcConfigurer /user.do = /user
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		// TODO Auto-generated method stub
		configurer.setUseSuffixPatternMatch(true);
		WebMvcConfigurer.super.configurePathMatch(configurer);
	}

	@Bean
	@LoadBalanced // 在注册中心里进行查找微服务,负载均衡
	public RestTemplate restTemplate() {
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setConnectTimeout(50000);// 设置超时
		requestFactory.setReadTimeout(50000);
		RestTemplate restTemplate = new RestTemplate(requestFactory);
		return restTemplate;
	}
}
