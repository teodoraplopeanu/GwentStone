package main;

import fileio.CardInput;
import main.cards.Hero;
import main.cards.heroes.EmpressThorina;
import main.cards.heroes.GeneralKocioraw;
import main.cards.heroes.KingMudface;
import main.cards.heroes.LordRoyce;

public final class Player {
    private Hand hand;
    private int mana;
    private Deck deck;
    private Hero hero;
    private int deckIdx;

    public Player() {
        hand = new Hand();
    }

    public Player(final Hand hand, final int mana, final Deck deck,
                  final Hero hero, final int deckIdx, final CardInput heroInput) {
        this.hand = hand;
        this.mana = mana;
        this.deck = deck;
        switch (hero.getName()) {
            case "Empress Thorina": {
                this.hero = new EmpressThorina(heroInput);
                break;
            }
            case "General Kocioraw": {
                this.hero = new GeneralKocioraw(heroInput);
                break;
            }
            case "King Mudface": {
                this.hero = new KingMudface(heroInput);
                break;
            }
            case "Lord Royce": {
                this.hero = new LordRoyce(heroInput);
                break;
            }
            default: {
                this.hero = hero;
            }
        }
        this.deckIdx = deckIdx;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(final Hand hand) {
        this.hand = hand;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(final int mana) {
        this.mana = mana;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(final Deck deck) {
        this.deck = deck;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(final Hero hero) {
        this.hero = hero;
    }

    public int getDeckIdx() {
        return deckIdx;
    }

    public void setDeckIdx(final int deckIdx) {
        this.deckIdx = deckIdx;
    }
}
