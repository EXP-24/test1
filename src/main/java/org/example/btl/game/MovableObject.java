package org.example.btl.game;

import javafx.scene.canvas.GraphicsContext;

public abstract class MovableObject extends GameObject {

    protected double dx;
    protected double dy;

    public MovableObject(double x, double y, double width, double height) {
        super(x, y, width, height);
        this.dx = 0.0;
        this.dy = 0.0;
    }

    public void move() {
        setX(getX() + dx);
        setY(getY() + dy);
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDx() {
        return dx;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public double getDy() {
        return dy;
    }
}
