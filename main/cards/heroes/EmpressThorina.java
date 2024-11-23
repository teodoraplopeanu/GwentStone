package main.cards.heroes;

import fileio.CardInput;
import main.Game;
import main.cards.Card;
import main.cards.Hero;

public final class EmpressThorina extends Hero {
    public EmpressThorina(final CardInput cardInput) {
        super(cardInput);
    }

    @Override
    public void applySuperPower(final int affectedRow, final Game game) {
        // Low Blow
        Card max = new Card();
        for (Card card : game.getGameBoard().getGameBoard().get(affectedRow)) {
            if (card.getHealth() > max.getHealth()) {
                max = card;
            }
        }
        game.getGameBoard().getGameBoard().get(affectedRow).remove(max);
    }
}
