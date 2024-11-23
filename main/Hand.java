package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import main.cards.Minion;

import java.util.ArrayList;

public final class Hand {
    private ArrayList<Minion> cards;

    public Hand() {
        cards = new ArrayList<>();
    }

    /**
     * Adds a card to the hand
     * */
    public void addCard(final Minion card) {
        cards.add(card);
    }

    /**
     * Removes a card from the hand
     * */
    public void removeCard(final Minion card) {
        cards.remove(card);
    }

    /**
     * Creates an ArrayNode from an object Hand
     * */
    public ArrayNode createArrayNode() {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode arrayNode = objectMapper.createArrayNode();

        for (Minion card : cards) {
            arrayNode.add(card.createObjectNode());
        }

        return arrayNode;
    }

    public ArrayList<Minion> getCards() {
        return cards;
    }

    public void setCards(final ArrayList<Minion> cards) {
        this.cards = cards;
    }
}
