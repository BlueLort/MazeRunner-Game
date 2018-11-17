package entities;

import javafx.scene.canvas.GraphicsContext;
import utility.Collider;

public interface Cell {
    public void render(GraphicsContext canvas);

    public void update( double deltaTime);

    public Collider getCollider();
}
