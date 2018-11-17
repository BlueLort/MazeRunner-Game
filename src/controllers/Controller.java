package controllers;

import Pause.PauseMenuView;
import Pause.SaveGui;
import entities.Cell;
import entities.ENUMS.PlayerDirection;
import entities.Entity;
import entities.EntityDecorator;
import entities.monsters.EnemyArrow;
import entities.player.Player;
import entities.tiles.TileSkullDecorator;
import game.system.BackGroundMusic;
import game.system.GameState;
import game.system.PlayState;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import main.Main;
import memento.CareTaker;
import observer.*;
import playerState.Playing;
import utility.MapParser;
import utility.TextRenderer;

import java.io.IOException;
import java.net.URL;
import java.security.Guard;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static javafx.scene.text.Font.loadFont;

public class Controller implements Initializable {
    final double SHOW_RADIUS=230;
    final double CLIP_FACTOR=35;
    private Circle showZone = new Circle(SHOW_RADIUS, Color.TRANSPARENT);


    @FXML
    private AnchorPane _main_layout;
    @FXML
    private AnchorPane _canvas_container;
    @FXML
    private Canvas _canvas;
    @FXML
    private Label _score_text;
    @FXML
    private ImageView muteImage;
    @FXML
    private Label healthLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private Label ammoLabel;
    @FXML
    private Label keyLabel;
    @FXML
    private AnchorPane wonWindow;
    @FXML
    private Button wonMenuButton;
    @FXML
    private Canvas winningCanvas;
    @FXML
    private Label finalScore;
    @FXML
    private AnchorPane loosewindow;
    @FXML
    private Canvas lossingCanvas1;
    @FXML
    private Label finalScore1;

    private Canvas GUICanvas=new Canvas();
    private static Controller c;
    private double gameTime = 0;
    private int health;
    private int lives;
    private int ammo;
    private int keys;
    private int score;
    private GameState gameState = new PlayState();
    private PauseMenuView pauseMenuView=new PauseMenuView() ;
    private SaveGui saveGui;

    //SINGLETON
    public static Controller getInstance() {
        return c;
    }

    private boolean upPressed;
    private boolean downPressed;
    private boolean leftPressed;
    private boolean rightPressed;
    private boolean spacePressed;
    private boolean fPressed;
    private AnimationTimer a;


    double deltaTime;
    int level = 1;

    private ArrayList<Cell> gameObjects = MapParser.getInstance()
            .ConstructMap(MapParser.getInstance().read("res/levels/level_" + level + ".txt"));
    private ArrayList<Cell> toBeDeletedObjects = new ArrayList<>();
    private ArrayList<Cell> toBeAddedObjects = new ArrayList<>();
    private Player player ;
    private CareTaker checkPoint=new CareTaker();
    private boolean won=false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        GUICanvas.setHeight(_canvas.getHeight());
        GUICanvas.setWidth(_canvas.getWidth());

        player = MapParser.getInstance().getPlayer();//KNOWN PLACE ON MAP
         _canvas_container.getChildren().add(showZone);
         _canvas_container.getChildren().add(GUICanvas);
        showZone.setStrokeType(StrokeType.OUTSIDE);
        showZone.setStroke(Color.web("black", 1.0));
        showZone.setStrokeWidth(512);
        showZone.setEffect(new BoxBlur(100, 100, 2));
        showZone.setBlendMode(BlendMode.OVERLAY);

        //MAX SPEED FOR CAMERA
        //HIGH SPEED SO IT WONT MOVE SMOOTHLY FOR NOW
        Entity.setCameraXShiftMAX(10240);
        Entity.setCameraYShiftMAX(10240);
        Entity.setCameraXShiftMIN(-10240);
        Entity.setCameraYShiftMIN(-10240);
        _canvas_container.getChildren().remove(wonWindow);
        _canvas_container.getChildren().remove(loosewindow);
        player.addObserver(new HealthObserver(this));
        player.addObserver(new DeathObserver(this));
        player.addObserver(new AmmoObserver(this));
        player.addObserver(new ScoreObserver(this));
        player.addObserver(new KeyObserver(this));
        player.setPlayerState(new Playing());
        BackGroundMusic.stop();
        BackGroundMusic backGroundMusic = new BackGroundMusic("res/Audio/Background/Escape the Dungeon- Dubious Dungeon.wav");
        backGroundMusic.run();
        _canvas.widthProperty().bind(_canvas_container.widthProperty());
        _canvas.heightProperty().bind(_canvas_container.heightProperty());
        GraphicsContext g = _canvas.getGraphicsContext2D();
        _main_layout.setOnKeyReleased(e->{
            if (e.getCode()==KeyCode.ESCAPE) {
                Entity.setCameraX(0);
                Entity.setCameraY(0);
                gameState.pauseUnPause();
            }

        });
        muteImage.setOnMouseClicked(e-> muteOnAction());
        _canvas_container.setOnKeyPressed(ev -> {
            switch (ev.getCode()) {

                case F:
                    fPressed = true;
                    break;
                case SPACE:
                    spacePressed = true;
                    break;
                case DOWN:
                    downPressed = true;
                    break;
                case UP:
                    upPressed = true;
                    break;
                case RIGHT:
                    rightPressed = true;
                    break;
                case LEFT:
                    leftPressed = true;
                    break;
                case M:
                    muteOnAction();
                    break;

            }
            ev.consume();

        });
        _canvas_container.setOnKeyReleased(ev -> {
            if(ev.getCode()==KeyCode.ESCAPE)
                gameState.pauseUnPause();
            switch (ev.getCode()) {
                case F:
                    fPressed = false;
                    break;
                case SPACE:
                    spacePressed = false;
                    break;
                case DOWN:
                    downPressed = false;
                    break;
                case UP:
                    upPressed = false;
                    break;
                case RIGHT:
                    rightPressed = false;
                    break;
                case LEFT:
                    leftPressed = false;
                    break;

            }
            player.setSpritePlaceX(0);//steady shape
            ev.consume();
        });

        a = new AnimationTimer() {

            final long startNanoTime = System.nanoTime();
            double lastTime;

            @Override
            public void handle(long now) {
                double currentTime = (now - startNanoTime) / 1000000000.0;
                if(gameState instanceof PlayState)deltaTime = currentTime-lastTime;
                else deltaTime=0;
                gameState.updateTime(c);
                timeLabel.setText(Integer.toString((int) gameTime));
                //UPDATE OBJECTS
                updateObjects(deltaTime, gameObjects);
                showZone.setCenterX(player.getPosX());
                showZone.setCenterY(player.getPosY());
                //SEARCH FOR INPUTS
                pollInputs();
                //RENDER SCENE
                prepareCanvas(g);
                renderObjects(g, gameObjects);

                player.render(g);
                //UI
                gameInfo(GUICanvas.getGraphicsContext2D());
                //OBSERVER
                player.refreshInfo();

                lastTime = currentTime;
            }
        };
        a.start();
    }

    private void pollInputs() {
        if (leftPressed) {
            player.setDirection(PlayerDirection.LEFT);
        }
        if (rightPressed) {
            player.setDirection(PlayerDirection.RIGHT);
        }
        if (upPressed) {
            player.setDirection(PlayerDirection.BACK);
        }
        if (downPressed) {
            player.setDirection(PlayerDirection.FRONT);
        }
        if (spacePressed) {
            player.setDirection(PlayerDirection.FIRE_ARROW);
        }
        if (fPressed) {
            player.setDirection(PlayerDirection.ATTACK_SWORD);
        }
    }

    public static void setSceneController(Controller c) {
        Controller.c = c;
    }

    public void renderObjects(GraphicsContext g, ArrayList<Cell> gameObjects) {
        for (Cell c : gameObjects) {
            //TODO THIS COULD BE DONE BETTER USING ONE PARENT CLASS FOR BOTH
            if(c instanceof Entity){
               if( ((Entity) c).getPosX() < showZone.getCenterX() + SHOW_RADIUS+CLIP_FACTOR&&
                       ((Entity) c).getPosX() > showZone.getCenterX() - SHOW_RADIUS-CLIP_FACTOR&&
                       ((Entity) c).getPosY() < showZone.getCenterY() + SHOW_RADIUS +CLIP_FACTOR&&
                       ((Entity) c).getPosY() > showZone.getCenterY() - SHOW_RADIUS-CLIP_FACTOR
                       ){
                   c.render(g);
               }
            }else if(c instanceof EntityDecorator){
                    if (((EntityDecorator) c).getPosX() < showZone.getCenterX() + SHOW_RADIUS+CLIP_FACTOR&&
                            ((EntityDecorator) c).getPosX() > showZone.getCenterX() - SHOW_RADIUS-CLIP_FACTOR&&
                             ((EntityDecorator) c).getPosY() < showZone.getCenterY() + SHOW_RADIUS+CLIP_FACTOR&&
                            ((EntityDecorator) c).getPosY() > showZone.getCenterY() - SHOW_RADIUS-CLIP_FACTOR
                            ) {
                        c.render(g);
                    }

            }

        }
    }

    public void updateObjects(double deltaTime, ArrayList<Cell> gameObjects) {
        if(deltaTime==0)return;

        for (Cell c : gameObjects) {
            c.update(deltaTime);
        }
        for (Cell c : toBeDeletedObjects) {
            gameObjects.remove(c);
        }
        for (Cell c : toBeAddedObjects) {
            //PAINTER's ALGORITHM WITH ARROWS !!
            //TODO EXPERIMENT WITH THIS EFFECT IF ITS BAD DELETE IT
           if(!(c instanceof TileSkullDecorator || c instanceof EnemyArrow)) gameObjects.add(0, c);//KEEP THEM AT FIRST index
            else gameObjects.add( c);//KEEP SKULLS && Enemy arrows & Arrows at last no problems

        }
        TextRenderer.getInstance().update(deltaTime);
        toBeDeletedObjects = new ArrayList<>();
        toBeAddedObjects = new ArrayList<>();
        player.updateMove(deltaTime);
        //DYNAMIC PLAYER SCORE
        player.addScore(-deltaTime*10);
        //UPDATE CAMERA
        Entity.setCameraX(player.getPosX()-Main.SCR_WIDTH/2);
        Entity.setCameraY(player.getPosY()-Main.SCR_HEIGHT/2);

    }

    private void prepareCanvas(GraphicsContext g) {
        g.clearRect(0, 0, Main.SCR_WIDTH, Main.SCR_HEIGHT);
        g.setFill(Color.rgb(20, 12, 28));//CLEAR COLOR
        g.fillRect(0, 0, Main.SCR_WIDTH, Main.SCR_HEIGHT);
        g.setStroke(Color.rgb(255, 255, 255));//TEXT COLOR

    }

    public ArrayList<Cell> getToBeDeletedObjects() {
        return toBeDeletedObjects;
    }

    public ArrayList<Cell> getToBeAddedObjects() {
        return toBeAddedObjects;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Cell> getGameObjects() {
        return gameObjects;
    }

    public void setGameTime(double gameTime) {
        this.gameTime = gameTime;
    }

    public double getDeltaTime() {
        return deltaTime;
    }

    public double getGameTime() {
        return gameTime;
    }

    public void muteOnAction() {
        if (BackGroundMusic.onOff) {
            BackGroundMusic.onOff = false;
            BackGroundMusic.stop();
            muteImage.setImage(new Image("ViewFXML/fxmlRes/mute.png"));
        } else {
            BackGroundMusic.onOff = true;
            BackGroundMusic backGroundMusic = new BackGroundMusic("res/Audio/Background/Escape the Dungeon- Dubious Dungeon.wav");
            muteImage.setImage(new Image("ViewFXML/fxmlRes/sound.png"));
            backGroundMusic.run();
        }

    }

    public void gameInfo(GraphicsContext g) {
        g.clearRect(0, 0, Main.SCR_WIDTH, Main.SCR_HEIGHT);
        Image heart = new Image("file:res/ImgData/GUI0.png");
        Image ammo = new Image("ViewFXML/fxmlRes/arrow.png");
        Image key = new Image("ViewFXML/fxmlRes/key.png");
        _score_text.setText("Score: "+Integer.toString(score));
        healthLabel.setText(Integer.toString(health));
        keyLabel.setText(Integer.toString(keys));
        ammoLabel.setText(Integer.toString(this.ammo));
        g.drawImage(heart, 0, 16, 16, 16, 10, 10, 16 * 1.6, 16 * 1.6);
        g.drawImage(ammo, 10, 55, 16 * 1.6, 16 * 1.6);
        g.drawImage(key, 10, 90, 16 * 1.6, 16 * 1.6);
        for (int i = 0; i < lives; i++) {

           g.drawImage(new Image("file:res/ImgData/Paladin.png"), 0, 0, 16, 16,
                    Main.SCR_WIDTH-100 + i * (16 * 1.25 + 5), Main.SCR_HEIGHT-50, 16 * 1.25, 16 * 1.25);
        }
        TextRenderer.getInstance().render(g);


    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public int getLives() {
        return lives;
    }

    public Canvas get_canvas() {
        return _canvas;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public Label getHealthLabel() {
        return healthLabel;
    }

    public int getHealth() {
        return health;
    }

    public void setHealthLabel(Label healthLabel) {
        this.healthLabel = healthLabel;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public int getAmmo() {
        return ammo;
    }

    public int getKeys() {
        return keys;
    }

    public void setKeys(int keys) {
        this.keys = keys;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public AnchorPane get_main_layout() {
        return _main_layout;
    }

    public PauseMenuView getPauseMenuView() {
        return pauseMenuView;
    }

    public void setPauseMenuView(PauseMenuView pauseMenuView) {
        this.pauseMenuView = pauseMenuView;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameObjects(ArrayList<Cell> gameObjects) {
        this.gameObjects = gameObjects;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public SaveGui getSaveGui() {
        return saveGui;
    }

    public void setSaveGui(SaveGui saveGui) {
        this.saveGui = saveGui;
    }

    public CareTaker getCheckPoint() {
        return checkPoint;
    }

    public void setCheckPoint(CareTaker checkPoint) {
        this.checkPoint = checkPoint;
    }

    public boolean isWon() {
        return won;
    }

    public void setWon(boolean won) {
        this.won = won;
    }

    public AnchorPane get_canvas_container() {
        return _canvas_container;
    }

    public void set_canvas_container(AnchorPane _canvas_container) {
        this._canvas_container = _canvas_container;
    }

    public AnchorPane getWonWindow() {
        return wonWindow;
    }

    public void setWonWindow(AnchorPane wonWindow) {
        this.wonWindow = wonWindow;
    }
       @FXML
    public void wonMainMenu() {
        player.deleteAllObservers();
        a.stop();
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

    public Button getWonMenuButton() {
        return wonMenuButton;
    }

    public void setWonMenuButton(Button wonMenuButton) {
        this.wonMenuButton = wonMenuButton;
    }

    public Canvas getWinningCanvas() {
        return winningCanvas;
    }

    public void setWinningCanvas(Canvas winningCanvas) {
        this.winningCanvas = winningCanvas;
    }

    public Label getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(Label finalScore) {
        this.finalScore = finalScore;
    }

    public AnchorPane getLoosewindow() {
        return loosewindow;
    }

    public void setLoosewindow(AnchorPane loosewindow) {
        this.loosewindow = loosewindow;
    }

    public Canvas getLossingCanvas1() {
        return lossingCanvas1;
    }

    public void setLossingCanvas1(Canvas lossingCanvas1) {
        this.lossingCanvas1 = lossingCanvas1;
    }

    public Label getFinalScore1() {
        return finalScore1;
    }

    public void setFinalScore1(Label finalScore1) {
        this.finalScore1 = finalScore1;
    }

    public AnimationTimer getA() {
        return a;
    }

    public void setA(AnimationTimer a) {
        this.a = a;
    }
}

