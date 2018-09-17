package com.pk.hangman.engine;

import com.pk.hangman.engine.dict.DictionaryEngine;
import com.pk.hangman.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.apache.logging.log4j.util.Strings.isNotBlank;

public class GameInitializer {

    @Autowired
    private MessageUtils messageUtils;

    @Autowired
    private Random random;

    @Value("${app.hangman.dict.file.path}")
    private String dictionaryFilePath;

    /**
     * Process user input, randomly select a word, star game
     */
    public void startGame() {
        messageUtils.printInitMessages();

        try (Scanner scanner = new Scanner(System.in)) {
            boolean repeat = true;
            while (repeat) {
                messageUtils.printEmptyMessage();

                messageUtils.printLengthMessage();
                int length = scanner.nextInt();

                messageUtils.printFirstLetterMessage();
                Character firstLetter = scanner.next().charAt(0);

                messageUtils.printLastLetterMessage();
                Character lastLetter = scanner.next().charAt(0);

                DictionaryEngine dictionaryEngine = new DictionaryEngine(dictionaryFilePath, length);
                List<String> filteredWordList = dictionaryEngine.getRecentlyFiltered().stream()
                        .filter(word -> word.indexOf(firstLetter) == 0 && word.lastIndexOf(lastLetter) == length - 1)
                        .collect(toList());

                String randomlyChosenTargetWord = "";
                if (!filteredWordList.isEmpty())
                    randomlyChosenTargetWord = filteredWordList.get(random.nextInt(filteredWordList.size()));

                if (isNotBlank(randomlyChosenTargetWord)) {
                    messageUtils.printTargetWordMessage(randomlyChosenTargetWord);
                    messageUtils.printAttemptsMessage();
                    int attempts = scanner.nextInt();

                    GameContext context = new GameContext(randomlyChosenTargetWord, attempts);
                    GameEngine gameEngine = new GameEngine(context, dictionaryEngine, messageUtils);
                    gameEngine.playGame();

                    messageUtils.printEmptyMessage();
                    messageUtils.printPlayAgainMessage();
                    Character yn = scanner.next().charAt(0);
                    repeat = playAgain(yn);
                } else {
                    messageUtils.printNoWordMessage();
                }
            }
        }
    }

    private Boolean playAgain(Character input) {
        if ('y' == input)
            return Boolean.TRUE;
        return Boolean.FALSE;
    }

}
