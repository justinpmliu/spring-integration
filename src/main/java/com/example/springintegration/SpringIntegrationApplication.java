package com.example.springintegration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("integration.xml")
public class SpringIntegrationApplication implements CommandLineRunner {

	@Autowired
	private ServiceClient serviceClient;

	public static void main(String[] args) {
		SpringApplication.run(SpringIntegrationApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		serviceClient.call();
	}
}
