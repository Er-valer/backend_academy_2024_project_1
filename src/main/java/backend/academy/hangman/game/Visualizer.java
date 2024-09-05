package backend.academy.hangman.game;

public class Visualizer {
    private static final char CONSTANT = '!';
    private static final char SPACE = ' ';
    private static final char MIN_ATTEMPT_CHAR = 'a';

    private static final String[] HANGMAN_PATTERN = new String[]{
        "                ",
        "    ________    ",
        "    |/    |     ",
        "    |     O     ",
        "    |    /|\\    ",
        "    |    / \\    ",
        "    |\\          ",
        "____|TTTTTT|____",
    };

    private static final String[] ATTEMPTS_PATTERN = new String[] {
        "!!!!!!!!!!!!!!!!",
        "!!!!eeeeeeee!!!!",
        "!!!!fe!!!!d!!!!!",
        "!!!!f!!!!!c!!!!!",
        "!!!!f!!!!bcb!!!!",
        "!!!!f!!!!a!a!!!!",
        "!!!!ff!!!!!!!!!!",
        "gggggggggggggggg"
    };

    private Visualizer() {}

    public static String generateHangman(int attempts) {
        StringBuilder hangman = new StringBuilder();

        for (int i = 0; i < ATTEMPTS_PATTERN.length; i++) {
            String line = ATTEMPTS_PATTERN[i];
            for (int j = 0; j < line.length(); j++) {
                if (line.charAt(j) == CONSTANT || line.charAt(j) - MIN_ATTEMPT_CHAR >= attempts) {
                    hangman.append(HANGMAN_PATTERN[i].charAt(j));
                } else {
                    hangman.append(SPACE);
                }
            }
            hangman.append(System.lineSeparator());
        }

        return hangman.toString();
    }
}
