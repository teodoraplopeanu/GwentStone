package main.cards.heroes;

import fileio.CardInput;
import main.Game;
import main.cards.Card;
import main.cards.Hero;

public final class KingMudface extends Hero {
    public KingMudface(final CardInput cardInput) {
        super(cardInput);
    }

    @Override
    public void applySuperPower(final int affectedRow, final Game game) {
        // Earth Born
        for (Card card : game.getGameBoard().getGameBoard().get(affectedRow)) {
            card.setHealth(card.getHealth() + 1);
        }
    }
}
