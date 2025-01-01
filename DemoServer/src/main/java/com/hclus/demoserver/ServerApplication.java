package com.hclus.demoserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principale dell'applicazione.
 */
@SpringBootApplication
public class ServerApplication {
	/**
	 * Punto di partenza dell'applicazione server.
	 *
	 * @param args  argomenti passati da terminale
	 */
	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

}
