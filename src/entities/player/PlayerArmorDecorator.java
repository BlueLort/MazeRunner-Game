package entities.player;

import controllers.Controller;
import entities.Entity;
import entities.EntityDecorator;
import javafx.scene.canvas.GraphicsContext;

public class PlayerArmorDecorator extends EntityDecorator {


    public PlayerArmorDecorator() {
    }

    public PlayerArmorDecorator(Entity decoratedCell) {
        super(decoratedCell);
    }

    @Override
    public void render(GraphicsContext canvas){
        canvas.drawImage(PLAYER_ARMORED_SPRITES, decoratedCell.getSpritePlaceX() * 16, decoratedCell.getSpritePlaceY() * 16, 16, 16,
                decoratedCell.getPosX(), decoratedCell.getPosY(), Entity.SPRITE_WIDTH, Entity.SPRITE_HEIGHT);

    }
    @Override
    public void update( double deltaTime) {
   //NO NEED TO UPDATE HERE
    }

}
