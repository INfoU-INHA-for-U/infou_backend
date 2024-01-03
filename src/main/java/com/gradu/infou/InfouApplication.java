package com.gradu.infou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

//시큐리티 기능 끄기
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class InfouApplication {

	public static void main(String[] args) {
		SpringApplication.run(InfouApplication.class, args);
	}

}
