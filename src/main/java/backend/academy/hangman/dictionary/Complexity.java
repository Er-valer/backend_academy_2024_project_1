package backend.academy.hangman.dictionary;

import org.apache.commons.lang3.StringUtils;

/**
 * Complexity of words
 */
public enum Complexity {
    LOW,
    MEDIUM,
    HIGH;

    public static int maxAttempts(Complexity complexity) {
        return switch (complexity) {
            case LOW -> 3;
            case MEDIUM -> 4;
            case HIGH -> 6;
        };
    }

    @Override
    public String toString() {
        return StringUtils.capitalize(this.name().toLowerCase());
    }
}
