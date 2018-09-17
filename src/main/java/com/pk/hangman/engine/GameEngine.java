package com.pk.hangman.engine;

import com.pk.hangman.engine.dict.DictionaryEngine;
import com.pk.hangman.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Value;

public class GameEngine {

    private MessageUtils messageUtils;

    @Value("${app.hangman.dict.file.path}")
    private String dictionaryFilePath;

    private DictionaryEngine dictionary;

    private GameContext context;

    public GameEngine(GameContext context, DictionaryEngine dictionaryEngine, MessageUtils messageUtils) {
        this.context = context;
        this.dictionary = dictionaryEngine;
        this.messageUtils = messageUtils;
    }

    /**
     * Game process with info messages
     */
    public void playGame() {
        Character nextLetter = null;
        do {
            messageUtils.printEmptyMessage();
            messageUtils.printAttemptsLeftMessage(context.getMaxAttempts() - context.getCurrAttempt());
            messageUtils.printUsedLettersMessage(context.getMissedLetters());
            messageUtils.printGuessingWordMessage(context.buildWordStringForDisplay());

            nextLetter = dictionary.guessNextLetter(context.getNotAllowedLetters());
            if (nextLetter != null) {//whole word is already guessed
                messageUtils.printGuessMessage(nextLetter);
                if (context.targetContainsLetter(nextLetter)) {
                    messageUtils.printGuessedMessage(nextLetter);
                    context.addGuessedLetter(nextLetter);
                } else {
                    messageUtils.printNotGuessedMessage(nextLetter);
                    context.addMissedLetter(nextLetter);
                }
                dictionary.filter(context);
            }
        } while (nextLetter != null && context.attemptsAvailable());

        if (context.isWordGuessed())
            messageUtils.printWinMessage(context.getTargetWord());
        else
            messageUtils.printLooseMessage();
    }

}
