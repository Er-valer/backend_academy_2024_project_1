package backend.academy.hangman.game;

import backend.academy.hangman.dictionary.Category;
import backend.academy.hangman.dictionary.Complexity;
import backend.academy.hangman.dictionary.Dictionary;
import backend.academy.hangman.dictionary.Word;
import backend.academy.hangman.game.exceptions.InvalidMoveException;
import backend.academy.hangman.player.Player;
import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent = false)
public class Game {
    private static final String HINT = "?";
    private static final String QUIT = "quit";

    private static final Dictionary DEFAULT_DICTIONARY = new Dictionary(
        new Word("orange", Category.FOOD, Complexity.LOW, "fruit that grows on а tree"),
        new Word("tangerine", Category.FOOD, Complexity.HIGH, "small orange fruit that grows on а tree"),
        new Word("grapefruit", Category.FOOD, Complexity.HIGH, "big orange fruit that grows on а tree"),

        new Word("cat", Category.ANIMALS, Complexity.LOW, "pet with whiskers and tail"),
        new Word("tiger", Category.ANIMALS, Complexity.MEDIUM, "big orange cat"),
        new Word("caracal", Category.ANIMALS, Complexity.HIGH, "Big Floppa"),

        new Word("rain", Category.WEATHER, Complexity.LOW, "water drops from a cloud"),
        new Word("hail", Category.WEATHER, Complexity.MEDIUM, "frozen water drops from a cloud"),
        new Word("drizzle", Category.WEATHER, Complexity.MEDIUM, "small water drops from a cloud")
    );

    private final Player player;
    private final Dictionary dictionary;

    @Getter
    private GameState state;

    public Game(final Player player, final Dictionary dictionary) {
        this.player = player;
        this.dictionary = dictionary;
    }

    public Game(final Player player) {
        this(player, DEFAULT_DICTIONARY);
    }

    /**
     * Starts the game.
     * @return game result
     */
    public Result play() {
        initState();
        Result result;
        do {
            result = move();
        } while (result == Result.RUNNING);

        switch (result) {
            case WIN -> {
                player.log(state.toString());
                player.log("You won!");
            }
            case LOSE -> {
                player.log(state.toString());
                player.log("You lost. The word was: " + state.getHiddenWord());
            }
            case QUIT -> player.log("Goodbye!");
        }
        return result;
    }

    /**
     * Starts endless game.
     */
    public void endlessPlay() {
        Result result;
        do {
            result = play();
        } while (result != Result.QUIT && player.continuePlaying());
    }

    public void initState() {
        Category category = player.getCategory(dictionary.availableCategories());
        Complexity complexity = player.getComplexity(dictionary.availableComplexities(category));
        state = new GameState(dictionary.getRandomWord(category, complexity));

        player.log("Category: " + state.getHiddenWord().category());
        player.log("Complexity: " + state.getHiddenWord().complexity());
    }

    public Result move() {
        player.log(state.toString());
        Move playerMove = player.getMove();

        if (!playerMove.isValid()) {
            throw new InvalidMoveException("User entered invalid move");
        }

        while (true) {
            if (playerMove.move().equals(HINT)) {
                player.log("HINT: " + state.getHiddenWord().hint());
            } else if (playerMove.move().equals(QUIT)) {
                return Result.QUIT;
            } else if (state.isUsed(playerMove)) {
                player.log("This letter has already been used");
            } else {
                break;
            }

            playerMove = player.getMove();
        }

        return state.makeMove(playerMove);
    }
}
