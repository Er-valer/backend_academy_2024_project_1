package backend.academy.hangman.player;

import backend.academy.hangman.dictionary.Category;
import backend.academy.hangman.dictionary.Complexity;
import backend.academy.hangman.game.Move;
import java.io.PrintStream;
import java.util.Random;

public class RandomPlayer implements Player {
    private final PrintStream out;

    public RandomPlayer(final PrintStream out) {
        this.out = out;
    }

    public RandomPlayer() {
        this(System.out);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Move getMove() {
        return new Move(String.valueOf((char)('a' + new Random().nextInt(26))));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Category getCategory(Category[] categories) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Complexity getComplexity(Complexity[] complexities) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean continuePlaying() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void log(String message) {
        out.println(message);
    }
}
