package backend.academy.hangman.dictionary;

import org.apache.commons.lang3.StringUtils;

/**
 * Complexity of words
 */
public enum Complexity {
    LOW,
    MEDIUM,
    HIGH;

    public static final int LOW_ATTEMPTS = 3;
    public static final int MEDIUM_ATTEMPTS = 4;
    public static final int HIGH_ATTEMPTS = 6;

    public static int maxAttempts(Complexity complexity) {
        return switch (complexity) {
            case LOW -> LOW_ATTEMPTS;
            case MEDIUM -> MEDIUM_ATTEMPTS;
            case HIGH -> HIGH_ATTEMPTS;
        };
    }

    @Override
    public String toString() {
        return StringUtils.capitalize(this.name().toLowerCase());
    }
}
