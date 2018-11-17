package game.system;

import controllers.Controller;
import entities.tiles.Tile;
import entities.tiles.TileEnd_DoorDecorator;

public class WinState implements GameState {
    @Override
    public void updateTime(Controller controller) {

    }

    @Override
    public void pauseUnPause() {

    }

    @Override
    public double updateDeltaTime(double currentTime, double LastTime) {
        return 0;
    }

}
