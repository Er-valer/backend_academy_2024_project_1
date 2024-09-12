package backend.academy.hangman;

import backend.academy.hangman.dictionary.Category;
import backend.academy.hangman.dictionary.Complexity;
import backend.academy.hangman.dictionary.Dictionary;
import backend.academy.hangman.dictionary.Word;
import backend.academy.hangman.dictionary.exceptions.InvalidWordException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import java.util.Set;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DictionaryTest {
    private static final Dictionary defaultDictionary = new Dictionary(
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

    @ParameterizedTest
    @ArgumentsSource(IncorrectWordsProvider.class)
    public void addIncorrectWord(Word invalidWord) {
        assertThatThrownBy(() -> new Dictionary(invalidWord))
            .isInstanceOf(InvalidWordException.class);
    }

    @Test
    public void addCorrectWords() {
        assertThatCode(() -> new Dictionary(
            new Word("aaa", Category.WEATHER, Complexity.MEDIUM, "correct"),
            new Word("bbb", Category.WEATHER, Complexity.MEDIUM, "correct"),
            new Word("ccc", Category.WEATHER, Complexity.MEDIUM, "correct")
        )).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ArgumentsSource(WordsInfoProvider.class)
    public void getRandomWordTest(Category category, Complexity complexity, Set<String> possibleWords) {
        // When
        Word randomWord = defaultDictionary.getRandomWord(category, complexity);

        // Then
        assertThat(possibleWords).contains(randomWord.word());
        assertEquals(category, randomWord.category());
        assertEquals(complexity, randomWord.complexity());
    }

    public static class IncorrectWordsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of(new Word(null, Category.WEATHER, Complexity.MEDIUM, "correct")),
                Arguments.of(new Word("correct", null, Complexity.MEDIUM, "correct")),
                Arguments.of(new Word("correct", Category.WEATHER, null, "correct")),
                Arguments.of(new Word("correct", Category.WEATHER, Complexity.MEDIUM, null)),

                Arguments.of(new Word("a", Category.WEATHER, Complexity.MEDIUM, "correct")),
                Arguments.of(new Word("ab", Category.WEATHER, Complexity.MEDIUM, "correct")),
                Arguments.of(new Word("_?!_?!", Category.WEATHER, Complexity.MEDIUM, "correct")),
                Arguments.of(new Word("abcdef1", Category.WEATHER, Complexity.MEDIUM, "correct")),
                Arguments.of(new Word("abc def", Category.WEATHER, Complexity.MEDIUM, "correct"))
            );
        }
    }

    public static class WordsInfoProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of(Category.FOOD, Complexity.LOW, Set.of("orange")),
                Arguments.of(Category.FOOD, Complexity.HIGH, Set.of("tangerine", "grapefruit")),

                Arguments.of(Category.ANIMALS, Complexity.LOW, Set.of("cat")),
                Arguments.of(Category.ANIMALS, Complexity.MEDIUM, Set.of("tiger")),
                Arguments.of(Category.ANIMALS, Complexity.HIGH, Set.of("caracal")),

                Arguments.of(Category.WEATHER, Complexity.LOW, Set.of("rain")),
                Arguments.of(Category.WEATHER, Complexity.MEDIUM, Set.of("hail", "drizzle"))
            );
        }
    }
}
