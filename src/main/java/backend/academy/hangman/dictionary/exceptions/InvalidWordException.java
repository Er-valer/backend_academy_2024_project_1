package backend.academy.hangman.dictionary.exceptions;

public class InvalidWordException extends RuntimeException {
    public InvalidWordException(String message) {
        super(message);
    }
}
