package main;

public final class Statistics {
    private int totalGamesPlayed;
    private int playerOneWins;
    private int playerTwoWins;

    public Statistics() {
        totalGamesPlayed = 0;
        playerOneWins = 0;
        playerTwoWins = 0;
    }

    public int getTotalGamesPlayed() {
        return totalGamesPlayed;
    }

    public void setTotalGamesPlayed(final int totalGamesPlayed) {
        this.totalGamesPlayed = totalGamesPlayed;
    }

    public int getPlayerOneWins() {
        return playerOneWins;
    }

    public void setPlayerOneWins(final int playerOneWins) {
        this.playerOneWins = playerOneWins;
    }

    public int getPlayerTwoWins() {
        return playerTwoWins;
    }

    public void setPlayerTwoWins(final int playerTwoWins) {
        this.playerTwoWins = playerTwoWins;
    }
}
