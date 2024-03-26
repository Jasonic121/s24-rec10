package game;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class GameState {

    private final Cell[] cells;
    private final Player winner;
    private final Player currentPlayer;
    private final Game game;
    private final Player nextTurn;
    List<GameState> history = new ArrayList<>();

    private GameState(Cell[] cells, Game game) {
        this.cells = cells;
        this.game = game;
        this.winner = game.getWinner();
        this.currentPlayer = game.getPlayer();
        this.nextTurn = game.getNextPlayer();
        this.history = history;
    }

    /**
     * Returns the history of game states leading up to the current state.
     *
     * @return a list of game states representing the history
     */
    public List<GameState> getAllHistoryState() {
        GameState state = this;
        while (state != null) {
            history.add(state);
            state = state.getPreviousState();
        }
        return history;
    }

    /**
     * get the previous state of a game.
     */
    private GameState getPreviousState() {
        if (history.size() > 0)
            return history.get(history.size() - 1);
        else
            return null;
    }

    public static GameState forGame(Game game) {
        Cell[] cells = getCells(game);
        return new GameState(cells, game);
    }

    public Cell[] getCells() {
        return this.cells;
    }
    public String getWinner() {
        if (this.winner == null)
            return "null";
        else
            return this.winner.toString();
    }

    public String getNextPlayer() {
        if (this.nextTurn == null)
            return "null";
        else
            return this.nextTurn.toString();
    }

    public String getCurrentPlayer() {
        if (this.currentPlayer == null)
            return "null";
        else
            return this.currentPlayer.toString();
    }

    public String getHistory() {
        return history.stream().map(GameState::toString).collect(Collectors.joining(","));
    }
/**
     * toString() of GameState will return the string representing
     * the GameState in JSON format.
     */
    @Override
    public String toString() {
        return String.format(
            "{ \"cells\": %s, \"winner\": \"%s\", \"currentPlayer\": \"%s\", \"nextTurn\": \"%s\", \"history\": %s}",
            Arrays.toString(this.cells), this.getWinner(), this.getCurrentPlayer(), this.getNextPlayer(), this.getHistory()
        );
    }


    private static Cell[] getCells(Game game) {
        Cell cells[] = new Cell[9];
        Board board = game.getBoard();
        for (int x = 0; x <= 2; x++) {
            for (int y = 0; y <= 2; y++) {
                String text = "";
                boolean playable = false;
                Player player = board.getCell(x, y);
                if (player == Player.PLAYER0)
                    text = "X";
                else if (player == Player.PLAYER1)
                    text = "O";
                else if (player == null) {
                    playable = true;
                }
                cells[3 * y + x] = new Cell(x, y, text, playable);
            }
        }
        return cells;
    }
}

class Cell {
    private final int x;
    private final int y;
    private final String text;
    private final boolean playable;

    Cell(int x, int y, String text, boolean playable) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.playable = playable;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getText() {
        return this.text;
    }

    public boolean isPlayable() {
        return this.playable;
    }

    @Override
    public String toString() {
        return """
                {
                    "text": "%s",
                    "playable": %b,
                    "x": %d,
                    "y": %d 
                }
                """.formatted(this.text, this.playable, this.x, this.y);
    }
}