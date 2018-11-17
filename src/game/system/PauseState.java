package game.system;

import controllers.Controller;

public class PauseState implements GameState {

    @Override
    public void updateTime(Controller controller) {

    }

    @Override
    public void pauseUnPause() {
        Controller.getInstance().get_main_layout().getChildren().remove(Controller.getInstance().getPauseMenuView());
        Controller.getInstance().getPlayer().pauseUnPause();
        Controller.getInstance().setGameState(new PlayState());
        if (Controller.getInstance().getSaveGui() != null) {
            Controller.getInstance().get_main_layout().getChildren().remove(Controller.getInstance().getSaveGui());
            Controller.getInstance().setSaveGui(null);
        }
    }

    @Override
    public double updateDeltaTime(double currentTime, double LastTime) {
        return 0;
    }
}
