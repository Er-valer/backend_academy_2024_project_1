package backend.academy.hangman;

import backend.academy.hangman.dictionary.Category;
import backend.academy.hangman.dictionary.Complexity;
import backend.academy.hangman.dictionary.Dictionary;
import backend.academy.hangman.dictionary.Word;
import backend.academy.hangman.game.Game;
import backend.academy.hangman.game.GameState;
import backend.academy.hangman.game.Result;
import backend.academy.hangman.player.HumanPlayer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ValueSource;
import java.io.ByteArrayInputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {
    @ParameterizedTest
    @ValueSource(strings = {"aa", "!", "_", "Ы"})
    public void invalidInputTest(String invalidInput) {
        Game game = getSampleGameWithMoves(System.out, invalidInput);
        game.initState();
        GameState before = new GameState(game.getState());
        game.move();
        assertEquals(before, game.getState(), "State should not change after an incorrect move");
    }

    @Test
    public void validCorrectInputTest() {
        String[] moves = new String[] {"o", "R", "a", "N", "g", "E"};
        Game game = getSampleGameWithMoves(System.out, moves);
        game.initState();
        for (var _ : moves) {
            GameState before = new GameState(game.getState());
            game.move();

            assertEquals(before.getGuessed() + 1, game.getState().getGuessed(),
                "The number of letters guessed should increase after entering the correct letter");
            assertEquals(before.getAttempts(), game.getState().getAttempts(),
                "The number of attempts should not change after entering the correct letter");
        }
    }

    @Test
    public void validIncorrectInputTest() {
        String[] moves = new String[] {"B", "c", "D", "f", "H", "z"};
        Game game = getSampleGameWithMoves(System.out, moves);
        game.initState();
        for (var _ : moves) {
            GameState before = new GameState(game.getState());
            game.move();

            assertEquals(before.getGuessed(), game.getState().getGuessed(),
                "The number of letters guessed should not change after entering the incorrect letter");
            assertEquals(before.getAttempts() - 1, game.getState().getAttempts(),
                "The number of attempts should decrease after entering the incorrect letter.");
        }
    }

    @ParameterizedTest
    @ArgumentsSource(resultTestArgumentsProvider.class)
    public void resultTest(String[] moves, Result expectedResult) {
        Game game = getSampleGameWithMoves(System.out, moves);
        assertEquals(expectedResult, game.play(),
            "For moves: " + Arrays.toString(moves) + " expected result: " + expectedResult);
    }

    private Game getSampleGameWithMoves(PrintStream out, String... moves) {
        return new Game(
            new HumanPlayer(out, new Scanner(new ByteArrayInputStream(
                ("1 1 " + String.join(" ", Arrays.asList(moves)) + " quit ").getBytes()
            ))),
            new Dictionary(
                new Word("orange", Category.FOOD, Complexity.LOW, "fruit that grows on а tree")
            )
        );
    }

    public static class resultTestArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of(new String[] {"o", "R", "a", "N", "g", "E"}, Result.WIN),
                Arguments.of(new String[] {"o", "_", "R", "_", "a", "_", "N", "_", "g", "_", "E"}, Result.WIN),
                Arguments.of(new String[] {"o", "R", "h", "a", "N", "g", "E"}, Result.WIN),
                Arguments.of(new String[] {"o", "R", "h", "a", "J", "N", "g", "E"}, Result.WIN),
                Arguments.of(new String[] {"o", "R", "h", "a", "J", "N", "k", "g", "E"}, Result.LOSE),
                Arguments.of(new String[] {"B", "c", "D", "f", "H", "z"}, Result.LOSE)
            );
        }
    }
}
