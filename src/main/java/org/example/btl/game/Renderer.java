package org.example.btl.game;

import org.example.btl.game.Brick;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.example.btl.game.bricks.MapBrick;
import java.util.List;


import static org.example.btl.GameApplication.*;

public class Renderer {

    private GraphicsContext gc;

    public Renderer(GraphicsContext gc) {
        this.gc = gc;
    }

    public void clear() {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    }

    public void renderAll(List<GameObject> objects) {
        for (GameObject object : objects) {
            object.render(gc);
        }
    }

    public void renderMap(MapBrick map) {
        if (map == null) return;
        for (Brick brick : map.getBricks()) {
            brick.render(gc);
        }
    }
}
