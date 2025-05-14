import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game {
    private char turn; // who's turn is it, 'x' or 'o'? x always starts
    private boolean twoPlayer; // true if this is a 2-player game, false if AI playing
    private char[][] grid; // a 2D array of chars representing the game grid
    private int freeSpots; // counts the number of empty spots remaining on the board (starts from 9 and counts down)
    private static GameUI gui;

    private int xWins = 0; // Track X wins
    private int oWins = 0; // Track O wins
    private int ties = 0;  // Track ties

    /**
     * Create a new single-player game
     */
    public Game() {
        newGame(false);
    }

    /**
     * Create a new game by clearing the 2D grid and restarting the freeSpots counter and setting the turn to x
     * @param twoPlayer: true if this is a 2-player game, false if playing against the computer
     */
    public void newGame(boolean twoPlayer) {
        this.twoPlayer = twoPlayer;

        // Initialize all chars in 3x3 game grid to '-'
        grid = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j] = '-';
            }
        }
        freeSpots = 9; // Reset free spots
        turn = 'x';   // X always starts
    }

    /**
     * Gets the char value at that particular position in the grid array
     * @param i the x index of the 2D array grid
     * @param j the y index of the 2D array grid
     * @return the char value at the position (i,j):
     *          'x' if x has played here
     *          'o' if o has played here
     *          '-' if no one has played here
     *          '!' if i or j is out of bounds
     */
    public char gridAt(int i, int j) {
        if (i >= 3 || j >= 3 || i < 0 || j < 0) return '!';
        return grid[i][j];
    }

    /**
     * Places current player's char at position (i,j)
     * Uses the variable turn to decide what char to use
     * @param i the x index of the 2D array grid
     * @param j the y index of the 2D array grid
     * @return boolean: true if play was successful, false if invalid play
     */
    public boolean playAt(int i, int j) {
        if (i >= 3 || j >= 3 || i < 0 || j < 0) return false;
        if (grid[i][j] != '-') return false;
        grid[i][j] = turn;
        freeSpots--;
        return true;
    }

    /**
     * Override
     * @return string format for 2D array values
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (char[] row : grid) {
            sb.append(Arrays.toString(row)).append("\n");
        }
        return sb.toString();
    }

    /**
     * Performs the winner check and displays a message if the game is over
     * @return true if the game is over to start a new game
     */
    public boolean doChecks() {
        String winnerMessage = checkGameWinner(grid);
        if (!winnerMessage.equals("None")) {
            updateScores(winnerMessage);
            gui.gameOver(winnerMessage);
            newGame(twoPlayer); // Restart with same mode
            return true;
        }
        return false;
    }

    /**
     * Allows the computer to play in a single-player game or switch turns for a 2-player game
     */
    public void nextTurn() {
        if (freeSpots == 0) return;

        if (!twoPlayer) {
            // Generate a list of available positions first
            List<int[]> available = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (grid[i][j] == '-') {
                        available.add(new int[]{i, j});
                    }
                }
            }
            if (!available.isEmpty()) {
                int[] move = available.get((int) (Math.random() * available.size()));
                grid[move[0]][move[1]] = 'o';
                freeSpots--;
            }
        } else {
            // Switch turns
            turn = (turn == 'x') ? 'o' : 'x';
        }
    }

    /**
     * Checks if the game has ended either because a player has won, or if the game has ended as a tie.
     * If the game hasn't ended, the return string has to be "None",
     * If the game ends as a tie, the return string has to be "Tie",
     * If the game ends because there's a winner, it should return "X wins" or "O wins" accordingly
     * @param grid 2D array of characters representing the game board
     * @return String indicating the outcome of the game: "X wins" or "O wins" or "Tie" or "None"
     */
    public String checkGameWinner(char[][] grid) {
        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            // Rows
            if (grid[i][0] != '-' && grid[i][0] == grid[i][1] && grid[i][1] == grid[i][2]) {
                return (grid[i][0] == 'x') ? "X wins" : "O wins";
            }
            // Columns
            if (grid[0][i] != '-' && grid[0][i] == grid[1][i] && grid[1][i] == grid[2][i]) {
                return (grid[0][i] == 'x') ? "X wins" : "O wins";
            }
        }

        // Diagonals
        if (grid[0][0] != '-' && grid[0][0] == grid[1][1] && grid[1][1] == grid[2][2]) {
            return (grid[0][0] == 'x') ? "X wins" : "O wins";
        }
        if (grid[0][2] != '-' && grid[0][2] == grid[1][1] && grid[1][1] == grid[2][0]) {
            return (grid[0][2] == 'x') ? "X wins" : "O wins";
        }

        // Check if the board is full (tie condition)
        boolean isFull = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[i][j] == '-') {
                    isFull = false;
                    break;
                }
            }
            if (!isFull) break;
        }
        if (isFull) {
            return "Tie";
        }

        return "None";
    }

    /**
     * Update scores based on the game result
     * @param result "X wins", "O wins", or "Tie"
     */
    private void updateScores(String result) {
        switch (result) {
            case "X wins":
                xWins++;
                break;
            case "O wins":
                oWins++;
                break;
            case "Tie":
                ties++;
                break;
        }
    }

    /**
     * Getters for scores
     */
    public int getXWins() { return xWins; }
    public int getOWins() { return oWins; }
    public int getTies() { return ties; }

    /**
     * Getter for the current player's turn
     * @return the current player ('x' or 'o')
     */
    public char getTurn() {
        return turn;
    }

    /**
     * Getter for the twoPlayer mode
     * @return true if the game is in two-player mode, false otherwise
     */
    public boolean isTwoPlayer() {
        return twoPlayer;
    }

    /**
     * Main function
     * @param args command line arguments
     */
    public static void main(String[] args) {
        gui = new GameUI(new Game());
    }
}