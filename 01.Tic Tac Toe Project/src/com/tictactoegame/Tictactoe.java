package com.tictactoegame;

import java.awt.*;
import java.awt.event.*;

public class Tictactoe extends Frame implements ActionListener {

    Button b[] = new Button[9];
    Button b1; // New game
    Button bExit; // Exit button
    int k = 0, x = 8, y = 28;
    int a = 0; // Tracks player turns (0 = Player O, 1 = Player X)
    int l = 70; // For displaying messages
    boolean gameOver = false; // Flag to track game state

    Tictactoe() {
        try {
            setLayout(null);
            setVisible(true);
            setSize(800, 600);
            setLocation(400, 100);
            setBackground(Color.black);
            setForeground(Color.white);

            // Initialize Tic Tac Toe buttons
            for (int i = 1; i <= 3; i++) {
                for (int j = 1; j <= 3; j++) {
                    b[k] = new Button();
                    b[k].setSize(100, 100);
                    b[k].setLocation(x, y);
                    b[k].setFont(new Font("", Font.BOLD, 40));
                    add(b[k]);
                    b[k].addActionListener(this);
                    b[k].setBackground(new Color(51, 165, 255)); // Light Blue
                    k++;
                    x = x + 100;
                }
                x = 8;
                y = y + 100;
            }

            // New Game button
            b1 = new Button("New Game");
            b1.setSize(150, 40);
            b1.setLocation(500, 300);
            b1.setFont(new Font("", Font.BOLD, 20));
            b1.setForeground(Color.black);
            add(b1);
            b1.addActionListener(this);

            // Exit button
            bExit = new Button("Exit");
            bExit.setSize(150, 40);
            bExit.setLocation(500, 350);
            bExit.setFont(new Font("", Font.BOLD, 20));
            bExit.setForeground(Color.black);
            add(bExit);

            // ActionListener for the Exit button
            bExit.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        System.exit(0); // Exit the application
                    } catch (SecurityException se) {
                        System.err.println("Security exception occurred: " + se.getMessage());
                    }
                }
            });
        } catch (Exception ex) {
            System.err.println("Error initializing game: " + ex.getMessage());
        }
    }

    public void actionPerformed(ActionEvent e) {
        try {
            // Reset game when New Game button is clicked
            if (e.getSource() == b1) {
                resetGame();
            }

            if (gameOver) {
                return; // If game is over, ignore further moves
            }

            // Set labels for Tic Tac Toe buttons
            for (int i = 0; i < 9; i++) {
                if (e.getSource() == b[i]) {
                    if (!b[i].getLabel().isEmpty()) {
                        throw new InvalidMoveException("Cell is already occupied!");
                    }
                    if (a % 2 == 0) {
                        b[i].setLabel("O");
                    } else {
                        b[i].setLabel("X");
                    }
                    a++;
                    break; // Only allow one move per click
                }
            }

            // Check for win or tie
            if (checkForWin()) {
                displayWinner();
                gameOver = true; // Set the game state to over
            } else if (isBoardFull()) {
                displayTie();
                gameOver = true; // Set the game state to over
            }
        } catch (InvalidMoveException ime) {
            System.err.println("Invalid move: " + ime.getMessage());
        } catch (Exception ex) {
            System.err.println("Unexpected error: " + ex.getMessage());
        }
    }

    private void resetGame() {
        try {
            for (int i = 0; i <= 8; i++) {
                b[i].setLabel("");
                b[i].setEnabled(true); // Enable buttons for new game
            }
            a = 0; // Reset turn tracker
            l = 70; // Reset message label position
            gameOver = false; // Reset game state
        } catch (Exception ex) {
            System.err.println("Error resetting game: " + ex.getMessage());
        }
    }

    private boolean isBoardFull() {
        try {
            for (int i = 0; i < 9; i++) {
                if (b[i].getLabel().isEmpty()) {
                    return false;
                }
            }
            return true;
        } catch (Exception ex) {
            System.err.println("Error checking board state: " + ex.getMessage());
            return false;
        }
    }

    private void displayTie() {
        try {
            Label tieLabel = new Label("Game is Tie");
            tieLabel.setSize(150, 50);
            tieLabel.setLocation(320, l);
            tieLabel.setFont(new Font("", Font.BOLD, 20));
            add(tieLabel);
            l += 50;
            validate();
            repaint();
        } catch (Exception ex) {
            System.err.println("Error displaying tie message: " + ex.getMessage());
        }
    }

    private boolean checkForWin() {
        try {
            return checkLine(0, 1, 2) || checkLine(3, 4, 5) || checkLine(6, 7, 8) || // Rows
                   checkLine(0, 3, 6) || checkLine(1, 4, 7) || checkLine(2, 5, 8) || // Columns
                   checkLine(0, 4, 8) || checkLine(2, 4, 6);                          // Diagonals
        } catch (Exception ex) {
            System.err.println("Error checking win conditions: " + ex.getMessage());
            return false;
        }
    }

    private boolean checkLine(int i, int j, int k) {
        try {
            return !b[i].getLabel().isEmpty() && b[i].getLabel().equals(b[j].getLabel()) && b[j].getLabel().equals(b[k].getLabel());
        } catch (Exception ex) {
            System.err.println("Error checking line: " + ex.getMessage());
            return false;
        }
    }

    private void displayWinner() {
        try {
            String winner = (a % 2 == 0) ? "Player 2 wins" : "Player 1 wins";
            Label winnerLabel = new Label(winner);
            winnerLabel.setSize(150, 50);
            winnerLabel.setLocation(320, l);
            winnerLabel.setFont(new Font("", Font.BOLD, 20));
            add(winnerLabel);
            l += 50;
            validate();
            repaint();
        } catch (Exception ex) {
            System.err.println("Error displaying winner: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            new Tictactoe();
        } catch (Exception ex) {
            System.err.println("Error launching application: " + ex.getMessage());
        }
    }
}

// Custom exception for invalid moves
class InvalidMoveException extends Exception {
    public InvalidMoveException(String message) {
        super(message);
    }
}
