package org.example.btl.game.powerups;

import javafx.scene.image.Image;
import org.example.btl.game.Paddle;
import org.example.btl.game.SkinManager;

public class ShrinkPaddlePowerUp extends PowerUp {
    private static final double SCALE_FACTOR = 1.5;
    private int skinIndexSelected = SkinManager.getSkinIndex();

    public ShrinkPaddlePowerUp(double x, double y) {
        super(x, y, "Shrink", 10000);
    }

    @Override
    public void applyEffect(Paddle paddle) {
        // Nếu paddle chưa quá nhỏ thì mới thu nhỏ
        if (paddle.getWidth() > 48) {
            paddle.setWidth(paddle.getWidth() / SCALE_FACTOR);
            Image imagePaddleShrink = loadImage("/org/example/btl/images/paddlesShrink/paddleShrink" + skinIndexSelected + ".png");
            paddle.setImage(imagePaddleShrink);
        }
    }

    @Override
    public void removeEffect(Paddle paddle) {
        if (paddle.getWidth() < 64) {
            paddle.setWidth(paddle.getWidth() * SCALE_FACTOR);
            Image imagePaddleNormal = loadImage("/org/example/btl/images/paddles/paddle" + skinIndexSelected + ".png");
            paddle.setImage(imagePaddleNormal);
        }
    }
}
