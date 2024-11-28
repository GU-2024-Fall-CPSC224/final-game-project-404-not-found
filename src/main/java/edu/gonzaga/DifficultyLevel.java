package edu.gonzaga;

public class DifficultyLevel {
    private String name;  // Name of the difficulty level
    private int rows;     // Number of rows in the grid
    private int columns;  // Number of columns in the grid
    private int mines;    // Number of mines on the grid

    /**
     * Constructor to create a DifficultyLevel.
     * @param name The name of the difficulty level (e.g., "Easy", "Medium", "Hard").
     * @param rows The number of rows in the grid.
     * @param columns The number of columns in the grid.
     * @param mines The number of mines on the grid.
     */
    public DifficultyLevel(String name, int rows, int columns, int mines) {
        this.name = name;
        this.rows = rows;
        this.columns = columns;
        this.mines = mines;
    }

    /**
     * Gets the name of the difficulty level.
     * @return The name of the difficulty level.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the difficulty level.
     * @param name The new name for the difficulty level.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the number of rows in the grid.
     * @return The number of rows.
     */
    public int getRows() {
        return rows;
    }

    /**
     * Sets the number of rows in the grid.
     * @param rows The new number of rows.
     */
    public void setRows(int rows) {
        this.rows = rows;
    }

    /**
     * Gets the number of columns in the grid.
     * @return The number of columns.
     */
    public int getColumns() {
        return columns;
    }

    /**
     * Sets the number of columns in the grid.
     * @param columns The new number of columns.
     */
    public void setColumns(int columns) {
        this.columns = columns;
    }

    /**
     * Gets the number of mines on the grid.
     * @return The number of mines.
     */
    public int getMines() {
        return mines;
    }

    /**
     * Sets the number of mines on the grid.
     * @param mines The new number of mines.
     */
    public void setMines(int mines) {
        this.mines = mines;
    }

    /**
     * Returns a string representation of the difficulty level details.
     * @return A string containing the name, rows, columns, and mines.
     */
    @Override
    public String toString() {
        return "Difficulty Level: " + name + ", Rows: " + rows + ", Columns: " + columns + ", Mines: " + mines;
    }
}

