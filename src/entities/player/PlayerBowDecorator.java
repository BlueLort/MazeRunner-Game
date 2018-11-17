package entities.player;

import controllers.Controller;
import entities.Entity;
import entities.EntityDecorator;
import javafx.scene.canvas.GraphicsContext;

public class PlayerBowDecorator extends EntityDecorator {
    private double viewTime=0.4;

    public PlayerBowDecorator(Entity decoratedCell) {
        super(decoratedCell);
    }

    @Override
    public void render(GraphicsContext canvas){
            canvas.drawImage(Bow, this.spritePlaceX * 16, this.spritePlaceY * 16, 16, 16
                    , this.posX, this.posY, Entity.SPRITE_WIDTH, Entity.SPRITE_HEIGHT);
    }

    @Override
    public void update( double deltaTime) {
        viewTime-=deltaTime;
    }
    public void prepare(){
        viewTime=0.4;
        this.spritePlaceX=0;
      //  int length= Controller.getInstance().getGameObjects().size();
        Controller.getInstance().getGameObjects().add(new Arrow(this.posX,this.posY,this.spritePlaceY));
    }

    public double getViewTime() {
        return viewTime;
    }

    public void setViewTime(double viewTime) {
        this.viewTime = viewTime;
    }
}
