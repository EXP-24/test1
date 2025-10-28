package org.example.btl.game.powerups;

import javafx.scene.image.Image;
import org.example.btl.game.GameObject;
import  org.example.btl.game.Paddle;
import org.example.btl.game.SkinManager;


public class ExpandPaddlePowerUp extends PowerUp {
    private static final double SCALE_FACTOR = 1.5;
    private int skinIndexSelected = SkinManager.getSkinIndex();

    public ExpandPaddlePowerUp(double x, double y) {
        super(x, y, "Expand", 10000);
    }

    @Override
    public void applyEffect(Paddle paddle) {
        if (paddle.getWidth() < 96) {
            paddle.setWidth(paddle.getWidth() * SCALE_FACTOR);
            Image imagePaddleExpland = loadImage("/org/example/btl/images/paddleExpland.png");
            paddle.setImage(imagePaddleExpland);
        }
    }

    @Override
    public void removeEffect(Paddle paddle) {
        if (paddle.getWidth() > 64) {
            paddle.setWidth((paddle.getWidth() / SCALE_FACTOR));
            Image imagePaddleNormal = loadImage("/org/example/btl/images/paddles/Paddle" + skinIndexSelected + ".png");
            paddle.setImage(imagePaddleNormal);
        }
    }
}
