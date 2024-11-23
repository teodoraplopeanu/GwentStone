package main.cards.superminions;

import fileio.CardInput;
import fileio.Coordinates;
import main.Game;
import main.cards.Card;
import main.cards.Minion;

public final class Miraj extends Minion {
    public Miraj(final CardInput cardInput) {
        super(cardInput);
    }

    @Override
    public void applyPower(final Coordinates attackerCoord,
                           final Coordinates attackedCoord,
                           final Game game) {
        Card attacker = game.getGameBoard().getCardAt(attackerCoord.getX(), attackerCoord.getY());
        Card attacked = game.getGameBoard().getCardAt(attackedCoord.getX(), attackedCoord.getY());
        int temp = attacker.getHealth();
        attacker.setHealth(attacked.getHealth());
        attacked.setHealth(temp);
    }
}
