package com.pk.hangman.engine;

import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public class GameContext {
    private int maxAttempts;
    private int currAttempt;
    private Set<Character> missedLetters;
    private Set<Character> guessedLetters;
    private String targetWord;
    private Map<Integer, Character> letterIndexMap;

    public GameContext(String targetWord, int maxAttempts) {
        this.targetWord = targetWord;
        this.maxAttempts = maxAttempts;
        this.currAttempt = 0;
        this.missedLetters = new TreeSet<>();
        this.guessedLetters = new TreeSet<>();
        letterIndexMap = new HashMap<>(targetWord.length());
        for (int i = 0; i < targetWord.length(); i++)
            letterIndexMap.put(i, null);
    }

    public boolean isWordGuessed() {
        return !letterIndexMap.containsValue(null);
    }

    public boolean attemptsAvailable() {
        return currAttempt < maxAttempts;
    }

    public Boolean targetContainsLetter(Character c) {
        return targetWord.indexOf(c) >= 0;
    }

    public void addGuessedLetter(Character c) {
        List<Integer> charPositions = getCharPositions(c);
        charPositions.forEach(i -> letterIndexMap.put(i, c));
        guessedLetters.add(c);
    }

    public void addMissedLetter(Character c) {
        missedLetters.add(c);
        currAttempt++;
    }

    public String buildWordStringForDisplay() {
        StringBuilder sb = new StringBuilder(targetWord.length());
        for (Map.Entry<Integer, Character> entry : letterIndexMap.entrySet())
            if (entry.getValue() != null)
                sb.append(entry.getValue());
            else
                sb.append("-");

        return sb.toString();
    }

    public Set<Character> getNotAllowedLetters() {
        return Stream.concat(missedLetters.stream(), guessedLetters.stream())
                .collect(Collectors.toSet());
    }

    private List<Integer> getCharPositions(Character c) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < targetWord.length(); i++) {
            if (targetWord.charAt(i) == c)
                result.add(i);
        }
        return result;
    }
}
