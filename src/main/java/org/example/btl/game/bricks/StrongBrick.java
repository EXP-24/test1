
package org.example.btl.game;

import org.example.btl.game.Brick;

public class StrongBrick extends Brick {

    public StrongBrick(double x, double y, double width, double height, int brickType, int powerUpType) {
        super(x, y, width, height, brickType, powerUpType);

        String colorName = switch (brickType) {
            case 7, 8 -> "Strong1";
            case 20 -> "Boss1";
            default -> "red"; // fallback
        };

        int hp = switch (brickType) {
            case 20 -> 3;
            default -> 2;
        };

        this.healthBrick = hp;
        this.brickImage = loadBrickImage(colorName);
    }
}
