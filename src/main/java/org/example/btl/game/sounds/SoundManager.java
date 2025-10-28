package org.example.btl.game.sounds;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Objects;

public class SoundManager {

    private static final String BOUNCE_PATH = "/org/example/btl/M&S/bounce.mp3";
    private static final String BRICK_DESTROY_PATH = "/org/example/btl/M&S/brick_destroy.mp3";
    private static final String BRICK_HIT_PATH = "/org/example/btl/M&S/brick_hit.mp3";

    private static Media bounceMedia;
    private static Media brickDestroyMedia;
    private static Media brickHitMedia;

    static {
        bounceMedia = loadMedia(BOUNCE_PATH);
        brickDestroyMedia = loadMedia(BRICK_DESTROY_PATH);
        brickHitMedia = loadMedia(BRICK_HIT_PATH);
    }

    private static Media loadMedia(String path) {
        try {
            return new Media(Objects.requireNonNull(SoundManager.class.getResource(path)).toExternalForm());
        } catch (Exception e) {
            System.err.println("Không thể tải được file âm thanh: " + path);
            e.printStackTrace();
            return null;
        }
    }

    private static void play(Media media) {
        if (media == null) return;
        MediaPlayer player = new MediaPlayer(media);
        player.setVolume(1.0); // âm lượng tối đa, có thể chỉnh
        player.setOnEndOfMedia(player::dispose);
        player.play();
    }

    public static void playBounce() {
        play(bounceMedia);
    }

    public static void playBrickDestroySound() {
        play(brickDestroyMedia);
    }

    public static void playBrickHitSound() {
        play(brickHitMedia);
    }
}
