package com.pk.hangman.utils;

import org.springframework.beans.factory.annotation.Value;

import java.util.Set;

import static java.lang.System.out;

/**
 * Utility class that helps printing messages
 */
public class MessageUtils {

    @Value("${app.hangman.hello.message}")
    private String helloMessage;

    @Value("${app.hangman.length.message}")
    private String lengthMessage;

    @Value("${app.hangman.first.letter.message}")
    private String firstLetterMessage;

    @Value("${app.hangman.last.letter.message}")
    private String lastLetterMessage;

    @Value("${app.hangman.word.message}")
    private String wordMessage;

    @Value("${app.hangman.attempts.message}")
    private String attemptsMessage;

    @Value("${app.hangman.not.found.message}")
    private String noWordMessage;

    @Value("${app.hangman.move.invitation.message}")
    private String moveInvitationMessage;

    @Value("${app.hangman.attempts.left.message}")
    private String attemptsLeftMessage;

    @Value("${app.hangman.used.letters.message}")
    private String usedLetterMessage;

    @Value("${app.hangman.guess.message}")
    private String guessMessage;

    @Value("${app.hangman.guessing.word.message}")
    private String guessingWordMessage;

    @Value("${app.hangman.not.guessed.word.message}")
    private String notGuessedLetterMessage;

    @Value("${app.hangman.guessed.word.message}")
    private String guessedLetterMessage;

    @Value("${app.hangman.win.message}")
    private String winMessage;

    @Value("${app.hangman.loose.message}")
    private String looseMessage;

    @Value("${app.hangman.play.again.message}")
    private String playAgainMessage;

    public void printInitMessages() {
        out.println(helloMessage);
    }

    public void printLengthMessage() {
        out.print(lengthMessage);
    }

    public void printFirstLetterMessage() {
        out.print(firstLetterMessage);
    }

    public void printLastLetterMessage() {
        out.print(lastLetterMessage);
    }

    public void printTargetWordMessage(String word) {
        out.println(wordMessage + word);
    }

    public void printAttemptsLeftMessage(int attempts) {
        out.println(attemptsLeftMessage + attempts);
    }

    public void printUsedLettersMessage(Set<Character> userLetter) {
        out.println(usedLetterMessage + userLetter);
    }

    public void printGuessingWordMessage(String word) {
        out.println(guessingWordMessage + word);
    }

    public void printGuessMessage(Character c) {
        out.println(guessMessage + c);
    }

    public void printNoWordMessage() {
        out.println(noWordMessage);
    }

    public void printNotGuessedMessage(Character c) {
        out.println(notGuessedLetterMessage + c);
    }

    public void printGuessedMessage(Character c) {
        out.println(guessedLetterMessage + c);
    }

    public void printWinMessage(String word) {
        out.println(winMessage + word);
    }

    public void printLooseMessage() {
        out.println(looseMessage);
    }

    public void printPlayAgainMessage() {
        out.print(playAgainMessage);
    }

    public void printAttemptsMessage() {
        out.print(attemptsMessage);
    }

    public void printEmptyMessage() {
        out.println();
    }

}
