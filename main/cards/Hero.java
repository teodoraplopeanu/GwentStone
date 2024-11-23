package main.cards;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;
import main.Game;

public class Hero extends Card {
    public Hero() {
        super();
    }

    public Hero(final CardInput cardInput) {
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
        objectNode.put("description", description);

        ArrayNode colorsNode = objectMapper.createArrayNode();
        for (String color : colors) {
            colorsNode.add(color);
        }
        objectNode.set("colors", colorsNode);

        objectNode.put("name", name);

        objectNode.put("health", health);

        return objectNode;
    }

    /**
     * Applies the super power of a certain hero to the cards
     * situated on the affectedRow on the gameBoard
     *
     * @param affectedRow the row that is going to be attacked
     * @param game the current game
     * */
    public void applySuperPower(final int affectedRow, final Game game) { }
}
