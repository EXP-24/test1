package org.example.btl.game.powerups;

import org.example.btl.game.Ball;
import org.example.btl.game.Paddle;

import java.util.List;

public class TinyBallPowerUp extends PowerUp {
    private static final double SIZE_MULTIPLIER = 0.7;
    private List<Ball> balls;
    private double originalWidth;
    private double originalHeight;

    public TinyBallPowerUp(double x, double y, List<Ball> balls) {
        super(x, y, "TinyBall", 10000);
        this.balls = balls;
    }

    @Override
    public void applyEffect(Paddle paddle) {
        for (Ball ball : balls) {
            if (ball != null) {
                originalWidth = ball.getWidth();
                originalHeight = ball.getHeight();

                ball.setWidth(originalWidth * SIZE_MULTIPLIER);
                ball.setHeight(originalHeight * SIZE_MULTIPLIER);
            }
        }
    }

    @Override
    public void removeEffect(Paddle paddle) {
        for (Ball ball : balls) {
            if (ball != null) {
                ball.setWidth(originalWidth);
                ball.setHeight(originalHeight);
            }
        }
    }
}
