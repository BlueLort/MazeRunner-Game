package entities.tiles;

import controllers.Controller;
import entities.Entity;
import entities.EntityDecorator;
import javafx.scene.canvas.GraphicsContext;

public class TileTrapDecorator extends EntityDecorator {
    private final double UPDATE_RATE=2.5;//2.5 second
    private double swapTime;
    boolean swapDecor=false;

    private final double DAMAGE=1600;


    public TileTrapDecorator() {
    }
    public TileTrapDecorator(Entity decoratedCell) {
        super(decoratedCell);
        this.spritePlaceX=1;
        this.spritePlaceY=3;
    }

    @Override
    public void render(GraphicsContext canvas){
        decoratedCell.render(canvas);
        if(swapDecor) {
            canvas.drawImage(Trap0, this.spritePlaceX * 16, this.spritePlaceY * 16, 16, 16
                    , this.posX, this.posY, Entity.SPRITE_WIDTH, Entity.SPRITE_HEIGHT);
        }else {
            canvas.drawImage(Trap1, this.spritePlaceX * 16, this.spritePlaceY * 16, 16, 16
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
        if(swapDecor) {
            if (Controller.getInstance().getPlayer().getCollider().isColliding(decoratedCell.getCollider())) {
                Controller.getInstance().getPlayer().takeDamage(DAMAGE);
            }
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
