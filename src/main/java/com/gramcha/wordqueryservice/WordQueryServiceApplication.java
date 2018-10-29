package com.gramcha.wordqueryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient     // Register this service as uerka client to eureka server
@ComponentScan(basePackages = "com.gramcha.*")
@EnableCircuitBreaker      // Enable circuit breakers
public class WordQueryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WordQueryServiceApplication.class, args);
	}
}
