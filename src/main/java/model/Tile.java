package model;

import java.awt.*;
import java.util.Objects;

public class Tile {
    private int value;
    private Color fontColor;
    private Color tileColor;

    public Tile() {
        setValue(0);
    }

    public Tile(int value) {
        setValue(value);
    }

    public Color getFontColor() {
        return fontColor;
    }

    public Color getTileColor() {
        return tileColor;
    }

    public boolean isEmpty() {
        return value == 0;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;

        switch (this.value){
            case 0:
                tileColor = new Color(0xcdc1b4);
                break;
            case 2:
                tileColor = new Color(0xeee4da);
                break;
            case 4:
                tileColor = new Color(0xede0c8);
                break;
            case 8:
                tileColor = new Color(0xf2b179);
                break;
            case 16:
                tileColor = new Color(0xf59563);
                break;
            case 32:
                tileColor = new Color(0xf67c5f);
                break;
            case 64:
                tileColor = new Color(0xf65e3b);
                break;
            case 128:
                tileColor =  new Color(0xedcf72);
                break;
            case 256:
                tileColor =  new Color(0xedcc61);
                break;
            case 512:
                tileColor =  new Color(0xedc850);
                break;
            case 1024:
                tileColor =  new Color(0xedc53f);
                break;
            case 2048:
                tileColor =  new Color(0xedc22e);
                break;
            default:
                tileColor =  Color.BLACK;
        }

        fontColor = value < 8 ? new Color(0x776e65) : new Color(0xf9f6f2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return value == tile.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
