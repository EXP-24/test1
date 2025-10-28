package org.example.btl.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Objects;

public class Animation {

    private Image[] images;
    private int frameCount;
    private int currentFrame;
    private long frameDuration;
    private long lastFrameTime;

    public Animation(int frameCount, long frameDuration) {
        this.images = new Image[frameCount];
        this.frameCount = frameCount;
        this.currentFrame = 0;
        this.lastFrameTime = 0;
        this.frameDuration = frameDuration;
    }

    public void loadAnimation(String basePath, String type) {
        for (int i = 0; i < frameCount; i++) {
            String path = basePath + type + i + ".png";
            try {
                images[i] = new Image(Objects.requireNonNull(Animation.class.getResource(path)).toExternalForm());
            } catch (Exception e) {
                System.err.println("Không thể tải frame: " + path);
                images[i] = null;
            }
        }
    }

    public void update() {
        long now = System.currentTimeMillis();
        if (now - lastFrameTime >= frameDuration) {
            currentFrame = (currentFrame + 1) % frameCount;
            lastFrameTime = now;
        }
    }

    public void render(GraphicsContext gc, double x, double y, double width, double height) {
        if (images[currentFrame] != null) {
            gc.drawImage(images[currentFrame], x, y, width, height);
        }
    }

    public Image[] getImages() {
        return images;
    }
}
