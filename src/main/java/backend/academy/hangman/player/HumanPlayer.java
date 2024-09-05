package backend.academy.hangman.player;

import backend.academy.hangman.dictionary.Category;
import backend.academy.hangman.dictionary.Complexity;
import backend.academy.hangman.game.Move;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class HumanPlayer implements Player {
    private final PrintStream out;
    private final Scanner in;

    public HumanPlayer(final PrintStream out, final Scanner in) {
        this.out = out;
        this.in = in;
    }

    public HumanPlayer(final PrintStream out) {
        this(out, new Scanner(System.in));
    }

    public HumanPlayer(final Scanner in) {
        this(System.out, in);
    }

    public HumanPlayer() {
        this(System.out, new Scanner(System.in));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Move getMove() {
        while (true) {
            log("Please, enter a letter, '?' (for a hint) or 'quit' (to exit): ");
            String input = in.next().trim();
            Move move = new Move(input);

            if (!move.isValid()) {
                log("Input must be a Latin letter (a-z, A-Z) or '?' (for a hint) or 'quit' (to exit)");
            } else {
                return move;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Category getCategory(Category[] categories) {
        return getFromArray(categories, "Available categories: ");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Complexity getComplexity(Complexity[] complexities) {
        return getFromArray(Arrays.stream(complexities)
                .sorted(Complexity::compareTo)
                .toArray(Complexity[]::new),
            "Available complexities: "
        );
    }

    /**
     * {@inheritDoc}
     */
    public boolean continuePlaying() {
        log("Do you want to play again? (y/n)");

        while (true) {
            String input = in.next().trim();
            if (input.equalsIgnoreCase("y")) {
                return true;
            } else if (input.equalsIgnoreCase("n")) {
                log("Goodbye!");
                return false;
            }
            log("Do you want to play again? (y/n)");
        }
    }

    private <T> T getFromArray(T[] array, String header) {
        while (true) {
            log(header);
            log("0. Random");

            IntStream.iterate(0, i -> i + 1)
                .limit(array.length)
                .forEach(i -> out.println((i + 1) + ". " + array[i].toString()));

            log("Please, enter number: ");

            try {
                int ind = Integer.parseInt(in.next().trim());

                if (0 <= ind && ind <= array.length) {
                    return (ind == 0) ? null : array[ind - 1];
                }
                log("Number out of range");
            } catch (NumberFormatException e) {
                log("Invalid input");
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void log(String message) {
        out.println(message);
    }
}
