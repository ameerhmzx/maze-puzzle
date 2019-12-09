package Helpers;

import core.RenderEngine;
import enums.GameState;
import objects.Board;
import objects.Player;
import objects.Puzzle;

public class Context implements Constants {
    private GameState gameState;
    private Player player;
    private Puzzle puzzle;
    private Board board;
    private ScoreCounter scoreCounter;
    private RenderEngine renderEngine;

    private int numberOfMoves;
    //TODO:: set value
    private int minimumMovesRequired;

    public boolean animate = ANIMATE_BY_DEFAULT;
    private String playerName;

    public Context() {
        setGameState(GameState.PLAYING);
        numberOfMoves = 0;
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

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getNumberOfMoves() {
        return numberOfMoves;
    }

    public void setNumberOfMoves(int numberOfMoves) {
        this.numberOfMoves = numberOfMoves;
    }

    public int getMinimumMovesRequired() {
        return minimumMovesRequired;
    }

    public void setMinimumMovesRequired(int minimumMovesRequired) {
        this.minimumMovesRequired = minimumMovesRequired;
    }
}
