package com.mara.tbot.chatgptbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ChatGPTBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatGPTBotApplication.class, args);
	}

}
