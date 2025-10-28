package org.example.btl.game.powerups;

import org.example.btl.game.Ball;
import org.example.btl.game.Paddle;

import java.util.List;

public class FastBallPowerUp extends PowerUp {
    private static final double SPEED_MULTIPLIER = 1.5;
    private List<Ball> balls;

    public FastBallPowerUp(double x, double y, List<Ball> balls) {
        super(x, y, "FastBall", 10000);
        this.balls = balls;
    }

    @Override
    public void applyEffect(Paddle paddle) {
        for (Ball ball : balls) {
            if (ball != null) {
                double speed = ball.getSpeed();
                if (speed < 1.0) {
                    ball.setSpeed(1.0);
                } else if (speed < 1.5) {
                    ball.setSpeed(speed * SPEED_MULTIPLIER);
                    if (ball.getSpeed() > 1.5) {
                        ball.setSpeed(1.5);
                    }
                }
            }
        }
    }

    @Override
    public void removeEffect(Paddle paddle) {
        for (Ball ball : balls) {
            if (ball != null && ball.getSpeed() > 1.0) {
                ball.setSpeed(1.0);
            }
        }
    }
}
