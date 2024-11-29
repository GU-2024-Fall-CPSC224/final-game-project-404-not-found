package edu.gonzaga;
/*
Liam Fitting
11/29/24
Board class. Note: used gpt for start algorithm generation. I could not figure out the populations and recursive revealing.
Added console priint out for debuggin. should refelect in view element of MVC Model as well.
 */

import java.util.ArrayList;
import java.util.Random;

public class Board {
    private Cell[][] cells;   // 2D array of Cell objects
    private int rows;         // Number of rows in the grid
    private int columns;      // Number of columns in the grid
    private int totalMines;   // Total number of mines on the board
    private boolean firstClick; // Indicates if the first click has been made

    /**
     * Constructor to initialize the board.
     * @param rows Number of rows in the grid.
     * @param columns Number of columns in the grid.
     * @param totalMines Total number of mines to place.
     */
    public Board(int rows, int columns, int totalMines) {
        this.rows = rows;
        this.columns = columns;
        this.totalMines = totalMines;
        this.firstClick = true; // First click has not yet been made
        initializeBoard();
    }

    /**
     * Initializes the board by creating a grid of cells.
     */
    private void initializeBoard() {
        cells = new Cell[rows][columns];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                cells[row][col] = new Cell(row, col);
            }
        }
    }

    /**
     * Places mines randomly on the board, avoiding a given initial position.
     * @param initialRow The row of the first clicked cell.
     * @param initialCol The column of the first clicked cell.
     */
    public void placeMines(int initialRow, int initialCol) {
        Random random = new Random();
        int minesPlaced = 0;

        while (minesPlaced < totalMines) {
            int row = random.nextInt(rows);
            int col = random.nextInt(columns);

            // Avoid placing a mine on the first clicked cell or an already mined cell
            if (!cells[row][col].isMine() && (row != initialRow || col != initialCol)) {
                cells[row][col].setMine(true);
                minesPlaced++;
            }
        }

        // Calculate adjacent mines for all cells
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                cells[row][col].calculateAdjacentMines(cells);
            }
        }
    }

    /**
     * Reveals a cell and handles game logic for revealing adjacent cells.
     * @param row The row of the cell to reveal.
     * @param col The column of the cell to reveal.
     */
    public void revealCell(int row, int col) {
        // If first click, place mines and ensure the first cell isn't a mine
        if (firstClick) {
            placeMines(row, col);
            firstClick = false;
        }

        Cell cell = cells[row][col];
        if (cell.isRevealed() || cell.isFlagged()) return; // Skip already revealed or flagged cells

        cell.reveal();

        // If the cell has no adjacent mines, reveal its neighbors recursively
        if (cell.getAdjacentMines() == 0) {
            for (Cell neighbor : getAdjacentCells(row, col)) {
                revealCell(neighbor.getRow(), neighbor.getColumn());
            }
        }
    }

    /**
     * Toggles the flag status of a cell.
     * @param row The row of the cell to flag/unflag.
     * @param col The column of the cell to flag/unflag.
     */
    public void flagCell(int row, int col) {
        cells[row][col].toggleFlag();
    }

    /**
     * Gets all adjacent cells for a given cell.
     * @param row The row of the cell.
     * @param col The column of the cell.
     * @return A list of adjacent cells.
     */
    public ArrayList<Cell> getAdjacentCells(int row, int col) {
        ArrayList<Cell> neighbors = new ArrayList<>();
        int[] rowOffsets = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] colOffsets = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < rowOffsets.length; i++) {
            int newRow = row + rowOffsets[i];
            int newCol = col + colOffsets[i];

            // Check if the neighbor is within bounds
            if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < columns) {
                neighbors.add(cells[newRow][newCol]);
            }
        }

        return neighbors;
    }

    /**
     * Prints the board to the console for debugging.
     */
    public void printBoard() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                Cell cell = cells[row][col];
                if (cell.isRevealed()) {
                    System.out.print(cell.isMine() ? "*" : cell.getAdjacentMines());
                } else if (cell.isFlagged()) {
                    System.out.print("F");
                } else {
                    System.out.print("-");
                }
            }
            System.out.println();
        }
    }
}

