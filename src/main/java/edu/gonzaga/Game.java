package edu.gonzaga;
/*
liam
check win condition is not working I commented it out for now.
this class is not entirely needed just makes it easier. can compile into "board class" if needed. just move win condition.
 */
public class Game {
    private Board board;              // The Minesweeper board
    private Player player;            // The player
    private Timer timer;              // Timer to track game duration
    private DifficultyLevel difficulty; // Difficulty level of the game

    /**
     * Constructor to initialize the game with a player and difficulty level.
     * @param player The player playing the game.
     * @param difficulty The difficulty level of the game.
     */
    public Game(Player player, DifficultyLevel difficulty) {
        this.player = player;
        this.difficulty = difficulty;
        this.timer = new Timer();
        resetGame();
    }

    /**
     * Starts the game by initializing the board and starting the timer.
     */
    public void startGame() {
        System.out.println("Starting the game...");
        timer.start(); // Start the timer
        board = new Board(difficulty.getRows(), difficulty.getColumns(), difficulty.getMines());
    }

    /**
     * Ends the game and stops the timer.
     * @param won Indicates if the player has won the game.
     */
    public void endGame(boolean won) {
        timer.stop(); // Stop the timer
        if (won) {
            System.out.println("Congratulations! You won the game in " + timer.getElapsedTime() + " seconds.");
        } else {
            System.out.println("Game over! Better luck next time.");
        }
    }

    /**
     * Resets the game to its initial state.
     */
    public void resetGame() {
        System.out.println("Resetting the game...");
        board = null; // Clear the board
        timer.reset(); // Reset the timer
        startGame(); // Restart the game
    }

    /**
     * Quits the game and performs cleanup operations.
     */
    public void quitGame() {
        System.out.println("Quitting the game...");
        System.exit(0); // Exit the application
    }

    /**
     * Checks if the win condition is met.
     * @return True if the player has won, false otherwise.
     */
    /*
    public boolean checkWinCondition() {
        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getColumns(); col++) {
                Cell cell = board.getCells()[row][col];
                // All non-mine cells must be revealed, and no mines can be revealed
                if (!cell.isMine() && !cell.isRevealed()) {
                    return false;
                }
            }
        }
        return true;
    }
     */

    // Getters for the attributes
    public Board getBoard() {
        return board;
    }

    public Player getPlayer() {
        return player;
    }

    public Timer getTimer() {
        return timer;
    }

    public DifficultyLevel getDifficulty() {
        return difficulty;
    }
}

