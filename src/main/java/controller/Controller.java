package controller;

import model.GameModel;
import model.Tile;
import view.View;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Controller extends KeyAdapter {

    private GameModel gameModel;
    private View view;
    private static final int WINNING_TILE = 2048;
    private boolean isGameWon;

    public Controller(GameModel gameModel) {
        this.gameModel = gameModel;
        this.view = new View(this);
    }

    private void resetGame() {
        this.view.setGameWon(false);
        this.view.setGameLost(false);
        this.gameModel.resetField();
    }

    public Tile[][] getGameField() {
        return gameModel.getFiled();
    }

    public int getScore() {
        return gameModel.getScore();
    }

    public View getView() {
        return view;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!gameModel.canMove()) {
            view.setGameLost(true);
        }
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                gameModel.moveLeft();
                break;
            case KeyEvent.VK_RIGHT:
                gameModel.moveRight();
                break;
            case KeyEvent.VK_UP:
                gameModel.moveUp();
                break;
            case KeyEvent.VK_DOWN:
                gameModel.moveDown();
                break;
            case KeyEvent.VK_ESCAPE:
                gameModel.rollBack();
                view.setGameLost(false);
                break;
            case KeyEvent.VK_SPACE:
                resetGame();
        }

        if (!isGameWon && gameModel.getMaxTile() == WINNING_TILE) {
            view.setGameWon(true);
            isGameWon = true;
        }

        view.repaint();
    }

}
