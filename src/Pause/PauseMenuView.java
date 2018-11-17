package Pause;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import main.Main;

public class PauseMenuView extends Pane {
    private Button ResumeButton = new Button();
    private Button SaveButton = new Button();
    private Button ReturnMainMenu = new Button();
    private Canvas canvas = new Canvas();
    private PauseController pauseController = new PauseController(this);

    public PauseMenuView() {
        this.setHeight(Main.SCR_HEIGHT);
        this.setWidth(Main.SCR_WIDTH);
        this.setLayoutX(0);
        this.setLayoutX(0);
        canvas.setHeight(Main.SCR_HEIGHT);
        canvas.setWidth(Main.SCR_WIDTH);
        canvas.setLayoutX(0);
        canvas.setLayoutY(0);
        Color color = Color.web("#000000", 0.7);
        Color color1 = Color.web("#551a8b", 1);
        this.canvas.getGraphicsContext2D().setFill(color);
        this.canvas.getGraphicsContext2D().fillRoundRect(250, 50, 350, 500, 50, 50);
        this.canvas.getGraphicsContext2D().setStroke(color1);
        this.canvas.getGraphicsContext2D().strokeRoundRect(250, 50, 350, 500, 50, 50);
        ImageView RESUMEImage = new ImageView(new Image("ViewFXML/fxmlRes/Resume.png"));
        RESUMEImage.setFitHeight(30);
        RESUMEImage.setFitWidth(200);
        ImageView SAVEImage = new ImageView(new Image("ViewFXML/fxmlRes/Save.png"));
        SAVEImage.setFitHeight(30);
        SAVEImage.setFitWidth(200);
        ImageView RETURN_MAIN_MENUImage = new ImageView(new Image("ViewFXML/fxmlRes/Main_Menu.png"));
        RETURN_MAIN_MENUImage.setFitHeight(30);
        RETURN_MAIN_MENUImage.setFitWidth(200);
        ResumeButton.setLayoutX(300);
        ResumeButton.setLayoutY(115);
        ResumeButton.setPrefWidth(200);
        ResumeButton.setPrefHeight(30);
        ResumeButton.setGraphic(RESUMEImage);
        ResumeButton.setOnAction(e -> pauseController.OnResume());
        SaveButton.setLayoutX(300);
        SaveButton.setLayoutY(230);
        SaveButton.setPrefWidth(200);
        SaveButton.setPrefHeight(30);
        SaveButton.setGraphic(SAVEImage);
        SaveButton.setOnAction(e -> pauseController.OnSave());
        ReturnMainMenu.setLayoutX(300);
        ReturnMainMenu.setLayoutY(350);
        ReturnMainMenu.setPrefWidth(200);
        ReturnMainMenu.setPrefHeight(30);
        ReturnMainMenu.setGraphic(RETURN_MAIN_MENUImage);
        ReturnMainMenu.setOnAction(e -> pauseController.OnMainMenu());
        this.getStylesheets().add("ViewFXML/fxmlRes/styling.css");

        this.getChildren().addAll(canvas, ResumeButton, SaveButton, ReturnMainMenu);
    }

    public Button getResumeButton() {
        return ResumeButton;
    }

    public void setResumeButton(Button resumeButton) {
        ResumeButton = resumeButton;
    }

    public Button getSaveButton() {
        return SaveButton;
    }

    public void setSaveButton(Button saveButton) {
        SaveButton = saveButton;
    }

    public Button getReturnMainMenu() {
        return ReturnMainMenu;
    }

    public void setReturnMainMenu(Button returnMainMenu) {
        ReturnMainMenu = returnMainMenu;
    }


    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }
}
