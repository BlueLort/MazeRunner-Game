package entities.player.playerArmoredState;

import javafx.scene.canvas.GraphicsContext;

public interface PlayerArmouredState {
    public void render(GraphicsContext canvas);
    public void takeDamage(double damage);
}
