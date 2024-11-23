package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import main.cards.Minion;

import java.util.ArrayList;

public final class GameBoard {
    private final ArrayList<ArrayList<Minion>> gameBoard;
    public static final int ROWS = 4;
    public static final int COLS = 5;
    public static final int THREE = 3;

    public GameBoard() {
        gameBoard = new ArrayList(ROWS);
        for (int i = 0; i < ROWS; i++) {
            gameBoard.add(new ArrayList(COLS));
        }
    }

    /**
     * Determines whether a row is full or not
     * */
    public boolean rowIsFull(final int row) {
        return gameBoard.get(row).size() == COLS;
    }

    /**
     * Places a card on the game board, at two specific coordinates
     * */
    public void placeCardOnBoard(final Minion card, final int row) {
        gameBoard.get(row).add(card);
    }

    /**
     * Creates an object of type ArrayNode,
     * containing all the cards on the game board
     * */
    public ArrayNode createArrayNode() {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode table = objectMapper.createArrayNode();

        for (int row = 0; row < ROWS; row++) {
            ArrayNode rowNode = objectMapper.createArrayNode();
            for (int col = 0; col < gameBoard.get(row).size(); col++) {
                Minion card = gameBoard.get(row).get(col);
                ObjectNode cardNode = card.createObjectNode();
                rowNode.add(cardNode);
            }
            table.add(rowNode);
        }
        return table;
    }

    /**
     * Resets the hasAttacked (boolean) field for each card on the game board
     * */
    public void removeHasAttacked() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < gameBoard.get(row).size(); col++) {
                Minion card = gameBoard.get(row).get(col);
                if (card.getHasAttacked()) {
                    card.setHasAttacked(false);
                }
            }
        }
    }

    /**
     * Unfreezes all the cards on the game board
     * */
    public void removeFrozen() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < gameBoard.get(row).size(); col++) {
                Minion card = gameBoard.get(row).get(col);
                if (card.isFrozen()) {
                    card.setFrozen(false);
                }

                if (card.isFrozenNextRound()) {
                    card.setFrozen(true);
                    card.setFrozenNextRound(false);
                }
            }
        }
    }

    /**
     * Determines whether the attacker and the attacked card are enemies,
     * based on their rows
     *
     * @param rowAttacker row on which the attacker card is placed
     * @param rowAttacked row on which the attacked card is placed
     * */
    public boolean isEnemy(final int rowAttacker, final int rowAttacked) {
        if ((rowAttacker == 0 || rowAttacker == 1)
                && (rowAttacked == 0 || rowAttacked == 1)) {
            return false;
        }

        if ((rowAttacker == 2 || rowAttacker == THREE)
                && (rowAttacked == 2 || rowAttacked == THREE)) {
            return false;
        }

        return true;
    }

    /**
     * Returns the card situated at two specific coordinates on the game board
     * */
    public Minion getCardAt(final int row, final int col) {
        return gameBoard.get(row).get(col);
    }

    /**
     * Removes the card situated at two specific coordinates on the game board
     * */
    public void removeCardAt(final int row, final int col) {
        gameBoard.get(row).remove(col);
    }

    /**
     * Determines the player who owns a specific card,
     * based on the row on which it is placed
     * */
    public int getPlayerIdx(final int row) {
        if (row == 0 || row == 1) {
            return 2;
        }
        if (row == 2 || row == THREE) {
            return 1;
        }
        return -1;
    }

    /**
     * Determines whether the enemy has Tank cards on the front row
     *
     * @param attackerRow the row on which the attacker card is placed
     * */
    public boolean enemyHasTanks(final int attackerRow) {
        int playerIdx = getPlayerIdx(attackerRow);

        if (playerIdx == 1) {
            for (int col = 0; col < gameBoard.get(1).size(); col++) {
                if (getCardAt(1, col).getName().equals("Goliath")
                        || getCardAt(1, col).getName().equals("Warden")) {
                    return true;
                }
            }
            return false;
        }

        if (playerIdx == 2) {
            for (int col = 0; col < gameBoard.get(2).size(); col++) {
                if (getCardAt(2, col).getName().equals("Goliath")
                        || getCardAt(2, col).getName().equals("Warden")) {
                    return true;
                }
            }
            return false;
        }

        return false;
    }

    public ArrayList<ArrayList<Minion>> getGameBoard() {
        return gameBoard;
    }
}
