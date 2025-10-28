package org.example.btl.game.powerups;

import org.example.btl.game.Ball;
import org.example.btl.game.Paddle;

import java.util.List;

public class SlowBallPowerUp extends PowerUp {
    private static final double SPEED_MULTIPLIER = 0.75;
    private List<Ball> balls;


    public SlowBallPowerUp(double x, double y, List<Ball> balls) {
        super(x, y, "SlowBall", 10000);
        this.balls = balls;
    }

    @Override
    public void applyEffect(Paddle paddle) {
        for (Ball ball : balls) {
            if (ball != null) {
                double speed = ball.getSpeed();
                if (speed > 1.0) {
                    ball.setSpeed(1.0);
                }
                else if (speed > 0.75) {
                    ball.setSpeed(speed * SPEED_MULTIPLIER);
                }
            }
        }
    }

    @Override
    public void removeEffect(Paddle paddle) {
        for (Ball ball : balls) {
            if (ball != null && ball.getSpeed() < 1.0) {
                ball.setSpeed(1.0);
            }
        }
    }
}