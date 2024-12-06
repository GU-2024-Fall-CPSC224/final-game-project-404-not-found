package edu.gonzaga;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Minesweeper extends JFrame {
    private int gridSize;
    private int numMines;
    private JButton[][] buttons;
    private boolean[][] mines;
    private boolean[][] revealed;
    private boolean[][] flagged;
    private boolean gameStarted = false;
    private javax.swing.Timer timer;
    private int elapsedTime = 0;
    private JLabel timerLabel;

    public Minesweeper() {
        setTitle("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setupMenu();
    }

    private void setupMenu() {
        String[] options = {"Easy (8x8, 10 mines)", "Medium (12x12, 20 mines)", "Hard (16x16, 40 mines)"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "Select Difficulty",
                "Minesweeper",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        switch (choice) {
            case 0:
                setupGame(8, 10);  // Easy
                break;
            case 1:
                setupGame(12, 20); // Medium
                break;
            case 2:
                setupGame(16, 40); // Hard
                break;
            default:
                System.exit(0);  // Exit if closed
        }
    }

    private void setupGame(int gridSize, int numMines) {
        this.gridSize = gridSize;
        this.numMines = numMines;
    
        buttons = new JButton[gridSize][gridSize];
        mines = new boolean[gridSize][gridSize];
        revealed = new boolean[gridSize][gridSize];
        flagged = new boolean[gridSize][gridSize];
    
        setLayout(new BorderLayout());
        timerLabel = new JLabel("Time: 0 seconds");
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center align the timer label
        add(timerLabel, BorderLayout.NORTH);
    
        JPanel gridPanel = new JPanel(new GridLayout(gridSize, gridSize));
        gridPanel.setPreferredSize(new Dimension(gridSize * 40, gridSize * 40)); // Set preferred size for grid panel
    
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                buttons[row][col] = new JButton();
                buttons[row][col].setFont(new Font("Arial", Font.BOLD, 12)); // Adjust font size
                buttons[row][col].setHorizontalAlignment(SwingConstants.CENTER); // Center-align text horizontally
                buttons[row][col].setVerticalAlignment(SwingConstants.CENTER);   // Center-align text vertically     
                buttons[row][col].setMargin(new Insets(0, 0, 0, 0));       
                buttons[row][col].addActionListener(new CellClickListener(row, col));
                buttons[row][col].setComponentPopupMenu(createFlagMenu(row, col));
                gridPanel.add(buttons[row][col]);
            }
        }

        add(gridPanel, BorderLayout.CENTER);
    
        pack(); 
        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }

    private JPopupMenu createFlagMenu(int row, int col) {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem flagOption = new JMenuItem("Toggle Flag");
        flagOption.addActionListener(e -> toggleFlag(row, col));
        popupMenu.add(flagOption);
        return popupMenu;
    }

    private void toggleFlag(int row, int col) {
        if (!revealed[row][col]) {
            flagged[row][col] = !flagged[row][col];
            buttons[row][col].setText(flagged[row][col] ? "F" : "");
            buttons[row][col].setBackground(flagged[row][col] ? Color.YELLOW : null);
        }
    }

    private void startGame() {
        if (!gameStarted) {
            placeMines();
            startTimer();
            gameStarted = true;
        }
    }

    private void placeMines() {
        Random random = new Random();
        int placedMines = 0;
    
        while (placedMines < numMines) {
            int row = random.nextInt(gridSize);
            int col = random.nextInt(gridSize);
            if (!mines[row][col]) {
                mines[row][col] = true;
                placedMines++;
                System.out.println("Mine placed at: (" + row + ", " + col + ")"); 
            }
        }
    }

    private void revealCell(int row, int col) {
    if (row < 0 || row >= gridSize || col < 0 || col >= gridSize || revealed[row][col] || flagged[row][col]) {
        return;
    }

    revealed[row][col] = true;
    int adjacentMines = countAdjacentMines(row, col);

    if (mines[row][col]) {
        buttons[row][col].setText("X");
        buttons[row][col].setBackground(Color.RED);
        System.out.println("Mine clicked at (" + row + ", " + col + ")");
        gameOver(false);
    } else {
        if (adjacentMines > 0) {
            buttons[row][col].setText(String.valueOf(adjacentMines));
            buttons[row][col].setEnabled(false);
            buttons[row][col].setForeground(Color.BLACK); // Ensure text is visible
            buttons[row][col].setBackground(null); // Clear background
            System.out.println("Revealed cell at (" + row + ", " + col + ") with " + adjacentMines + " adjacent mines");
        } else {
            buttons[row][col].setText("");
            buttons[row][col].setEnabled(false);
            System.out.println("Revealed empty cell at (" + row + ", " + col + ")");

            // Recursively reveal surrounding cells
            for (int dr = -1; dr <= 1; dr++) {
                for (int dc = -1; dc <= 1; dc++) {
                    revealCell(row + dr, col + dc);
                }
            }
        }
    }

    if (checkWin()) {
        gameOver(true);
    }
}

    private int countAdjacentMines(int row, int col) {
        int count = 0;
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                int newRow = row + dr;
                int newCol = col + dc;
                if (newRow >= 0 && newRow < gridSize && newCol >= 0 && newCol < gridSize && mines[newRow][newCol]) {
                    count++;
                }
            }
        }
        System.out.println("Cell (" + row + ", " + col + ") has " + count + " adjacent mines."); // Debugging
        return count;
    }

    private void startTimer() {
        timer = new Timer(1000, e -> {
            elapsedTime++;
            timerLabel.setText("Time: " + elapsedTime + " seconds");
        });
        timer.start();
    }

    private boolean checkWin() {
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                if (!mines[row][col] && !revealed[row][col]) {
                    return false;
                }
            }
        }
        return true;
    }

    private void gameOver(boolean won) {
        timer.stop();
        String message = won ? "You won in " + elapsedTime + " seconds!" : "Game Over!";
        int choice = JOptionPane.showOptionDialog(
                this,
                message + "\nWould you like to play again?",
                "Game Over",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new String[]{"Restart", "Exit"},
                "Restart"
        );

        if (choice == JOptionPane.YES_OPTION) {
            restartGame();
        } else {
            System.exit(0);
        }
    }

    private void restartGame() {
        getContentPane().removeAll(); // Clear the existing content
        gameStarted = false;          // Reset the game started flag
        elapsedTime = 0;              // Reset the timer
        if (timer != null) {
            timer.stop();             // Stop the timer if it's running
        }
        setupMenu();                  // Reinitialize the game with a new difficulty
        revalidate();                 // Refresh the UI
        repaint();                    // Redraw the window
    }

    private class CellClickListener implements ActionListener {
        private final int row, col;

        public CellClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            startGame();
            revealCell(row, col);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Minesweeper::new);
    }
}

