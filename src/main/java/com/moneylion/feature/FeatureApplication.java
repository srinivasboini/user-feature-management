package com.moneylion.feature;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.moneylion.feature.configuration",
								"com.moneylion.feature.repository",
								"com.moneylion.feature.resource",
								"com.moneylion.feature.service",
								"com.moneylion.feature.exception"})
public class FeatureApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeatureApplication.class, args);
	}

}
