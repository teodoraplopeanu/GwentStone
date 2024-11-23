package main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import fileio.ActionsInput;
import fileio.Input;
import fileio.Coordinates;
import main.cards.Hero;
import main.commands.GetPlayerMana;
import main.commands.GetCardAtPosition;
import main.commands.GetCardsInHand;
import main.commands.GetPlayerHero;
import main.commands.GetPlayerDeck;

public final class Game {
    private int shuffleSeed;
    private Player player1;
    private Player player2;
    private GameBoard gameBoard;
    private Error error;
    private GameFlow gameFlow;
    private Statistics statistics;
    private GameBoardActions gameBoardActions;

    public static final int MAX_MANA = 10;
    public static final int CHANGE_PLAYER = 3;

    public Game() { }

    public Game(final Input inputData, final int gameIdx, final Statistics statistics) {
        gameBoard = new GameBoard();
        gameBoardActions = new GameBoardActions();
        error = new Error();
        this.statistics = statistics;
        int currentPlayer = inputData.getGames().get(gameIdx).getStartGame().getStartingPlayer();
        gameFlow = new GameFlow(currentPlayer, false, 0, 0);

        shuffleSeed = inputData.getGames().get(gameIdx).getStartGame().getShuffleSeed();

        int deckIdx1 = inputData.getGames().get(gameIdx).getStartGame().getPlayerOneDeckIdx();
        Deck deck1 = new Deck(inputData.getPlayerOneDecks(), deckIdx1, shuffleSeed);
        Hero hero1 = new Hero(inputData.getGames().get(gameIdx).getStartGame().getPlayerOneHero());
        player1 = new Player(new Hand(), 1, deck1, hero1, deckIdx1,
                inputData.getGames().get(gameIdx).getStartGame().getPlayerOneHero());

        int deckIdx2 = inputData.getGames().get(gameIdx).getStartGame().getPlayerTwoDeckIdx();
        Deck deck2 = new Deck(inputData.getPlayerTwoDecks(), deckIdx2, shuffleSeed);
        Hero hero2 = new Hero(inputData.getGames().get(gameIdx).getStartGame().getPlayerTwoHero());
        player2 = new Player(new Hand(), 1, deck2, hero2, deckIdx2,
                inputData.getGames().get(gameIdx).getStartGame().getPlayerTwoHero());

        player1.getHand().addCard(player1.getDeck().getCards().getFirst());
        player1.getDeck().removeCard();
        player2.getHand().addCard(player2.getDeck().getCards().getFirst());
        player2.getDeck().removeCard();
    }

    /**
     * Performs or redirects the command given through the ActionsInput
     *
     * @return an ObjectNode containing the output
     * */
    public ObjectNode handleAction(final ActionsInput actionsInput)
            throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode actionResult = objectMapper.createObjectNode();

        String command = actionsInput.getCommand();
        switch (command) {
            case "getPlayerDeck":
                GetPlayerDeck getPlayerDeck = new GetPlayerDeck();
                getPlayerDeck.executeCommand(actionsInput, this, actionResult);
                break;

            case "getPlayerHero":
                GetPlayerHero getPlayerHero = new GetPlayerHero();
                getPlayerHero.executeCommand(actionsInput, this, actionResult);
                break;

            case "getPlayerTurn":
                actionResult.put("command", "getPlayerTurn");
                actionResult.put("output", getCurrentPlayer());
                break;

            case "endPlayerTurn":
                return endPlayerTurn();

            case "getCardsInHand":
                GetCardsInHand getCardsInHand = new GetCardsInHand();
                getCardsInHand.executeCommand(actionsInput, this, actionResult);
                break;

            case "placeCard":
                return getGameBoardActions().placeCard(actionsInput, this);

            case "getPlayerMana":
                GetPlayerMana getPlayerMana = new GetPlayerMana();
                getPlayerMana.executeCommand(actionsInput, this, actionResult);
                break;

            case "getCardsOnTable":
                actionResult.put("command", "getCardsOnTable");
                ArrayNode table = gameBoard.createArrayNode();
                actionResult.put("output", table);
                break;

            case "cardUsesAttack":
                return getGameBoardActions().cardUsesAttack(actionsInput, this);

            case "getCardAtPosition":
                GetCardAtPosition getCardAtPosition = new GetCardAtPosition();
                getCardAtPosition.executeCommand(actionsInput, this, actionResult);
                break;

            case "cardUsesAbility":
                Coordinates attackerCoord = actionsInput.getCardAttacker();
                Coordinates attackedCoord = actionsInput.getCardAttacked();
                actionResult = getGameBoardActions().useAbility(attackerCoord, attackedCoord, this);
                break;

            case "useAttackHero":
                attackerCoord = actionsInput.getCardAttacker();
                actionResult = getGameBoardActions().attackHero(attackerCoord, this);
                break;

            case "useHeroAbility":
                int affectedRow = actionsInput.getAffectedRow();
                actionResult = getGameBoardActions().useHeroAbility(affectedRow, this);
                break;

            case "getFrozenCardsOnTable":
                ArrayNode cardArray = getGameBoardActions().getFrozenCards(this);
                actionResult.put("command", "getFrozenCardsOnTable");
                actionResult.put("output", cardArray);
                break;

            case "getTotalGamesPlayed":
                actionResult.put("command", "getTotalGamesPlayed");
                actionResult.put("output", statistics.getTotalGamesPlayed());
                break;

            case "getPlayerOneWins":
                actionResult.put("command", "getPlayerOneWins");
                actionResult.put("output", statistics.getPlayerOneWins());
                break;

            case "getPlayerTwoWins":
                actionResult.put("command", "getPlayerTwoWins");
                actionResult.put("output", statistics.getPlayerTwoWins());
                break;

            default:
                return null;
        }

        return actionResult;
    }

    /**
     * Performs the command endPlayerTurn
     *
     * @return an ObjectNode containing the output
     * */
    public ObjectNode endPlayerTurn() {
        setCurrentPlayer(CHANGE_PLAYER - getCurrentPlayer());
        setTurns(getTurns() + 1);

        // New round
        if (getTurns() % 2 == 0) {
            // Update mana
            player1.setMana(player1.getMana() + Math.min(MAX_MANA, getTurns() / 2 + 1));
            player2.setMana(player2.getMana() + Math.min(MAX_MANA, getTurns() / 2 + 1));

            // Update hands
            if (!player1.getDeck().getCards().isEmpty()) {
                player1.getHand().addCard(player1.getDeck().getCards().getFirst());
                player1.getDeck().removeCard();
            }
            if (!player2.getDeck().getCards().isEmpty()) {
                player2.getHand().addCard(player2.getDeck().getCards().getFirst());
                player2.getDeck().removeCard();
            }

            gameBoard.removeHasAttacked();
            gameBoard.removeFrozen();
            player1.getHero().setHasAttacked(false);
            player2.getHero().setHasAttacked(false);
        }
        return null;
    }

    /**
     * Determines the player based on its index
     *
     * @param playerIdx the index of the player
     * @return the reference to the object of the player
     * */
    public Player getPlayer(final int playerIdx) {
        if (playerIdx == 1) {
            return player1;
        }
        if (playerIdx == 2) {
            return player2;
        }
        return null;
    }

    public int getShuffleSeed() {
        return shuffleSeed; }

    public void setShuffleSeed(final int shuffleSeed) {
        this.shuffleSeed = shuffleSeed; }

    public GameFlow getGameFlow() {
        return gameFlow; }

    public boolean isGameOver() {
        return getGameFlow().isGameOver(); }

    /**
     * Sets the gameOver, through the gameFlow
     * */
    public void setGameOver(final boolean gameOver) {
        getGameFlow().setGameOver(gameOver); }

    /**
     * Gets the currentPlayer, through the gameFlow
     * */
    public int getCurrentPlayer() {
        return getGameFlow().getCurrentPlayer(); }

    /**
     * Sets the currentPlayer, through the gameFlow
     * */
    public void setCurrentPlayer(final int currentPlayer) {
        getGameFlow().setCurrentPlayer(currentPlayer); }

    public GameBoard getGameBoard() {
        return gameBoard; }

    /**
     * Gets the number of Turns, through the gameFlow
     * */
    public int getTurns() {
        return getGameFlow().getTurns(); }

    /**
     * Sets the number of Turns, through the gameFlow
     * */
    public void setTurns(final int turns) {
        getGameFlow().setTurns(turns); }

    public Error getError() {
        return error; }

    public void setError(final Error error) {
        this.error = error; }

    public int getWinner() {
        return getGameFlow().getWinner(); }

    /**
     * Sets the winner, through the gameFlow
     * */
    public void setWinner(final int winner) {
        getGameFlow().setWinner(winner); }

    public GameBoardActions getGameBoardActions() {
        return gameBoardActions; }
}
