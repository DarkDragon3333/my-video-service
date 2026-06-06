package com.kino.my_video_service;

import com.kino.my_video_service.entities.UserEntity;
import com.kino.my_video_service.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MyVideoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyVideoServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(UserRepository repo){
		return args -> {
			UserEntity userEntity = new UserEntity();
			userEntity.setDisplayName("Max");
			userEntity.setLogin("max@max.com");
			userEntity.setPasswordHash("123");
			repo.save(userEntity);
		};
	}

}
