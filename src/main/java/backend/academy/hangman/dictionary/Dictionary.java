package backend.academy.hangman.dictionary;

import backend.academy.hangman.dictionary.exceptions.InvalidWordException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Predicate;

public final class Dictionary {
    private final SecureRandom random = new SecureRandom();

    private final List<Word> words;
    private final Set<Complexity> availableComplexities = EnumSet.noneOf(Complexity.class);
    private final Set<Category> availableCategories = EnumSet.noneOf(Category.class);
    private final Map<Category, Set<Complexity>> availableComplexitiesByCategory = new EnumMap<>(Category.class);

    /**
     * Creates dictionary.
     *
     * @param words words that will be added to the dictionary
     * @throws InvalidWordException if even one word is invalid ({@link Word#isValid()})
     */
    public Dictionary(final Word... words) {
        this.words = new ArrayList<>(words.length);

        for (Word word : words) {
            if (!word.isValid()) {
                throw new InvalidWordException("Try to add invalid word to dictionary");
            }

            this.words.add(word);
            availableComplexities.add(word.complexity());
            availableCategories.add(word.category());
            availableComplexitiesByCategory.putIfAbsent(word.category(), EnumSet.noneOf(Complexity.class));
            availableComplexitiesByCategory.get(word.category()).add(word.complexity());
        }
    }

    /**
     * Filter words by given predicate.
     *
     * @param predicate given predicate
     * @return list of filtered words
     */
    private List<Word> filterBy(final Predicate<Word> predicate) {
        return words.stream().filter(predicate).toList();
    }

    /**
     * Filter words by given category and complexity.
     * If category (or complexity) is null, then returns words with all possible categories (or complexities).
     *
     * @param category   category by which words are filtered
     * @param complexity complexity by which words are filtered
     * @return list of filtered words
     */
    public List<Word> filterByCategoryAndComplexity(final Category category, final Complexity complexity) {
        if (category == null) {
            if (complexity == null) {
                return words;
            }
            return filterBy(w -> w.complexity() == complexity);
        } else if (complexity == null) {
            return filterBy(w -> w.category() == category);
        }
        return filterBy(w -> w.category() == category && w.complexity() == complexity);
    }

    /**
     * Returns all available complexities of words with given category.
     * If category is null, then words with all possible categories are considered.
     *
     * @param category category of word
     * @return all available complexities
     */
    public Complexity[] availableComplexities(final Category category) {
        if (category == null) {
            return availableComplexities.toArray(Complexity[]::new);
        }
        return availableComplexitiesByCategory.get(category).toArray(Complexity[]::new);
    }

    /**
     * Gets all available categories in the dictionary.
     *
     * @return all available categories
     */
    public Category[] availableCategories() {
        return availableCategories.toArray(Category[]::new);
    }

    /**
     * Returns random word with given category and complexity.
     * If category (or complexity) is null, then words with all possible categories (or complexities) are considered.
     *
     * @param category   category of word
     * @param complexity complexity of word
     * @return random word from dictionary with given category and complexity
     * @throws NoSuchElementException if there is no words with given category and complexity
     */
    public Word getRandomWord(final Category category, final Complexity complexity) {
        List<Word> filteredWords = filterByCategoryAndComplexity(category, complexity);

        if (!filteredWords.isEmpty()) {
            return filteredWords.get(random.nextInt(filteredWords.size()));
        }
        throw new NoSuchElementException("There is no way to get a random word");
    }
}
