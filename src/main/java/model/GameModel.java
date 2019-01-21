package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class GameModel {

    private static final int FIELD_WIDTH = 4;
    private Tile[][] filed;
    private Stack<Tile[][]> savedFields;
    private Stack<Integer> savedScore;
    private int score;
    private int maxTile;
    private boolean isSaveNeeded = true;

    public GameModel() {
        savedFields = new Stack<>();
        savedScore = new Stack<>();
        resetField();
    }

    public void resetField() {
        filed = new Tile[FIELD_WIDTH][FIELD_WIDTH];

        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                filed[i][j] = new Tile();
            }
        }

        addTile();
        addTile();
        score = 0;
        maxTile = 2;
    }

    public Tile[][] getFiled() {
        return filed;
    }

    public int getScore() {
        return score;
    }

    public int getMaxTile() {
        return maxTile;
    }

    private void addTile() {
        List<Tile> emptyTiles = getEmptyTiles();

        if (!emptyTiles.isEmpty()) {
            emptyTiles.get((int) (Math.random() * emptyTiles.size())).setValue(Math.random() > 0.9 ? 4 : 2);
        }
    }

    private List<Tile> getEmptyTiles() {
        List<Tile> emptyTiles = new ArrayList<>();

        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                if (filed[i][j].isEmpty()) {
                    emptyTiles.add(filed[i][j]);
                }
            }
        }

        return emptyTiles;
    }

    private boolean compressTiles(Tile[] line) {
        Tile[] copyLine = line.clone();

        Arrays.sort(line, (e1, e2) -> {
            if (!e1.isEmpty() && !e2.isEmpty()) {
                return 0;
            }
            return e2.getValue() - e1.getValue();
        });

        return !Arrays.equals(line, copyLine);
    }

    private boolean margeTiles(Tile[] line) {

        boolean isMarge = false;

        for (int i = 0; i < FIELD_WIDTH - 1; i++) {
            if (!line[i].isEmpty() && line[i].equals(line[i + 1])) {
                line[i].setValue(line[i].getValue() * 2);
                line[i + 1].setValue(0);
                score += line[i].getValue();
                maxTile = line[i].getValue() > maxTile ? line[i].getValue() : maxTile;
                i++;
                isMarge = true;
            }
        }

        compressTiles(line);
        return isMarge;
    }

    public void moveLeft() {
        save();

        boolean isChange = false;
        for (int i = 0; i < FIELD_WIDTH; i++) {
            if (compressTiles(filed[i]) | margeTiles(filed[i])) {
                isChange = true;
            }
        }

        if (isChange) {
            addTile();
            isSaveNeeded = true;
        }
    }

    public void moveRight() {
        save();
        rotate();
        rotate();
        moveLeft();
        rotate();
        rotate();
    }

    public void moveDown() {
        save();
        rotate();
        rotate();
        rotate();
        moveLeft();
        rotate();
    }

    public void moveUp() {
        save();
        rotate();
        moveLeft();
        rotate();
        rotate();
        rotate();
    }

    private void rotate() {
        for (int i = 0; i < FIELD_WIDTH / 2; i++) {
            for (int j = i; j < FIELD_WIDTH - 1 - i; j++) {
                Tile tmp = filed[i][j];
                filed[i][j] = filed[j][FIELD_WIDTH - i - 1];
                filed[j][FIELD_WIDTH - i - 1] = filed[FIELD_WIDTH - i - 1][FIELD_WIDTH - j - 1];
                filed[FIELD_WIDTH - i - 1][FIELD_WIDTH - j - 1] = filed[FIELD_WIDTH - j - 1][i];
                filed[FIELD_WIDTH - j - 1][i] = tmp;
            }
        }
    }

    private void save() {
        if (isSaveNeeded) {
            Tile[][] copyField = new Tile[FIELD_WIDTH][FIELD_WIDTH];

            for (int i = 0; i < FIELD_WIDTH; i++) {
                for (int j = 0; j < FIELD_WIDTH; j++) {
                    copyField[i][j] = new Tile(filed[i][j].getValue());
                }
            }

            savedFields.push(copyField);
            savedScore.push(score);
            isSaveNeeded = false;
        }
    }

    public void rollBack() {
        if (Arrays.deepEquals(savedFields.peek(), filed)) {
            savedFields.pop();
            savedScore.pop();
        }

        if (!savedScore.empty()) {
            filed = savedFields.pop();
            score = savedScore.pop();
            isSaveNeeded = true;
        }
    }

    public boolean canMove() {
        if (!getEmptyTiles().isEmpty()) {
            return true;
        }

        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                if (i != FIELD_WIDTH - 1) {
                    if (filed[i][j].equals(filed[i + 1][j])) {
                        return true;
                    }
                }
                if (j != FIELD_WIDTH - 1) {
                    if (filed[i][j].equals(filed[i][j + 1])) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

}
