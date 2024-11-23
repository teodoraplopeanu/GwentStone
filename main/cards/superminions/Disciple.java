package main.cards.superminions;

import fileio.CardInput;
import fileio.Coordinates;
import main.Game;
import main.cards.Card;
import main.cards.Minion;

public final class Disciple extends Minion {
    public Disciple(final CardInput cardInput) {
        super(cardInput);
    }

    @Override
    public void applyPower(final Coordinates attackerCoord,
                           final Coordinates attackedCoord,
                           final Game game) {
        Card attacked = game.getGameBoard().getCardAt(attackedCoord.getX(), attackedCoord.getY());
        attacked.setHealth(attacked.getHealth() + 2);
    }
}
