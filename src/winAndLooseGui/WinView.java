package winAndLooseGui;

import controllers.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import main.Main;

import java.io.IOException;


public class WinView extends AnchorPane {
    private Button mainMenu = new Button();
    private Label WON=new Label();
    private Canvas canvas = new Canvas();

    public WinView() {
        Controller.getInstance().get_main_layout().getChildren().remove(Controller.getInstance().getPauseMenuView());
        Controller.getInstance().get_main_layout().getChildren().add(this);
        mainMenu.setStyle("-fx-font-family: SDS_6x6");
        WON.setStyle("-fx-font-family: SDS_6x6");
        WON.setText("Congratiulations!\n\tyou Won!"+"\n\t\t Score:"+Controller.getInstance().getPlayer().getScore());
        WON.setLayoutX(50);
        WON.setLayoutY(50);
        WON.setPrefWidth(300);
        WON.setPrefHeight(30);
        WON.setStyle("-fx-text-fill: white;");
        Color color = Color.web("#000000", 0.7);
        Color color1 = Color.web("#551a8b", 1);
        this.canvas.getGraphicsContext2D().setFill(color);
        this.canvas.getGraphicsContext2D().fillRoundRect(250, 50, 350, 500, 50, 50);
        this.canvas.getGraphicsContext2D().setStroke(color1);
        this.canvas.getGraphicsContext2D().strokeRoundRect(250, 50, 350, 500, 50, 50);
        this.getStylesheets().add("ViewFXML/fxmlRes/styling.css");
        mainMenu.setOnAction(e -> {
            Stage primaryStage= Main.window;
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/ViewFXML/StartMenuView.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException exp) {

            }
            primaryStage.setResizable(false);
            Font.loadFont("Art/GUI/SDS_6x6.ttf", 12);
            primaryStage.setTitle("Maze Runner");
            primaryStage.setScene(new Scene(root, Main.SCR_WIDTH, Main.SCR_HEIGHT));
            primaryStage.show();
        });
    }
}


