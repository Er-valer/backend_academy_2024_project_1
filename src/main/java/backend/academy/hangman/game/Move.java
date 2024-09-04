package backend.academy.hangman.game;

public record Move(String move) {
    /**
     * Validity of the move.
     * A move is valid if it is a Latin letter (a-z, A-Z), '?' (for a hint) or 'quit' (to exit)
     * @return validity of the move
     */
    public boolean isValid() {
        return move.matches("^[a-zA-Z?]$|^quit$");
    }
}
