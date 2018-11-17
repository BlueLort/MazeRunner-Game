package controllers;

import IOFile.SaverAndLoader;
import game.system.BackGroundMusic;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import main.Main;
import memento.CheckPoint;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartMenuController implements Initializable {
    @FXML
    public ImageView image;
    @FXML
    Button buttonNewGame;
    @FXML
    Button buttonLoad;
    @FXML
    Button Exit;
    @FXML
    Pane mainContainer;
    @FXML
    ImageView muteImage;
    @FXML
    ComboBox<String> listOfPaths;
    @FXML
    AnchorPane loadPane;
    @FXML
    Canvas canvas;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainContainer.getChildren().remove(loadPane);
        if (BackGroundMusic.getClip() != null) BackGroundMusic.stop();
        BackGroundMusic backGroundMusic = new BackGroundMusic("res/Audio/Background/Do Not Run.wav");
        backGroundMusic.run();
        mainContainer.setOnKeyPressed(
                e -> {
                    if (e.getCode() == KeyCode.M) {
                        muteOnAction();
                    }
                    if (e.getCode() == KeyCode.ENTER) {
                        if (buttonLoad.isHover() || buttonNewGame.isHover() || Exit.isHover()) {
                            try {
                                Robot robot = new Robot();
                                robot.mousePress(java.awt.event.InputEvent.BUTTON1_MASK);
                                robot.mouseRelease(java.awt.event.InputEvent.BUTTON1_MASK);
                            } catch (AWTException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                    if (e.getCode() == KeyCode.DOWN) {
                        if (buttonNewGame.isHover()) {
                            try {
                                Robot robot = new Robot();
                                Bounds boundsInScreen = buttonLoad.localToScreen(buttonLoad.getBoundsInLocal());
                                robot.mouseMove((int) boundsInScreen.getMaxX() - 100, (int) boundsInScreen.getMaxY() - 50);
                            } catch (AWTException e1) {
                                //e1.printStackTrace();
                            }
                        } else if (buttonLoad.isHover()) {
                            try {
                                Robot robot = new Robot();
                                Bounds boundsInScreen = Exit.localToScreen(Exit.getBoundsInLocal());
                                robot.mouseMove((int) boundsInScreen.getMaxX() - 100, (int) boundsInScreen.getMaxY() - 50);
                            } catch (AWTException e1) {
                                //e1.printStackTrace();
                            }
                        } else if (Exit.isHover()) {
                            try {
                                Robot robot = new Robot();
                                Bounds boundsInScreen = buttonNewGame.localToScreen(buttonNewGame.getBoundsInLocal());
                                robot.mouseMove((int) boundsInScreen.getMaxX() - 100, (int) boundsInScreen.getMaxY());
                            } catch (AWTException e1) {
                                //e1.printStackTrace();
                            }
                        } else {
                            try {
                                Robot robot = new Robot();
                                Bounds boundsInScreen = buttonNewGame.localToScreen(buttonNewGame.getBoundsInLocal());
                                robot.mouseMove((int) boundsInScreen.getMaxX() - 100, (int) boundsInScreen.getMaxY());
                            } catch (AWTException e1) {
                                //e1.printStackTrace();
                            }
                        }
                    } else if (e.getCode() == KeyCode.UP) {
                        if (buttonNewGame.isHover()) {
                            try {
                                Robot robot = new Robot();
                                Bounds boundsInScreen = Exit.localToScreen(Exit.getBoundsInLocal());
                                robot.mouseMove((int) boundsInScreen.getMaxX() - 100, (int) boundsInScreen.getMaxY() - 50);
                            } catch (AWTException e1) {
                                //e1.printStackTrace();
                            }
                        } else if (buttonLoad.isHover()) {
                            try {
                                Robot robot = new Robot();
                                Bounds boundsInScreen = buttonNewGame.localToScreen(buttonNewGame.getBoundsInLocal());
                                robot.mouseMove((int) boundsInScreen.getMaxX() - 100, (int) boundsInScreen.getMaxY());
                            } catch (AWTException e1) {
                                //e1.printStackTrace();
                            }
                        } else if (Exit.isHover()) {
                            try {
                                Robot robot = new Robot();
                                Bounds boundsInScreen = buttonLoad.localToScreen(buttonLoad.getBoundsInLocal());
                                robot.mouseMove((int) boundsInScreen.getMaxX() - 100, (int) boundsInScreen.getMaxY());
                            } catch (AWTException e1) {
                                //e1.printStackTrace();
                            }
                        } else {
                            try {
                                Robot robot = new Robot();
                                Bounds boundsInScreen = buttonNewGame.localToScreen(buttonNewGame.getBoundsInLocal());
                                robot.mouseMove((int) boundsInScreen.getMaxX() - 100, (int) boundsInScreen.getMaxY());
                            } catch (AWTException e1) {
                                //e1.printStackTrace();
                            }
                        }
                    }
                }
        );
    }


    @FXML
    private void newGameOnAction() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewFXML/GameFXML.fxml"));
        Controller.setSceneController(loader.getController());
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Controller.setSceneController(loader.getController());
        Main.window.setTitle("Maze Runner");
        Main.window.setScene(new Scene(root, Main.SCR_WIDTH, Main.SCR_HEIGHT));
        Main.window.show();
        Controller.getInstance().getPlayer().refreshInfo();
        CheckPoint checkPoint=new CheckPoint();
        checkPoint.saveNewCheckPoint(Controller.getInstance().getPlayer().getPosX(),Controller.getInstance().getPlayer().getPosY());

    }


    @FXML
    private void buttonLoadOnAction() {
        mainContainer.getChildren().add(loadPane);
        for (String string : Main.savedGamesPaths) {
            listOfPaths.getItems().add(string);
        }
        javafx.scene.paint.Color color = javafx.scene.paint.Color.web("#000000", 0.7);
        javafx.scene.paint.Color color1 = Color.web("#551a8b", 1);
        canvas.getGraphicsContext2D().setFill(color);
        canvas.getGraphicsContext2D().setStroke(color1);
        canvas.getGraphicsContext2D().fillRoundRect(canvas.getLayoutX(), canvas.getLayoutY(), canvas.getWidth()-1, canvas.getHeight()-2 , 50, 50);
        canvas.getGraphicsContext2D().strokeRoundRect(canvas.getLayoutX(), canvas.getLayoutY(), canvas.getWidth()-1 , canvas.getHeight()-2 , 50, 50);
        mainContainer.getChildren().remove(buttonLoad);
        mainContainer.getChildren().remove(Exit);
        mainContainer.getChildren().remove(buttonNewGame);
    }


    @FXML
    private void ExitOnAction() {
        Main.window.close();
    }

    public void muteOnAction() {
        if (BackGroundMusic.onOff) {
            BackGroundMusic.onOff = false;
            BackGroundMusic.stop();
            muteImage.setImage(new javafx.scene.image.Image("ViewFXML/fxmlRes/mute.png"));
        } else {
            BackGroundMusic.onOff = true;
            BackGroundMusic backGroundMusic = new BackGroundMusic("res/Audio/Background/Do Not Run.wav");
            muteImage.setImage(new Image("ViewFXML/fxmlRes/Sound.png"));
            backGroundMusic.run();
        }
    }

    @FXML
    public void loadFromListOnAction() {
        try {
            if (listOfPaths.getSelectionModel().getSelectedItem() != null) {
                if (listOfPaths.getItems().get(0) != null) {
                    String fileName;
                    fileName = listOfPaths.getSelectionModel().getSelectedItem();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewFXML/GameFXML.fxml"));
                    Controller.setSceneController(loader.getController());
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Controller.setSceneController(loader.getController());

                    Main.window.setTitle("Maze Runner");
                    Main.window.setScene(new Scene(root, Main.SCR_WIDTH, Main.SCR_HEIGHT));
                    Main.window.show();

                    SaverAndLoader saverAndLoader = new SaverAndLoader();
                    saverAndLoader.loadGame(fileName);
                }
            }
        } catch (NullPointerException | ArrayIndexOutOfBoundsException exception) {
        }
    }

    @FXML
    public void CancelOnActoin() {
        mainContainer.getChildren().remove(loadPane);
        mainContainer.getChildren().add(buttonLoad);
        mainContainer.getChildren().add(Exit);
        mainContainer.getChildren().add(buttonNewGame);
    }
}
