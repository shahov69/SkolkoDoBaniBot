package ru.xander.telebot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

/**
 * @author Alexander Shakhov
 */
@SpringBootApplication
public class SkolkoDoBaniApplication {

	public static void main(String[] args) {
		ApiContextInitializer.init();
		SpringApplication.run(SkolkoDoBaniApplication.class, args);
	}

}
