package entities.tiles;

import entities.Cell;
import entities.Entity;
import entities.EntityDecorator;
import javafx.scene.canvas.GraphicsContext;

public class TileRockDecorator extends EntityDecorator {
    private final double UPDATE_RATE=0.8;//0.8 second
    private double swapTime;
    boolean swapDecor=false;

    public TileRockDecorator() {
    }

    public TileRockDecorator(Entity decoratedCell) {
        super(decoratedCell);
        this.spritePlaceX=4+(int)(Math.random()*2);
        this.spritePlaceY=0/*(int)(Math.random()*2)*/;
    }

    @Override
    public void render(GraphicsContext canvas){
        decoratedCell.render(canvas);
        if(swapDecor) {
            canvas.drawImage(Ground0, this.spritePlaceX * 16, this.spritePlaceY * 16, 16, 16
                    , this.posX, this.posY, Entity.SPRITE_WIDTH, Entity.SPRITE_HEIGHT);
        }else {
            canvas.drawImage(Ground1, this.spritePlaceX * 16, this.spritePlaceY * 16, 16, 16
                    , this.posX, this.posY, Entity.SPRITE_WIDTH, Entity.SPRITE_HEIGHT);
        }
    }
    @Override
    public void update( double deltaTime) {
        cameraNColliderUpdate(deltaTime);
        swapTime-=deltaTime;
        if(swapTime<=0){
            swapTime=UPDATE_RATE;
            swapDecor=!swapDecor;
        }
    }

    public double getUPDATE_RATE() {
        return UPDATE_RATE;
    }

    public double getSwapTime() {
        return swapTime;
    }

    public boolean isSwapDecor() {
        return swapDecor;
    }

    public void setSwapTime(double swapTime) {
        this.swapTime = swapTime;
    }

    public void setSwapDecor(boolean swapDecor) {
        this.swapDecor = swapDecor;
    }
}
