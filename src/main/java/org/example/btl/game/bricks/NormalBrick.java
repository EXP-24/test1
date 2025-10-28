package org.example.btl.game;

import org.example.btl.game.Brick;

public class NormalBrick extends Brick {

    public NormalBrick(double x, double y, double width, double height, int brickType, int powerUpType) {
        super(x, y, width, height, brickType, powerUpType);

        String colorName;
        switch (brickType) {
            case 1 -> colorName = "Blue";
            case 2 -> colorName = "Yellow";
            case 3 -> colorName = "Green";
            case 4 -> colorName = "Purple";
            case 5 -> colorName = "Orange";
            default -> colorName = "Red";
        }

        this.healthBrick = 1;
        this.brickImage = loadBrickImage(colorName);
    }
}
