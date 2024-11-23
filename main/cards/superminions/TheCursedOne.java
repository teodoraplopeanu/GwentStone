package main.cards.superminions;

import fileio.CardInput;
import fileio.Coordinates;
import main.Game;
import main.cards.Card;
import main.cards.Minion;

public final class TheCursedOne extends Minion {
    public TheCursedOne(final CardInput cardInput) {
        super(cardInput);
    }

    @Override
    public void applyPower(final Coordinates attackerCoord,
                           final Coordinates attackedCoord,
                           final Game game) {
        Card attacker = game.getGameBoard().getCardAt(attackerCoord.getX(), attackerCoord.getY());
        Card attacked = game.getGameBoard().getCardAt(attackedCoord.getX(), attackedCoord.getY());
        int temp = attacked.getHealth();
        attacked.setHealth(attacked.getAttackDamage());
        attacked.setAttackDamage(temp);
        if  (attacked.getHealth() <= 0) {
            game.getGameBoard().removeCardAt(attackedCoord.getX(), attackedCoord.getY());
        }
    }
}
