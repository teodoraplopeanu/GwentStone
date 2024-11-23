package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import fileio.Coordinates;
import main.cards.Card;
import main.cards.Hero;
import main.cards.Minion;

public class GameBoardActions {
    static final int ROWS = 4;
    static final int CHANGE_PLAYER = 3;

    public GameBoardActions() { }

    /**
     * Creates an object of type ArrayNode
     * containing all the frozen cards on the game board
     * */
    public ArrayNode getFrozenCards(final Game game) {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode frozenCards = objectMapper.createArrayNode();

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < game.getGameBoard().getGameBoard().get(i).size(); j++) {
                //System.out.println(getGameBoard().getGameBoard().get(i).get(j).isFrozen());
                if (game.getGameBoard().getGameBoard().get(i).get(j).isFrozen()) {
                    //System.out.println(i + " " + j);
                    frozenCards.add(game.getGameBoard().getGameBoard().get(i).get(j).
                            createObjectNode());
                }
            }
        }

        return frozenCards;
    }

    /**
     * Performs the command cardUsesAttack
     * based on the input information
     *
     * @return an ObjectNode containing the output
     * */
    public ObjectNode cardUsesAttack(final ActionsInput actionsInput, final Game game) {
        ObjectNode actionResult;
        Coordinates attackerCoord = actionsInput.getCardAttacker();
        Card attacker = game.getGameBoard().getCardAt(attackerCoord.getX(), attackerCoord.getY());

        Coordinates attackedCoord = actionsInput.getCardAttacked();
        Card attacked = game.getGameBoard().getCardAt(attackedCoord.getX(), attackedCoord.getY());

        // Attacked card does not belong to the enemy
        if (!game.getGameBoard().isEnemy(attackerCoord.getX(), attackedCoord.getX())) {
            actionResult = game.getError().isNotEnemy("cardUsesAttack",
                    attackerCoord, attackedCoord);
            return actionResult;
        }
        // Attacker card has already attacked this turn
        if (attacker.getHasAttacked()) {
            actionResult = game.getError().hasAttacked("cardUsesAttack",
                    attackerCoord, attackedCoord);
            return actionResult;
        }
        // Attacker card is frozen
        if (attacker.isFrozen()) {
            actionResult = game.getError().isFrozen("cardUsesAttack",
                    attackerCoord, attackedCoord);
            return actionResult;
        }
        // Attacked player has tanks
        if (!attacked.isTank() && game.getGameBoard().enemyHasTanks(attacker.
                getCoordinates().getX())) {
            actionResult = game.getError().hasTanks("cardUsesAttack",
                    attackerCoord, attackedCoord);
            return actionResult;
        }
        // Use attack
        attacked.setHealth(attacked.getHealth() - attacker.getAttackDamage());
        attacker.setHasAttacked(true);
        if (attacked.getHealth() <= 0) {
            game.getGameBoard().removeCardAt(attackedCoord.getX(), attackedCoord.getY());
        }
        return null;
    }

    /**
     * Performs the command placeCard
     *
     * @return an ObjectNode containing the output
     * */
    public ObjectNode placeCard(final ActionsInput actionsInput, final Game game) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode actionResult = objectMapper.createObjectNode();
        int handIdx = actionsInput.getHandIdx();
        int playerIdx = game.getGameFlow().getCurrentPlayer();
        Player player = game.getPlayer(playerIdx);

        Minion card = player.getHand().getCards().get(handIdx);
        // Not enough mana
        if (player.getMana() < card.getMana()) {
            actionResult = game.getError().notEnoughMana(handIdx);
            return actionResult;
        }

        int row = card.getRow();
        if (playerIdx == 1) {
            row = CHANGE_PLAYER - row;
        }
        // Row is full
        if (game.getGameBoard().rowIsFull(row)) {
            actionResult = game.getError().rowIsFull(handIdx);
            return actionResult;
        }

        // Place card on board
        player.setMana(player.getMana() - card.getMana());
        game.getGameBoard().placeCardOnBoard(card, row);
        player.getHand().removeCard(card);
        // Set coordinates
        card.getCoordinates().setX(row);
        card.getCoordinates().setY(game.getGameBoard().getGameBoard().get(row).size() - 1);
        return null;
    }

    /**
     * Performs the command useHeroAbility
     *
     * @return an ObjectNode containing the output
     * */
    public ObjectNode useHeroAbility(final int affectedRow, final Game game) {
        Player player = game.getPlayer(game.getCurrentPlayer());
        Hero hero = player.getHero();

        if (player.getMana() < hero.getMana()) {
            return game.getError().notEnoughManaHero(affectedRow);
        }

        if (hero.getHasAttacked()) {
            return game.getError().heroHasAttacked(affectedRow);
        }

        if ((hero.getName().equals("Empress Thorina") || hero.getName().equals("Lord Royce"))
                && ((game.getCurrentPlayer() == 1 && affectedRow >= 2)
                || (game.getCurrentPlayer() == 2 && affectedRow <= 1))) {
            return game.getError().selRowNotEnemy(affectedRow);
        }

        if ((hero.getName().equals("General Kocioraw") || hero.getName().equals("King Mudface"))
                && ((game.getCurrentPlayer() == 1 && affectedRow < 2)
                || (game.getCurrentPlayer() == 2 && affectedRow > 1))) {
            return game.getError().selRowNotPlayer(affectedRow);
        }

        player.setMana(player.getMana() - hero.getMana());
        hero.setHasAttacked(true);
        hero.applySuperPower(affectedRow, game);
        return null;
    }

    /**
     * Performs the attackHero command
     *
     * @return an ObjectNode containing the output
     * */
    public ObjectNode attackHero(final Coordinates attackerCoord, final Game game) {
        Card attacker = game.getGameBoard().getCardAt(attackerCoord.getX(), attackerCoord.getY());

        if (attacker.isFrozen()) {
            return game.getError().isFrozen("useHeroAttack", attackerCoord, null);
        }

        if (attacker.getHasAttacked()) {
            return game.getError().hasAttacked("useAttackHero", attackerCoord, null);
        }

        if (game.getGameBoard().enemyHasTanks(attacker.getCoordinates().getX())) {
            return game.getError().hasTanks("useAttackHero", attackerCoord, null);
        }
        attacker.setHasAttacked(true);

        Hero hero;
        hero = game.getPlayer(CHANGE_PLAYER - game.getCurrentPlayer()).getHero();
        hero.setHealth(hero.getHealth() - attacker.getAttackDamage());
        if (hero.getHealth() <= 0) {
            game.setGameOver(true);
            game.setWinner(game.getCurrentPlayer());
            return null;
        }

        return null;
    }

    /**
     * Performs the command cardUsesAbility
     *
     * @return an ObjectNode containing the output
     * */
    public ObjectNode useAbility(final Coordinates attackerCoord,
                                 final Coordinates attackedCoord,
                                 final Game game) {
        Minion attacker = game.getGameBoard().getCardAt(attackerCoord.getX(), attackerCoord.getY());
        Minion attacked = game.getGameBoard().getCardAt(attackedCoord.getX(), attackedCoord.getY());

        if (attacker.isFrozen()) {
            return game.getError().isFrozen("cardUsesAbility", attackerCoord, attackedCoord);
        }

        if (attacker.getHasAttacked()) {
            return game.getError().hasAttacked("cardUsesAbility", attackerCoord, attackedCoord);
        }

        if (attacker.getName().equals("Disciple")
                && game.getGameBoard().isEnemy(attackerCoord.getX(), attackedCoord.getX())) {
            return game.getError().isEnemy(attackerCoord, attackedCoord);
        }

        if ((attacker.getName().equals("The Ripper") || attacker.getName().equals("The Cursed One")
                || attacker.getName().equals("Miraj"))) {
            if (!game.getGameBoard().isEnemy(attackerCoord.getX(), attackedCoord.getX())) {
                return game.getError().isNotEnemy("cardUsesAbility", attackerCoord, attackedCoord);
            }
            if (!attacked.isTank() && game.getGameBoard().enemyHasTanks(attackerCoord.getX())) {
                return game.getError().hasTanks("cardUsesAbility", attackerCoord, attackedCoord);
            }
        }

        attacker.applyPower(attackerCoord, attackedCoord, game);
        attacker.setHasAttacked(true);

        return null;
    }
}
