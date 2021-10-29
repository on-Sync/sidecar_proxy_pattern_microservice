package com.putstack.user_service_query;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EntityScan(value = {"com.putstack.cqrs_axon_common.user.entity", "org.axonframework"})
public class UserServiceQueryApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceQueryApplication.class, args);
	}

}
