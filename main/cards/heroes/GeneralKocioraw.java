package main.cards.heroes;

import fileio.CardInput;
import main.Game;
import main.cards.Card;
import main.cards.Hero;

public final class GeneralKocioraw extends Hero {
    public GeneralKocioraw(final CardInput cardInput) {
        super(cardInput);
    }

    @Override
    public void applySuperPower(final int affectedRow, final Game game) {
        // Blood Thirst
        for (Card card : game.getGameBoard().getGameBoard().get(affectedRow)) {
            card.setAttackDamage(card.getAttackDamage() + 1);
        }
    }
}
