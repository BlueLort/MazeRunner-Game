package main;

import IOFile.SaverAndLoader;
import controllers.Controller;
import entities.Entity;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.SequentialTransition;
import javafx.animation.TimelineBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Main extends Application {
    public static final int SCR_WIDTH=1024;
    public static final int SCR_HEIGHT=768;
    public static  Stage window;
    public static ArrayList<String> savedGamesPaths=new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception {
        SaverAndLoader saverAndLoader=new SaverAndLoader();
        saverAndLoader.loadSavedGamesPaths();
        window=primaryStage;
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/ViewFXML/StartMenuView.fxml"));
        Font.loadFont("file:res/Fonts/SDS_6x6.ttf", 12);
        Parent root = loader.load();
        primaryStage.setResizable(false);
        primaryStage.setTitle("Maze Runner");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
