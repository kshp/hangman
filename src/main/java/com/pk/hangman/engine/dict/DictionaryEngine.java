package com.pk.hangman.engine.dict;

import com.pk.hangman.engine.GameContext;
import lombok.Getter;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class DictionaryEngine {

    @Getter
    private Set<String> recentlyFiltered;

    /**
     * Load dictionary located at the specified dictFilePath
     * Filter out words with wrong length
     * Filter out word with "'"
     *
     * @param dictFilePath Dictionary file path location
     * @param length       word's length
     */
    public DictionaryEngine(String dictFilePath, int length) {
        ClassPathResource classPathResource = new ClassPathResource(dictFilePath);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(classPathResource.getInputStream()));
             Stream<String> stream = reader.lines()) {
            Set<String> strings = stream
                    .filter(s -> s.length() == length)
                    .filter(s -> !s.contains("'"))
                    .collect(groupingBy(String::length, mapping(l -> l, toSet())))
                    .get(length);
            recentlyFiltered = strings == null ? Collections.emptySet() : strings;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Filter out words based on the guessed / tried letters
     *
     * @param gameContext {@link GameContext}
     * @return Set of filtered words
     */
    public Set<String> filter(GameContext gameContext) {
        Set<Character> missedLetters = gameContext.getMissedLetters();
        Map<Integer, Character> letterIndexMap = gameContext.getLetterIndexMap();

        Set<String> collect = recentlyFiltered.stream()
                .filter(w -> doesNotContainMissedLetters(w, missedLetters))
                .filter(w -> containsGuessedLettersAtTheirIndexes(w, letterIndexMap))
                .collect(toSet());
        if (!collect.isEmpty())
            recentlyFiltered = collect;

        return collect;
    }


    private Boolean doesNotContainMissedLetters(String word, Set<Character> missedLetters) {
        for (Character l : missedLetters)
            if (word.indexOf(l) >= 0)
                return false;
        return true;
    }

    private Boolean containsGuessedLettersAtTheirIndexes(String word, Map<Integer, Character> letterIndexMap) {
        for (Map.Entry<Integer, Character> entry : letterIndexMap.entrySet())
            if (entry.getValue() != null &&
                    !entry.getValue().equals(word.charAt(entry.getKey())))
                return false;
        return true;
    }

    /**
     * Build letter frequency map
     *
     * @return Letter frequency map
     */
    public Map<Character, Long> getLetterFrequency() {
        return recentlyFiltered.stream()
                .flatMap(s -> s.chars().boxed())
                .collect(Collectors
                        .groupingBy(c -> (char) c.intValue(),
                                Collectors.counting()));
    }

    /**
     * Guess next letter based on the letter frequency map
     *
     * @param notAllowedLetters Set of the already tried or guessed letters
     * @return Next letter guess
     */
    public Character guessNextLetter(Set<Character> notAllowedLetters) {
        Optional<Map.Entry<Character, Long>> max = getLetterFrequency().entrySet().stream()
                .filter(e -> !notAllowedLetters.contains(e.getKey()))
                .max(Map.Entry.comparingByValue());
        return max.map(Map.Entry::getKey).orElse(null);
    }

}
