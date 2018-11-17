package Pause;

import IOFile.SaverAndLoader;
import controllers.Controller;
import game.system.PauseState;
import game.system.PlayState;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import main.Main;
import playerState.Pause;
import playerState.Playing;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PauseController  {

   private Button resumeButton;
   private Button SaveButton;
   private Button MainMenuButton;
   private PauseMenuView pauseMenuView;

    public PauseController(PauseMenuView pauseMenuView) {
        this.pauseMenuView=pauseMenuView;
    }
    public void OnResume(){
        Controller.getInstance().getGameState().pauseUnPause();
    }
    public void OnSave()
    {
        SaveGui saveGui=new SaveGui();
    }
    public void OnMainMenu()
    {
        Controller.getInstance().getA().stop();
     Stage primaryStage= Main.window;
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/ViewFXML/StartMenuView.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {

        }
        primaryStage.setResizable(false);
        primaryStage.setTitle("Maze Runner");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public Button getResumeButton() {
        return resumeButton;
    }

    public void setResumeButton(Button resumeButton) {
        this.resumeButton = resumeButton;
    }

    public Button getSaveButton() {
        return SaveButton;
    }

    public void setSaveButton(Button saveButton) {
        SaveButton = saveButton;
    }

    public Button getMainMenuButton() {
        return MainMenuButton;
    }

    public void setMainMenuButton(Button mainMenuButton) {
        MainMenuButton = mainMenuButton;
    }


}
