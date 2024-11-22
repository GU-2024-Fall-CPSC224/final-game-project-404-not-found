package edu.gonzaga;

public class Player {
    private String name;       // The player's name
    private int score;         // The player's current score

    //not sure if I will need "gamesPlayed and gamesWon" but created them just in case.
    private int gamesPlayed;   // Total games played by the player
    private int gamesWon;      // Total games won by the player

    /**
     * Constructor to initialize a Player with a given name.
     * @param name The name of the player.
     */
    public Player(String name) {
        this.name = name;
        this.score = 0;        // Start with a score of 0
        this.gamesPlayed = 0;  // Initially, no games played
        this.gamesWon = 0;     // Initially, no games won
    }

    /**
     * Gets the player's name.
     * @return The player's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the player's name.
     * @param name The new name for the player.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the player's current score.
     * @return The player's score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Updates the player's score by adding points.
     * @param points The number of points to add.
     */
    public void updateScore(int points) {
        this.score += points;
    }

    /**
     * Gets the total number of games the player has played.
     * @return The number of games played.
     */
    public int getGamesPlayed() {
        return gamesPlayed;
    }

    /**
     * Increments the total number of games played by the player.
     */
    public void incrementGamesPlayed() {
        this.gamesPlayed++;
    }

    /**
     * Gets the total number of games the player has won.
     * @return The number of games won.
     */
    public int getGamesWon() {
        return gamesWon;
    }

    /**
     * Increments the total number of games won by the player.
     */
    public void incrementGamesWon() {
        this.gamesWon++;
    }

    /**
     * Resets the player's statistics (score, games played, and games won).
     */
    public void resetStats() {
        this.score = 0;
        this.gamesPlayed = 0;
        this.gamesWon = 0;
    }

    /**
     * Returns a string representation of the player's details.
     * @return A string containing the player's name, score, games played, and games won.
     */
    @Override
    public String toString() {
        return "Player: " + name + ", Score: " + score + ", Games Played: " + gamesPlayed + ", Games Won: " + gamesWon;
    }
}

