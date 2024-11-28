package edu.gonzaga;

public class Cell {
    private int row;           // Row index of the cell
    private int column;        // Column index of the cell
    private boolean isMine;    // True if the cell contains a mine
    private boolean isRevealed; // True if the cell has been revealed
    private boolean isFlagged; // True if the cell is flagged
    private int adjacentMines; // Number of mines in adjacent cells

    /**
     * Constructor to create a Cell.
     * @param row The row index of the cell.
     * @param column The column index of the cell.
     */
    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
        this.isMine = false;       // Default: no mine
        this.isRevealed = false;   // Default: not revealed
        this.isFlagged = false;    // Default: not flagged
        this.adjacentMines = 0;    // Default: no adjacent mines
    }

    // Getters and setters for each attribute
    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean isMine) {
        this.isMine = isMine;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean isRevealed) {
        this.isRevealed = isRevealed;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setFlagged(boolean isFlagged) {
        this.isFlagged = isFlagged;
    }

    public int getAdjacentMines() {
        return adjacentMines;
    }

    public void setAdjacentMines(int adjacentMines) {
        this.adjacentMines = adjacentMines;
    }

    /**
     * Reveals the cell. Sets isRevealed to true.
     */
    public void reveal() {
        if (!isRevealed && !isFlagged) {
            this.isRevealed = true;
        }
    }

    /**
     * Toggles the flagged state of the cell.
     */
    public void toggleFlag() {
        if (!isRevealed) {
            this.isFlagged = !isFlagged;
        }
    }

    /**
     * Calculates the number of adjacent mines.
     * @param grid The grid of cells.
     */
    public void calculateAdjacentMines(Cell[][] grid) {
        int count = 0;
        int[] rowOffsets = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] colOffsets = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < rowOffsets.length; i++) {
            int newRow = this.row + rowOffsets[i];
            int newCol = this.column + colOffsets[i];

            // Check if the new position is within the grid
            if (newRow >= 0 && newRow < grid.length && newCol >= 0 && newCol < grid[0].length) {
                if (grid[newRow][newCol].isMine()) {
                    count++;
                }
            }
        }

        this.adjacentMines = count;
    }

    /**
     * Returns a string representation of the cell for debugging.
     * @return A string containing cell details.
     */
    @Override
    public String toString() {
        return "Cell(" + row + ", " + column + ") [Mine: " + isMine + ", Revealed: " + isRevealed +
                ", Flagged: " + isFlagged + ", Adjacent Mines: " + adjacentMines + "]";
    }
}

