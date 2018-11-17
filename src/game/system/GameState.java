package game.system;

import controllers.Controller;
import entities.tiles.TileEnd_DoorDecorator;

public interface GameState {
    void updateTime(Controller controller);
    void pauseUnPause();
    double updateDeltaTime(double currentTime,double LastTime);
}
