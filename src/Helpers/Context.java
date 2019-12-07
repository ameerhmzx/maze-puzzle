package Helpers;

import core.RenderEngine;
import enums.GameState;
import objects.Board;
import objects.Player;
import objects.Puzzle;

public class Context {
    private GameState gameState;
    private Player player;
    private Puzzle puzzle;
    private Board board;
    private ScoreCounter scoreCounter;
    private RenderEngine renderEngine;

    public Context() {
        setGameState(GameState.PLAYING);
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Puzzle getPuzzle() {
        return puzzle;
    }

    public void setPuzzle(Puzzle puzzle) {
        this.puzzle = puzzle;
        this.setBoard(puzzle.getBoard());
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public ScoreCounter getScoreCounter() {
        return scoreCounter;
    }

    public void setScoreCounter(ScoreCounter scoreCounter) {
        this.scoreCounter = scoreCounter;
    }

    public RenderEngine getRenderEngine() {
        return renderEngine;
    }

    public void setRenderEngine(RenderEngine renderEngine) {
        this.renderEngine = renderEngine;
    }
}
