package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.CardInput;
import fileio.DecksInput;
import main.cards.Card;
import main.cards.Minion;
import main.cards.superminions.Disciple;
import main.cards.superminions.Miraj;
import main.cards.superminions.TheCursedOne;
import main.cards.superminions.TheRipper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public final class Deck {
    private int nrCardsInDeck;
    private ArrayList<Minion> cards;

    public Deck(final DecksInput decksInput, final int deckIdx, final int shuffleSeed) {
        nrCardsInDeck = decksInput.getNrCardsInDeck();
        ArrayList<CardInput> cardInput = decksInput.getDecks().get(deckIdx);
        cards = new ArrayList<>(nrCardsInDeck);

        for (int i = 0; i < nrCardsInDeck; i++) {
            //cards.add(new Minion(cardInput.get(i)));
            Card card = new Card(cardInput.get(i));
            switch (card.getName()) {
                case "The Ripper": {
                    cards.add(new TheRipper(cardInput.get(i)));
                    break;
                }
                case "The Cursed One": {
                    cards.add(new TheCursedOne(cardInput.get(i)));
                    break;
                }
                case "Miraj": {
                    cards.add(new Miraj(cardInput.get(i)));
                    break;
                }
                case "Disciple": {
                    cards.add(new Disciple(cardInput.get(i)));
                    break;
                }
                default: {
                    cards.add(new Minion(cardInput.get(i)));
                    break;
                }
            }
        }

        Random random = new Random(shuffleSeed);
        Collections.shuffle(cards, random);
    }

    /**
     * Removes a card from the deck
     * (to put it in the player's hand)
     * */
    public void removeCard() {
        cards.removeFirst();
    }

    /**
     * Creates an ArrayNode from an object Deck
     * */
    public ArrayNode createArrayNode() {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode deck = objectMapper.createArrayNode();

        for (int i = 0; i < cards.size(); i++) {
            deck.add(cards.get(i).createObjectNode());
        }

        return deck;
    }

    @Override
    public String toString() {
        return cards.toString();
    }

    public int getNrCardsInDeck() {
        return nrCardsInDeck;
    }

    public void setNrCardsInDeck(final int nrCardsInDeck) {
        this.nrCardsInDeck = nrCardsInDeck;
    }

    public ArrayList<Minion> getCards() {
        return cards;
    }

    public void setCards(final ArrayList<Minion> cards) {
        this.cards = cards;
    }
}
