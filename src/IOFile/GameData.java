package IOFile;

import entities.Cell;
import entities.player.Player;

import java.util.ArrayList;

public class GameData implements Cloneable {
   private ArrayList<Cell> gameObjects;
   private Player player;
   private int gameTime;
public GameData(){}
    public GameData(ArrayList<Cell> gameObjects, Player player,int gameTime) {
        this.gameObjects = gameObjects;
        this.player = player;
        this.gameTime=gameTime;
    }

    public ArrayList<Cell> getGameObjects() {
        return gameObjects;
    }

    public Player getPlayer() {
        return player;
    }

    public void setGameObjects(ArrayList<Cell> gameObjects) {
        this.gameObjects = gameObjects;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getGameTime() {
        return gameTime;
    }

    public void setGameTime(int gameTime) {
        this.gameTime = gameTime;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
