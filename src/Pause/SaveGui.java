package Pause;

import controllers.Controller;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class SaveGui extends Pane {
  private   TextField fileName=new TextField();
  private Button saveButton=new Button("Save");
  private Button cancelButton=new Button("Cancel");
  private Canvas canvas=new Canvas();
  private SaveController saveController=new SaveController(this);
   public SaveGui()
   {
       Controller.getInstance().setSaveGui(this);
       Controller.getInstance().get_main_layout().getChildren().remove(Controller.getInstance().getPauseMenuView());
       this.getStylesheets().add("ViewFXML/fxmlRes/styling.css");
       this.setPrefHeight(200);
       this.setPrefWidth(400);
       this.setLayoutX(200);
       this.setLayoutY(200);
       saveButton.setStyle("-fx-font-family: SDS_6x6");
       cancelButton.setStyle("-fx-font-family: SDS_6x6");
       fileName.setStyle("-fx-font-family: SDS_6x6");
       fileName.setLayoutX(50);
       fileName.setLayoutY(50);
       fileName.setPrefWidth(300);
       fileName.setPrefHeight(30);
       fileName.setStyle("-fx-text-fill: white;");
       canvas.setLayoutY(0);
       canvas.setLayoutX(0);
       canvas.setWidth(400);
       canvas.setHeight(200);
       saveButton.setLayoutY(120);
       cancelButton.setLayoutY(120);
       saveButton.setLayoutX(10);
       cancelButton.setLayoutX(190);
       saveButton.setPrefWidth(200);
       cancelButton.setPrefWidth(200);
       Color color = Color.web("#000000", 0.7);
       Color color1 = Color.web("#551a8b", 1);
       canvas.getGraphicsContext2D().setFill(color);
       canvas.getGraphicsContext2D().fillRoundRect(0,0,400,200,50,50);
       canvas.getGraphicsContext2D().setStroke(color1);
       canvas.getGraphicsContext2D().strokeRoundRect(0,0,400,200,50,50);
       this.getChildren().addAll(canvas,fileName,saveButton,cancelButton);
       Controller.getInstance().get_main_layout().getChildren().add(this);
       saveButton.setOnAction(e->saveController.onSave());
       cancelButton.setOnAction(e->saveController.onCancel());
   }

    public TextField getFileName() {
        return fileName;
    }

    public void setFileName(TextField fileName) {
        this.fileName = fileName;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public void setSaveButton(Button saveButton) {
        this.saveButton = saveButton;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    public void setCancelButton(Button cancelButton) {
        this.cancelButton = cancelButton;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public SaveController getSaveController() {
        return saveController;
    }

    public void setSaveController(SaveController saveController) {
        this.saveController = saveController;
    }
}
