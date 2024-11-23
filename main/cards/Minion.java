package main.cards;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;
import fileio.Coordinates;
import main.Game;

public class Minion extends Card {
    public Minion() {
        super();
    }

    public Minion(final CardInput cardInput) {
        super(cardInput);
    }

    /**
     * Creates an object node from a Card,
     * in order to add it to an ArrayNode
     * */
    @Override
    public ObjectNode createObjectNode() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("mana", mana);

        objectNode.put("attackDamage", attackDamage);

        objectNode.put("health", health);

        objectNode.put("description", description);

        ArrayNode colorsNode = objectMapper.createArrayNode();
        for (String color : colors) {
            colorsNode.add(color);
        }
        objectNode.set("colors", colorsNode);

        objectNode.put("name", name);

        return objectNode;
    }

    /**
     * Applies the power of the minions with superpowers
     * */
    public void applyPower(final Coordinates attackerCoord,
                           final Coordinates attackedCoord,
                           final Game game) { }
}
