package org.example.btl.game.powerups;

import javafx.scene.image.Image;
import org.example.btl.game.Paddle;
import  org.example.btl.game.powerups.PowerUp;

public class ShrinkPaddlePowerUp extends PowerUp {
    private static final double SCALE_FACTOR = 1.5;
    private double originalWidth = -1;

    public ShrinkPaddlePowerUp(double x, double y) {
        super(x, y, "Shrink", 10000);
    }

    @Override
    public void applyEffect(Paddle paddle) {
        if (originalWidth < 0) {
            originalWidth = paddle.getWidth();
            double newWidth = paddle.getWidth() / SCALE_FACTOR;
            paddle.setWidth(newWidth);
            Image img = loadImage("/org/example/btl/images/paddleShrink.png");
            paddle.setImage(img);
        }
    }

    @Override
    public void removeEffect(Paddle paddle) {
        if (originalWidth > 0) {
            paddle.setWidth(originalWidth);
            Image img = loadImage("/org/example/btl/images/paddle.png");
            paddle.setImage(img);
        }
    }
}
