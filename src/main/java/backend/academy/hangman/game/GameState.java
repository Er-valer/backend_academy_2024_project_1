package backend.academy.hangman.game;

import backend.academy.hangman.dictionary.Complexity;
import backend.academy.hangman.dictionary.Word;
import lombok.Getter;
import lombok.experimental.Accessors;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.IntStream;

@Accessors(fluent = false)
@Getter
public class GameState {
    private final Word hiddenWord;
    private final Character[] guessedChars;
    private final Set<Character> used = new HashSet<>();
    private int attempts;
    private int guessed;

    public GameState(Word hiddenWord) {
        this.hiddenWord = hiddenWord;
        attempts = Complexity.maxAttempts(hiddenWord.complexity());

        guessedChars = new Character[hiddenWord.word().length()];
        Arrays.fill(guessedChars, '_');
    }

    public GameState(GameState state) {
        this.hiddenWord = state.hiddenWord;
        this.guessedChars = state.guessedChars;
        this.used.addAll(state.used);
        this.attempts = state.attempts;
        this.guessed = state.guessed;
    }

    /**
     * Makes valid move and changes state.
     * @param move given move
     * @return game result after move
     */
    public Result makeMove(Move move) {
        String hiddenWordStr = hiddenWord.word().toLowerCase();
        char moveChar = move.move().toLowerCase().charAt(0);

        List<Integer> indexes = IntStream
            .iterate(hiddenWordStr.indexOf(moveChar), i -> i > -1, i -> hiddenWordStr.indexOf(moveChar, i + 1))
            .boxed()
            .toList();

        used.add(moveChar);
        if (indexes.isEmpty()) {
            attempts--;
        } else {
            indexes.forEach(i -> guessedChars[i] = moveChar);
            guessed += indexes.size();
        }

        if (guessed == hiddenWordStr.length()) {
            return Result.WIN;
        } else if (attempts == 0) {
            return Result.LOSE;
        }
        return Result.RUNNING;
    }

    public boolean isUsed(Move move) {
        return used.contains(move.move().toLowerCase().charAt(0));
    }

    @Override
    public String toString() {
        return Visualizer.generateHangman(attempts) + System.lineSeparator()
            + "Attempts left: " + attempts + System.lineSeparator()
            + "Hidden word: " + String.join(" ", Arrays.stream(guessedChars).map(String::valueOf).toList())
            + System.lineSeparator();
    }

    @Override
    public int hashCode() {
        return Objects.hash(hiddenWord, Arrays.hashCode(guessedChars), used, attempts, guessed);
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameState gameState)) return false;

        return attempts == gameState.attempts && guessed == gameState.guessed &&
            Objects.equals(hiddenWord, gameState.hiddenWord) &&
            Objects.deepEquals(guessedChars, gameState.guessedChars) && Objects.equals(used, gameState.used);
    }
}
