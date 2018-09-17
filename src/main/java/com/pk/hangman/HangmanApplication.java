package com.pk.hangman;

import com.pk.hangman.engine.GameInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HangmanApplication implements CommandLineRunner {

    @Autowired
    private GameInitializer gameInitializer;

    public static void main(String[] args) {
        SpringApplication.run(HangmanApplication.class, args);
    }

    @Override
    public void run(String... args) {
        gameInitializer.startGame();
    }
}
