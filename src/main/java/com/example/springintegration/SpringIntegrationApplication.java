package com.example.springintegration;

import com.example.springintegration.integration.SiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ImportResource({"classpath:integration-context.xml", "classpath:outbound.xml"})
@EnableScheduling
public class SpringIntegrationApplication implements CommandLineRunner {

	@Autowired
	private SiClient siClient;

	public static void main(String[] args) {
		SpringApplication.run(SpringIntegrationApplication.class, args);
	}

	@Override
	public void run(String... args) {
		siClient.invokeSiGateway();
	}
}
