package org.example.btl.game.powerups;

import org.example.btl.game.Ball;
import org.example.btl.game.Paddle;

import java.util.List;

public class FastBallPowerUp extends PowerUp {
    private static final double SPEED_MULTIPLIER = 1.25;
    private static final double NORMAL_SPEED = 1.0;
    private static final double MAX_SPEED = 1.5;
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

                if (speed < NORMAL_SPEED) {
                    ball.setSpeed(NORMAL_SPEED);
                }

                else if (speed < MAX_SPEED) {
                    ball.setSpeed(speed * SPEED_MULTIPLIER);
                    if (ball.getSpeed() > MAX_SPEED) {
                        ball.setSpeed(MAX_SPEED);
                    }
                }
            }
        }
    }

    @Override
    public void removeEffect(Paddle paddle) {
        for (Ball ball : balls) {
            if (ball != null && ball.getSpeed() > NORMAL_SPEED) {
                ball.setSpeed(NORMAL_SPEED);
            }
        }
    }
}
