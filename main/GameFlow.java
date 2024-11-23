package main;

public final class GameFlow {
    private int currentPlayer;
    private boolean gameOver;
    private int turns;
    private int winner;

    public GameFlow() { }

    public GameFlow(final int currentPlayer, final boolean gameOver,
                    final int turns, final int winner) {
        this.currentPlayer = currentPlayer;
        this.gameOver = gameOver;
        this.turns = turns;
        this.winner = winner;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(final int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(final boolean gameOver) {
        this.gameOver = gameOver;
    }

    public int getTurns() {
        return turns;
    }

    public void setTurns(final int turns) {
        this.turns = turns;
    }

    public int getWinner() {
        return winner;
    }

    public void setWinner(final int winner) {
        this.winner = winner;
    }
}
