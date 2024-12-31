package com.github.gabrielvelosoo.libraryapi;

import com.github.gabrielvelosoo.libraryapi.models.Author;
import com.github.gabrielvelosoo.libraryapi.repositories.AuthorRepository;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.time.LocalDate;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(Application.class);
		builder.bannerMode(Banner.Mode.OFF);
		builder.profiles("hml");

		builder.run(args);

		ConfigurableApplicationContext applicationContext = builder.context();
		ConfigurableEnvironment environment = applicationContext.getEnvironment();
		String applicationName = environment.getProperty("spring.application.name");
		System.out.println("Application name: " + applicationName);

		AuthorRepository authorRepository = applicationContext.getBean(AuthorRepository.class);
		exampleSaveRecord(authorRepository);
	}

	public static void exampleSaveRecord(AuthorRepository authorRepository) {
		Author author = new Author();
		author.setName("José");
		author.setNationality("Brazilian");
		author.setBirthDate(LocalDate.of(1950, 1, 31));

		var savedAuthor = authorRepository.save(author);
		System.out.println("Saved author: " + savedAuthor);
	}
}
