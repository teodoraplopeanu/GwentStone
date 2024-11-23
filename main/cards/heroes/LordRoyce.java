package main.cards.heroes;

import fileio.CardInput;
import main.Game;
import main.cards.Card;
import main.cards.Hero;

public final class LordRoyce extends Hero {
    public LordRoyce(final CardInput cardInput) {
        super(cardInput);
    }

    @Override
    public void applySuperPower(final int affectedRow, final Game game) {
        // Sub-Zero
        for (Card card : game.getGameBoard().getGameBoard().get(affectedRow)) {
            if (game.getCurrentPlayer() == 2) {
                card.setFrozenNextRound(true);
            } else {
                card.setFrozen(true);
            }
        }
    }
}
