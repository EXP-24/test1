package org.example.btl.game.bricks;

import org.example.btl.game.Brick;

public class UnbreakBrick extends Brick {
    public UnbreakBrick(double x, double y, double width, double height, int brickType, int powerUpType) {
        super(x, y, width, height, brickType, powerUpType);
        this.healthBrick = 999999;
        this.brickImage = loadBrickImage("Unbreak");
    }
}
