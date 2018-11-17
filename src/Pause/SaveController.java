package Pause;

import IOFile.SaverAndLoader;
import controllers.Controller;
import game.system.PauseState;
import playerState.Pause;

public class SaveController {
    SaveGui saveGui;
    public SaveController(SaveGui saveGui) {
        this.saveGui=saveGui;
        saveGui.setSaveController(this);
    }
    public void onSave()
    {
        if(saveGui.getFileName().getText()!=null) {
            String fileName = saveGui.getFileName().getText();
            SaverAndLoader saverAndLoader = new SaverAndLoader();
            saverAndLoader.saveGame(fileName);
            Controller.getInstance().setGameState(new PauseState());
            Controller.getInstance().getPlayer().setPlayerState(new Pause());
            Controller.getInstance().get_main_layout().getChildren().remove(Controller.getInstance().getSaveGui());
            Controller.getInstance().setSaveGui(null);
            Controller.getInstance().get_main_layout().getChildren().add(Controller.getInstance().getPauseMenuView());
        }
    }
    public void onCancel()
    {
        Controller.getInstance().get_main_layout().getChildren().remove(Controller.getInstance().getSaveGui());
        Controller.getInstance().setSaveGui(null);
        Controller.getInstance().get_main_layout().getChildren().add(Controller.getInstance().getPauseMenuView());
    }
}
