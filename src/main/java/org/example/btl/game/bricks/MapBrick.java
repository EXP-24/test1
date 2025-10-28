
package org.example.btl.game.bricks;

import org.example.btl.game.*;
import org.example.btl.game.Brick;
import org.example.btl.game.NormalBrick;
import org.example.btl.game.StrongBrick;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapBrick {

    private List<Brick> bricks;
    private int mapLevel;
    private Random randomGenerator;

    public static final int BRICK_WIDTH = 32;
    public static final int BRICK_HEIGHT = 16;

    public MapBrick() {
        bricks = new ArrayList<>();
        randomGenerator = new Random();
    }

    public void setMapLevel(int level) {
        this.mapLevel = level;
    }

    public int getMapLevel() {
        return mapLevel;
    }

    public void createMap(int[][] mapLayout, double offsetX, double offsetY) {
        bricks.clear();

        for (int row = 0; row < mapLayout.length; row++) {
            for (int col = 0; col < mapLayout[row].length; col++) {
                int brickType = mapLayout[row][col];

                if (brickType > 0) {
                    double brickX = offsetX + col * BRICK_WIDTH;
                    double brickY = offsetY + row * BRICK_HEIGHT;

                    int powerUpType = 0;

                    if (brickType == 2) {
                        int numberOfPowerUpType = 3;
                        powerUpType = randomGenerator.nextInt(numberOfPowerUpType) + 1;
                    }

                    Brick brick;
                    if (brickType >= 1 && brickType <= 5) {
                        brick = new NormalBrick(brickX, brickY, BRICK_WIDTH, BRICK_HEIGHT, brickType, powerUpType);
                    } else if (brickType == 7 || brickType == 8) {
                        brick = new StrongBrick(brickX, brickY, BRICK_WIDTH, BRICK_HEIGHT, brickType, powerUpType);
                    } else if (brickType == 9) {
                        brick = new UnbreakBrick(brickX, brickY, BRICK_WIDTH, BRICK_HEIGHT, brickType, powerUpType);
                    } else {
                        // fallback
                        brick = new NormalBrick(brickX, brickY, BRICK_WIDTH, BRICK_HEIGHT, 0, powerUpType);
                    }

                    bricks.add(brick);
                }
            }
        }
    }

    public void update() {
        // Có thể cập nhật logic brick ở đây sau này (ví dụ hiệu ứng nứt, đổi màu,...)
    }

    public List<Brick> getBricks() {
        return bricks;
    }

    public static int[][] loadMap(int level) {
        List<int[]> mapList = new ArrayList<>();
        String filePath = "/org/example/btl/Map/Map" + level + ".txt";

        try (InputStream is = MapBrick.class.getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            if (is == null) {
                System.err.println("Không tìm thấy file map tại: " + filePath);
                return new int[0][0];
            }

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] numbers = line.split("\\s+");
                int[] row = new int[numbers.length];
                for (int i = 0; i < numbers.length; i++) {
                    row[i] = Integer.parseInt(numbers[i]);
                }
                mapList.add(row);
            }

        } catch (IOException | NumberFormatException e) {
            System.err.println("Lỗi khi đọc file map: " + filePath);
            e.printStackTrace();
            return new int[0][0];
        }

        return mapList.toArray(new int[0][]);
    }

    public void createBossMap(double screenWidth, double screenHeight) {
        bricks.clear();

        double brickX = (screenWidth - BRICK_WIDTH * 6) / 2.0;
        double brickY = (screenHeight - BRICK_HEIGHT * 15) / 2.0 - 64;

        Brick bossBrick = new BossBrick(brickX, brickY, BRICK_WIDTH * 6 , BRICK_HEIGHT * 16, 20, 0);

        // hoặc NormalBrick(..., 1, 0) nếu bạn chỉ muốn viên gạch thường

        bricks.add(bossBrick);
    }

}
