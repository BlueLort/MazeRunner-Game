package utility;


import controllers.Controller;
import entities.Cell;
import entities.monsters.Monster;
import entities.tiles.TileEnd_DoorDecorator;
import entities.tiles.TileH_DoorDecorator;
import entities.walls.DarkWall;
import entities.walls.DeepWall;
import entities.walls.Wall;
import entities.walls.WallTorchDecorator;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Utility {
    private static Utility ut = null;

    //SINGLETON
    public static Utility getInstance() {
        if (ut == null) {
            return new Utility();
        } else {
            return ut;
        }
    }

    public void playSound(String path) {
        SoundThread s = new SoundThread(path);
        s.run();
    }

    //ONLY FOR THE PLAYER !!!
    public boolean checkCollision(Collider coll) {
        for (Cell c : Controller.getInstance().getGameObjects()) {
            boolean colliding = c.getCollider().isColliding(coll);
            if (colliding) {
                if (c instanceof Wall || c instanceof DarkWall || c instanceof WallTorchDecorator
                        || c instanceof DeepWall) {
                    return true;
                } else if (c instanceof TileH_DoorDecorator) {
                    if (!((TileH_DoorDecorator) c).isOpened()) {
                        return !((TileH_DoorDecorator) c).openDoor();

                    }
                } else if (c instanceof TileEnd_DoorDecorator && Controller.getInstance().isWon() == false) {
                    //TODO WIN GAME LOGIC
                    Controller.getInstance().getA().stop();
                            Controller.getInstance().get_canvas_container().getChildren().add(Controller.getInstance().getWonWindow());
                           GraphicsContext g=Controller.getInstance().getWinningCanvas().getGraphicsContext2D();
                           Canvas canvas=Controller.getInstance().getWinningCanvas();
                            Color color = Color.web("#000000", 0.7);
                            Color color1 = Color.web("#551a8b", 1);
                            g.setFill(color);
                            g.fillRoundRect(canvas.getLayoutX(), canvas.getLayoutY(), canvas.getWidth()-1, canvas.getHeight()-1, 50, 50);
                            g.setStroke(color1);
                            g.strokeRoundRect(canvas.getLayoutX(), canvas.getLayoutY(), canvas.getWidth()-1, canvas.getHeight()-1, 50, 50);
                            Controller.getInstance().getGameState().pauseUnPause();
                            Controller.getInstance().get_main_layout().getChildren().remove(Controller.getInstance().getPauseMenuView());
                            Controller.getInstance().getFinalScore().setText(Controller.getInstance().getFinalScore().getText()+ ((int) Controller.getInstance().getPlayer().getScore()));

                    return true;
                }
            }

        }
        return false;
    }

    //MONSTERS
    public boolean checkCollisionMonster(Collider coll) {
        for (Cell c : Controller.getInstance().getGameObjects()) {
            if (c.getCollider().isColliding(coll)) {
                if(c instanceof Monster)continue;
                if (c instanceof Wall || c instanceof DarkWall || c instanceof WallTorchDecorator || c instanceof TileH_DoorDecorator
                        || c instanceof TileEnd_DoorDecorator ) {
                    return true;
                }
            }

        }
        return false;
    }
}
