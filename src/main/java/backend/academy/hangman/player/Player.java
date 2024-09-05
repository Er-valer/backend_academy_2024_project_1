package backend.academy.hangman.player;

import backend.academy.hangman.dictionary.Category;
import backend.academy.hangman.dictionary.Complexity;
import backend.academy.hangman.game.Move;

public interface Player {
    /**
     * Reads the user's next <strong>valid</strong> move ({@link Move#isValid()}).
     * @return next valid move
     */
    Move getMove();

    /**
     * Provides the user with a choice of categories from the available categories.
     * @param categories available categories
     * @return selected category
     */
    Category getCategory(Category[] categories);

    /**
     * Provides the user with a choice of complexities from the available complexities.
     * @param complexities available complexities
     * @return selected complexity
     */
    Complexity getComplexity(Complexity[] complexities);

    /**
     * Asks the user if he wants to continue the game
     * @return whether the player wants to continue the game
     */
    boolean continuePlaying();

    /**
     * Shows a message to the user
     * @param message to show
     */
    void log(String message);
}
