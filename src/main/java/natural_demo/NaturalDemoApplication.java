package natural_demo;

import natural_demo.Models.Role;
import natural_demo.Models.User;
import natural_demo.Service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class NaturalDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(NaturalDemoApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
		CommandLineRunner run(UserService userService){
		return args -> {

			userService.saveRole(new Role(null,"adm"));
			userService.saveRole(new Role(null,"manager"));

			userService.saveUser(new User(null,"Ester","Skana","Oscar","hello","skana123", new ArrayList<>()));
			userService.saveUser(new User(null,"Oscar","White","cat","haj","1234444", new ArrayList<>()));

			userService.addRoleToUser("Skana","adm");
			userService.addRoleToUser("White","manager");
		};
		}
}
