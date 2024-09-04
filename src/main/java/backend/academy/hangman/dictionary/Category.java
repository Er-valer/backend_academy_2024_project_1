package backend.academy.hangman.dictionary;

import org.apache.commons.lang3.StringUtils;

/**
 * Categories of words
 */
public enum Category {
    FOOD,
    ANIMALS,
    WEATHER;

    @Override
    public String toString() {
        return StringUtils.capitalize(this.name().toLowerCase());
    }
}
