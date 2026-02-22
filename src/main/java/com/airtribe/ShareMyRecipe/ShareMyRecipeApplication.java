package com.airtribe.ShareMyRecipe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ShareMyRecipeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShareMyRecipeApplication.class, args);
	}

}
