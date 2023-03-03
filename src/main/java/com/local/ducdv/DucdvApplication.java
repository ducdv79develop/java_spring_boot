package com.local.ducdv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.local.ducdv.config.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({
		FileStorageProperties.class
})
public class DucdvApplication {

	public static void main(String[] args) {
		SpringApplication.run(DucdvApplication.class, args);
	}

}
