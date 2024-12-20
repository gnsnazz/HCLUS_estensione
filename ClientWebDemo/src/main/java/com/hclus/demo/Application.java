package com.hclus.demo;

import com.vaadin.flow.component.dependency.CssImport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principale dell'applicazione.
 */
@SpringBootApplication
@CssImport("./styles/style.css")
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
