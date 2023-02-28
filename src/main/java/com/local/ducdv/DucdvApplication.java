package com.local.ducdv;

import com.local.ducdv.config.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
		FileStorageProperties.class
})
public class DucdvApplication {

	public static void main(String[] args) {
		SpringApplication.run(DucdvApplication.class, args);
	}

}
