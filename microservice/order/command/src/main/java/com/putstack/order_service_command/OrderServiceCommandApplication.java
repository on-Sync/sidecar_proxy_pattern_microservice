package com.putstack.order_service_command;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication(scanBasePackages = "com.putstack")
@EnableDiscoveryClient
@EnableEurekaClient
@EnableFeignClients
public class OrderServiceCommandApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceCommandApplication.class, args);
	}
    
}
