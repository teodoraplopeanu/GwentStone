package main.cards;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;
import fileio.Coordinates;

import java.util.ArrayList;

public class Card {
    protected int mana;
    protected int attackDamage;
    protected int health;
    protected String description;
    protected ArrayList<String> colors;
    protected String name;
    protected Coordinates coordinates;
    protected boolean isFrozen;
    protected boolean isFrozenNextRound;
    protected int row;
    protected boolean hasAttacked;

    public static final int HERO_HEALTH = 30;

    public Card() { }

    public Card(final CardInput cardInput) {
        mana = cardInput.getMana();
        attackDamage = cardInput.getAttackDamage();
        health = cardInput.getHealth();
        description = cardInput.getDescription();
        colors = cardInput.getColors();
        name = cardInput.getName();
        isFrozen = false;
        isFrozenNextRound = false;
        hasAttacked = false;
        coordinates = new Coordinates();

        if (isHero()) {
            health = HERO_HEALTH;
        }
        row = -1;

        if (name.equals("Goliath") || name.equals("Warden")
                || name.equals("The Ripper") || name.equals("Miraj")) {
            row = 1;
        }

        if (name.equals("Sentinel") || name.equals("Berserker")
                || name.equals("The Cursed One") || name.equals("Disciple")) {
            row = 0;
        }
    }

//    public Card(final CardInput cardInput, String name) {
//        switch (name) {
//            case "TheRipper":
//                TheRipper(cardInput);
//        }
//    }

    /**
     * Checks if a card represents a hero
     * */
    private boolean isHero() {
        if (name.equals("Lord Royce") || name.equals("Empress Thorina")
                || name.equals("King Mudface") || name.equals("General Kocioraw")) {
            return true;
        }
        return false;
    }

    /**
     * Checks if a card represents a tank
     * */
    public boolean isTank() {
        if (name.equals("Goliath") || name.equals("Warden")) {
            return true;
        }
        return false;
    }

    /**
     * Creates an object node from a Card,
     * in order to add it to an ArrayNode
     * */
    public ObjectNode createObjectNode() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("mana", mana);
        if (!isHero()) {
            objectNode.put("attackDamage", attackDamage);
        }

        if (!isHero()) {
            objectNode.put("health", health);
        }
        objectNode.put("description", description);

        ArrayNode colorsNode = objectMapper.createArrayNode();
        for (String color : colors) {
            colorsNode.add(color);
        }
        objectNode.set("colors", colorsNode);

        objectNode.put("name", name);

        if (isHero()) {
            objectNode.put("health", health);
        }

        return objectNode;
    }

    public final int getMana() {
        return mana;
    }

    public final void setMana(final int mana) {
        this.mana = mana;
    }

    public final int getAttackDamage() {
        return attackDamage;
    }

    public final void setAttackDamage(final int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public final int getHealth() {
        return health;
    }

    public final void setHealth(final int health) {
        this.health = health;
    }

    public final String getDescription() {
        return description;
    }

    public final void setDescription(final String description) {
        this.description = description;
    }

    public final ArrayList<String> getColors() {
        return colors;
    }

    public final void setColors(final ArrayList<String> colors) {
        this.colors = colors;
    }

    public final String getName() {
        return name;
    }

    public final void setName(final String name) {
        this.name = name;
    }

    public final Coordinates getCoordinates() {
        return coordinates;
    }

    public final void setCoordinates(final Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public final boolean isFrozen() {
        return isFrozen;
    }

    public final void setFrozen(final boolean frozen) {
        this.isFrozen = frozen;
    }

    public final int getRow() {
        return row;
    }

    public final void setRow(final int row) {
        this.row = row;
    }

    public final boolean getHasAttacked() {
        return hasAttacked;
    }

    public final void setHasAttacked(final boolean hasAttacked) {
        this.hasAttacked = hasAttacked;
    }

    public final boolean isFrozenNextRound() {
        return isFrozenNextRound;
    }

    public final void setFrozenNextRound(final boolean frozenNextRound) {
        isFrozenNextRound = frozenNextRound;
    }
}
