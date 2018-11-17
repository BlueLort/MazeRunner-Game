package game.system;

import controllers.Controller;
import entities.tiles.TileEnd_DoorDecorator;

public class PlayState implements GameState {
    @Override
    public void updateTime(Controller controller) {
        controller.setGameTime(controller.getDeltaTime()+controller.getGameTime());
    }

    @Override
    public void pauseUnPause()
    {
        Controller.getInstance().get_main_layout().getChildren().add(Controller.getInstance().getPauseMenuView());
        Controller.getInstance().getPlayer().pauseUnPause();
        Controller.getInstance().setGameState(new PauseState());
    }

    @Override
    public double updateDeltaTime(double currentTime,double LastTime) {
        return currentTime-LastTime;
    }

}
