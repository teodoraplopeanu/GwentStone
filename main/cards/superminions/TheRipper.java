package main.cards.superminions;

import fileio.CardInput;
import fileio.Coordinates;
import main.Game;
import main.cards.Card;
import main.cards.Minion;

public final class TheRipper extends Minion {
    public TheRipper(final CardInput cardInput) {
        super(cardInput);
    }

    @Override
    public void applyPower(final Coordinates attackerCoord,
                           final Coordinates attackedCoord,
                           final Game game) {
        Card attacked = game.getGameBoard().getCardAt(attackedCoord.getX(), attackedCoord.getY());
        attacked.setAttackDamage(attacked.getAttackDamage() - 2);
        if (attacked.getAttackDamage() < 2) {
            attacked.setAttackDamage(0);
        }
    }
}
