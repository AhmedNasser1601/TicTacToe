import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class GameUI extends JPanel {
    private final Game game; // Reference to the game logic
    private BufferedImage grid; // Grid image
    private BufferedImage x;   // X image
    private BufferedImage o;   // O image

    /**
     * Creates a GameUI given a Game object
     * @param game the game logic object
     */
    public GameUI(Game game) {
        this.game = game;

        // Load images from resources files
        try {
            ClassLoader classLoader = GameUI.class.getClassLoader();
            grid = ImageIO.read(Objects.requireNonNull(classLoader.getResourceAsStream("grid.png")));
            x = ImageIO.read(Objects.requireNonNull(classLoader.getResourceAsStream("x.png")));
            o = ImageIO.read(Objects.requireNonNull(classLoader.getResourceAsStream("o.png")));
        } catch (IOException ex) {
            System.out.println("Failed to load images");
        }

        // Create new game buttons
        JButton newGameButton = new JButton("New Single Player Game");
        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        newGameButton.addActionListener(e -> newGameButtonPressed(false));

        JButton new2PlayerGameButton = new JButton("New 2 Player Game");
        new2PlayerGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        new2PlayerGameButton.addActionListener(e -> newGameButtonPressed(true));

        // Add buttons to the panel for display
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(newGameButton);
        this.add(new2PlayerGameButton);

        // Set panel dimensions
        final int WIDTH = 620;
        final int HEIGHT = 650;
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        // Control what happens when mouse clicks occur
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int cellSizeX = 200;
                int cellSizeY = 200;
                int offsetX = 10;
                int offsetY = 40;

                int i = (e.getX() - offsetX) / cellSizeX;
                int j = (e.getY() - offsetY) / cellSizeY;

                if (i < 0 || i >= 3 || j < 0 || j >= 3) return;

                if (!game.playAt(i, j)) {
                    Toolkit.getDefaultToolkit().beep(); // Invalid spot feedback
                    return;
                }

                repaint();
                if (game.doChecks()) return;

                if (!game.isTwoPlayer()) {
                    Timer timer = new Timer(500, e2 -> {
                        game.nextTurn();
                        repaint();
                        game.doChecks();
                    });
                    timer.setRepeats(false);
                    timer.start();
                } else {
                    game.nextTurn();
                    repaint();
                    game.doChecks();
                }
            }
        });

        // Create and display the frame
        JFrame frame = new JFrame();
        frame.add(this);
        frame.pack();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // Draw grid
        if (grid != null) {
            g.drawImage(grid, 10, 40, null);
        }

        // Draw X and O images
        for (int i = 0; i < 3; i++) {
            for (int j = 2; j >= 0; j--) {
                if (game.gridAt(i, j) == 'x' && x != null) {
                    g.drawImage(x, 200 * i + 40, 200 * j + 70, null);
                } else if (game.gridAt(i, j) == 'o' && o != null) {
                    g.drawImage(o, 200 * i + 40, 200 * j + 70, null);
                }
            }
        }

        // Highlight current player's turn
        g.setColor(Color.BLACK);
        g.drawString("Current Turn: " + ((game.getTurn() == 'x') ? "X" : "O"), 10, 20);

        // Display scores
        g.drawString("X Wins: " + game.getXWins(), 10, 35);
        g.drawString("O Wins: " + game.getOWins(), 10, 50);
        g.drawString("Ties: " + game.getTies(), 10, 65);
    }

    /**
     * Displays a game-over message dialog
     * @param message the message to display
     */
    public void gameOver(String message) {
        JOptionPane.showMessageDialog(null, message, "Game Over!", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Starts a new game
     * @param twoPlayer true if starting a 2-player game, false for single-player
     */
    private void newGameButtonPressed(boolean twoPlayer) {
        game.newGame(twoPlayer);
        this.repaint();
    }
}