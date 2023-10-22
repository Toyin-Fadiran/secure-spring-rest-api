package net.dovale.okta.secure_rest_api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication
@ComponentScan("net.dovale.okta.secure_rest_api")
public class SecureRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecureRestApiApplication.class, args);
		
		//check what this does again
		 new ArrayList<>().add(4);
		 
		 
	}
	

}
