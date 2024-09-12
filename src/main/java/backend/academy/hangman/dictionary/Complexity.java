package backend.academy.hangman.dictionary;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

/**
 * Complexity of words
 */
@Accessors(fluent = false)
@Getter
public enum Complexity {

    LOW(3),
    MEDIUM(4),
    HIGH(6);

    private final int maxAttempts;

    Complexity(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    @Override
    public String toString() {
        return StringUtils.capitalize(this.name().toLowerCase());
    }
}
