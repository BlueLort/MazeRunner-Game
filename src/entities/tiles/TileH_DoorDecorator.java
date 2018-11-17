package entities.tiles;

import controllers.Controller;
import entities.Cell;
import entities.Entity;
import entities.EntityDecorator;
import entities.player.Player;
import javafx.scene.canvas.GraphicsContext;
import memento.CheckPoint;

public class TileH_DoorDecorator extends EntityDecorator {
    boolean Opened=false;
    public TileH_DoorDecorator(Entity decoratedCell) {
        super(decoratedCell);
        this.mapX=decoratedCell.getPosX();
        this.mapY=decoratedCell.getPosY();
        this.spritePlaceX=2;
        this.spritePlaceY=0;
    }
    private double mapX,mapY;
    @Override
    public void render(GraphicsContext canvas){
        decoratedCell.render(canvas);
        if(Opened) {
            canvas.drawImage(Door1, this.spritePlaceX * 16, this.spritePlaceY * 16, 16, 16
                    , this.posX, this.posY, Entity.SPRITE_WIDTH, Entity.SPRITE_HEIGHT);
        }else {
            canvas.drawImage(Door0, this.spritePlaceX * 16, this.spritePlaceY * 16, 16, 16
                    , this.posX, this.posY, Entity.SPRITE_WIDTH, Entity.SPRITE_HEIGHT);
        }
    }
    @Override
    public void update( double deltaTime) {
        cameraNColliderUpdate(deltaTime);
    }
    public boolean openDoor() {
        if (Controller.getInstance().getPlayer().getAvailableKeys() > 0) {
            Controller.getInstance().getPlayer().useKey();
             if(!isOpened()) {
                 Opened = true;
                 CheckPoint checkPoint=new CheckPoint();
                 //checkPoint.saveNewCheckPoint(this.mapX,this.mapY);
                 checkPoint.saveNewCheckPoint(this.posX,this.posY);
             }
            return true;
        }
        return false;
    }
    public void setOpened(boolean opened) {
        Opened = opened;
    }

    public boolean isOpened() {
        return Opened;
    }

    public double getMapX() {
        return mapX;
    }

    public void setMapX(double mapX) {
        this.mapX = mapX;
    }

    public double getMapY() {
        return mapY;
    }

    public void setMapY(double mapY) {
        this.mapY = mapY;
    }

    public TileH_DoorDecorator() {
    }
}
