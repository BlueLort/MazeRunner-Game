package entities.walls;

import entities.Cell;
import entities.Entity;
import entities.EntityDecorator;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import utility.Collider;

public class WallTorchDecorator extends EntityDecorator {

    private final double UPDATE_RATE=0.8;//0.8 second
    private double lightTime;
    boolean swapDecor=false;

    public WallTorchDecorator() {
    }


    public WallTorchDecorator(Entity decoratedCell) {
        super(decoratedCell);
        this.spritePlaceX=(int)(Math.random()*2);
        this.spritePlaceY=8;
    }

    @Override
    public void render(GraphicsContext canvas){
            decoratedCell.render(canvas);
            if(swapDecor) {
                canvas.drawImage(Decor0, this.spritePlaceX * 16, this.spritePlaceY * 16, 16, 16
                        , this.posX, this.posY, Entity.SPRITE_WIDTH, Entity.SPRITE_HEIGHT);
            }else {
                canvas.drawImage(Decor1, this.spritePlaceX * 16, this.spritePlaceY * 16, 16, 16
                        , this.posX, this.posY, Entity.SPRITE_WIDTH, Entity.SPRITE_HEIGHT);
            }
    }
    @Override
    public void update( double deltaTime) {
        cameraNColliderUpdate(deltaTime);
        lightTime-=deltaTime;
        if(lightTime<=0){
            lightTime=UPDATE_RATE;
            swapDecor=!swapDecor;
        }
    }

    public void setLightTime(double lightTime) {
        this.lightTime = lightTime;
    }

    public void setSwapDecor(boolean swapDecor) {
        this.swapDecor = swapDecor;
    }

    public double getUPDATE_RATE() {
        return UPDATE_RATE;
    }

    public double getLightTime() {
        return lightTime;
    }

    public boolean isSwapDecor() {
        return swapDecor;
    }
}
