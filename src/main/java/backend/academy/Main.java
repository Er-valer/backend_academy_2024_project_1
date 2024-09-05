package backend.academy;

import backend.academy.hangman.game.Game;
import backend.academy.hangman.player.HumanPlayer;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        Game game = new Game(new HumanPlayer());
        game.endlessPlay();
    }
}
