package br.com.atlantico.demoteste;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class DemoTesteApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoTesteApplication.class, args);
	}

}
