package org.example.btl.game.powerups;

import org.example.btl.game.Ball;
import org.example.btl.game.Paddle;

import java.util.List;

public class TripleBallPowerUp extends PowerUp {
    private final List<Ball> balls;

    public TripleBallPowerUp(double x, double y, List<Ball> balls) {
        super(x, y, "TripleBall", 0);
        this.balls = balls;
    }

    @Override
    public void applyEffect(Paddle paddle) {
        if (balls == null || balls.isEmpty()) return;

        if (balls.size() > 1) {
            return;
        }

        for (Ball ball : balls) {
            if (ball.isAttached()) {
                return;
            }
        }

        Ball original = balls.get(0);

        Ball ball1 = new Ball(original.getX(), original.getY(),
                original.getWidth(), original.getHeight(),
                -original.getDirectionX(), original.getDirectionY(), original.getSpeed());
        ball1.setAttached(false);
        Ball ball2 = new Ball(original.getX(), original.getY(),
                original.getWidth(), original.getHeight(),
                original.getDirectionX(), -original.getDirectionY(), original.getSpeed());
        ball2.setAttached(false);

        balls.add(ball1);
        balls.add(ball2);
    }

    @Override
    public void removeEffect(Paddle paddle) {
    }
}
