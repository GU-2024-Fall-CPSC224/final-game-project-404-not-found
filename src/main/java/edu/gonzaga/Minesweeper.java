package edu.gonzaga;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Random;

public class Minesweeper extends JFrame {
    private String playerName;
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
        Locale.setDefault(Locale.ENGLISH);

        setTitle("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Ask for player name
        askPlayerName();

        // Show the difficulty selection menu
        setupMenu();
    }

    private void askPlayerName() {
        playerName = JOptionPane.showInputDialog(
                this,
                "Please enter your name:",
                "Player Name",
                JOptionPane.QUESTION_MESSAGE
        );

        // Ensure the player enters a valid name
        if (playerName == null || playerName.trim().isEmpty()) {
            playerName = "Player"; // Default name if none is provided
        }

        // Show a greeting message
        JOptionPane.showMessageDialog(
                this,
                "Welcome, " + playerName + "! Let's play Minesweeper!",
                "Welcome",
                JOptionPane.INFORMATION_MESSAGE
        );
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
                playSound("src/main/resources/start_game.wav"); // Play sound after selecting Easy
                setupGame(8, 10);  // Easy
                break;
            case 1:
                playSound("src/main/resources/start_game.wav"); // Play sound after selecting Medium
                setupGame(12, 20); // Medium
                break;
            case 2:
                playSound("src/main/resources/start_game.wav"); // Play sound after selecting Hard
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
        playSound("src/main/resources/boom.wav");
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
    
        // Create the dialog
        JDialog gameOverDialog = new JDialog(this, "Game Over", true);
        gameOverDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        gameOverDialog.setLayout(new BorderLayout());
    
        // Add the image
        JLabel imageLabel = new JLabel();
        if (!won) {
            try {
                // Load and resize the image
                ImageIcon icon = new ImageIcon("src/main/resources/boom.png"); // Adjust path as needed
                Image scaledImage = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(scaledImage));
            } catch (Exception e) {
                System.err.println("Image loading failed: " + e.getMessage());
            }
        }
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gameOverDialog.add(imageLabel, BorderLayout.CENTER);
    
        // Add the message
        JPanel textPanel = new JPanel(new BorderLayout());
        JLabel messageLabel = new JLabel(won ? "You won in " + elapsedTime + " seconds!" : "Game Over!");
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 16));
        textPanel.add(messageLabel, BorderLayout.NORTH);
    
        JLabel playAgainLabel = new JLabel("Would you like to play again?");
        playAgainLabel.setHorizontalAlignment(SwingConstants.CENTER);
        playAgainLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        textPanel.add(playAgainLabel, BorderLayout.SOUTH);
    
        gameOverDialog.add(textPanel, BorderLayout.NORTH);
    
        // Add the buttons
        JPanel buttonPanel = new JPanel();
        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener(e -> {
            gameOverDialog.dispose();
            restartGame();
        });
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        buttonPanel.add(restartButton);
        buttonPanel.add(exitButton);
        gameOverDialog.add(buttonPanel, BorderLayout.SOUTH);
    
        // Show the dialog
        gameOverDialog.pack();
        gameOverDialog.setLocationRelativeTo(this);
        gameOverDialog.setVisible(true);
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

        private void playSound(String filePath) {
        try {
            File soundFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error playing sound: " + e.getMessage());
        }
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

