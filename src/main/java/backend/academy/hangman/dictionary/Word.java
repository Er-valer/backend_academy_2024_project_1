package backend.academy.hangman.dictionary;

public record Word(String word, Category category, Complexity complexity, String hint) {
    /**
     * Validity of the word.
     * A word is valid if its length is more than 2, it consists of letters of the Latin alphabet,
     * and its category, complexity and hint are not null.
     * @return validity of the word
     */
    public boolean isValid() {
        return word != null && category != null && complexity != null && hint != null
            && word.matches("^[a-zA-Z]{2}[a-zA-Z]+$");
    }

    @Override
    public String toString() {
        return word.toLowerCase();
    }
}
