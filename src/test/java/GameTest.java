import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameTest {
    private Game game;

    @Before
    public void setUp() {
        game = new Game();
    }

    /**
     * Notes:
     * - Horizontal refers to rows in the grid.
     * - Vertical refers to columns in the grid.
     * - Diagonal refers to the two main diagonals in the grid.
     */

    // X Wins - Horizontal Cases
    @Test
    public void horizontal_x_win_case1() {
        char[][] grid = {   {'-', 'o', 'x'},
                {'x', 'x', 'x'},
                {'-', 'o', 'o'}};
        assertEquals("X horizontal win fail", "X wins", game.checkGameWinner(grid));
    }

    @Test
    public void horizontal_x_win_case2() {
        char[][] grid = {   {'x', '-', '-'},
                {'x', 'x', 'o'},
                {'x', 'o', 'o'}};
        assertEquals("X horizontal win fail", "X wins", game.checkGameWinner(grid));
    }

    @Test
    public void horizontal_x_win_case3() {
        char[][] grid = {   {'-', 'x', '-'},
                {'o', 'x', 'o'},
                {'x', 'x', 'o'}};
        assertEquals("X horizontal win fail", "X wins", game.checkGameWinner(grid));
    }

    // O Wins - Horizontal Cases
    @Test
    public void horizontal_o_win_case1() {
        char[][] grid = {   {'-', 'o', 'x'},
                {'x', 'o', '-'},
                {'-', 'o', 'x'}};
        assertEquals("O horizontal win fail", "O wins", game.checkGameWinner(grid));
    }

    @Test
    public void horizontal_o_win_case2() {
        char[][] grid = {   {'x', 'x', 'o'},
                {'-', 'x', 'o'},
                {'x', 'o', 'o'}};
        assertEquals("O horizontal win fail", "O wins", game.checkGameWinner(grid));
    }

    @Test
    public void horizontal_o_win_case3() {
        char[][] grid = {   {'o', 'x', '-'},
                {'o', '-', 'x'},
                {'o', 'x', '-'}};
        assertEquals("O horizontal win fail", "O wins", game.checkGameWinner(grid));
    }

    // X Wins - Vertical Cases
    @Test
    public void vertical_x_win_case1() {
        char[][] grid = {   {'-', 'o', 'x'},
                {'x', 'x', 'x'},
                {'-', 'o', 'o'}};
        assertEquals("X vertical win fail", "X wins", game.checkGameWinner(grid));
    }

    @Test
    public void vertical_x_win_case2() {
        char[][] grid = {   {'x', 'o', 'o'},
                {'-', '-', 'o'},
                {'x', 'x', 'x'}};
        assertEquals("X vertical win fail", "X wins", game.checkGameWinner(grid));
    }

    @Test
    public void vertical_x_win_case3() {
        char[][] grid = {   {'x', 'x', 'x'},
                {'o', '-', 'o'},
                {'x', '-', 'o'}};
        assertEquals("X vertical win fail", "X wins", game.checkGameWinner(grid));
    }

    // O Wins - Vertical Cases
    @Test
    public void vertical_o_win_case1() {
        char[][] grid = {   {'o', 'o', 'o'},
                {'x', 'x', '-'},
                {'x', 'o', 'x'}};
        assertEquals("O vertical win fail", "O wins", game.checkGameWinner(grid));
    }

    @Test
    public void vertical_o_win_case2() {
        char[][] grid = {   {'x', 'x', 'o'},
                {'o', 'o', 'o'},
                {'x', 'o', 'x'}};
        assertEquals("O vertical win fail", "O wins", game.checkGameWinner(grid));
    }

    @Test
    public void vertical_o_win_case3() {
        char[][] grid = {   {'x', 'x', '-'},
                {'x', '-', 'x'},
                {'o', 'o', 'o'}};
        assertEquals("O vertical win fail", "O wins", game.checkGameWinner(grid));
    }

    // X Wins - Diagonal Cases
    @Test
    public void diagonal_x_win_case1() {
        char[][] grid = {   {'x', 'o', 'o'},
                {'-', 'x', 'o'},
                {'x', '-', 'x'}};
        assertEquals("X diagonal win fail", "X wins", game.checkGameWinner(grid));
    }

    @Test
    public void diagonal_x_win_case2() {
        char[][] grid = {   {'x', '-', 'x'},
                {'-', 'x', 'o'},
                {'x', 'o', 'o'}};
        assertEquals("X diagonal win fail", "X wins", game.checkGameWinner(grid));
    }

    // O Wins - Diagonal Cases
    @Test
    public void diagonal_o_win_case1() {
        char[][] grid = {   {'x', '-', 'o'},
                {'x', 'o', 'x'},
                {'o', 'o', 'x'}};
        assertEquals("O diagonal win fail", "O wins", game.checkGameWinner(grid));
    }

    @Test
    public void diagonal_o_win_case2() {
        char[][] grid = {   {'o', 'x', '-'},
                {'x', 'o', 'x'},
                {'x', 'o', 'o'}};
        assertEquals("O diagonal win fail", "O wins", game.checkGameWinner(grid));
    }

    // Tie Cases
    @Test
    public void tie_game_case1() {
        char[][] grid = {   {'o', 'x', 'o'},
                {'x', 'o', 'x'},
                {'x', 'o', 'x'}};
        assertEquals("Tie game failed", "Tie", game.checkGameWinner(grid));
    }

    @Test
    public void tie_game_case2() {
        char[][] grid = {   {'o', 'x', 'o'},
                {'o', 'x', 'x'},
                {'x', 'o', 'x'}};
        assertEquals("Tie game failed", "Tie", game.checkGameWinner(grid));
    }

    @Test
    public void tie_game_case3() {
        char[][] grid = {   {'x', 'o', 'o'},
                {'o', 'x', 'x'},
                {'x', 'o', 'o'}};
        assertEquals("Tie game failed", "Tie", game.checkGameWinner(grid));
    }

    // No Winner Cases
    @Test
    public void no_winner_case1() {
        char[][] grid = {   {'o', 'x', '-'},
                {'o', 'x', 'x'},
                {'x', 'o', 'x'}};
        assertEquals("Game not supposed to end", "None", game.checkGameWinner(grid));
    }

    @Test
    public void no_winner_case2() {
        char[][] grid = {   {'o', 'x', '-'},
                {'o', 'x', 'x'},
                {'-', 'o', 'x'}};
        assertEquals("Game not supposed to end", "None", game.checkGameWinner(grid));
    }

    @Test
    public void no_winner_case3() {
        char[][] grid = {   {'-', '-', '-'},
                {'-', 'x', 'o'},
                {'-', '-', 'x'}};
        assertEquals("Game not supposed to end", "None", game.checkGameWinner(grid));
    }

    @Test
    public void no_winner_case4() {
        char[][] grid = {   {'-', '-', '-'},
                {'-', 'x', '-'},
                {'-', '-', '-'}};
        assertEquals("Game not supposed to end", "None", game.checkGameWinner(grid));
    }
}