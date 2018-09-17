package com.pk.hangman;

import com.pk.hangman.engine.GameInitializer;
import com.pk.hangman.utils.MessageUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class HangmanConfiguration {

    @Bean
    public MessageUtils messageUtils() {
        return new MessageUtils();
    }

    @Bean
    public GameInitializer gameInitializer() {
        return new GameInitializer();
    }

    @Bean
    public Random random() {
        return new Random();
    }

}
