package com.carbonPrediction;

import com.carbonPrediction.Entity.Role;
import com.carbonPrediction.Repository.RoleRepository;
import com.carbonPrediction.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class CarbonPredictionApplication {

	public static void main(String[] args) {

		SpringApplication.run(CarbonPredictionApplication.class, args);
	}

	@Bean
	CommandLineRunner initAdmin(UserRepository userRepository,
								RoleRepository roleRepository,
								PasswordEncoder passwordEncoder) {

		return args -> {

			List<String> roleNames = Arrays.asList("ROLE_USER", "ROLE_ADMIN");

			List<Role> adminRole = roleNames.stream()
					.map(name -> roleRepository.findByName(name)
							.orElseGet(() -> roleRepository.save(new Role(null, name))))
					.collect(Collectors.toList());

		};
	}

}
