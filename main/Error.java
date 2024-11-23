package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.Coordinates;

public final class Error {
    private ObjectMapper objectMapper;

    public Error() {
        objectMapper = new ObjectMapper();
    }

    /**
     * Creates an ObjectNode that contains the necessary information
     * for an error of type
     * "Attacked card does not belong to the enemy."
     * Useful for commands: cardUsesAttack & cardUsesAbility
     *
     * @param command command that caused the error
     * @param attackerCoord coordinates of the attacker card
     * @param attackedCoord coordinates of the attacked card
     * */
    public ObjectNode isNotEnemy(final String command, final Coordinates attackerCoord,
                                 final Coordinates attackedCoord) {
        ObjectNode error = objectMapper.createObjectNode();

        error.put("command", command);
        ObjectNode cardAttacker = objectMapper.createObjectNode();
        cardAttacker.put("x", attackerCoord.getX());
        cardAttacker.put("y", attackerCoord.getY());
        error.put("cardAttacker", cardAttacker);

        ObjectNode cardAttacked = objectMapper.createObjectNode();
        cardAttacked.put("x", attackedCoord.getX());
        cardAttacked.put("y", attackedCoord.getY());
        error.put("cardAttacked", cardAttacked);

        error.put("error", "Attacked card does not belong to the enemy.");

        return error;
    }

    /**
     * Creates an ObjectNode that contains the necessary information
     * for an error of type
     * "Attacked card does not belong to the current player."
     * Useful for command: cardUsesAbility
     *
     * @param attackerCoord coordinates of the attacker card
     * @param attackedCoord coordinates of the attacked card
     * */
    public ObjectNode isEnemy(final Coordinates attackerCoord,
                              final Coordinates attackedCoord) {
        ObjectNode error = objectMapper.createObjectNode();

        error.put("command", "cardUsesAbility");
        ObjectNode cardAttacker = objectMapper.createObjectNode();
        cardAttacker.put("x", attackerCoord.getX());
        cardAttacker.put("y", attackerCoord.getY());
        error.put("cardAttacker", cardAttacker);

        ObjectNode cardAttacked = objectMapper.createObjectNode();
        cardAttacked.put("x", attackedCoord.getX());
        cardAttacked.put("y", attackedCoord.getY());
        error.put("cardAttacked", cardAttacked);

        error.put("error", "Attacked card does not belong to the current player.");

        return error;
    }

    /**
     * Creates an ObjectNode that contains the necessary information
     * for an error of type
     * "Attacker card has already attacked this turn."
     * Useful for commands: cardUsesAttack, cardUsesAbility & useHeroAttack
     *
     * @param command command that caused the error
     * @param attackerCoord coordinates of the attacker card
     * @param attackedCoord coordinates of the attacked card
     * */
    public ObjectNode hasAttacked(final String command, final Coordinates attackerCoord,
                                  final Coordinates attackedCoord) {
        ObjectNode error = objectMapper.createObjectNode();

        error.put("command", command);
        ObjectNode cardAttacker = objectMapper.createObjectNode();
        cardAttacker.put("x", attackerCoord.getX());
        cardAttacker.put("y", attackerCoord.getY());
        error.put("cardAttacker", cardAttacker);

        if (!command.equals("useAttackHero")) {
            ObjectNode cardAttacked = objectMapper.createObjectNode();
            cardAttacked.put("x", attackedCoord.getX());
            cardAttacked.put("y", attackedCoord.getY());
            error.put("cardAttacked", cardAttacked);
        }

        error.put("error", "Attacker card has already attacked this turn.");

        return error;
    }

    /**
     * Creates an ObjectNode that contains the necessary information
     * for an error of type
     * "Attacker card has already attacked this turn."
     * Useful for commands: cardUsesAttack, cardUsesAbility & useHeroAttack
     *
     * @param command command that caused the error
     * @param attackerCoord coordinates of the attacker card
     * @param attackedCoord coordinates of the attacked card
     * */
    public ObjectNode isFrozen(final String command, final Coordinates attackerCoord,
                               final Coordinates attackedCoord) {
        ObjectNode error = objectMapper.createObjectNode();

        error.put("command", command);
        ObjectNode cardAttacker = objectMapper.createObjectNode();
        cardAttacker.put("x", attackerCoord.getX());
        cardAttacker.put("y", attackerCoord.getY());
        error.put("cardAttacker", cardAttacker);

        if (!command.equals("useHeroAttack")) {
            ObjectNode cardAttacked = objectMapper.createObjectNode();
            cardAttacked.put("x", attackedCoord.getX());
            cardAttacked.put("y", attackedCoord.getY());
            error.put("cardAttacked", cardAttacked);
        }

        error.put("error", "Attacker card is frozen.");

        return error;
    }

    /**
     * Generates an ObjectNode that contains all the necessary information
     * for an error of type
     * "Not enough mana to place card on table."
     * Useful for command: placeCard
     *
     * @param handIdx the index of the card that caused the error
     * */
    public ObjectNode notEnoughMana(final int handIdx) {
        ObjectNode error = objectMapper.createObjectNode();
        error.put("command", "placeCard");
        error.put("handIdx", handIdx);
        error.put("error", "Not enough mana to place card on table.");
        return error;
    }

    /**
     * Generates an ObjectNode that contains all the necessary information
     * for an error of type
     * "Cannot place card on table since row is full."
     * Useful for command: placeCard
     *
     * @param handIdx the index of the card that caused the error
     * */
    public ObjectNode rowIsFull(final int handIdx) {
        ObjectNode error = objectMapper.createObjectNode();
        error.put("command", "placeCard");
        error.put("handIdx", handIdx);
        error.put("error", "Cannot place card on table since row is full.");
        return error;
    }

    /**
     * Creates an ObjectNode that contains the necessary information
     * for an error of type
     * "Attacked card is not of type 'Tank'."
     * Useful for commands: cardUsesAttack, cardUsesAbility & useHeroAttack
     *
     * @param command command that caused the error
     * @param attackerCoord coordinates of the attacker card
     * @param attackedCoord coordinates of the attacked card
     * */
    public ObjectNode hasTanks(final String command, final Coordinates attackerCoord,
                               final Coordinates attackedCoord) {
        ObjectNode error = objectMapper.createObjectNode();

        error.put("command", command);
        ObjectNode cardAttacker = objectMapper.createObjectNode();
        cardAttacker.put("x", attackerCoord.getX());
        cardAttacker.put("y", attackerCoord.getY());
        error.put("cardAttacker", cardAttacker);

        if (!command.equals("useAttackHero")) {
            ObjectNode cardAttacked = objectMapper.createObjectNode();
            cardAttacked.put("x", attackedCoord.getX());
            cardAttacked.put("y", attackedCoord.getY());
            error.put("cardAttacked", cardAttacked);
        }

        error.put("error", "Attacked card is not of type 'Tank'.");

        return error;
    }

    /**
     * Generates an ObjectNode that contains all the necessary information
     * for an error of type
     * "Not enough mana to use hero's ability."
     * Useful for command: useHeroAbility
     *
     * @param affectedRow attacked row
     * */
    public ObjectNode notEnoughManaHero(final int affectedRow) {
        ObjectNode error = objectMapper.createObjectNode();
        error.put("command", "useHeroAbility");
        error.put("affectedRow", affectedRow);
        error.put("error", "Not enough mana to use hero's ability.");
        return error;
    }

    /**
     * Generates an ObjectNode that contains all the necessary information
     * for an error of type
     * "Hero has already attacked this turn."
     * Useful for command: useHeroAbility
     *
     * @param affectedRow attacked row
     * */
    public ObjectNode heroHasAttacked(final int affectedRow) {
        ObjectNode error = objectMapper.createObjectNode();
        error.put("command", "useHeroAbility");
        error.put("affectedRow", affectedRow);
        error.put("error", "Hero has already attacked this turn.");
        return error;
    }

    /**
     * Generates an ObjectNode that contains all the necessary information
     * for an error of type
     * "Selected row does not belong to the enemy."
     * Useful for command: useHeroAbility
     *
     * @param affectedRow attacked row
     * */
    public ObjectNode selRowNotEnemy(final int affectedRow) {
        ObjectNode error = objectMapper.createObjectNode();
        error.put("command", "useHeroAbility");
        error.put("affectedRow", affectedRow);
        error.put("error", "Selected row does not belong to the enemy.");
        return error;
    }

    /**
     * Generates an ObjectNode that contains all the necessary information
     * for an error of type
     * "Selected row does not belong to the current player."
     * Useful for command: useHeroAbility
     *
     * @param affectedRow attacked row
     * */
    public ObjectNode selRowNotPlayer(final int affectedRow) {
        ObjectNode error = objectMapper.createObjectNode();
        error.put("command", "useHeroAbility");
        error.put("affectedRow", affectedRow);
        error.put("error", "Selected row does not belong to the current player.");
        return error;
    }
}
