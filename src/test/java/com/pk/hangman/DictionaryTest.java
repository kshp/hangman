package com.pk.hangman;

import com.pk.hangman.engine.GameContext;
import com.pk.hangman.engine.dict.DictionaryEngine;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class DictionaryTest {

    private DictionaryEngine dictionary;

    @Test
    public void testDictionaryIsInitializedFromFile() throws IOException {
        dictionary = new DictionaryEngine("/brit-a-z.txt", 6);
        assertThat(dictionary.getRecentlyFiltered().isEmpty(), is(false));
    }

    @Test
    public void testFilterWordsWith() throws IOException {
        dictionary = new DictionaryEngine("/brit-a-z.txt", 6);
        GameContext context = new GameContext("oxygen", 10);
        context.addGuessedLetter('o');
        context.addGuessedLetter('x');
        context.addGuessedLetter('y');
        context.addGuessedLetter('g');
        context.addGuessedLetter('e');
        context.addGuessedLetter('n');

        Set<String> filtered = dictionary.filter(context);

        assertThat(filtered.isEmpty(), is(false));
        assertThat(filtered.size(), is(equalTo(1)));
        assertThat(dictionary.getRecentlyFiltered().isEmpty(), is(false));
    }

    @Test
    public void testCountLetterFrequency() throws IOException {
        dictionary = new DictionaryEngine("/brit-a-z.txt", 6);
        GameContext context = new GameContext("oxygen", 10);
        context.addGuessedLetter('o');
        context.addGuessedLetter('x');
        context.addGuessedLetter('y');
        context.addGuessedLetter('g');
        context.addGuessedLetter('e');
        context.addGuessedLetter('n');

        dictionary.filter(context);

        Map<Character, Long> letterFrequency = dictionary.getLetterFrequency();
        assertThat(letterFrequency.isEmpty(), is(false));
        assertThat(letterFrequency.values().stream().mapToLong(l -> l).sum(),
                is(equalTo(6L)));
    }
}